package org.simple4j.apiaopvalidator.validation;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * Validator implementation that checks if its a valid time zone id.
 * 
 * @author jsrinivas108
 *
 */
public class TimeZoneValidator implements FieldValidator, Validator<Object>
{

    public static final String TZ_CHECK_VALIDATION_SUFFIX = "-invalid";

    /**
     * Whether the value will be trimmed before applying validation.
     */
    private boolean trim = false;

    public TimeZoneValidator()
    {
        super();
    }
    
    @Override
    public List<String> validate(String fieldName, Object value)
    {
        if(value != null)
        {
            
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

        	if(!ZoneId.getAvailableZoneIds().contains(strValue))
        	{
            	List<String> ret = new ArrayList<String>();
	        	ret.add(fieldName + TZ_CHECK_VALIDATION_SUFFIX);
	            return ret;
        	}
        }
        return null;
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
		builder.append(super.toString()).append(" [trim=").append(trim).append("]");
		return builder.toString();
	}

}
