package com.example.demo

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InjectionPoint
import org.springframework.beans.factory.BeanCreationException
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import org.springframework.web.client.RestTemplate


@Configuration
class Config {
    @Bean
    fun getRestTemplate() = RestTemplate()

    @Bean
    @Scope("prototype")
    fun logger(injectionPoint: InjectionPoint) : Logger {
        return LoggerFactory.getLogger(
            injectionPoint.methodParameter?.containingClass
            ?: injectionPoint.field?.declaringClass
            ?: throw BeanCreationException("Cannot find type for Logger"))
    }

    @Bean
    fun funcUrl() : String {
        return System.getenv("FUNC_URL") ?: "NOT FOUND"
    }

    @Bean
    fun apimUrl() : String {
        return System.getenv("APIM_URL") ?: "NOT FOUND"
    }
}