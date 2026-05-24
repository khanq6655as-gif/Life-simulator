package com.example.game.ui

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.game.*
import com.example.ui.theme.*

@Composable
fun MainMenuScreen(
    hallOfFameCount: Int,
    onStartNewSetup: () -> Unit,
    onOpenSaveSlots: () -> Unit,
    onOpenLeaderboard: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SlateDarkBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            
            // Brand Logo / Icon
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(com.example.R.drawable.img_app_icon)
                    .size(400)
                    .crossfade(true)
                    .build(),
                contentDescription = "Life Choice Simulator Logo",
                modifier = Modifier
                    .size(240.dp)
                    .shadow(elevation = 6.dp, shape = CircleShape)
                    .clip(CircleShape)
                    .border(2.5.dp, RetroGold, CircleShape)
            )
            Spacer(modifier = Modifier.height(24.dp))

            // Game Title
            Text(
                text = "Life Choice",
                fontSize = 32.sp,
                fontWeight = FontWeight.Black,
                color = TextLight,
                fontFamily = FontFamily.SansSerif,
                letterSpacing = (-0.5).sp
            )
            Text(
                text = "SIMULATOR",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = RetroGold,
                fontFamily = FontFamily.Monospace,
                letterSpacing = 4.sp,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Text(
                text = "A modern interactive strategy game. Start from age 18, control your career, invest cash, manage burnout and build dynamic legacy.",
                fontSize = 13.sp,
                color = TextMuted,
                textAlign = TextAlign.Center,
                lineHeight = 18.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Menu Options Column
            Column(
                verticalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                // Play / Slots Button
                Button(
                    onClick = onOpenSaveSlots,
                    colors = ButtonDefaults.buttonColors(containerColor = RetroGold, contentColor = Color.White),
                    shape = RoundedCornerShape(14.dp),
                    contentPadding = PaddingValues(16.dp),
                    modifier = Modifier.fillMaxWidth().testTag("enter_slots_btn")
                ) {
                    Text(text = "🎮 START GAME / SAVES", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }

                // Hall of fame leaderboards
                Button(
                    onClick = onOpenLeaderboard,
                    colors = ButtonDefaults.buttonColors(containerColor = SlateCardSelected, contentColor = TextLight),
                    shape = RoundedCornerShape(14.dp),
                    contentPadding = PaddingValues(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color(0xFFCAC4D0), RoundedCornerShape(14.dp))
                        .testTag("leaderboards_btn")
                ) {
                    Text(
                        text = "🏆 HALL OF FAME (${hallOfFameCount})",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = RetroGold
                    )
                }
            }

            Spacer(modifier = Modifier.height(100.dp))
            Text(
                text = "v1.0.0 • Verified Google Play Release",
                fontSize = 10.sp,
                color = TextMuted.copy(alpha = 0.6f),
                fontFamily = FontFamily.Monospace
            )
        }
    }
}

