package dev.gitlive.appauth.authorization

import android.content.Intent
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import dev.gitlive.appauth.session.EndSessionRequest
import dev.gitlive.appauth.session.EndSessionResponse
import dev.gitlive.appauth.token.TokenRequest
import dev.gitlive.appauth.token.TokenResponse
import kotlinx.coroutines.CompletableDeferred
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

actual class AuthorizationService private constructor(private val android: net.openid.appauth.AuthorizationService) {
    actual constructor(context: () -> AuthorizationServiceContext) : this(net.openid.appauth.AuthorizationService(context()))

    fun bind(activityOrFragment: ActivityResultCaller) {
        launcher = activityOrFragment
            .registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                result.data
                    ?.let { AuthorizationException.fromIntent(it) }
                    ?.let { response.completeExceptionally(it.wrapIfNecessary()) }
                    ?: response.complete(result.data)
            }
    }

    private var response = CompletableDeferred<Intent?>(value = null)

    private lateinit var launcher: ActivityResultLauncher<Intent>

    actual suspend fun performAuthorizationRequest(request: AuthorizationRequest): AuthorizationResponse {
        // if a previous request is still pending then wait for it to finish
        response.runCatching { await() }
        response = CompletableDeferred()
        launcher.launch(android.getAuthorizationRequestIntent(request.android))
        return AuthorizationResponse(net.openid.appauth.AuthorizationResponse.fromIntent(response.await()!!)!!)
    }

    actual suspend fun performEndSessionRequest(request: EndSessionRequest): EndSessionResponse {
        // if a previous request is still pending then wait for it to finish
        response.runCatching { await() }
        response = CompletableDeferred()
        launcher.launch(android.getEndSessionRequestIntent(request.android))
        return EndSessionResponse.fromIntent(response.await()!!)!!
    }

    actual suspend fun performTokenRequest(request: TokenRequest): TokenResponse =
        suspendCoroutine { cont ->
            android.performTokenRequest(request.android) { response, ex ->
                response?.let { cont.resume(response) }
                    ?: cont.resumeWithException(ex!!.wrapIfNecessary())
            }
        }
}
