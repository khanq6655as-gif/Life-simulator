package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.example.game.GameState
import com.example.game.GameViewModel
import com.example.game.ui.*
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val gameViewModel: GameViewModel = viewModel()
                val activeState by gameViewModel.gameState.collectAsState()
                val stats by gameViewModel.playerStats.collectAsState()
                val currentCard by gameViewModel.currentCard.collectAsState()
                val hallOfFame by gameViewModel.hallOfFame.collectAsState()
                val stockPrices by gameViewModel.stockPrices.collectAsState()

                when (activeState) {
                    GameState.MAIN_MENU -> {
                        MainMenuScreen(
                            hallOfFameCount = hallOfFame.size,
                            onStartNewSetup = { gameViewModel.enterSetup() },
                            onOpenSaveSlots = { gameViewModel.selectSaveSlotMenu() },
                            onOpenLeaderboard = { gameViewModel.viewHallOfFame() }
                        )
                    }

                    GameState.START_SETUP -> {
                        SetupScreen(
                            onConfirmSetup = { name, gender ->
                                gameViewModel.startNewLife(name, gender)
                            },
                            onBack = { gameViewModel.enterMainMenu() }
                        )
                    }

                    GameState.ACTIVE_GAME -> {
                        ActiveGameScreen(
                            stats = stats,
                            currentCard = currentCard,
                            stockPrices = stockPrices,
                            onChoiceProcessed = { choice ->
                                gameViewModel.chooseOption(choice)
                            },
                            onBackToMenu = { gameViewModel.enterMainMenu() },
                            viewModel = gameViewModel
                        )
                    }

                    GameState.YEAR_REVIEW_DIALOG -> {
                        YearReviewDialog(
                            stats = stats,
                            onDismiss = {
                                gameViewModel.dismissYearReview()
                            }
                        )
                    }

                    GameState.GAME_OVER_SCREEN -> {
                        GameOverScreen(
                            stats = stats,
                            onRestart = {
                                gameViewModel.enterSetup()
                            },
                            onBackMenu = {
                                gameViewModel.enterMainMenu()
                            }
                        )
                    }

                    GameState.SAVED_SLOTS_PANEL -> {
                        SaveSlotsScreen(
                            onSelectSlot = { slot ->
                                gameViewModel.selectSlot(slot)
                            },
                            onDeleteSlot = { slot ->
                                gameViewModel.deleteSaveSlot(slot)
                            },
                            onBack = { gameViewModel.enterMainMenu() }
                        )
                    }

                    GameState.HALL_OF_FAME -> {
                        HallOfFameScreen(
                            leaders = hallOfFame,
                            onBack = { gameViewModel.enterMainMenu() }
                        )
                    }
                }
            }
        }
    }
}
