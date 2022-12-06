package dev.gitlive.appauth.token

import dev.gitlive.appauth.authorization.AuthorizationServiceConfiguration

expect class TokenRequest(
    config: AuthorizationServiceConfiguration,
    clientId: String,
    grantType: String,
    refreshToken: String? = null
)
