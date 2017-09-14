package com.zzsim.taxi.admin.validate.factory;

import com.zzsim.taxi.admin.validate.annotation.Matche;
import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.regex.Pattern;

/**
 * User: leochen
 * Date: 11-12-8
 * Time: 下午11:39
 */
public class MatcheValidator implements ConstraintValidator<Matche, Object> {
	private String field;
	private String verifyField;
	private Boolean notNull;
	private Boolean notBlank;
	private Integer max;
	private Integer min;
	private String match;
	private Class<? extends Enum> enumType;

	@Override
	public void initialize(Matche matche) {
		this.field = matche.field();
		this.verifyField = matche.verifyField();
		this.notNull = matche.notNull();
		this.notBlank = matche.notBlank();
		this.max = matche.max();
		this.min = matche.min();
		this.match = matche.match();
		this.enumType = matche.enumType();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		try {
			Object fieldValue = value;
			String verifyFieldValue = null;
			boolean isEnum = false;

			if (value != null
					&& value instanceof Object && !(value instanceof CharSequence)
					&& !(value instanceof Number)) {
				try {
					Field declaredField = value.getClass().getDeclaredField(this.field);
					if(declaredField.getType().isEnum()){
						isEnum = true;
					}
					Matche annotation = declaredField.getAnnotation(Matche.class);
					if (declaredField.getName().equals(this.field) && annotation != null)
						return true;
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				}
				fieldValue = BeanUtils.getProperty(value, this.field);
			}

			if(this.verifyField != null && !"".equals(this.verifyField)){
				try {
					verifyFieldValue = BeanUtils.getProperty(value, this.verifyField);
				} catch (Exception e) {
				}
			}

			if (verifyFieldValue != null && !"".equals(verifyFieldValue)) {
				boolean isMatch = (fieldValue != null) && fieldValue.equals(verifyFieldValue);
				if (!isMatch) {
					String messageTemplate = context.getDefaultConstraintMessageTemplate();
					context.disableDefaultConstraintViolation();
					context.buildConstraintViolationWithTemplate(messageTemplate)
							.addNode(verifyField)
							.addConstraintViolation();
					return isMatch;
				}
			}

			if (this.notNull) {
				boolean isMatch = fieldValue != null;
				if (!isMatch) {
					String messageTemplate = context.getDefaultConstraintMessageTemplate();
					context.disableDefaultConstraintViolation();
					context.buildConstraintViolationWithTemplate(messageTemplate)
							.addNode(field)
							.addConstraintViolation();
					return isMatch;
				}
			}

			if (this.notBlank) {
				boolean isMatch = fieldValue != null && !"".equals(fieldValue);
				if (!isMatch) {
					String messageTemplate = context.getDefaultConstraintMessageTemplate();
					context.disableDefaultConstraintViolation();
					context.buildConstraintViolationWithTemplate(messageTemplate)
							.addNode(field)
							.addConstraintViolation();
					return isMatch;
				}
			}

			if (this.max < Integer.MAX_VALUE) {
				if(fieldValue == null)
					return true;
				boolean isMatch;
				try {
					isMatch = Integer.parseInt(String.valueOf(fieldValue)) <= this.max;
				} catch (NumberFormatException e) {
					isMatch = String.valueOf(fieldValue).length() <= this.max;
				}
				if (!isMatch) {
					String messageTemplate = context.getDefaultConstraintMessageTemplate();
					context.disableDefaultConstraintViolation();
					context.buildConstraintViolationWithTemplate(messageTemplate)
							.addNode(field)
							.addConstraintViolation();
					return isMatch;
				}
			}

			if (this.min < Integer.MAX_VALUE) {
				if(fieldValue == null)
					return true;
				boolean isMatch;
				try {
					isMatch = Integer.parseInt(String.valueOf(fieldValue)) >= this.min;
				} catch (NumberFormatException e) {
					isMatch = String.valueOf(fieldValue).length() >= this.min;
				}
				if (!isMatch) {
					String messageTemplate = context.getDefaultConstraintMessageTemplate();
					context.disableDefaultConstraintViolation();
					context.buildConstraintViolationWithTemplate(messageTemplate)
							.addNode(field)
							.addConstraintViolation();
					return isMatch;
				}
			}

			if (this.match != null) {
				boolean isMatch = fieldValue != null && Pattern.compile(this.match).matcher(String.valueOf(fieldValue)).find();
				if (!isMatch) {
					String messageTemplate = context.getDefaultConstraintMessageTemplate();
					context.disableDefaultConstraintViolation();
					context.buildConstraintViolationWithTemplate(messageTemplate)
							.addNode(field)
							.addConstraintViolation();
					return isMatch;
				}
			}

			if (isEnum && this.enumType != null) {
				boolean isMatch = false;
				if(fieldValue != null){
					Enum[] enumConstants = this.enumType.getEnumConstants();
					for (Enum enumConstant : enumConstants) {
						if(enumConstant.name().equals(fieldValue)){
							isMatch = true;
							continue;
						}
					}
				}
				if (!isMatch) {
					String messageTemplate = context.getDefaultConstraintMessageTemplate();
					context.disableDefaultConstraintViolation();
					context.buildConstraintViolationWithTemplate(messageTemplate)
							.addNode(field)
							.addConstraintViolation();
					return isMatch;
				}
			}


		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return true;
	}
}  