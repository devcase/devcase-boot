package br.com.devcase.boot.sample.validation;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

import br.com.devcase.boot.crud.validation.config.ValidationMessagesConfig;
import br.com.devcase.boot.labels.EnableDevcaseLabels;

@SpringBootApplication
@Import(ValidationMessagesConfig.class)
@EnableDevcaseLabels
public class SampleValidationApplication implements CommandLineRunner {
	
	@Autowired
	private Validator validator;
	@Autowired
	private ConfigurableApplicationContext applicationContext;

	public static void main(String[] args) {
		SpringApplication.run(SampleValidationApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ConfigurableListableBeanFactory bf = applicationContext.getBeanFactory();
		String[] validatorNames = bf.getBeanNamesForType(Validator.class);
		for (String validatorBeanName : validatorNames) {
			BeanDefinition bd = bf.getBeanDefinition(validatorBeanName);
			System.out.println(bd);
		}
		
		Example bean = Example.builder().withFieldNotNull(null).withMaxTen(1115L).withNotNullWithCustomMessage(null).build();
		
		Set<ConstraintViolation<Example>> errors;
		Locale.setDefault(Locale.US);
		System.out.println("-----------" + Locale.getDefault());
		errors = validator.validate(bean);
		for (ConstraintViolation<Example> constraintViolation : errors) {
			System.out.println(constraintViolation.getPropertyPath() + " --> " + constraintViolation.getMessage());
		}
		
		Locale.setDefault(Locale.GERMANY);
		System.out.println("-----------" + Locale.getDefault());
		errors = validator.validate(bean);
		for (ConstraintViolation<Example> constraintViolation : errors) {
			System.out.println(constraintViolation.getPropertyPath() + " --> " + constraintViolation.getMessage());
		}

		Locale.setDefault(new Locale("pt", "BR"));
		System.out.println("-----------" + Locale.getDefault());
		errors = validator.validate(bean);
		for (ConstraintViolation<Example> constraintViolation : errors) {
			System.out.println(constraintViolation.getPropertyPath() + " --> " + constraintViolation.getMessage());
		}

	}

}