@Composable
fun SetupScreen(
    onConfirmSetup: (name: String, gender: String) -> Unit,
    onBack: () -> Unit
) {
    var rawName by remember { mutableStateOf("") }
    var selectedGender by remember { mutableStateOf("Male") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SlateDarkBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier.background(SlateCard, CircleShape)
                ) {
                    Text(text = "←", color = TextLight, fontSize = 18.sp)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Character Creation",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextLight
                )
            }
            Spacer(modifier = Modifier.height(40.dp))

            // Profile Card Preview
            Card(
                colors = CardDefaults.cardColors(containerColor = SlateCard),
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, RetroGold.copy(alpha = 0.15f), RoundedCornerShape(18.dp))
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(70.dp)
                            .background(Color(0xFF2E3D5E), CircleShape)
                    ) {
                        Text(
                            text = if (selectedGender == "Male") "🧑" else if (selectedGender == "Female") "👩" else "🧑‍🚀",
                            fontSize = 32.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = rawName.ifBlank { "Choose Name" },
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black,
                        color = RetroGold
                    )
                    Text(
                        text = "Status: Age 18 • $selectedGender",
                        fontSize = 12.sp,
                        color = TextMuted,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            // Form inputs
            Card(
                colors = CardDefaults.cardColors(containerColor = SlateCard),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "ENTER NAME:",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextMuted,
                        fontFamily = FontFamily.Monospace,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    OutlinedTextField(
                        value = rawName,
                        onValueChange = { if (it.length <= 15) rawName = it },
                        placeholder = { Text(text = "e.g., Siddharth", color = TextMuted) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = TextLight,
                            unfocusedTextColor = TextLight,
                            focusedBorderColor = RetroGold,
                            unfocusedBorderColor = Color(0xFFCAC4D0),
                            focusedContainerColor = SlateCardSelected,
                            unfocusedContainerColor = SlateCardSelected
                        ),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("name_input")
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "SELECT GENDER IDENTIFICATION:",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextMuted,
                        fontFamily = FontFamily.Monospace,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        listOf("Male", "Female", "Non-Binary").forEach { gender ->
                            val isSelected = selectedGender == gender
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(if (isSelected) RetroGold else SlateCardSelected)
                                    .border(
                                        1.dp,
                                        if (isSelected) Color.Transparent else Color(0xFFCAC4D0),
                                        RoundedCornerShape(10.dp)
                                    )
                                    .clickable { selectedGender = gender }
                            ) {
                                Text(
                                    text = gender,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) Color.White else TextLight
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Action Launch button
            Button(
                onClick = { onConfirmSetup(rawName, selectedGender) },
                colors = ButtonDefaults.buttonColors(containerColor = HustleGreen, contentColor = Color.White),
                shape = RoundedCornerShape(14.dp),
                contentPadding = PaddingValues(15.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("commence_btn")
            ) {
                Text(text = "COMMENCE LIFE 🚀", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun ActiveGameScreen(
    stats: PlayerStats,
    currentCard: Decision?,
    stockPrices: Map<String, Double>,
    onChoiceProcessed: (ChoiceOption) -> Unit,
    onBackToMenu: () -> Unit,
    viewModel: GameViewModel
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabNames = listOf("📓 DECK", "💼 CAREERS", "💸 STOCK/RE", "🍷 WELLNESS")

    Scaffold(
        containerColor = SlateDarkBackground,
        topBar = {
            UnifiedHeaderSection(stats = stats, onBackToMenu = onBackToMenu)
        },
        bottomBar = {
            NavigationBar(
                containerColor = SlateCardSelected,
                tonalElevation = 6.dp
            ) {
                tabNames.forEachIndexed { index, name ->
                    val isSel = selectedTab == index
                    NavigationBarItem(
                        selected = isSel,
                        onClick = { selectedTab = index },
                        icon = {
                            Text(
                                text = name.split(" ")[0],
                                fontSize = 18.sp
                            )
                        },
                        label = {
                            Text(
                                text = name.split(" ")[1],
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = RetroGold,
                            selectedTextColor = RetroGold,
                            indicatorColor = Color(0xFFE8DEF8)
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (selectedTab) {
                0 -> DashboardDeckTab(stats, currentCard, onChoiceProcessed)
                1 -> CareersDeskTab(stats, viewModel)
                2 -> MarketAssetsTab(stats, stockPrices, viewModel)
                3 -> WellnessSocialTab(stats, viewModel)
            }
        }
    }
}

// ────────────────────────────────────────────────────────────────────────
// INDIVIDUAL TABS CONSOLE
// ────────────────────────────────────────────────────────────────────────

@Composable
fun DashboardDeckTab(
    stats: PlayerStats,
    currentCard: Decision?,
    onChoiceProcessed: (ChoiceOption) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        // Cash metrics
        MetricCashCounter(cashAmount = stats.money)
        Spacer(modifier = Modifier.height(14.dp))

        // Basic Stats Progression Columns
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            StatProgressBar(
                label = "VITALITY",
                value = stats.health,
                iconSymbol = "❤️",
                barColor = AlertRed,
                modifier = Modifier.weight(1f)
            )
            StatProgressBar(
                label = "HAPPINESS",
                value = stats.happiness,
                iconSymbol = "😊",
                barColor = HeartRed,
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            StatProgressBar(
                label = "INFLUENCE",
                value = stats.reputation,
                iconSymbol = "★",
                barColor = RetroGold,
                modifier = Modifier.weight(1f)
            )
            val avgSkill = ((stats.skills.academic + stats.skills.professional + stats.skills.social) / 3)
            StatProgressBar(
                label = "SKILL DEX",
                value = avgSkill,
                iconSymbol = "⚡",
                barColor = SkillBlue,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Main Scenario
        if (currentCard != null) {
            DecisionScenarioCard(card = currentCard, onOptionSelected = onChoiceProcessed)
        } else {
            Card(
                colors = CardDefaults.cardColors(containerColor = SlateCard),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Awaiting future choices... Click tab items below to study, job applications or pursue social trades!",
                    fontSize = 14.sp,
                    color = TextMuted,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Collapsible Chronology logs
        Text(
            text = "📖 LIFE HISTORY & LOGS",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = TextLight,
            fontFamily = FontFamily.Monospace,
            modifier = Modifier.padding(bottom = 6.dp)
        )
        Card(
            colors = CardDefaults.cardColors(containerColor = SlateCard),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(14.dp)
            ) {
                stats.logHistory.takeLast(6).reversed().forEach { logLine ->
                    Row(
                        modifier = Modifier.padding(vertical = 4.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(text = "• ", color = RetroGold, fontSize = 13.sp)
                        Text(
                            text = logLine,
                            fontSize = 12.sp,
                            color = TextLight.copy(alpha = 0.85f),
                            lineHeight = 16.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CareersDeskTab(
    stats: PlayerStats,
    viewModel: GameViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Study certificate upgrades
        Text(
            text = "🎓 ACADEMIC & EDUCATION COURSES",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = TextLight,
            fontFamily = FontFamily.Monospace,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        
        val courses = listOf(
            Triple("Coding Bootcamp certification", 300.0, Pair(5, 20)),
            Triple("Professional Scrum & Management Course", 250.0, Pair(10, 15)),
            Triple("Executive Part-time MBA Program", 800.0, Pair(15, 25)),
            Triple("Artificial Intelligence PhD Fellowship", 1400.0, Pair(45, 15))
        )

        courses.forEach { (name, cost, statsPlus) ->
            val canAfford = stats.money >= cost
            Card(
                colors = CardDefaults.cardColors(containerColor = SlateCard),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .border(1.dp, SlateCardSelected, RoundedCornerShape(12.dp))
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = name, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextLight)
                        Text(
                            text = "Cost: ${cost.toInt()} Units • Academic +${statsPlus.first}, Professional +${statsPlus.second}",
                            fontSize = 11.sp,
                            color = TextMuted,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                    Button(
                        onClick = { viewModel.enrollCourse(name, cost, statsPlus.first, statsPlus.second) },
                        enabled = canAfford && stats.education != name,
                        colors = ButtonDefaults.buttonColors(containerColor = SkillBlue),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(text = if (stats.education == name) "Finished" else "Study", fontSize = 11.sp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Available Job Application desk
        Text(
            text = "💼 ACTIVE CAREER COCKPIT",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = TextLight,
            fontFamily = FontFamily.Monospace,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = "Current Job: [ ${stats.job} ]",
            fontSize = 11.sp,
            color = RetroGold,
            fontFamily = FontFamily.Monospace,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        val jobPostings = listOf(
            // Title, annual Salary, requirements (Prof, Acad, Soc)
            listOf("Retail Assistant", "550.0", "5", "0", "15"),
            listOf("Junior Programmer", "1300.0", "30", "15", "5"),
            listOf("Freelance App Dev", "1750.0", "40", "0", "20"),
            listOf("Senior Software Engineer", "3600.0", "55", "30", "20"),
            listOf("VP of Product / Manager", "5200.0", "50", "40", "50"),
            listOf("Enterprise Chief Executive (CEO)", "13500.0", "70", "50", "65")
        )

        jobPostings.forEach { job ->
            val title = job[0]
            val wage = job[1].toDouble()
            val reqP = job[2].toInt()
            val reqA = job[3].toInt()
            val reqS = job[4].toInt()

            val meetsReq = stats.skills.professional >= reqP &&
                    stats.skills.academic >= reqA &&
                    stats.skills.social >= reqS

            val isHired = stats.job == title

            Card(
                colors = CardDefaults.cardColors(containerColor = if (isHired) SlateCardSelected else SlateCard),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
                    .border(
                        1.dp,
                        if (isHired) RetroGold else SlateCardSelected,
                        RoundedCornerShape(12.dp)
                    )
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = title, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextLight)
                        Text(
                            text = "+${wage.toInt()} Units / yr",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = HustleGreen,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Prerequisites: Professional $reqP% • Academic $reqA% • Social $reqS%",
                        fontSize = 11.sp,
                        color = TextMuted,
                        fontFamily = FontFamily.Monospace
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (meetsReq) {
                            Text(text = "✓ Qualified", color = HustleGreen, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        } else {
                            Text(text = "❌ Low Skill Metrics", color = AlertRed, fontSize = 11.sp)
                        }

                        Button(
                            onClick = { viewModel.applyForJob(title, wage, reqP, reqA, reqS) },
                            enabled = meetsReq && !isHired,
                            colors = ButtonDefaults.buttonColors(containerColor = if (isHired) Color.Gray else RetroGold),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.testTag("apply_${title.replace(" ", "_")}_btn")
                        ) {
                            Text(
                                text = if (isHired) "Active Hired" else "Send Resume",
                                fontSize = 11.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MarketAssetsTab(
    stats: PlayerStats,
    stockPrices: Map<String, Double>,
    viewModel: GameViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Stock / Crypto Market
        Text(
            text = "📉 VOLATILE CRYPTO & EQUITY EXCHANGES",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = TextLight,
            fontFamily = FontFamily.Monospace,
            modifier = Modifier.padding(bottom = 6.dp)
        )
        Text(
            text = "Prices change on turning a Year Older. Higher risks yields massive scaling opportunity.",
            fontSize = 11.sp,
            color = TextMuted,
            modifier = Modifier.padding(bottom = 14.dp)
        )

        stockPrices.forEach { (ticker, price) ->
            val owned = stats.sharesOwned[ticker] ?: 0
            val logoSymbol = if (ticker == "BTC") "₿" else if (ticker == "COMP") "🏛" else "🪙"
            val detailTag = if (ticker == "BTC") "Hustle Bitcoin" else if (ticker == "COMP") "Tech Syndicate Group" else "Elon Dogecoin Token"
            
            Card(
                colors = CardDefaults.cardColors(containerColor = SlateCard),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
                    .border(1.dp, SlateCardSelected, RoundedCornerShape(14.dp))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = logoSymbol, fontSize = 20.sp, modifier = Modifier.padding(end = 8.dp))
                            Column {
                                Text(text = ticker, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextLight)
                                Text(text = detailTag, fontSize = 10.sp, color = TextMuted)
                            }
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "${String.format("%.1f", price)} Units",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Black,
                                color = RetroGold,
                                fontFamily = FontFamily.Monospace
                            )
                            Text(
                                text = "Have: $owned shares",
                                fontSize = 11.sp,
                                color = TextMuted,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = { viewModel.buyStock(ticker, 1) },
                            enabled = stats.money >= price,
                            colors = ButtonDefaults.buttonColors(containerColor = HustleGreen),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(text = "Buy 1 Share", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                        Button(
                            onClick = { viewModel.sellStock(ticker, 1) },
                            enabled = owned >= 1,
                            colors = ButtonDefaults.buttonColors(containerColor = AlertRed),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(text = "Sell 1 Share", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Real Estate Desk
        Text(
            text = "🏨 REAL ESTATE PORTFOLIO",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = TextLight,
            fontFamily = FontFamily.Monospace,
            modifier = Modifier.padding(bottom = 6.dp)
        )
        Text(
            text = "Buy buildings outright to receive secure annual residual rental payments.",
            fontSize = 11.sp,
            color = TextMuted,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        val propertiesCatalog = listOf(
            PropertyAsset("prop_studio", "Suburban Studio Flat", 1500.0, 180.0, "Cozy, fully tenanted by tech students."),
            PropertyAsset("prop_penthouse", "Luxury City Penthouse", 8000.0, 1000.0, "Fitted with dynamic rooftop swimming pool."),
            PropertyAsset("prop_complex", "Commercial Cargo Warehouse", 20000.0, 3000.0, "Long term industrial commercial contract.")
        )

        propertiesCatalog.forEach { prop ->
            val ownedCount = stats.propertiesOwned.count { it.id == prop.id }
            val canAfford = stats.money >= prop.buyPrice

            Card(
                colors = CardDefaults.cardColors(containerColor = SlateCard),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
                    .border(
                        1.dp,
                        if (ownedCount > 0) HustleGreen.copy(alpha = 0.4f) else SlateCardSelected,
                        RoundedCornerShape(14.dp)
                    )
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column {
                            Text(text = prop.name, fontSize = 15.sp, fontWeight = FontWeight.Black, color = TextLight)
                            Text(text = prop.description, fontSize = 11.sp, color = TextMuted)
                        }
                        if (ownedCount > 0) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(HustleGreen.copy(alpha = 0.15f))
                                    .padding(horizontal = 6.dp, vertical = 3.dp)
                            ) {
                                Text(text = "Owned: $ownedCount", fontSize = 9.sp, color = HustleGreen, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Price: ${prop.buyPrice.toInt()} Units • Yields Rent: +${prop.rentIncome.toInt()}/yr Residual Cash",
                        fontSize = 11.sp,
                        color = TextMuted,
                        fontFamily = FontFamily.Monospace
                    )
                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (ownedCount > 0) {
                            Button(
                                onClick = { viewModel.sellProperty(prop) },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF475569)),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.padding(end = 8.dp)
                            ) {
                                Text(text = "Sell (Apar. +15%)", fontSize = 11.sp)
                            }
                        }
                        Button(
                            onClick = { viewModel.buyProperty(prop) },
                            enabled = canAfford,
                            colors = ButtonDefaults.buttonColors(containerColor = HustleGreen),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.testTag("buy_prop_${prop.id}")
                        ) {
                            Text(text = "Buy Building", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WellnessSocialTab(
    stats: PlayerStats,
    viewModel: GameViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Health / Healing center
        Text(
            text = "🏥 WELLNESS CLINIC & HEALING",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = TextLight,
            fontFamily = FontFamily.Monospace,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Custom simple wellness option mapping
        data class WellnessOption(val name: String, val cost: Double, val hp: Int, val hap: Int)

        val healingTreatments = listOf(
            WellnessOption("Workout at local fitness gym", 50.0, 15, 10),
            WellnessOption("General medical body checkup", 180.0, 30, 0),
            WellnessOption("Deep tissue spa massage", 120.0, 10, 25),
            WellnessOption("Comprehensive heart bypass therapy", 1200.0, 60, 10)
        )

        healingTreatments.forEach { (name, cost, hp, hap) ->
            val canAfford = stats.money >= cost
            Card(
                colors = CardDefaults.cardColors(containerColor = SlateCard),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .border(1.dp, SlateCardSelected, RoundedCornerShape(12.dp))
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = name, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextLight)
                        Text(
                            text = "Cost: ${cost.toInt()} Units • Health +$hp • Happiness +$hap",
                            fontSize = 11.sp,
                            color = TextMuted,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                    Button(
                        onClick = { viewModel.treatHealthAction(name, cost, hp, hap) },
                        enabled = canAfford,
                        colors = ButtonDefaults.buttonColors(containerColor = HustleGreen),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(text = "Treat", fontSize = 11.sp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Love Cafe
        Text(
            text = "🍷 LOVE & FAMILY DIALOGUE",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = TextLight,
            fontFamily = FontFamily.Monospace,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        if (stats.partner == null) {
            Card(
                colors = CardDefaults.cardColors(containerColor = SlateCard),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "You are currently single. Romance trigger choices will emerge organically in your decision deck cards!",
                    fontSize = 13.sp,
                    color = TextMuted,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(20.dp)
                )
            }
        } else {
            Card(
                colors = CardDefaults.cardColors(containerColor = SlateCard),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, HeartRed.copy(alpha = 0.3f), RoundedCornerShape(14.dp))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "💖", fontSize = 28.sp)
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(text = stats.partner ?: "Sam", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextLight)
                            Text(text = "Status: Married Partner", fontSize = 11.sp, color = TextMuted)
                        }
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = "Keeping partners happy sustains your emotional wellness index (+Happiness per turn). Spend funds for dates!",
                        fontSize = 12.sp,
                        color = TextLight.copy(alpha = 0.8f),
                        lineHeight = 16.sp
                    )
                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = { viewModel.giftPartnerAction(100.0, 20) },
                            enabled = stats.money >= 100.0,
                            colors = ButtonDefaults.buttonColors(containerColor = HeartRed),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(text = "Date / Gift (-100)", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

// ────────────────────────────────────────────────────────────────────────
// LEADERS & RECAPS
// ────────────────────────────────────────────────────────────────────────

@Composable
fun YearReviewDialog(
    stats: PlayerStats,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.85f))
            .clickable(enabled = false) {},
        contentAlignment = Alignment.Center
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = SlateCard),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .border(2.dp, RetroGold, RoundedCornerShape(20.dp))
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "🎂 YEAR ADVANCED!",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black,
                    color = RetroGold,
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier.testTag("year_dialog_title")
                )
                Text(
                    text = "You successfully navigated another year and turned Age ${stats.age}.",
                    fontSize = 12.sp,
                    color = TextMuted,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 4.dp)
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Financial Summary
                Card(
                    colors = CardDefaults.cardColors(containerColor = SlateCardSelected),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "FINANCIAL RECAP:",
                            fontSize = 11.sp,
                            color = RetroGold,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                        HorizontalDivider(color = Color(0xFFCAC4D0))
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "Pocket cash balance", fontSize = 13.sp, color = TextLight)
                            Text(
                                text = "₹/ $ ${String.format("%.1f", stats.money)}",
                                fontSize = 13.sp,
                                color = TextLight,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "Active Occupation", fontSize = 13.sp, color = TextLight)
                            Text(
                                text = stats.job,
                                fontSize = 13.sp,
                                color = TextLight,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "Home Residence", fontSize = 13.sp, color = TextLight)
                            Text(
                                text = stats.house,
                                fontSize = 13.sp,
                                color = TextMuted,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Stats check
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = SlateCardSelected),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "❤️ Health", fontSize = 11.sp, color = TextMuted)
                            Text(
                                text = "${stats.health}%",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = HustleGreen,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                    Card(
                        colors = CardDefaults.cardColors(containerColor = SlateCardSelected),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "😊 Happiness", fontSize = 11.sp, color = TextMuted)
                            Text(
                                text = "${stats.happiness}%",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = HeartRed,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor = RetroGold, contentColor = Color.White),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("dismiss_year_btn")
                ) {
                    Text(text = "Continue Journey", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun GameOverScreen(
    stats: PlayerStats,
    onRestart: () -> Unit,
    onBackMenu: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SlateDarkBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            // Icon Grave
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF2E3B5E))
            ) {
                Text(text = "🕊️", fontSize = 42.sp)
            }
            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = stats.endingType ?: "Life Completed",
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                color = RetroGold,
                textAlign = TextAlign.Center,
                lineHeight = 28.sp
            )
            Text(
                text = "PEAK DESTINY ACHIEVED",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = TextMuted,
                fontFamily = FontFamily.Monospace,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Score Summary card
            Card(
                colors = CardDefaults.cardColors(containerColor = SlateCard),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color(0xFFCAC4D0), RoundedCornerShape(16.dp))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "EPILOGUE PROFILE",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = RetroGold,
                        fontFamily = FontFamily.Monospace
                    )
                    HorizontalDivider(color = Color(0xFFCAC4D0))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Name of Citizen", fontSize = 13.sp, color = TextLight)
                        Text(text = stats.name, fontSize = 13.sp, color = TextLight, fontWeight = FontWeight.Bold)
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Final Age Reached", fontSize = 13.sp, color = TextLight)
                        Text(text = "${stats.age} Years", fontSize = 13.sp, color = TextLight, fontWeight = FontWeight.Bold)
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Final Net Worth", fontSize = 13.sp, color = TextLight)
                        Text(text = "$ ${String.format("%.1f", stats.money)} Units", fontSize = 13.sp, color = HustleGreen, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Peak Occupation", fontSize = 13.sp, color = TextLight)
                        Text(text = stats.job, fontSize = 13.sp, color = TextLight, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Annual milestone feed
            Text(
                text = "📜 ANNUAL BIOGRAPHY HIGHLIGHTS",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = TextLight,
                fontFamily = FontFamily.Monospace,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(bottom = 6.dp)
            )
            Card(
                colors = CardDefaults.cardColors(containerColor = SlateCard),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(14.dp)
                ) {
                    stats.annualEvents.forEach { yearLine ->
                        Row(
                            modifier = Modifier.padding(vertical = 4.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Text(text = "★ ", color = RetroGold, fontSize = 12.sp)
                            Text(
                                text = yearLine,
                                fontSize = 11.sp,
                                color = TextLight.copy(alpha = 0.82f),
                                lineHeight = 15.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            // Action triggers
            Button(
                onClick = onRestart,
                colors = ButtonDefaults.buttonColors(containerColor = RetroGold, contentColor = Color.White),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("restart_game_btn")
            ) {
                Text(text = "INCUBATE A NEW SOUL 🌱", fontSize = 15.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = onBackMenu,
                colors = ButtonDefaults.buttonColors(containerColor = SlateCardSelected, contentColor = TextLight),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Exit to Main Menu", fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}

@Composable
fun SaveSlotsScreen(
    onSelectSlot: (slot: String) -> Unit,
    onDeleteSlot: (slot: String) -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val sharedPrefs = remember { context.getSharedPreferences("LifeChoicePrefs", Context.MODE_PRIVATE) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SlateDarkBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier.background(SlateCard, CircleShape)
                ) {
                    Text(text = "←", color = TextLight, fontSize = 18.sp)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Select Save Slot",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextLight
                )
            }
            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "LifeChoice Simulator supports up to 3 parallel separate save files. Select one slot to resume previous progress or incubate a fresh brand-new life.",
                fontSize = 13.sp,
                color = TextMuted,
                lineHeight = 18.sp,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            listOf("slot_1", "slot_2", "slot_3").forEachIndexed { idx, slotKey ->
                val hasSave = sharedPrefs.contains(slotKey)
                var saveDetailStr = "EMTPY TIME SLOT - Ready for Incubating"
                
                if (hasSave) {
                    val raw = sharedPrefs.getString(slotKey, null)
                    if (raw != null) {
                        try {
                            // Extract basic quick text data
                            val nameStart = raw.indexOf("\"name\":\"") + 8
                            val nameEnd = raw.indexOf("\"", nameStart)
                            val ageStart = raw.indexOf("\"age\":") + 6
                            val ageEnd = raw.indexOf(",", ageStart)
                            val moneyStart = raw.indexOf("\"money\":") + 8
                            val moneyEnd = raw.indexOf(",", moneyStart)
                            
                            val name = raw.substring(nameStart, nameEnd)
                            val age = raw.substring(ageStart, ageEnd)
                            val money = raw.substring(moneyStart, moneyEnd).toDoubleOrNull() ?: 0.0
                            
                            saveDetailStr = "Active Life: $name (Age $age) • Balance: $ ${money.toInt()}"
                        } catch (e: Exception) {
                            saveDetailStr = "Active Save Profile"
                        }
                    }
                }

                Card(
                    colors = CardDefaults.cardColors(containerColor = SlateCard),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                        .border(
                            1.dp,
                            if (hasSave) RetroGold.copy(alpha = 0.3f) else SlateCardSelected,
                            RoundedCornerShape(16.dp)
                        )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "SAVE FILE SLOT ${idx + 1}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextMuted,
                                fontFamily = FontFamily.Monospace
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = saveDetailStr,
                                fontSize = 13.sp,
                                color = if (hasSave) TextLight else TextMuted.copy(alpha = 0.6f),
                                fontWeight = if (hasSave) FontWeight.Bold else FontWeight.Normal
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (hasSave) {
                                IconButton(
                                    onClick = { onDeleteSlot(slotKey) },
                                    modifier = Modifier.padding(end = 4.dp)
                                ) {
                                    Text(text = "❌", fontSize = 14.sp)
                                }
                            }
                            Button(
                                onClick = { onSelectSlot(slotKey) },
                                colors = ButtonDefaults.buttonColors(containerColor = if (hasSave) HustleGreen else RetroGold),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.testTag("select_slot_${idx + 1}_btn")
                            ) {
                                Text(
                                    text = if (hasSave) "Play" else "Create",
                                    fontSize = 11.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HallOfFameScreen(
    leaders: List<PastLife>,
    onBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SlateDarkBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier.background(SlateCard, CircleShape)
                ) {
                    Text(text = "←", color = TextLight, fontSize = 18.sp)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Hall of Fame 🏆",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextLight
                )
            }
            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Archived achievements of past completed lives sorted by total net liquid/equity asset worth.",
                fontSize = 12.sp,
                color = TextMuted,
                lineHeight = 16.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (leaders.isEmpty()) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "⏳", fontSize = 42.sp)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "No recorded completed lives yet. Your legacy profiles will automatically emerge here upon reaching destiny endings!",
                            fontSize = 13.sp,
                            color = TextMuted,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 24.dp)
                        )
                    }
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    items(leaders) { life ->
                        Card(
                            colors = CardDefaults.cardColors(containerColor = SlateCard),
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column {
                                        Text(text = life.name, fontSize = 15.sp, fontWeight = FontWeight.Black, color = TextLight)
                                        Text(
                                            text = "Career: ${life.carrierPath} • Age ${life.finalAge}",
                                            fontSize = 11.sp,
                                            color = TextMuted
                                        )
                                    }
                                    Text(
                                        text = "$ ${String.format("%.1f", life.netWorth)}",
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = RetroGold,
                                        fontFamily = FontFamily.Monospace
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                HorizontalDivider(color = Color(0xFFCAC4D0))
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "Ending Reached: ${life.endingType}",
                                    fontSize = 11.sp,
                                    color = HustleGreen,
                                    fontFamily = FontFamily.Monospace
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
