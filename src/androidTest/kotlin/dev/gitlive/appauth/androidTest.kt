package dev.gitlive.appauth

import kotlinx.coroutines.CoroutineScope

actual val context: Any
    get() = TODO("Not yet implemented")

actual fun simulateSignIn() {
}

actual suspend fun CoroutineScope.withAuthorizationService(action: suspend (service: AuthorizationService) -> Unit) {
}
