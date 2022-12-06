package dev.gitlive.appauth.authorization

import cocoapods.AppAuth.OIDAuthorizationRequest
import platform.Foundation.NSURL

actual class AuthorizationRequest private constructor(internal val ios: OIDAuthorizationRequest) {

    actual constructor(
        config: AuthorizationServiceConfiguration,
        clientId: String,
        scopes: List<String>,
        responseType: String,
        redirectUri: String
    ) : this(
        OIDAuthorizationRequest(
            configuration = config.ios,
            clientId = clientId,
            scopes = scopes,
            redirectURL = NSURL.URLWithString(redirectUri)!!,
            responseType = responseType,
            additionalParameters = null
        )
    )
}
