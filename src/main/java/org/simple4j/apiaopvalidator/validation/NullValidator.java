package org.simple4j.apiaopvalidator.validation;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author jsrinivas108
 *
 */
public class NullValidator implements FieldValidator, Validator<Object>
{

    public static final String NULL_CHECK_VALIDATION_SUFFIX = "-missing";
    public NullValidator()
    {
        super();
    }
    
    @Override
    public List<String> validate(String fieldName, Object value)
    {
    	List<String> ret = null;
        if(value == null)
        {
        	ret = new ArrayList<String>();
        	ret.add(fieldName + this.NULL_CHECK_VALIDATION_SUFFIX);
            return ret;
        }
        if(value instanceof String && ((String)value).trim().length() == 0)
        {
        	ret = new ArrayList<String>();
        	ret.add(fieldName + this.NULL_CHECK_VALIDATION_SUFFIX);
            return ret;
        }
        return ret;
    }

}
