package dev.gitlive.appauth.token

expect class TokenResponse {
    val idToken: String?
    val accessToken: String?
    val refreshToken: String?
}
