package dev.gitlive.appauth.authorization

expect class AuthorizationServiceConfiguration(
    authorizationEndpoint: String,
    tokenEndpoint: String,
    registrationEndpoint: String? = null,
    endSessionEndpoint: String? = null,
) {
    val authorizationEndpoint: String
    val tokenEndpoint: String
    val registrationEndpoint: String?
    val endSessionEndpoint: String?

    companion object {
        suspend fun fetchFromIssuer(url: String): AuthorizationServiceConfiguration
    }
}
