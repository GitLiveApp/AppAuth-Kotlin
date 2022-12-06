package dev.gitlive.appauth.authorization

expect class AuthorizationRequest(
    config: AuthorizationServiceConfiguration,
    clientId: String,
    scopes: List<String>,
    responseType: String,
    redirectUri: String
)
