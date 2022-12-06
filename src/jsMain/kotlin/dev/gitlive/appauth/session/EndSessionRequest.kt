package dev.gitlive.appauth.session

import dev.gitlive.appauth.authorization.AuthorizationServiceConfiguration

actual class EndSessionRequest actual constructor(
    config: AuthorizationServiceConfiguration,
    idTokenHint: String?,
    postLogoutRedirectUri: String?
)
