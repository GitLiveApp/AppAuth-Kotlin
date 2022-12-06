package dev.gitlive.appauth.authorization

actual class AuthorizationRequest actual constructor(
    config: AuthorizationServiceConfiguration,
    clientId: String,
    scopes: List<String>,
    responseType: String,
    redirectUri: String
)
