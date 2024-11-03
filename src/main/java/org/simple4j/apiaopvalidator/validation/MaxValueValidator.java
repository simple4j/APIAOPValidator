package org.simple4j.apiaopvalidator.validation;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author jsrinivas108
 *
 */
public class MaxValueValidator implements FieldValidator, Validator<Object>
{
    private double maxValue = 0;
    private static String validationTypeSuffix = "-maxvalue";
    
    public MaxValueValidator()
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
            dblValue = Double.parseDouble("" + value);
        }
        
        if(dblValue > this.getMaxValue())
        {
        	List<String> ret = new ArrayList<String>();
        	ret.add(fieldName + this.validationTypeSuffix);
            return ret;
        }
        return null;
    }

    public double getMaxValue()
    {
        return maxValue;
    }

    public void setMaxValue(double maxValue)
    {
        this.maxValue = maxValue;
    }

    @Override
    public String toString()
    {
        return "MaxValueValidator [maxValue=" + maxValue + "]";
    }

}
