package dev.gitlive.appauth.session

import cocoapods.AppAuth.OIDEndSessionRequest
import dev.gitlive.appauth.authorization.AuthorizationServiceConfiguration
import platform.Foundation.NSURL

actual class EndSessionRequest internal constructor(internal val ios: OIDEndSessionRequest) {
    actual constructor(
        config: AuthorizationServiceConfiguration,
        idTokenHint: String?,
        postLogoutRedirectUri: String?,
    ) : this(
        OIDEndSessionRequest(
            configuration = config.ios,
            idTokenHint = idTokenHint,
            postLogoutRedirectURL = postLogoutRedirectUri?.let { NSURL.URLWithString(it) },
            additionalParameters = null
        )
    )
}
