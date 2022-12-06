package dev.gitlive.appauth.token

import cocoapods.AppAuth.OIDTokenRequest
import dev.gitlive.appauth.authorization.AuthorizationServiceConfiguration

actual class TokenRequest internal constructor(internal val ios: OIDTokenRequest) {
    actual constructor(
        config: AuthorizationServiceConfiguration,
        clientId: String,
        grantType: String,
        refreshToken: String?
    ) : this(
        OIDTokenRequest(
            configuration = config.ios,
            grantType = grantType,
            authorizationCode = null,
            redirectURL = null,
            clientID = clientId,
            clientSecret = null,
            scope = null,
            refreshToken = refreshToken,
            codeVerifier = null,
            additionalParameters = null
        )
    )
}
