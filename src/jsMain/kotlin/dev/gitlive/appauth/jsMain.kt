package dev.gitlive.appauth

actual class AuthorizationServiceConfiguration actual constructor(
    authorizationEndpoint: String,
    tokenEndpoint: String,
    registrationEndpoint: String?,
    endSessionEndpoint: String?
) {
    actual companion object {
        actual suspend fun fetchFromIssuer(url: String): AuthorizationServiceConfiguration {
            TODO("Not yet implemented")
        }
    }

    actual val authorizationEndpoint: String
        get() = TODO("Not yet implemented")
    actual val tokenEndpoint: String
        get() = TODO("Not yet implemented")
    actual val registrationEndpoint: String?
        get() = TODO("Not yet implemented")
    actual val endSessionEndpoint: String?
        get() = TODO("Not yet implemented")
}

actual class AuthorizationException : Exception()

actual class AuthorizationRequest actual constructor(
    config: AuthorizationServiceConfiguration,
    clientId: String,
    responseType: String,
    redirectUri: String
)

actual class AuthorizationResponse {
    actual fun createTokenExchangeRequest(): TokenRequest {
        TODO("Not yet implemented")
    }

    actual val authorizationCode: String?
        get() = TODO("Not yet implemented")
    actual val idToken: String?
        get() = TODO("Not yet implemented")
}

actual class TokenRequest actual constructor(
    config: AuthorizationServiceConfiguration,
    clientId: String,
    grantType: String,
    refreshToken: String?
)

actual class TokenResponse {
    actual val accessToken: String?
        get() = TODO("Not yet implemented")
    actual val refreshToken: String?
        get() = TODO("Not yet implemented")
    actual val idToken: String?
        get() = TODO("Not yet implemented")
}

actual class AuthorizationService {
    actual suspend fun performAuthorizationRequest(request: AuthorizationRequest): AuthorizationResponse {
        TODO("Not yet implemented")
    }

    actual suspend fun performTokenRequest(request: TokenRequest): TokenResponse {
        TODO("Not yet implemented")
    }

    actual suspend fun performEndSessionRequest(request: EndSessionRequest): EndSessionResponse {
        TODO("Not yet implemented")
    }
}

actual class EndSessionRequest actual constructor(
    config: AuthorizationServiceConfiguration,
    idTokenHint: String?,
    postLogoutRedirectUri: String?
)

actual class EndSessionResponse
