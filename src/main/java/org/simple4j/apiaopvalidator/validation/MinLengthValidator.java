package org.simple4j.apiaopvalidator.validation;

import java.util.ArrayList;
import java.util.List;

/**
 * Validator implementation to check the length of a String field
 * 
 * @author jsrinivas108
 *
 */
public class MinLengthValidator implements FieldValidator, Validator<Object>
{
    private static String validationTypeSuffix = "-minlength";
    
    /**
     * This validator will fail if the length of the String is less than this minLength configuration
     */
    private Integer minLength = 0;

    /**
     * Whether the value will be trimmed before applying validation.
     */
    private boolean trim = false;

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
            strValue = ((String) value);
            
        }
        else
        {
            strValue = "" + value;
        }
        
        if(this.isTrim())
        {
        	strValue = strValue.trim();
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

	public boolean isTrim()
	{
		return trim;
	}

	public void setTrim(boolean trim)
	{
		this.trim = trim;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString()).append(" [minLength=").append(minLength).append("]");
		return builder.toString();
	}


}
