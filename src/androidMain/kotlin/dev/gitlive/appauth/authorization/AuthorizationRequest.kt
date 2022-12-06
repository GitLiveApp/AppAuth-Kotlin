package dev.gitlive.appauth.authorization

import android.net.Uri

actual class AuthorizationRequest private constructor(internal val android: net.openid.appauth.AuthorizationRequest) {
    actual constructor(
        config: AuthorizationServiceConfiguration,
        clientId: String,
        scopes: List<String>,
        responseType: String,
        redirectUri: String
    ) : this(
        net.openid.appauth.AuthorizationRequest.Builder(
            config.android,
            clientId,
            responseType,
            Uri.parse(redirectUri)
        )
            .setScopes(scopes)
            .build()
    )
}
