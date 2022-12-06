package dev.gitlive.appauth.authorization

import dev.gitlive.appauth.token.TokenRequest

expect class AuthorizationResponse {
    val idToken: String?
    val authorizationCode: String?
    val scope: String?
    fun createTokenExchangeRequest(): TokenRequest
}
