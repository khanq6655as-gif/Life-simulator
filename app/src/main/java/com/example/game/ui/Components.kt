package com.example.game.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.game.ChoiceOption
import com.example.game.Decision
import com.example.game.PlayerStats
import com.example.ui.theme.*

@Composable
fun StatProgressBar(
    label: String,
    value: Int, // 0 - 100
    iconSymbol: String,
    barColor: Color,
    modifier: Modifier = Modifier
) {
    val floatVal = (value / 100f).coerceIn(0f, 1f)
    
    Card(
        colors = CardDefaults.cardColors(containerColor = SlateCard),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, SlateCardSelected, RoundedCornerShape(12.dp))
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = iconSymbol,
                        fontSize = 15.sp,
                        modifier = Modifier.padding(end = 6.dp)
                    )
                    Text(
                        text = label,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextMuted,
                        fontFamily = FontFamily.Monospace
                    )
                }
                Text(
                    text = "$value%",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextLight,
                    fontFamily = FontFamily.Monospace
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE7E0DE))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(floatVal)
                        .clip(CircleShape)
                        .background(
                            Brush.horizontalGradient(
                                listOf(barColor.copy(alpha = 0.7f), barColor)
                            )
                        )
                )
            }
        }
    }
}

@Composable
fun DecisionScenarioCard(
    card: Decision,
    onOptionSelected: (ChoiceOption) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = SlateCard),
        shape = RoundedCornerShape(18.dp),
        modifier = modifier
            .fillMaxWidth()
            .border(2.dp, RetroGold.copy(alpha = 0.25f), RoundedCornerShape(18.dp))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Stage Indicator Tag
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color(0xFFF4EFF4))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = card.stage.name.replace("_", " "),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = RetroGold,
                    fontFamily = FontFamily.Monospace,
                    letterSpacing = 1.sp
                )
            }
            Spacer(modifier = Modifier.height(10.dp))

            // Card Title
            Text(
                text = card.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Black,
                color = TextLight,
                lineHeight = 24.sp,
                modifier = Modifier.testTag("decision_card_title")
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Scenario Narrative
            Text(
                text = card.scenario,
                fontSize = 15.sp,
                color = TextLight.copy(alpha = 0.9f),
                lineHeight = 22.sp
            )
            Spacer(modifier = Modifier.height(18.dp))

            // Options list
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                card.options.forEachIndexed { idx, option ->
                    Button(
                        onClick = { onOptionSelected(option) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SlateCardSelected,
                            contentColor = TextLight
                        ),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, Color(0xFFCAC4D0), RoundedCornerShape(12.dp))
                            .testTag("choice_${idx}_btn")
                    ) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = option.text,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = RetroGold,
                                textAlign = TextAlign.Start
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = option.description,
                                fontSize = 11.sp,
                                color = TextMuted,
                                textAlign = TextAlign.Start
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun UnifiedHeaderSection(
    stats: PlayerStats,
    onBackToMenu: () -> Unit
) {
    Surface(
        color = SlateDarkBackground,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            Column {
                Text(
                    text = stats.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    color = RetroGold
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(HustleGreen)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "Age: ${stats.age} • ${stats.gender}",
                        fontSize = 11.sp,
                        color = TextMuted,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                // Exit Menu Button
                IconButton(
                    onClick = onBackToMenu,
                    modifier = Modifier
                        .size(38.dp)
                        .background(SlateCardSelected, CircleShape)
                ) {
                    Text(
                        text = "🏠",
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Composable
fun MetricCashCounter(
    cashAmount: Double,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = SlateCardSelected),
        shape = RoundedCornerShape(14.dp),
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, RetroGold.copy(alpha = 0.35f), RoundedCornerShape(14.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(36.dp)
                        .background(RetroGold.copy(alpha = 0.15f), CircleShape)
                ) {
                    Text(
                        text = "🪙",
                        fontSize = 18.sp
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "AVAILABLE FUNDS",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextMuted,
                        letterSpacing = 1.sp,
                        fontFamily = FontFamily.Monospace
                    )
                    Text(
                        text = "₹ / $ ${String.format("%.1f", cashAmount)}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black,
                        color = PremiumGold,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
        }
    }
}
