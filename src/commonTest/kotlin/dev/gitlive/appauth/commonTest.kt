package dev.gitlive.appauth

import dev.gitlive.appauth.session.EndSessionRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

expect val context: Any

expect fun simulateSignIn()

expect suspend fun CoroutineScope.withAuthorizationService(action: suspend (service: AuthorizationService) -> Unit)

@OptIn(ExperimentalCoroutinesApi::class)
class AuthorizationServiceTest {
//    @Test
    fun testFetchFromIssuer() = runTest {
        val actual = AuthorizationServiceConfiguration
            .fetchFromIssuer("https://oauth-server.com/auth/realms/MyRealm")
        assertEquals(
            "https://oauth-server.com/auth/realms/MyRealm/protocol/openid-connect/auth",
            actual.authorizationEndpoint
        )
    }

    @Test
    fun testPerformAuthorizationRequest() = runTest {
        val config = AuthorizationServiceConfiguration(
            "https://oauth-server.com/auth/realms/MyRealm/protocol/openid-connect/auth",
            "https://oauth-server.com/auth/realms/MyRealm/protocol/openid-connect/token",
        )
        val request = AuthorizationRequest(
            config,
            "MyClient",
            listOf("profile"),
            "code",
            "myapp://oauth2redirect"
        )
        withAuthorizationService { service ->
            val actual = async(Dispatchers.Main) { service.performAuthorizationRequest(request) }
            simulateSignIn()
            assertNotNull(actual.await().authorizationCode)
        }
    }

//    @Test
    fun testPerformTokenRequest() = runTest {
        val config = AuthorizationServiceConfiguration(
            "https://oauth-server.com/auth/realms/MyRealm/protocol/openid-connect/auth",
            "https://oauth-server.com/auth/realms/MyRealm/protocol/openid-connect/token",
        )
        val request = AuthorizationRequest(
            config,
            "MyClient",
            listOf("profile"),
            "code",
            "myapp://oauth2redirect"
        )
        withAuthorizationService { service ->
            val response = async(Dispatchers.Main) { service.performAuthorizationRequest(request) }
//            simulateSignIn()
            val actual = service.performTokenRequest(response.await().createTokenExchangeRequest())
            assertNotNull(actual.accessToken)
        }
    }

//    @Test
    fun testPerformEndSessionRequest() = runTest {
        val config = AuthorizationServiceConfiguration(
            "https://oauth-server.com/auth/realms/MyRealm/protocol/openid-connect/auth",
            "https://oauth-server.com/auth/realms/MyRealm/protocol/openid-connect/token",
            endSessionEndpoint =
            "https://oauth-server.com/auth/realms/MyRealm/protocol/openid-connect/logout",
        )
        val request = EndSessionRequest(
            config,
            postLogoutRedirectUri = "myapp://oauth2redirect"
        )
        withAuthorizationService { service ->
            service.performEndSessionRequest(request)
        }
    }
}
