package org.simple4j.apiaopvalidator.validation;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author jsrinivas108
 *
 */
public class MaxLengthValidator implements FieldValidator, Validator<Object>
{
    private Integer maxLength = 0;
    private static String validationTypeSuffix = "-maxlength";
    
    public MaxLengthValidator()
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
        
        if(strValue.length() > this.getMaxLength())
        {
        	List<String> ret = new ArrayList<String>();
        	ret.add(fieldName + this.validationTypeSuffix);
            return ret;
        }
        return null;
    }

    public Integer getMaxLength()
    {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength)
    {
        this.maxLength = maxLength;
    }

    @Override
    public String toString()
    {
        return "MaxLengthValidator [maxLength=" + maxLength + "]";
    }

}
