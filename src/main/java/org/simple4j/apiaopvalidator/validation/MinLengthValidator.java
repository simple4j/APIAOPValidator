package org.simple4j.apiaopvalidator.validation;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author jsrinivas108
 *
 */
public class MinLengthValidator implements FieldValidator, Validator<Object>
{
    private Integer minLength = 0;
    private static String validationTypeSuffix = "-minlength";
    
    public MinLengthValidator()
    {
        super();
    }

    @Override
    public List<String> validate(String fieldName, Object value)
    {
        if(value == null)
            return null;
        String strValue = null;
        if(value instanceof String)
        {
            strValue = ((String) value).trim();
            
        }
        else
        {
            strValue = "" + value;
        }
        
        if(value == null || strValue.length() < this.getMinLength())
        {
        	List<String> ret = new ArrayList<String>();
        	ret.add(fieldName + this.validationTypeSuffix);
            return ret;
        }
        return null;
    }

    public Integer getMinLength()
    {
        return minLength;
    }

    public void setMinLength(Integer minLength)
    {
        this.minLength = minLength;
    }

    @Override
    public String toString()
    {
        return "MinLengthValidator [minLength=" + minLength + "]";
    }

}
