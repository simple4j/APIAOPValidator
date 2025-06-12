package org.simple4j.apiaopvalidator.validation;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Validator implementation that checks a String value against a regular expression
 * 
 * @author jsrinivas108
 *
 */
public class RegExpValidator implements FieldValidator, Validator<Object>
{
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    
    private static String validationTypeSuffix = "-invalid";
    
    /**
     * This validator will fail if the String does not match regExp configuration.
     * If the negation is set to true, validator will fail if the pattern matches.
     */
    private String regExp = null;
    
    /**
     * This boolean will reverse the matching rule when set to true.
     * See documentation for regExp property
     */
    private boolean negation = false;

    /**
     * This property will define if the matching will be case sensitive or not.
     * By default, it will be case sensitive.
     */
    private boolean casesensitive = true;
    
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
            strValue =(String) value;
            
        }
        else
        {
            strValue = "" + value;
        }
        
        LOGGER.debug("match {}", match(strValue));
        if( !this.negation && !match(strValue) )
        {
        	List<String> ret = new ArrayList<String>();
        	ret.add(fieldName + this.validationTypeSuffix);
            return ret;
        }
        
        if( this.negation && match(strValue) )
        {
        	List<String> ret = new ArrayList<String>();
        	ret.add(fieldName + this.validationTypeSuffix);
            return ret;
        }
        return null;
    }

    private Pattern pattern = null;
    
	private boolean match(String strValue)
	{
		if(this.pattern == null)
		{
			if(this.isCasesensitive())
				pattern = Pattern.compile(this.getRegExp());
			else
				pattern = Pattern.compile(this.getRegExp(), Pattern.CASE_INSENSITIVE);
		}
		
        return pattern.matcher(strValue).matches();
	}

    public String getRegExp()
    {
    	if(this.regExp == null || this.regExp.trim().length() < 1)
    		throw new RuntimeException("regexp not configured");
        return regExp;
    }

    public void setRegExp(String regExp)
    {
        this.regExp = regExp;
    }

    public boolean isNegation()
	{
		return negation;
	}

	public void setNegation(boolean negation)
	{
		this.negation = negation;
	}

	public boolean isCasesensitive()
	{
		return casesensitive;
	}

	public void setCasesensitive(boolean casesensitive)
	{
		this.casesensitive = casesensitive;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString()).append(" [regExp=").append(regExp).append(", negation=").append(negation)
				.append(", casesensitive=").append(casesensitive).append("]");
		return builder.toString();
	}


}
