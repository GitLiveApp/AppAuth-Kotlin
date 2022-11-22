package dev.gitlive.appauth

expect class AuthorizationException : Exception

expect class AuthorizationService {
    suspend fun performAuthorizationRequest(request: AuthorizationRequest): AuthorizationResponse
    suspend fun performEndSessionRequest(request: EndSessionRequest): EndSessionResponse
    suspend fun performTokenRequest(request: TokenRequest): TokenResponse
}

expect class AuthorizationServiceConfiguration(
    authorizationEndpoint: String,
    tokenEndpoint: String,
    registrationEndpoint: String? = null,
    endSessionEndpoint: String? = null,
) {
    val authorizationEndpoint: String
    val tokenEndpoint: String
    val registrationEndpoint: String?
    val endSessionEndpoint: String?

    companion object {
        suspend fun fetchFromIssuer(url: String): AuthorizationServiceConfiguration
    }
}

expect class AuthorizationRequest(
    config: AuthorizationServiceConfiguration,
    clientId: String,
    responseType: String,
    redirectUri: String
)

expect class AuthorizationResponse {
    val idToken: String?
    val authorizationCode: String?
    fun createTokenExchangeRequest(): TokenRequest
}

expect class TokenRequest(
    config: AuthorizationServiceConfiguration,
    clientId: String,
    grantType: String,
    refreshToken: String? = null
)

expect class TokenResponse {
    val idToken: String?
    val accessToken: String?
    val refreshToken: String?
}

expect class EndSessionRequest(
    config: AuthorizationServiceConfiguration,
    idTokenHint: String? = null,
    postLogoutRedirectUri: String? = null
)

expect class EndSessionResponse
