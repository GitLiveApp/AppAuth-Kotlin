package dev.gitlive.appauth

import cocoapods.AppAuth.OIDAuthorizationRequest
import cocoapods.AppAuth.OIDAuthorizationResponse
import cocoapods.AppAuth.OIDAuthorizationService
import cocoapods.AppAuth.OIDEndSessionRequest
import cocoapods.AppAuth.OIDEndSessionResponse
import cocoapods.AppAuth.OIDErrorCodeNetworkError
import cocoapods.AppAuth.OIDExternalUserAgentIOS
import cocoapods.AppAuth.OIDExternalUserAgentSessionProtocol
import cocoapods.AppAuth.OIDGeneralErrorDomain
import cocoapods.AppAuth.OIDServiceConfiguration
import cocoapods.AppAuth.OIDTokenRequest
import cocoapods.AppAuth.OIDTokenResponse
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import platform.Foundation.NSError
import platform.Foundation.NSURL
import platform.UIKit.UIViewController
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

actual class AuthorizationException(message: String?) : Exception(message)

// wrap network errors in an IOException so it matches ktor
private fun NSError.toException() = when (domain) {
    OIDGeneralErrorDomain -> when (code) {
        OIDErrorCodeNetworkError -> IOException(localizedDescription)
        else -> AuthorizationException(localizedDescription)
    }
    else -> AuthorizationException(localizedDescription)
}

actual class AuthorizationServiceConfiguration private constructor(val ios: OIDServiceConfiguration) {

    actual constructor(
        authorizationEndpoint: String,
        tokenEndpoint: String,
        registrationEndpoint: String?,
        endSessionEndpoint: String?
    ) : this(
        OIDServiceConfiguration(
            NSURL.URLWithString(authorizationEndpoint)!!,
            NSURL.URLWithString(tokenEndpoint)!!,
            null,
            registrationEndpoint?.let { NSURL.URLWithString(it) },
            endSessionEndpoint?.let { NSURL.URLWithString(it) }
        )
    )

    actual companion object {
        actual suspend fun fetchFromIssuer(url: String): AuthorizationServiceConfiguration = suspendCoroutine { cont ->
            OIDAuthorizationService.discoverServiceConfigurationForIssuer(NSURL.URLWithString(url)!!) { config, error ->
                config?.let { cont.resume(AuthorizationServiceConfiguration(it)) }
                    ?: cont.resumeWithException(error!!.toException())
            }
        }
    }

    actual val authorizationEndpoint: String get() = ios.authorizationEndpoint.relativeString
    actual val tokenEndpoint: String get() = ios.tokenEndpoint.relativeString
    actual val registrationEndpoint: String? get() = ios.registrationEndpoint?.relativeString
    actual val endSessionEndpoint: String? get() = ios.endSessionEndpoint?.relativeString
}

actual class AuthorizationRequest private constructor(internal val ios: OIDAuthorizationRequest) {

    actual constructor(
        config: AuthorizationServiceConfiguration,
        clientId: String,
        scopes: List<String>,
        responseType: String,
        redirectUri: String
    ) : this(
        OIDAuthorizationRequest(
            configuration = config.ios,
            clientId = clientId,
            scopes = scopes,
            redirectURL = NSURL.URLWithString(redirectUri)!!,
            responseType = responseType,
            additionalParameters = null
        )
    )
}

actual class TokenRequest internal constructor(internal val ios: OIDTokenRequest) {
    actual constructor(
        config: AuthorizationServiceConfiguration,
        clientId: String,
        grantType: String,
        refreshToken: String?
    ) : this(
        OIDTokenRequest(
            configuration = config.ios,
            grantType = grantType,
            authorizationCode = null,
            redirectURL = null,
            clientID = clientId,
            clientSecret = null,
            scope = null,
            refreshToken = refreshToken,
            codeVerifier = null,
            additionalParameters = null
        )
    )
}

actual class AuthorizationResponse internal constructor(internal val ios: OIDAuthorizationResponse) {
    actual val authorizationCode: String? get() = ios.authorizationCode
    actual val idToken: String? get() = ios.idToken
    actual val scope get() = ios.scope
    actual fun createTokenExchangeRequest() = TokenRequest(ios.tokenExchangeRequest()!!)
}

actual class EndSessionRequest internal constructor(internal val ios: OIDEndSessionRequest) {
    actual constructor(
        config: AuthorizationServiceConfiguration,
        idTokenHint: String?,
        postLogoutRedirectUri: String?,
    ) : this(
        OIDEndSessionRequest(
            configuration = config.ios,
            idTokenHint = idTokenHint,
            postLogoutRedirectURL = postLogoutRedirectUri?.let { NSURL.URLWithString(it) },
            additionalParameters = null
        )
    )
}

actual class TokenResponse internal constructor(internal val ios: OIDTokenResponse) {
    actual val idToken: String? get() = ios.idToken
    actual val accessToken: String? get() = ios.accessToken
    actual val refreshToken: String? get() = ios.refreshToken
}

actual typealias EndSessionResponse = OIDEndSessionResponse

actual typealias AuthorizationServiceContext = UIViewController

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
