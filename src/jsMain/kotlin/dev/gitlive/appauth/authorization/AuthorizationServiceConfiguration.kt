package dev.gitlive.appauth.authorization

actual class AuthorizationServiceConfiguration actual constructor(
    authorizationEndpoint: String,
    tokenEndpoint: String,
    registrationEndpoint: String?,
    endSessionEndpoint: String?
) {
    actual companion object {
        actual suspend fun fetchFromIssuer(url: String): AuthorizationServiceConfiguration {
            TODO("Not yet implemented")
        }
    }

    actual val authorizationEndpoint: String
        get() = TODO("Not yet implemented")
    actual val tokenEndpoint: String
        get() = TODO("Not yet implemented")
    actual val registrationEndpoint: String?
        get() = TODO("Not yet implemented")
    actual val endSessionEndpoint: String?
        get() = TODO("Not yet implemented")
}
