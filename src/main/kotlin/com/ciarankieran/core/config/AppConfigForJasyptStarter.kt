package com.ciarankieran.core.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@PropertySource("classpath:encrypted.properties")
class AppConfigForJasyptStarter {
}

/***
 * This class defines the encrypted.properties file in resources as a Property Source to be configured at runtime.
 ***/

// The encrypted.properties file has not been published to Git to hide the password encryption details
// associated with the project.
