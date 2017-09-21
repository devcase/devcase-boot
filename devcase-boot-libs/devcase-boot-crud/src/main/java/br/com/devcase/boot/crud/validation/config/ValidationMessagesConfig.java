package br.com.devcase.boot.crud.validation.config;

import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.validation.MessageInterpolatorFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.context.support.AbstractResourceBasedMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class ValidationMessagesConfig {

	@Bean
	public static LocalValidatorFactoryBean defaultValidator(MessageSource messageSource) {
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