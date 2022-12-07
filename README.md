# AppAuth-Kotlin

<h1 align="left">AppAuth-Kotlin<img alt="GitHub last commit" src="https://img.shields.io/github/last-commit/gitliveapp/AppAuth-Kotlin?style=flat-square"> <a href="https://git.live"><img src="https://img.shields.io/badge/collaborate-on%20gitlive-blueviolet?style=flat-square"></a></h1>
<img align="left" width="75px" src="https://avatars2.githubusercontent.com/u/42865805?s=200&v=4"> 
  <b>Built and maintained with 🧡 by <a href="https://git.live">GitLive</a></b><br/>
  <i>Real-time code collaboration inside any IDE</i><br/>
<br/>
<br/>
The AppAuth-Kotlin SDK is a Kotlin-first SDK for AppAuth. It's API is similar to the <a href="https://github.com/openid/AppAuth-Android">Open ID AppAuth Android</a> but also supports multiplatform projects, enabling you to use AppAuth directly from your common source targeting <strong>iOS</strong>, <strong>Android</strong> or <strong>JS</strong>.

## Installation

To install simply add to your common sourceset in the build gradle

```kotlin
    implementation("dev.gitlive:appauth-kotlin:0.0.1")
```

Perform a gradle refresh and you should then be able to import the app auth files.

## Useage

```kotlin
val config = AuthorizationServiceConfiguration(
    authorizationEndpoint = "https://endpoint/oauth/authorize",
    tokenEndpoint = "https://endpoint/oauth/token",
    endSessionEndpoint = "https://endpoint/oauth/logout"
)
val request = AuthorizationRequest(
    config,
    "CLIENT_ID",
    listOf("openid", "profile", "member"),
    "code",
    "callback://oauth/callback"
)
try {
    val response = authorizationService.performAuthorizationRequest(request)
    tokenRequest.emit(response.createTokenExchangeRequest())
} catch (exception: AuthorizationException) {
    println("User attempted to cancel login")
}
```
