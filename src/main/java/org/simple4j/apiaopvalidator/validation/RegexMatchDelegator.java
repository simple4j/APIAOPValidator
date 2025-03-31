package org.simple4j.apiaopvalidator.validation;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Validator implementation to match a regular expression and delegate to child validators.
 * The regular expression will be matched against the value of a property on the object.
 * 
 * @author jsrinivas108
 *
 */
@SuppressWarnings("hiding")
public class RegexMatchDelegator implements Validator<Object>
{
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
    /**
     * boolean configuration to do null check as the first step.
     * Validation will fail if this config is true and the bean is null
     */
    private boolean doNullCheck = true;
    
    /**
     * Property path to be retrieved on which the regular expression gets matched
     */
    private String propertyPath = null;

    /**
     * Regular expression to be matched before delegating to the validators
     */
    private String matchRegex = null;

    /**
     * Regular expression to be not matched before delegating to the validators
     */
    private String notMatchRegex = null;
    
    /**
     * List of validator implementations that will be executed on the value retrieved based on propertyPath 
     */
    private List<Validator> validators = null;

    public List<String> validate(String fieldName, Object beanValue)
    {
    	LOGGER.info("Entering validate {} {}", this.getPropertyPath(), this.getMatchRegex());
    	LOGGER.debug("{} {}", fieldName, beanValue);
        ArrayList<String> ret = null;
    	if(beanValue == null)
    	{
            return null;
    	}
        Object fieldValue;
        try
        {
            //TODO: another option is to convert nested object tree to collections tree and use CollectionsPathRetreiver
            fieldValue = PropertyUtils.getNestedProperty(beanValue, this.getPropertyPath());
            
            if(this.getMatchRegex() != null && !(""+fieldValue).matches(this.getMatchRegex()))
            	return null;
            if(this.getNotMatchRegex() != null && (""+fieldValue).matches(this.getNotMatchRegex()))
            	return null;
        }
        catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e)
        {
            LOGGER.warn("Exception while getting nested property targetbean:{}, propertyPath:{}", beanValue, this.getPropertyPath(), e);
            throw new RuntimeException("Exception while getting nested property targetbean:"+beanValue+", propertyPath:"+this.getPropertyPath(), e);
        }
        
        if(getValidators() != null)
        {
            for (int i = 0 ; i < getValidators().size() ; i++)
            {
                List<String> result = getValidators().get(i).validate(fieldName, beanValue);
                if(result != null && result.size() > 0)
                {
                	if(getValidators().get(i) instanceof FieldValidator)
	                    return result;
                	else
                	{
                		if(ret == null)
                		{
                			ret = new ArrayList<String>();
                		}
                		ret.addAll(result);
                	}
                }
            }
        }
           
        return ret;
    }

    public boolean isDoNullCheck()
	{
		return doNullCheck;
	}

	public void setDoNullCheck(boolean doNullCheck)
	{
		this.doNullCheck = doNullCheck;
	}

	public List<Validator> getValidators()
    {
        return validators;
    }

    public void setValidators(List<Validator> validators)
    {
        this.validators = validators;
    }

	public String getPropertyPath()
    {
		if(this.propertyPath == null)
            throw new RuntimeException("propertyPath not configured");
        return propertyPath;
    }

    public void setPropertyPath(String propertyPath)
    {
        this.propertyPath = propertyPath;
    }

    public String getMatchRegex()
	{
		if(this.matchRegex == null && this.notMatchRegex == null)
            throw new RuntimeException("matchRegex not configured");
		return matchRegex;
	}

	public void setMatchRegex(String matchRegex)
	{
		this.matchRegex = matchRegex;
	}

	public String getNotMatchRegex()
	{
		if(this.matchRegex == null && this.notMatchRegex == null)
            throw new RuntimeException("notMatchRegex not configured");
		return notMatchRegex;
	}

	public void setNotMatchRegex(String notMatchRegex)
	{
		this.notMatchRegex = notMatchRegex;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString()).append(" [doNullCheck=").append(doNullCheck).append(", matchRegex=").append(matchRegex)
				.append(", notMatchRegex=").append(notMatchRegex)
				.append(", propertyPath=").append(propertyPath).append(", validators=").append(validators).append("]");
		return builder.toString();
	}

}
