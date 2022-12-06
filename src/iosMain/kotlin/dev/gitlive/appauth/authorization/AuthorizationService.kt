package dev.gitlive.appauth.authorization

import cocoapods.AppAuth.OIDAuthorizationService
import cocoapods.AppAuth.OIDExternalUserAgentIOS
import cocoapods.AppAuth.OIDExternalUserAgentSessionProtocol
import dev.gitlive.appauth.session.EndSessionRequest
import dev.gitlive.appauth.session.EndSessionResponse
import dev.gitlive.appauth.toException
import dev.gitlive.appauth.token.TokenRequest
import dev.gitlive.appauth.token.TokenResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import platform.Foundation.NSURL
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

actual class AuthorizationService actual constructor(private val context: () -> AuthorizationServiceContext) {

    private var session: OIDExternalUserAgentSessionProtocol? = null

    fun resumeExternalUserAgentFlow(url: NSURL): Boolean =
        session?.resumeExternalUserAgentFlowWithURL(url) == true

    actual suspend fun performAuthorizationRequest(request: AuthorizationRequest): AuthorizationResponse =
        withContext(Dispatchers.Main) {
            suspendCoroutine { cont ->
                session = OIDAuthorizationService.presentAuthorizationRequest(
                    request.ios,
                    OIDExternalUserAgentIOS(context())
                ) { response, error ->
                    session = null
                    response?.let { cont.resume(AuthorizationResponse(it)) }
                        ?: cont.resumeWithException(error!!.toException())
                }
            }
        }

    actual suspend fun performEndSessionRequest(request: EndSessionRequest): EndSessionResponse =
        withContext(Dispatchers.Main) {
            suspendCoroutine { cont ->
                session = OIDAuthorizationService.presentEndSessionRequest(
                    request.ios,
                    OIDExternalUserAgentIOS(context())
                ) { response, error ->
                    session = null
                    response?.let { cont.resume(it) }
                        ?: cont.resumeWithException(error!!.toException())
                }
            }
        }

    actual suspend fun performTokenRequest(request: TokenRequest): TokenResponse = suspendCoroutine { cont ->
        OIDAuthorizationService.performTokenRequest(request.ios) { response, error ->
            response?.let { cont.resume(TokenResponse(it)) }
                ?: cont.resumeWithException(error!!.toException())
        }
    }
}
