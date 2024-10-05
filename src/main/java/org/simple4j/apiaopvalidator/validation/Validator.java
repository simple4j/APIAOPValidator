package org.simple4j.apiaopvalidator.validation;

/**
 * 
 * @author jsrinivas108
 *
 */
public abstract class Validator
{

    protected String validationTypeSuffix = null;
    
    abstract public String validate(String fieldName, Object value);
}
