package dev.gitlive.appauth

import cocoapods.AppAuth.OIDErrorCodeNetworkError
import cocoapods.AppAuth.OIDGeneralErrorDomain
import dev.gitlive.appauth.authorization.AuthorizationException
import io.ktor.utils.io.errors.IOException
import platform.Foundation.NSError

// wrap network errors in an IOException so it matches ktor
internal fun NSError.toException() = when (domain) {
    OIDGeneralErrorDomain -> when (code) {
        OIDErrorCodeNetworkError -> IOException(localizedDescription)
        else -> AuthorizationException(localizedDescription)
    }
    else -> AuthorizationException(localizedDescription)
}
