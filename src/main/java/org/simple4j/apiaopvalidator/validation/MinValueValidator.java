package org.simple4j.apiaopvalidator.validation;

import java.util.ArrayList;
import java.util.List;

/**
 * Validator implementation to check the value of an int or float field
 * 
 * @author jsrinivas108
 *
 */
public class MinValueValidator implements FieldValidator, Validator<Object>
{
    private static String validationTypeSuffix = "-minvalue";
    
    /**
     * This validator will fail if the value of the int or float is less than this minValue configuration
     */
    private double minValue = 0;

    /**
     * Whether the value will be trimmed before applying validation.
     */
    private boolean trim = false;

    public MinValueValidator()
    {
        super();
    }

    @Override
    public List<String> validate(String fieldName, Object value)
    {
        if(value == null)
            return null;
        Double dblValue = null;
        if(value instanceof Number)
        {
            dblValue = ((Number) value).doubleValue();
            
        }
        else
        {
            String strValue = "" + value;
            if(this.isTrim())
            {
            	strValue = strValue.trim();
            }

            if(value != null)
                dblValue = Double.parseDouble(strValue);
        }
        
        if(value == null || dblValue < this.getMinValue())
        {
        	List<String> ret = new ArrayList<String>();
        	ret.add(fieldName + this.validationTypeSuffix);
            return ret;
        }
        return null;
    }

    public double getMinValue()
    {
        return minValue;
    }

    public void setMinValue(double minValue)
    {
        this.minValue = minValue;
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
		builder.append(super.toString()).append(" [minValue=").append(minValue).append(", trim=").append(trim)
				.append("]");
		return builder.toString();
	}

}
