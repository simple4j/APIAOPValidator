package org.simple4j.apiaopvalidator.validation;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * Validator implementation that checks a String value against a regular expression
 * 
 * @author jsrinivas108
 *
 */
@JsonRootName(value = "RegExpValidator")
public class RegExpValidator implements FieldValidator, Validator<Object>
{
    private static String validationTypeSuffix = "-invalid";
    
    /**
     * This validator will fail if the String doesnot match regExp configuration
     */
    private String regExp = null;
    
    public RegExpValidator()
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
        
        if(!strValue.matches(this.getRegExp()))
        {
        	List<String> ret = new ArrayList<String>();
        	ret.add(fieldName + this.validationTypeSuffix);
            return ret;
        }
        return null;
    }

    public String getRegExp()
    {
        return regExp;
    }

    public void setRegExp(String regExp)
    {
        this.regExp = regExp;
    }

    @Override
    public String toString()
    {
        return "RegExpValidator [regExp=" + regExp + "]";
    }

}
