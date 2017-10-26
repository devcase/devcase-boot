package br.com.devcase.boot.labels;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value="classpath:devcase/application-devcaselabels.properties")
public class LabelsConfig {
}
