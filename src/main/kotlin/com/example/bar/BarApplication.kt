package com.example.bar

import com.auth0.spring.security.api.JwtWebSecurityConfigurer
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.stereotype.Component

@SpringBootApplication
class BarApplication

fun main(args: Array<String>) {
    SpringApplication.run(BarApplication::class.java, *args)
}

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
class SecurityConfiguration {
    @Bean
    fun jwtWebSecurityConfigurer(auth0Config: Auth0Config): JwtWebSecurityConfigurer {
        return JwtWebSecurityConfigurer.forRS256(auth0Config.audience,
                                                 auth0Config.issuer)
    }
}

@Component
class SecurityConfigurer(val jwtWebSecurityConfigurer: JwtWebSecurityConfigurer) : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity?) {
        jwtWebSecurityConfigurer.configure(http)
                .authorizeRequests()
                .anyRequest()
                .permitAll()
    }
}

@Configuration
@ConfigurationProperties(prefix = "auth0")
data class Auth0Config(
        var audience: String = "",
        var issuer: String = ""
)