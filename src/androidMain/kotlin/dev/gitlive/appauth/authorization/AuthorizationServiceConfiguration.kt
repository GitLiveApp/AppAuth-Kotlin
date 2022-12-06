package dev.gitlive.appauth.authorization

import android.net.Uri
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

actual class AuthorizationServiceConfiguration private constructor(
    val android: net.openid.appauth.AuthorizationServiceConfiguration
) {

    actual constructor(
        authorizationEndpoint: String,
        tokenEndpoint: String,
        registrationEndpoint: String?,
        endSessionEndpoint: String?
    ) : this(
        net.openid.appauth.AuthorizationServiceConfiguration(
            Uri.parse(authorizationEndpoint),
            Uri.parse(tokenEndpoint),
            registrationEndpoint?.let { Uri.parse(it) },
            endSessionEndpoint?.let { Uri.parse(it) },
        )
    )

    actual companion object {
        actual suspend fun fetchFromIssuer(url: String): AuthorizationServiceConfiguration =
            suspendCoroutine { cont ->
                net.openid.appauth.AuthorizationServiceConfiguration
                    .fetchFromIssuer(Uri.parse(url)) { serviceConfiguration, ex ->
                        serviceConfiguration?.let {
                            cont.resume(AuthorizationServiceConfiguration(it))
                        }
                            ?: cont.resumeWithException(ex!!.wrapIfNecessary())
                    }
            }
    }

    actual val authorizationEndpoint get() = android.authorizationEndpoint.toString()
    actual val tokenEndpoint get() = android.tokenEndpoint.toString()
    actual val registrationEndpoint get() = android.registrationEndpoint?.toString()
    actual val endSessionEndpoint get() = android.endSessionEndpoint?.toString()
}
