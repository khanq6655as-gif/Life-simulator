package com.example.game

/**
 * Skill metrics that define the player's potential, career capability,
 * and social influence.
 */
data class SkillStats(
    val academic: Int = 0,     // Higher education potential
    val professional: Int = 0, // Quality of work, specialized labor
    val social: Int = 0,       // Networking, team leader, marriage
    val creative: Int = 0      // Art, freelancing, startup innovation
) {
    fun plus(other: SkillStats): SkillStats {
        return SkillStats(
            academic = (this.academic + other.academic).coerceIn(0, 100),
            professional = (this.professional + other.professional).coerceIn(0, 100),
            social = (this.social + other.social).coerceIn(0, 100),
            creative = (this.creative + other.creative).coerceIn(0, 100)
        )
    }
}

/**
 * Real estate assets providing monthly passive residual rents.
 */
data class PropertyAsset(
    val id: String,
    val name: String,
    val buyPrice: Double,
    val rentIncome: Double,
    val description: String
)

/**
 * Completed life profile saved in the Hall of Fame.
 */
data class PastLife(
    val id: String,
    val name: String,
    val finalAge: Int,
    val netWorth: Double,
    val endingType: String,
    val carrierPath: String,
    val dateCompleted: Long
)

/**
 * Dynamic player stats container representing the active game state.
 */
data class PlayerStats(
    val name: String = "Siddharth",
    val gender: String = "Male",
    val age: Int = 18,
    val money: Double = 1200.0,
    val health: Int = 100,       // 0 - 100
    val reputation: Int = 10,    // 0 - 100
    val happiness: Int = 85,     // 0 - 100
    val skills: SkillStats = SkillStats(20, 15, 20, 15),
    val education: String = "High School Graduate",
    val job: String = "Unemployed",
    val yearsInJob: Int = 0,
    val house: String = "Parent's Tiny Room (Free)",
    val partner: String? = null,
    val hasChildren: Boolean = false,
    val sharesOwned: Map<String, Int> = emptyMap(), // Ticker -> Count (e.g., BTC, TECH, SLATE)
    val propertiesOwned: List<PropertyAsset> = emptyList(),
    val logHistory: List<String> = listOf("Began an exciting new life! Age 18, filled with absolute dreams."),
    val annualEvents: List<String> = listOf("Left school and set foot in the big world with nothing but grit."),
    val isDead: Boolean = false,
    val isRetired: Boolean = false,
    val endingType: String? = null
)

/**
 * Numeric change variables on a choice consequence.
 */
data class ConsequenceImpact(
    val moneyChange: Double = 0.0,
    val healthChange: Int = 0,
    val reputationChange: Int = 0,
    val happinessChange: Int = 0,
    val skillsImpact: SkillStats = SkillStats(),
    val educationSet: String? = null,
    val jobSet: String? = null,
    val houseSet: String? = null,
    val partnerSet: String? = null,
    val isPartnerRemove: Boolean = false,
    val forceEnding: String? = null,
    val narrative: String = ""
)

/**
 * Representation of one player choice option.
 */
data class ChoiceOption(
    val text: String,
    val description: String,
    val impact: ConsequenceImpact,
    val requiredStats: StatsPredicate? = null
)

/**
 * Interactive card event loaded by the decision engine.
 */
data class Decision(
    val id: String,
    val title: String,
    val scenario: String,
    val stage: DecisionStage,
    val options: List<ChoiceOption>,
    val minAge: Int = 18,
    val maxAge: Int = 100,
    val requiredStats: StatsPredicate? = null
)

enum class DecisionStage {
    EARLY_LIFE,  // 18 - 25
    MID_LIFE,    // 25 - 45
    LATE_LIFE,   // 45 - 75
    RANDOM,      // Triggers dynamically
    ASSET,       // Specialized choices
    CAREER,      // Job-specific struggles
    RELATIONSHIP // Dating life events
}

/**
 * Stat prerequisites required to reveal critical story cards.
 */
data class StatsPredicate(
    val minMoney: Double = 0.0,
    val minReputation: Int = 0,
    val minAcademic: Int = 0,
    val minCreative: Int = 0,
    val minProfessional: Int = 0,
    val minSocial: Int = 0,
    val requiredJob: String? = null,
    val requiresPartner: Boolean = false,
    val maxMoneyAllowed: Double = Double.MAX_VALUE
) {
    fun matches(stats: PlayerStats): Boolean {
        if (stats.money < minMoney || stats.money > maxMoneyAllowed) return false
        if (stats.reputation < minReputation) return false
        if (stats.skills.academic < minAcademic) return false
        if (stats.skills.creative < minCreative) return false
        if (stats.skills.professional < minProfessional) return false
        if (stats.skills.social < minSocial) return false
        if (requiredJob != null && stats.job != requiredJob) return false
        if (requiresPartner && stats.partner == null) return false
        return true
    }
}
