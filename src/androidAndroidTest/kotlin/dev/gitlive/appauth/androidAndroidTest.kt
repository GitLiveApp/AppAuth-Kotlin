package dev.gitlive.appauth

import androidx.activity.ComponentActivity
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.launchActivity
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import kotlinx.coroutines.CoroutineScope

actual val context: Any = InstrumentationRegistry.getInstrumentation().targetContext

actual fun simulateSignIn(): Unit = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).run {
    UiSelector().run {
        findObject(resourceId("username")).run {
            click()
            setText("MyUsername")
        }
        findObject(resourceId("password")).run {
            click()
            setText("MyPassword")
        }
        findObject(resourceId("login-button")).click()
//        wait(Until.findObject(By.text("Send anyway")), 5000).click()
    }
}

class MyActivity : ComponentActivity()

actual suspend fun CoroutineScope.withAuthorizationService(action: suspend (service: AuthorizationService) -> Unit) {

    val service = AuthorizationService(InstrumentationRegistry.getInstrumentation().targetContext)

    val scenario = launchActivity<MyActivity>()
        .moveToState(Lifecycle.State.CREATED)
        .onActivity { service.bind(it) }
        .moveToState(Lifecycle.State.RESUMED)

    action(service)

    scenario.close()
}
