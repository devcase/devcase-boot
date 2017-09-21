package br.com.devcase.boot.crud.validation.constraints;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import br.com.devcase.boot.crud.validation.validator.PropertyNotNullValidator;

@Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = PropertyNotNullValidator.class)
@Documented
@Repeatable(PropertyNotNull.List.class)
public @interface PropertyNotNull {
	
    String message() default "{br.com.devcase.boot.crud.validation.constraints.PropertyNotNull." +
            "message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String value() default "id";

	@Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE })
    @Retention(RUNTIME)
    @Documented
    @interface List {
    	PropertyNotNull[] value();
    }

}
