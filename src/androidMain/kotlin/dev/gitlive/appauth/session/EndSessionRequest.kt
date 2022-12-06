package dev.gitlive.appauth.session

import android.net.Uri
import dev.gitlive.appauth.authorization.AuthorizationServiceConfiguration

actual class EndSessionRequest internal constructor(internal val android: net.openid.appauth.EndSessionRequest) {
    actual constructor(
        config: AuthorizationServiceConfiguration,
        idTokenHint: String?,
        postLogoutRedirectUri: String?
    ) : this(
        net.openid.appauth.EndSessionRequest.Builder(config.android).apply {
            idTokenHint?.let { setIdTokenHint(it) }
            postLogoutRedirectUri?.let { setPostLogoutRedirectUri(Uri.parse(postLogoutRedirectUri)) }
        }.build()
    )
}
