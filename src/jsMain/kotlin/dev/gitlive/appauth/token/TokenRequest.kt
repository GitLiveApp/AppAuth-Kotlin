package dev.gitlive.appauth.token

import dev.gitlive.appauth.authorization.AuthorizationServiceConfiguration

actual class TokenRequest actual constructor(
    config: AuthorizationServiceConfiguration,
    clientId: String,
    grantType: String,
    refreshToken: String?
)
