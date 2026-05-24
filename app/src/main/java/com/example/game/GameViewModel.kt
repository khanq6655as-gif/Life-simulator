package com.example.game

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID
import kotlin.random.Random

enum class GameState {
    MAIN_MENU,
    START_SETUP,
    ACTIVE_GAME,
    YEAR_REVIEW_DIALOG,
    GAME_OVER_SCREEN,
    SAVED_SLOTS_PANEL,
    HALL_OF_FAME
}

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPrefs = application.getSharedPreferences("LifeChoicePrefs", Context.MODE_PRIVATE)
    
    // Moshi serializer config
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
        
    private val statsAdapter = moshi.adapter(PlayerStats::class.java)
    private val pastLifeListAdapter = moshi.adapter<List<PastLife>>(
        Types.newParameterizedType(List::class.java, PastLife::class.java)
    )

    // Main States
    private val _gameState = MutableStateFlow(GameState.MAIN_MENU)
    val gameState: StateFlow<GameState> = _gameState.asStateFlow()

    private val _playerStats = MutableStateFlow(PlayerStats())
    val playerStats: StateFlow<PlayerStats> = _playerStats.asStateFlow()

    private val _currentCard = MutableStateFlow<Decision?>(null)
    val currentCard: StateFlow<Decision?> = _currentCard.asStateFlow()

    private val _hallOfFame = MutableStateFlow<List<PastLife>>(emptyList())
    val hallOfFame: StateFlow<List<PastLife>> = _hallOfFame.asStateFlow()

    // Interactive asset rates that fluctuate each year
    private val _stockPrices = MutableStateFlow(mapOf("BTC" to 340.0, "COMP" to 120.0, "MEME" to 4.5))
    val stockPrices: StateFlow<Map<String, Double>> = _stockPrices.asStateFlow()

    // Last choice's precise consequence narrative for the recap dialog
    private val _latestImpactNarration = MutableStateFlow<ConsequenceImpact?>(null)
    val latestImpactNarration: StateFlow<ConsequenceImpact?> = _latestImpactNarration.asStateFlow()

    private var activeSlot = "slot_1"
    private val playedCardIds = mutableSetOf<String>()
    private var subTurnCounter = 0 // 4 decisions per year

    init {
        loadHallOfFame()
    }

    // ────────────────────────────────────────────────────────────────────────
    // CORE NARRATIVE WORKFLOW
    // ────────────────────────────────────────────────────────────────────────

    fun enterSetup() {
        _gameState.value = GameState.START_SETUP
    }

    fun startNewLife(name: String, gender: String) {
        // Reset state parameters
        playedCardIds.clear()
        subTurnCounter = 0
        _gameState.value = GameState.ACTIVE_GAME

        val initialStats = PlayerStats(
            name = name.ifBlank { "Siddharth" },
            gender = gender,
            age = 18,
            money = 1200.0,
            health = 95,
            reputation = 15,
            happiness = 85,
            skills = SkillStats(academic = 15, professional = 15, social = 20, creative = 15),
            education = "High School Graduate",
            job = "Unemployed",
            house = "Parent's Tiny Room (Free)",
            sharesOwned = mapOf("BTC" to 0, "COMP" to 0, "MEME" to 0)
        )
        _playerStats.value = initialStats
        _stockPrices.value = mapOf("BTC" to 340.0, "COMP" to 120.0, "MEME" to 4.5)
        
        // Pick first mandatory branch
        _currentCard.value = DecisionDatabase.earlyLifeEvents.first()
        triggerVibration()
        saveActiveGame()
    }

    fun selectSaveSlotMenu() {
        _gameState.value = GameState.SAVED_SLOTS_PANEL
    }

    fun enterMainMenu() {
        _gameState.value = GameState.MAIN_MENU
    }

    fun viewHallOfFame() {
        loadHallOfFame()
        _gameState.value = GameState.HALL_OF_FAME
    }

    fun chooseOption(option: ChoiceOption) {
        val current = _playerStats.value
        if (current.isDead) return

        triggerVibration()
        
        // Extract basic core impacts
        val imp = option.impact
        var moneyVal = imp.moneyChange
        var healthVal = imp.healthChange
        var repVal = imp.reputationChange
        var hapVal = imp.happinessChange
        
        // Capture a record of the precise card resolved
        _currentCard.value?.let { playedCardIds.add(it.id) }

        // Compile situational / randomized micro-outcomes (e.g. gambling, snatcher combats)
        var customLog = imp.narrative
        
        if (_currentCard.value?.id == "early_crypto_bubble" && option.text.contains("Speculate")) {
            // Speculative crypto roll (50/50 outcomes)
            val luck = Random.nextBoolean()
            if (luck) {
                moneyVal = 600.0 // Got lucky! Gained +600!
                hapVal += 20
                customLog = "Jackpot! HustleElonCoin rocketed right after you bought. You cashed out solid profits of 600 cash!"
            } else {
                moneyVal = -300.0 // Slit! Lost it all
                hapVal -= 25
                customLog = "Rug pulled! The developers deleted the coin group. Your speculation dropped to absolute zero."
            }
        } else if (_currentCard.value?.id == "random_wallet_snatch" && option.text.contains("Fight")) {
            val successOdds = current.skills.social + current.skills.creative + 15
            val roll = Random.nextInt(100)
            if (roll < successOdds) {
                moneyVal = 100.0
                repVal += 20
                hapVal += 15
                customLog = "You countered their slice, grabbed the stick, and locked their elbow. The snatcher fled leaving 100 cash on the tarmac! Incredible combat win!"
            } else {
                moneyVal = -200.0
                healthVal = -40
                hapVal -= 25
                customLog = "You missed your kick and got sliced on the wrist. You spent all night in a public emergency room bleeding. Lost 200 cash and health dropped severely!"
            }
        }

        // Apply new values to profile state
        val updatedSkills = current.skills.plus(imp.skillsImpact)
        val updatedHistory = current.logHistory + "Age ${current.age}: $customLog"
        
        val intermediateStats = current.copy(
            money = (current.money + moneyVal).coerceAtLeast(0.0),
            health = (current.health + healthVal).coerceIn(0, 100),
            reputation = (current.reputation + repVal).coerceIn(0, 100),
            happiness = (current.happiness + hapVal).coerceIn(0, 100),
            skills = updatedSkills,
            job = imp.jobSet ?: current.job,
            house = imp.houseSet ?: current.house,
            education = imp.educationSet ?: current.education,
            partner = if (imp.isPartnerRemove) null else (imp.partnerSet ?: current.partner),
            logHistory = updatedHistory
        )

        _playerStats.value = intermediateStats

        // Log this choice consequence
        val descriptiveImpact = ConsequenceImpact(
            moneyChange = moneyVal,
            healthChange = healthVal,
            reputationChange = repVal,
            happinessChange = hapVal,
            skillsImpact = imp.skillsImpact,
            narrative = customLog,
            forceEnding = imp.forceEnding
        )
        _latestImpactNarration.value = descriptiveImpact

        // Check immediate fatal threshold
        if (intermediateStats.health <= 0) {
            handleEndingReached("Tragic Medical Collapse", "Your health bottomed out. Your systems failed.")
            return
        }

        // If choice carries an explicit structural game ending
        if (imp.forceEnding != null) {
            handleEndingReached(imp.forceEnding, "Scripted choices concluded in this destiny.")
            return
        }

        // Check Turn Incrementation
        subTurnCounter++
        if (subTurnCounter >= 4) {
            // A full life cycle year passes!
            subTurnCounter = 0
            advanceOneYear()
        } else {
            // Find next card
            pickNextDecisionCard()
            saveActiveGame()
        }
    }

    fun dismissYearReview() {
        _gameState.value = GameState.ACTIVE_GAME
        pickNextDecisionCard()
        saveActiveGame()
    }

    // ────────────────────────────────────────────────────────────────────────
    // ANNUAL ECONOMIC ADVANCEMENT & SIMULATION
    // ────────────────────────────────────────────────────────────────────────

    private fun advanceOneYear() {
        val current = _playerStats.value
        val nextAge = current.age + 1

        if (nextAge >= 80) {
            // Natural beautiful retirement conclusion!
            val finalEndName = if (current.money >= 10000.0) {
                "Titan of Modern Conglomerate"
            } else if (current.money >= 3000.0) {
                "Comfortable Middle-class Legend"
            } else if (current.happiness >= 80) {
                "Zen Philanthropist of Peace"
            } else {
                "Humble Retired Citizen"
            }
            handleEndingReached(finalEndName, "You reached the peaceful grand age of 80 and completed your journey.")
            return
        }

        // Compile annual financial logs
        val yearRecapLogs = mutableListOf<String>()
        yearRecapLogs.add("--- Year Summary: Turning Age $nextAge ---")

        // 1. Passive Job Salary
        var jobWage = 0.0
        when (current.job) {
            "Retail Assistant" -> jobWage = 550.0
            "Junior Programmer" -> jobWage = 1300.0
            "Rookie Freelancer" -> jobWage = 900.0
            "Freelance App Dev" -> jobWage = 1750.0
            "Senior Software Engineer" -> jobWage = 3600.0
            "Indie Game Developer" -> jobWage = 1100.0
            "Hustling Consultant" -> jobWage = 2200.0
            "VP of Product / Manager" -> jobWage = 5200.0
            "Enterprise Chief Executive (CEO)" -> jobWage = 13500.0
            "Wealthy Jetsetter" -> jobWage = 500.0 // Minor dividends
        }
        
        // Add random bonus if professional skill is very high!
        if (current.job != "Unemployed" && current.skills.professional > 60) {
            val bonus = 200.0 + Random.nextInt(300)
            jobWage += bonus
            yearRecapLogs.add("Professional Expertise Bonus: Earned +$bonus cash!")
        }

        if (jobWage > 0.0) {
            yearRecapLogs.add("Earned Salary from '${current.job}': +$jobWage cash.")
        }

        // 2. Real Estate Rental Passive Income
        var rentalPassive = 0.0
        current.propertiesOwned.forEach { prop ->
            rentalPassive += prop.rentIncome
        }
        if (rentalPassive > 0.0) {
            yearRecapLogs.add("Received Passive Rental income: +$rentalPassive cash.")
        }

        // 3. Housing costs & core living expenses
        var maintenanceCost = 80.0 // base hunger/electricity
        when (current.house) {
            "Luxury Smart Flat (Rented)" -> maintenanceCost = 350.0
            "Suburban Condo (Owned)" -> maintenanceCost = 120.0 // cheap maintenance
            "Luxury City Penthouse (Owned)" -> maintenanceCost = 250.0
            "Parent's Tiny Room (Free)" -> maintenanceCost = 30.0
        }
        
        // Expense scaling with marriage/children
        if (current.partner != null) {
            maintenanceCost += 100.0
            yearRecapLogs.add("Family Support Maintenance: -$100.0 cost.")
        }

        yearRecapLogs.add("Paid bills & food standard expenses: -$maintenanceCost cash.")

        // Compute absolute net capital delta
        val financialDelta = (jobWage + rentalPassive) - maintenanceCost
        val newMoney = (current.money + financialDelta).coerceAtLeast(0.0)

        // 4. Stock Market Price Volatility
        val currentPrices = _stockPrices.value.toMutableMap()
        // BTC: Fluctuate wild!
        val btcPct = Random.nextDouble(-0.35, 0.65)
        currentPrices["BTC"] = (currentPrices["BTC"]!! * (1.0 + btcPct)).coerceIn(80.0, 4500.0)
        
        // COMP Stocks: Fluctuate moderately
        val compPct = Random.nextDouble(-0.15, 0.35)
        currentPrices["COMP"] = (currentPrices["COMP"]!! * (1.0 + compPct)).coerceIn(30.0, 950.0)

        // MEME Tokens: Extreme noise!
        val memePct = Random.nextDouble(-0.60, 1.40)
        currentPrices["MEME"] = (currentPrices["MEME"]!! * (1.0 + memePct)).coerceIn(0.1, 80.0)
        
        _stockPrices.value = currentPrices
        yearRecapLogs.add("Financial Markets closed. Stocks/Crypto experienced annual fluctuations.")

        // Natural Age-based vitality shifts
        var healthLoss = 0
        if (nextAge > 50) healthLoss += 3
        if (nextAge > 65) healthLoss += 4
        // Hard work stress
        if (current.job == "Enterprise Chief Executive (CEO)" || current.job == "Startup Founder") healthLoss += 2
        
        val finalHealth = (current.health - healthLoss).coerceIn(0, 100)

        // Compile finalized year state
        val compiledAnnualHighlight = "Age $nextAge: Balance: ${"%.1f".format(newMoney)}. Job: ${current.job}. Housing: ${current.house}."
        
        val nextStats = current.copy(
            age = nextAge,
            money = newMoney,
            health = finalHealth,
            yearsInJob = if (current.job != "Unemployed") current.yearsInJob + 1 else 0,
            annualEvents = current.annualEvents + compiledAnnualHighlight,
            logHistory = current.logHistory + "--- Reached Age $nextAge! ---"
        )
        
        _playerStats.value = nextStats

        // Check if stats are in fatal ranges
        if (finalHealth <= 0) {
            handleEndingReached("Passed Away from Old-Age Exhaustion", "Your body could not survive the continuous strain of the hustle at age $nextAge.")
            return
        }

        if (newMoney <= 5.0 && current.job == "Unemployed") {
            // Impoverished collapse
            _playerStats.value = nextStats.copy(health = (finalHealth - 25).coerceAtLeast(0))
            yearRecapLogs.add("🔴 WARNING: Zero money and no income support! Suffering malnutrition (-25% Health).")
            if (nextStats.health - 25 <= 0) {
                handleEndingReached("Bankruptcy & Poverty Collapse", "Penniless, flat broke in the streets, you succumbed to harsh weather.")
                return
            }
        }

        // Show annual recap dialog!
        _gameState.value = GameState.YEAR_REVIEW_DIALOG
    }

    private fun pickNextDecisionCard() {
        val current = _playerStats.value
        val stage = when {
            current.age < 26 -> DecisionStage.EARLY_LIFE
            current.age < 46 -> DecisionStage.MID_LIFE
            else -> DecisionStage.LATE_LIFE
        }

        // Pull corresponding list from Database matching conditions
        val basePool = when (stage) {
            DecisionStage.EARLY_LIFE -> DecisionDatabase.earlyLifeEvents
            DecisionStage.MID_LIFE -> DecisionDatabase.midLifeEvents
            else -> DecisionDatabase.lateLifeEvents
        }

        // Add random spice chances (30% chance of inserting a random/spicy event!)
        val pullRandomCard = Random.nextInt(100) < 30
        val finalPool = if (pullRandomCard) {
            DecisionDatabase.randomEventsList + basePool
        } else {
            basePool
        }

        // Filter: not already played, age fits, required predicate matches
        val candidates = finalPool.filter { card ->
            !playedCardIds.contains(card.id) && 
            current.age >= card.minAge && 
            current.age <= card.maxAge &&
            (card.requiredStats == null || card.requiredStats.matches(current))
        }

        if (candidates.isNotEmpty()) {
            _currentCard.value = candidates.random()
        } else {
            // Soft fallback if all scenarios exhausted: Generate a clean procedural card
            _currentCard.value = generateProceduralMacroCard(current)
        }
    }

    private fun generateProceduralMacroCard(stats: PlayerStats): Decision {
        val age = stats.age
        return Decision(
            id = "procedural_hustle_${UUID.randomUUID()}",
            title = "A Day of Quiet Focus",
            scenario = "You are currently Age $age. Life is pacing steadily. What is your central focus index this quarter?",
            stage = DecisionStage.RANDOM,
            options = listOf(
                ChoiceOption(
                    text = "Double down on professional tasks",
                    description = "+15 professional skill, +100 cash. Grind hard.",
                    impact = ConsequenceImpact(
                        moneyChange = 100.0,
                        skillsImpact = SkillStats(professional = 15),
                        happinessChange = -5,
                        narrative = "You locked your door, put on classical music, and refactored core backend tasks. Highly productive hours!"
                    )
                ),
                ChoiceOption(
                    text = "Spend quality time with friends",
                    description = "-80 cash, +20 happiness, +15 social skill.",
                    impact = ConsequenceImpact(
                        moneyChange = -80.0,
                        happinessChange = 20,
                        skillsImpact = SkillStats(social = 15),
                        narrative = "You hosted an outdoor barbecue with friends. Drank iced sodas, discussed goals, and let off heavy corporate pressure."
                    )
                ),
                ChoiceOption(
                    text = "Go on long scenic cycling journeys",
                    description = "-20 cash, +15 health, +10 happiness.",
                    impact = ConsequenceImpact(
                        moneyChange = -20.0,
                        healthChange = 15,
                        happinessChange = 10,
                        narrative = "Swung your legs over a bicycle and cycled 20 kilometers into rural paths. Fresh air cleaned your lungs perfectly!"
                    )
                )
            )
        )
    }

    // ────────────────────────────────────────────────────────────────────────
    // PROACTIVE ACTIVITIES / DASHBOARD DESKS
    // ────────────────────────────────────────────────────────────────────────

    fun buyStock(ticker: String, quantity: Int) {
        val current = _playerStats.value
        val price = _stockPrices.value[ticker] ?: return
        val totalCost = price * quantity
        if (current.money >= totalCost) {
            triggerVibration()
            val currentOwned = current.sharesOwned.toMutableMap()
            val prevCount = currentOwned[ticker] ?: 0
            currentOwned[ticker] = prevCount + quantity

            _playerStats.value = current.copy(
                money = current.money - totalCost,
                sharesOwned = currentOwned,
                logHistory = current.logHistory + "Purchased $quantity units of $ticker at price ${"%.1f".format(price)}"
            )
            saveActiveGame()
        }
    }

    fun sellStock(ticker: String, quantity: Int) {
        val current = _playerStats.value
        val price = _stockPrices.value[ticker] ?: return
        val ownedCount = current.sharesOwned[ticker] ?: 0
        if (ownedCount >= quantity) {
            triggerVibration()
            val currentOwned = current.sharesOwned.toMutableMap()
            currentOwned[ticker] = ownedCount - quantity

            val proceeds = price * quantity
            _playerStats.value = current.copy(
                money = current.money + proceeds,
                sharesOwned = currentOwned,
                logHistory = current.logHistory + "Sold $quantity units of $ticker at price ${"%.1f".format(price)}"
            )
            saveActiveGame()
        }
    }

    fun buyProperty(prop: PropertyAsset) {
        val current = _playerStats.value
        if (current.money >= prop.buyPrice) {
            triggerVibration()
            _playerStats.value = current.copy(
                money = current.money - prop.buyPrice,
                propertiesOwned = current.propertiesOwned + prop,
                logHistory = current.logHistory + "Acquired real-estate asset: '${prop.name}' for ${"%.0f".format(prop.buyPrice)}. Passive rent: +${prop.rentIncome}/yr."
            )
            saveActiveGame()
        }
    }

    fun sellProperty(prop: PropertyAsset) {
        val current = _playerStats.value
        if (current.propertiesOwned.contains(prop)) {
            triggerVibration()
            val sellPrice = prop.buyPrice * 1.15 // Slight capital appreciation on sell!
            _playerStats.value = current.copy(
                money = current.money + sellPrice,
                propertiesOwned = current.propertiesOwned.filter { it.id != prop.id },
                logHistory = current.logHistory + "Liquidated real estate '${prop.name}' for ${"%.0f".format(sellPrice)} Capital Liquidity."
            )
            saveActiveGame()
        }
    }

    fun applyForJob(jobTitle: String, payRate: Double, minProfessional: Int, minAcademic: Int, minSocial: Int) {
        val current = _playerStats.value
        if (current.skills.professional >= minProfessional &&
            current.skills.academic >= minAcademic &&
            current.skills.social >= minSocial
        ) {
            triggerVibration()
            _playerStats.value = current.copy(
                job = jobTitle,
                yearsInJob = 0,
                reputation = (current.reputation + 10).coerceAtMost(100),
                logHistory = current.logHistory + "Hired successfully as: [ $jobTitle ]!"
            )
            saveActiveGame()
        }
    }

    fun enrollCourse(courseName: String, cost: Double, plusAcademic: Int, plusProfessional: Int) {
        val current = _playerStats.value
        if (current.money >= cost) {
            triggerVibration()
            val updatedSkills = current.skills.copy(
                academic = (current.skills.academic + plusAcademic).coerceAtMost(100),
                professional = (current.skills.professional + plusProfessional).coerceAtMost(100)
            )
            _playerStats.value = current.copy(
                money = current.money - cost,
                skills = updatedSkills,
                education = courseName,
                logHistory = current.logHistory + "Completed studies course: '$courseName'."
            )
            saveActiveGame()
        }
    }

    fun treatHealthAction(actionName: String, cost: Double, healthHeal: Int, happinessHeal: Int) {
        val current = _playerStats.value
        if (current.money >= cost) {
            triggerVibration()
            _playerStats.value = current.copy(
                money = current.money - cost,
                health = (current.health + healthHeal).coerceAtMost(100),
                happiness = (current.happiness + happinessHeal).coerceAtMost(100),
                logHistory = current.logHistory + "Wellness Care: $actionName."
            )
            saveActiveGame()
        }
    }

    fun giftPartnerAction(giftCost: Double, happinessImprove: Int) {
        val current = _playerStats.value
        if (current.money >= giftCost && current.partner != null) {
            triggerVibration()
            _playerStats.value = current.copy(
                money = current.money - giftCost,
                happiness = (current.happiness + happinessImprove).coerceAtMost(100),
                reputation = (current.reputation + 3).coerceAtMost(100),
                logHistory = current.logHistory + "Sent gift to partner Sam: Spent ${"%.0f".format(giftCost)} cash."
            )
            saveActiveGame()
        }
    }

    // ────────────────────────────────────────────────────────────────────────
    // SAVE / LOAD ENGINE
    // ────────────────────────────────────────────────────────────────────────

    fun selectSlot(slotKey: String) {
        activeSlot = slotKey
        // Attempt load
        val rawJson = sharedPrefs.getString(activeSlot, null)
        if (rawJson != null) {
            try {
                val stats = statsAdapter.fromJson(rawJson)
                if (stats != null) {
                    _playerStats.value = stats
                    _gameState.value = GameState.ACTIVE_GAME
                    pickNextDecisionCard()
                    return
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        // If empty slot, move to setup
        _gameState.value = GameState.START_SETUP
    }

    private fun saveActiveGame() {
        try {
            val rawJson = statsAdapter.toJson(_playerStats.value)
            sharedPrefs.edit().putString(activeSlot, rawJson).apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun deleteSaveSlot(slotKey: String) {
        sharedPrefs.edit().remove(slotKey).apply()
        triggerVibration()
    }

    private fun handleEndingReached(endingName: String, reason: String) {
        val current = _playerStats.value
        val finalStats = current.copy(
            isDead = true,
            endingType = endingName,
            logHistory = current.logHistory + "--- DESTINY DRAWN: $endingName ---"
        )
        _playerStats.value = finalStats
        _gameState.value = GameState.GAME_OVER_SCREEN
        triggerVibration()

        // Clear slot save since life completed
        sharedPrefs.edit().remove(activeSlot).apply()

        // Save PastLife Record in central leaderboard!
        val netWorth = current.money + (current.propertiesOwned.sumOf { it.buyPrice }) + 
                (current.sharesOwned.entries.sumOf { (ticker, count) -> count * (_stockPrices.value[ticker] ?: 0.0) })

        val newPastLife = PastLife(
            id = UUID.randomUUID().toString(),
            name = current.name,
            finalAge = current.age,
            netWorth = netWorth,
            endingType = endingName,
            carrierPath = current.job,
            dateCompleted = System.currentTimeMillis()
        )

        val historyList = getHallOfFameListMutable()
        historyList.add(newPastLife)
        saveHallOfFameList(historyList)
    }

    private fun loadHallOfFame() {
        val raw = sharedPrefs.getString("hall_of_fame_list", null)
        if (raw != null) {
            try {
                val list = pastLifeListAdapter.fromJson(raw)
                if (list != null) {
                    _hallOfFame.value = list.sortedByDescending { it.netWorth }
                    return
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        _hallOfFame.value = emptyList()
    }

    private fun getHallOfFameListMutable(): MutableList<PastLife> {
        val raw = sharedPrefs.getString("hall_of_fame_list", null)
        if (raw != null) {
            try {
                val list = pastLifeListAdapter.fromJson(raw)
                if (list != null) return list.toMutableList()
            } catch (e: Exception) {
                // Ignore
            }
        }
        return mutableListOf()
    }

    private fun saveHallOfFameList(list: List<PastLife>) {
        try {
            val raw = pastLifeListAdapter.toJson(list)
            sharedPrefs.edit().putString("hall_of_fame_list", raw).apply()
            _hallOfFame.value = list.sortedByDescending { it.netWorth }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun triggerVibration() {
        // Safe check or minor feedback logic. Shunt for Android compatibility boundaries.
    }
}
