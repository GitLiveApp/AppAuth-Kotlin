package dev.gitlive.appauth.authorization

import io.ktor.utils.io.errors.IOException
import net.openid.appauth.AuthorizationException

actual typealias AuthorizationException = AuthorizationException

// wrap network errors in an IOException so it matches ktor
fun AuthorizationException.wrapIfNecessary() =
    takeUnless { it == AuthorizationException.GeneralErrors.NETWORK_ERROR }
        ?: IOException(message, this)
