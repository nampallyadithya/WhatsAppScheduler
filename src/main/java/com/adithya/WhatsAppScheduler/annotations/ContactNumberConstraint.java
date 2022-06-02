package com.adithya.WhatsAppScheduler.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.validation.Payload;

@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ContactNumberConstraint {
	
	
	String message() default "Invalid contact number";
    //represents group of constraints
    Class<?>[] groups() default {};
    //represents additional information about annotation
    Class<? extends Payload>[] payload() default {};


}
