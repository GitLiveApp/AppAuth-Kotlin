package dev.gitlive.appauth.session

import dev.gitlive.appauth.authorization.AuthorizationServiceConfiguration

expect class EndSessionRequest(
    config: AuthorizationServiceConfiguration,
    idTokenHint: String? = null,
    postLogoutRedirectUri: String? = null
)
