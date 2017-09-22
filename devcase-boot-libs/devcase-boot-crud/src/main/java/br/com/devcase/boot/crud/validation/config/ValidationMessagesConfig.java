package br.com.devcase.boot.crud.validation.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractResourceBasedMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class ValidationMessagesConfig {

	@Bean
	@ConditionalOnBean(value=MessageSource.class)
	public LocalValidatorFactoryBean defaultValidator(MessageSource messageSource) {
		LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
		if(messageSource instanceof AbstractResourceBasedMessageSource) {
			((AbstractResourceBasedMessageSource) messageSource).addBasenames("ValidationMessages");
		} else {
			ResourceBundleMessageSource messageSource2 = new ResourceBundleMessageSource();
			messageSource2.setBasename("ValidationMessages");
			messageSource2.setParentMessageSource(messageSource);
			messageSource = messageSource2;
		}
		factoryBean.setValidationMessageSource(messageSource);
		return factoryBean;
	}

}