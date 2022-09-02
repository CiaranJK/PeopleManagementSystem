package com.ciarankieran.core.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@PropertySource("classpath:strings.properties")
class LoadResourceAsString
 {
     @Value("\${bad-input-response}")
     val badInputResponse: String? = null

     @Value("\${no-resource-response}")
     val noResourceResponse: String? = null

     @Value("\${successful-input-response}")
     val successfulInputResponse: String? = null
}

/***
 * This class defines the strings.properties file as a Property Source to be configured at runtime.
 * and configures the string values that are used repeatedly in other classes.
 ***/
