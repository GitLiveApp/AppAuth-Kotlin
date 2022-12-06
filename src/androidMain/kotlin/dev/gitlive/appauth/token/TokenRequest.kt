package dev.gitlive.appauth.token

import dev.gitlive.appauth.authorization.AuthorizationServiceConfiguration


actual class TokenRequest internal constructor(internal val android: net.openid.appauth.TokenRequest) {
    actual constructor(
        config: AuthorizationServiceConfiguration,
        clientId: String,
        grantType: String,
        refreshToken: String?
    ) : this(
        net.openid.appauth.TokenRequest.Builder(config.android, clientId).apply {
            setGrantType(grantType)
            refreshToken?.let { setRefreshToken(it) }
        }.build()
    )
}