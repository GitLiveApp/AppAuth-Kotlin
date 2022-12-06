package dev.gitlive.appauth.authorization

import cocoapods.AppAuth.OIDAuthorizationResponse
import dev.gitlive.appauth.token.TokenRequest

actual class AuthorizationResponse internal constructor(internal val ios: OIDAuthorizationResponse) {
    actual val authorizationCode: String? get() = ios.authorizationCode
    actual val idToken: String? get() = ios.idToken
    actual val scope get() = ios.scope
    actual fun createTokenExchangeRequest() = TokenRequest(ios.tokenExchangeRequest()!!)
}
