package dev.gitlive.appauth.authorization

import dev.gitlive.appauth.token.TokenRequest

actual class AuthorizationResponse {
    actual fun createTokenExchangeRequest(): TokenRequest {
        TODO("Not yet implemented")
    }

    actual val authorizationCode: String?
        get() = TODO("Not yet implemented")
    actual val idToken: String?
        get() = TODO("Not yet implemented")
    actual val scope: String?
        get() = TODO("Not yet implemented")
}
