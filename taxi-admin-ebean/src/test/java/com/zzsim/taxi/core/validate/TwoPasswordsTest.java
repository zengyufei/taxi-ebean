package com.zzsim.taxi.core.validate;

import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Iterator;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * User: leochen
 * Date: 11-12-20
 * Time: 下午4:06
 */
public class TwoPasswordsTest {
    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }


    @Test
    public void testBuildDefaultValidatorFactory() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        assertNotNull(validator);
    }

    @Test
    public void testPasswordEqualsConfirmPassword() {
        TwoPasswords bean = new TwoPasswords();
        bean.setPassword("110");
        bean.setConfirmPassword("110");

        Set<ConstraintViolation<TwoPasswords>> constraintViolations = validator.validate(bean);
        for (ConstraintViolation<TwoPasswords> constraintViolation : constraintViolations) {
            System.out.println(constraintViolation.getMessage());
        }

        assertEquals("newPassword and confirmNewPassword should be the same.", 0, constraintViolations.size());
    }

    @Test
    public void testPasswordNotEqualsConfirmPassword() {
        TwoPasswords bean = new TwoPasswords();
        bean.setPassword("110");
        bean.setConfirmPassword("111");

        Set<ConstraintViolation<TwoPasswords>> constraintViolations = validator.validate(bean);

        assertEquals(1, constraintViolations.size());
        assertEquals("two password not the same", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void testIfTwoPasswordWereNullShouldPast() {
        TwoPasswords bean = new TwoPasswords();
        bean.setPassword(null);
        bean.setConfirmPassword(null);

        Set<ConstraintViolation<TwoPasswords>> constraintViolations = validator.validate(bean);

        Iterator<ConstraintViolation<TwoPasswords>> iterator = constraintViolations.iterator();
        while (iterator.hasNext()){
            ConstraintViolation<TwoPasswords> next = iterator.next();
            System.out.println(next.getMessage());
        }
    }

    @Test
    public void testIfOneIsNullAndOtherIsNotShouldNotPast() {
        TwoPasswords bean = new TwoPasswords();
        bean.setPassword(null);
        bean.setConfirmPassword("110");

        Set<ConstraintViolation<TwoPasswords>> constraintViolations = validator.validate(bean);

        assertEquals(1, constraintViolations.size());
        assertEquals("two password not the same", constraintViolations.iterator().next().getMessage());
    }
}  