package org.simple4j.apiaopvalidator.validation;

import java.util.ArrayList;
import java.util.List;

/**
 * Validator implementation to check the length of a String field
 * 
 * @author jsrinivas108
 *
 */
public class MaxLengthValidator implements FieldValidator, Validator<Object>
{
    private static String validationTypeSuffix = "-maxlength";
    
    /**
     * This validator will fail if the length of the String is more than this maxLength configuration
     */
    private Integer maxLength = 0;

    /**
     * Whether the value will be trimmed before applying validation.
     */
    private boolean trim = false;

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
		builder.append(super.toString()).append(" [maxLength=").append(maxLength).append("]");
		return builder.toString();
	}


}
