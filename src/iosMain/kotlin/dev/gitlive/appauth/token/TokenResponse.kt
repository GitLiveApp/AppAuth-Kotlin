package dev.gitlive.appauth.token

import cocoapods.AppAuth.OIDTokenResponse

actual class TokenResponse internal constructor(internal val ios: OIDTokenResponse) {
    actual val idToken: String? get() = ios.idToken
    actual val accessToken: String? get() = ios.accessToken
    actual val refreshToken: String? get() = ios.refreshToken
}
