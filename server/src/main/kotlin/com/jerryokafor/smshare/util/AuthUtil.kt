package com.jerryokafor.smshare.util

import com.auth0.jwk.JwkProvider
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
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

class AuthUtil(
    environment: ApplicationEnvironment,
    private val userRepository: DefaultUserRepository,
    private val jwkProvider: JwkProvider
) {

    private val secret = environment.config.property("jwt.secret").getString()
    private val privateKeyString = environment.config.property("jwt.privateKey").getString()
    private val publicKeyId = environment.config.property("jwt.publicKeyId").getString()
    private val issuer = environment.config.property("jwt.issuer").getString()
    private val audience = environment.config.property("jwt.audience").getString()
    private val myRealm = environment.config.property("jwt.realm").getString()

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

    suspend fun checkPassword(userName: String, password: String): Boolean {
        val user = userRepository.getUserByEmailOrUserName(userName)
            ?: throw NotFoundException("User with username or email does not exist")

        if (!BCrypt.checkpw(/* plaintext = */ password,/* hashed = */ user.password)
        ) throw NotFoundException("Invalid login credentials, please try again")

        return true
    }
}
