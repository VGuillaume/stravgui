package io.stravgui.infrastructure.adapter.out.strava;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(StravaProperties.class)
class StravaPropertiesConfig {
}