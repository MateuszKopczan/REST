package com.example.rest.domain.imdb.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import java.util.Map;

@Data
@Validated
@Configuration
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "key")
@ConfigurationProperties(prefix = "imdb.api")
public class IMDbProperties {

    private String key;
    private Map<String, String> urls;
}
