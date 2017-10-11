package com.example.bar.company

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm

import java.time.Instant

class TokenHelper {

    static String token(String scope, String subject = "testSubject") {
        return "Bearer " + JWT.create()
                              .withIssuer("testIssuer")
                              .withSubject(subject)
                              .withExpiresAt(Date.from(Instant.parse("2030-05-06T01:02:03Z")))
                              .withClaim("scope", scope)
                              .sign(Algorithm.none())
    }
}
