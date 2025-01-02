package com.jerryokafor.smshare.graphql

import com.apurebase.kgraphql.Context
import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.auth0.jwk.JwkProvider
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.jerryokafor.smshare.model.LoginInput
import com.jerryokafor.smshare.model.User
import com.jerryokafor.smshare.model.UserProfile
import com.jerryokafor.smshare.model.UserSetting
import com.jerryokafor.smshare.repository.DefaultUserRepository
import io.ktor.server.application.ApplicationEnvironment
import io.ktor.server.plugins.NotFoundException
import org.mindrot.jbcrypt.BCrypt
import java.security.KeyFactory
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.util.Base64
import java.util.Date

fun SchemaBuilder.smsShareScheme(
    environment: ApplicationEnvironment,
    userRepository: DefaultUserRepository,
    jwkProvider: JwkProvider
) {
    val secret = environment.config.property("jwt.secret").getString()
    val privateKeyString = environment.config.property("jwt.privateKey").getString()
    val publicKeyId = environment.config.property("jwt.publicKeyId").getString()
    val issuer = environment.config.property("jwt.issuer").getString()
    val audience = environment.config.property("jwt.audience").getString()
    val myRealm = environment.config.property("jwt.realm").getString()

    fun createAccessToken(claim: String): String? {
        val publicKey = jwkProvider.get(publicKeyId).publicKey
        val keySpecPKCS8 = PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyString))
        val privateKey = KeyFactory.getInstance("RSA").generatePrivate(keySpecPKCS8)

        return JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("claim", claim)
            .withExpiresAt(Date(System.currentTimeMillis() + 60000))
            .sign(Algorithm.RSA256(publicKey as RSAPublicKey, privateKey as RSAPrivateKey))
    }


    mutation("createUser") {
        description = "Creates a new user"

        resolver { email: String, password: String ->
//            userRepository.createUer(email, password)
            User(token = createAccessToken(email), email = email, password = password)
        }

    }

    mutation("login") {
        description = "Login an existing user"
        resolver { userName: String, password: String ->
            val user = userRepository.getUserByEmailOrUserName(userName)
                ?: throw NotFoundException("User with username or email does not exist")

            if (!BCrypt.checkpw(
                    /* plaintext = */ password,
                    /* hashed = */ user.password
                )
            ) throw NotFoundException("Invalid login credentials, please try again")

            user.copy(token = createAccessToken(userName))
        }
    }

    query("user") {
        description = "Returns users profile"
        resolver { ctx: Context -> User() }
    }

    type<User> {
        description = "A user on the app"

        property("profile") {
            resolver { ctx -> UserProfile() }

        }

        property("settings") {
            resolver { ctx -> UserSetting() }
        }
    }

    /*Handle login input*/
    inputType<LoginInput>()

}
