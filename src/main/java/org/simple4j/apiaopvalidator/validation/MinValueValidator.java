package org.simple4j.apiaopvalidator.validation;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author jsrinivas108
 *
 */
public class MinValueValidator implements FieldValidator, Validator<Object>
{
    private double minValue = 0;
    private static String validationTypeSuffix = "-minvalue";
    
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
            if(value != null)
                dblValue = Double.parseDouble("" + value);
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

    @Override
    public String toString()
    {
        return "MinValueValidator [minValue=" + minValue + "]";
    }

}
