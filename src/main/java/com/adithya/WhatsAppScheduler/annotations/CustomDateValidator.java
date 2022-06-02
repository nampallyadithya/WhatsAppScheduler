package com.adithya.WhatsAppScheduler.annotations;

import java.time.LocalDateTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CustomDateValidator implements
ConstraintValidator<CustomDateConstraint, String>{

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		try {
            LocalDateTime.parse(value);
        } catch (Exception e) {
            return false;
        }
        return true;


	}

}
