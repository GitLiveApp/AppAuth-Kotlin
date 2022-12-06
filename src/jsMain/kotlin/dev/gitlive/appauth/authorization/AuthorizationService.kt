package dev.gitlive.appauth.authorization

import dev.gitlive.appauth.session.EndSessionRequest
import dev.gitlive.appauth.session.EndSessionResponse
import dev.gitlive.appauth.token.TokenRequest
import dev.gitlive.appauth.token.TokenResponse

actual class AuthorizationService actual constructor(context: () -> AuthorizationServiceContext) {
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
