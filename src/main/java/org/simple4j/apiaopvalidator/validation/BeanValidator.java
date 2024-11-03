package org.simple4j.apiaopvalidator.validation;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Validator entry point for every field of the java API call
 * 
 * @author jsrinivas108
 *
 */
@SuppressWarnings("hiding")
public class BeanValidator implements Validator<Object>
{
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private boolean doNullCheck = true;
    private String fieldName = null;
    private String propertyPath = null;
    private List<Validator> validators = null;

    public List<String> validate(String fieldName, Object beanValues)
    {
    	LOGGER.info("Entering validate {} {}", this.getFieldName(), this.getPropertyPath());
    	LOGGER.debug("{} {}", fieldName, beanValues);
        ArrayList<String> ret = null;
    	if(beanValues == null)
    	{
        	if(this.doNullCheck)
        	{
        		ret = new ArrayList<String>();
            	ret.add(fieldName + NullValidator.NULL_CHECK_VALIDATION_SUFFIX);
            	return ret;
        	}
        	else
        	{
                return null;
        	}
    	}
        Object fieldValue;
        try
        {
            //TODO: another option is to convert nested object tree to collections tree and use CollectionsPathRetreiver
            fieldValue = PropertyUtils.getNestedProperty(beanValues, this.getPropertyPath());
        }
        catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e)
        {
            LOGGER.warn("Exception while getting nested property targetbean:{}, propertyPath:{}", beanValues, this.getPropertyPath(), e);
            throw new RuntimeException("Exception while getting nested property targetbean:"+beanValues+", propertyPath:"+this.getPropertyPath(), e);
        }
        
        if(getValidators() != null)
        {
            for (int i = 0 ; i < getValidators().size() ; i++)
            {
                List<String> result = getValidators().get(i).validate(fieldName + "." + this.getFieldName(), fieldValue);
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

    public String getFieldName()
    {
        if(this.fieldName == null)
            throw new RuntimeException("fieldName not configured");
        return fieldName;
    }

    public void setFieldName(String fieldName)
    {
        this.fieldName = fieldName;
    }

	public String getPropertyPath()
    {
        return propertyPath;
    }

    public void setPropertyPath(String propertyPath)
    {
        this.propertyPath = propertyPath;
    }

    @Override
    public String toString()
    {
        return "ParameterValidator [validators=" + validators + ", fieldName=" + fieldName + ", propertyPath="
                + propertyPath + "]";
    }

}