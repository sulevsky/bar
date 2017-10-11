package com.example.bar.company

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.spring.security.api.JwtWebSecurityConfigurer
import com.auth0.spring.security.api.authentication.JwtAuthentication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException

@Configuration
class TestConfiguration {
    @Bean
    @Primary
    JwtWebSecurityConfigurer testJwtWebSecurityConfigurer() {


        AuthenticationProvider authenticationProvider = new AuthenticationProvider() {
            private JWTVerifier jwtVerifier = JWT.require(Algorithm.none()).build()

            @Override
            Authentication authenticate(Authentication authentication) throws AuthenticationException {
                return ((JwtAuthentication) authentication).verify(jwtVerifier)
            }

            @Override
            boolean supports(Class<?> authentication) {
                return JwtAuthentication.class.isAssignableFrom(authentication)
            }
        }

        return JwtWebSecurityConfigurer.forRS256("testAudience", "testIssuer", authenticationProvider)
    }
}
