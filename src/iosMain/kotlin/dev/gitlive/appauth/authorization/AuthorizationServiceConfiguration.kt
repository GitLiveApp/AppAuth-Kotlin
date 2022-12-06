package dev.gitlive.appauth.authorization

import cocoapods.AppAuth.OIDAuthorizationService
import cocoapods.AppAuth.OIDServiceConfiguration
import dev.gitlive.appauth.toException
import platform.Foundation.NSURL
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

actual class AuthorizationServiceConfiguration private constructor(val ios: OIDServiceConfiguration) {

    actual constructor(
        authorizationEndpoint: String,
        tokenEndpoint: String,
        registrationEndpoint: String?,
        endSessionEndpoint: String?
    ) : this(
        OIDServiceConfiguration(
            NSURL.URLWithString(authorizationEndpoint)!!,
            NSURL.URLWithString(tokenEndpoint)!!,
            null,
            registrationEndpoint?.let { NSURL.URLWithString(it) },
            endSessionEndpoint?.let { NSURL.URLWithString(it) }
        )
    )

    actual companion object {
        actual suspend fun fetchFromIssuer(url: String): AuthorizationServiceConfiguration = suspendCoroutine { cont ->
            OIDAuthorizationService.discoverServiceConfigurationForIssuer(NSURL.URLWithString(url)!!) { config, error ->
                config?.let { cont.resume(AuthorizationServiceConfiguration(it)) }
                    ?: cont.resumeWithException(error!!.toException())
            }
        }
    }

    actual val authorizationEndpoint: String get() = ios.authorizationEndpoint.relativeString
    actual val tokenEndpoint: String get() = ios.tokenEndpoint.relativeString
    actual val registrationEndpoint: String? get() = ios.registrationEndpoint?.relativeString
    actual val endSessionEndpoint: String? get() = ios.endSessionEndpoint?.relativeString
}
