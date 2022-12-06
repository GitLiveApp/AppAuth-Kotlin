package dev.gitlive.appauth.authorization

import dev.gitlive.appauth.token.TokenRequest

actual class AuthorizationResponse internal constructor(private val android: net.openid.appauth.AuthorizationResponse) {
    actual fun createTokenExchangeRequest() = TokenRequest(android.createTokenExchangeRequest())
    actual val idToken get() = android.idToken
    actual val scope get() = android.scope
    actual val authorizationCode get() = android.authorizationCode
}
