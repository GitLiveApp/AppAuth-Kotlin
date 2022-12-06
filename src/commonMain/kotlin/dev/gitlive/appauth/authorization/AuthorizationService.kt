package dev.gitlive.appauth.authorization

import dev.gitlive.appauth.session.EndSessionRequest
import dev.gitlive.appauth.session.EndSessionResponse
import dev.gitlive.appauth.token.TokenRequest
import dev.gitlive.appauth.token.TokenResponse

expect class AuthorizationService(context: () -> AuthorizationServiceContext) {
    suspend fun performAuthorizationRequest(request: AuthorizationRequest): AuthorizationResponse
    suspend fun performEndSessionRequest(request: EndSessionRequest): EndSessionResponse
    suspend fun performTokenRequest(request: TokenRequest): TokenResponse
}