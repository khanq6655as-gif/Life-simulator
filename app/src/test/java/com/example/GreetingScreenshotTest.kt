package com.example

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import coil.Coil
import coil.ImageLoader
import coil.request.ImageResult
import coil.request.SuccessResult
import com.example.game.ui.MainMenuScreen
import com.example.ui.theme.MyApplicationTheme
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(qualifiers = RobolectricDeviceQualifiers.Pixel8, sdk = [36])
class GreetingScreenshotTest {

  @get:Rule val composeTestRule = createComposeRule()

  @Before
  fun setup() {
    val context = RuntimeEnvironment.getApplication()
    val imageLoader = object : ImageLoader {
      override val defaults = coil.request.DefaultRequestOptions()
      override val components = coil.ComponentRegistry()
      override val memoryCache: coil.memory.MemoryCache? = null
      override val diskCache: coil.disk.DiskCache? = null

      override fun enqueue(request: coil.request.ImageRequest): coil.request.Disposable {
        val result = SuccessResult(
          drawable = ColorDrawable(Color.BLUE),
          request = request,
          dataSource = coil.decode.DataSource.MEMORY
        )
        request.listener?.onStart(request)
        request.listener?.onSuccess(request, result)
        return object : coil.request.Disposable {
          override val isDisposed = true
          override fun dispose() {}
          override val job = kotlinx.coroutines.CompletableDeferred(result)
        }
      }

      override suspend fun execute(request: coil.request.ImageRequest): ImageResult {
        return SuccessResult(
          drawable = ColorDrawable(Color.BLUE),
          request = request,
          dataSource = coil.decode.DataSource.MEMORY
        )
      }

      override fun shutdown() {}
      override fun newBuilder() = ImageLoader.Builder(context)
    }
    Coil.setImageLoader(imageLoader)
  }

  @Test
  fun greeting_screenshot() {
    composeTestRule.setContent {
      MyApplicationTheme {
        MainMenuScreen(
          hallOfFameCount = 2,
          onStartNewSetup = {},
          onOpenSaveSlots = {},
          onOpenLeaderboard = {}
        )
      }
    }

    composeTestRule.waitForIdle()
    composeTestRule.onRoot().captureRoboImage(filePath = "src/test/screenshots/greeting.png")
  }
}
