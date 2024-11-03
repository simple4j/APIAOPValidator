package org.simple4j.apiaopvalidator.validation;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Validator entry point for argument of the java API call
 * 
 * @author jsrinivas108
 *
 */
@SuppressWarnings("hiding")
public class MethodArgumentValidator implements Validator<Object[]>
{
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * boolean configuration to do null check as the first step.
     * Validation will fail if this config is true and the argument at the argumentIndex is null
     */
    private boolean doNullCheck = true;
    
    /**
     * The index of the argument that will be validated
     */
    private int argumentIndex = -1;
    
    /**
     * Field name that will be used in the reason code like <fieldName>-<reason>. For example, order-missing
     */
    private String fieldName = null;
    
    /**
     * Optional property path to be retrieved before applying the validators
     */
    private String argumentPropertyPath = null;
    
    /**
     * List of validator implementations that will be executed for the field retrieved based on argumentIndex and argumentPropertyPath 
     */
    private List<Validator> validators = null;

    public List<String> validate(String fieldName, Object[] parameterValues)
    {

    	Object parameterValue = parameterValues[this.getArgumentIndex()];
        ArrayList<String> ret = null;
        Object fieldValue = parameterValue;
        if(parameterValue == null)
        {
        	if(this.doNullCheck)
        	{
        		ret = new ArrayList<String>();
            	ret.add("[" + this.getArgumentIndex() + "]" + NullValidator.NULL_CHECK_VALIDATION_SUFFIX);
            	return ret;
        	}
        	else
        	{
                return null;
        	}
        }
        try
        {
        	if(this.getArgumentPropertyPath() != null)
	            //TODO: another option is to convert nested object tree to collections tree and use CollectionsPathRetreiver
	            fieldValue = PropertyUtils.getNestedProperty(parameterValue, this.getArgumentPropertyPath());
        }
        catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e)
        {
            LOGGER.warn("Exception while getting nested property targetbean:{}, propertyPath:{}", parameterValue, this.getArgumentPropertyPath(), e);
            throw new RuntimeException("Exception while getting nested property targetbean:"+parameterValue+", propertyPath:"+this.getArgumentPropertyPath(), e);
        }

        if(getValidators() != null)
        {
            for (int i = 0 ; i < getValidators().size() ; i++)
            {
                List<String> result = getValidators().get(i).validate(this.getFieldName(), fieldValue);
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

    public int getArgumentIndex()
	{
    	if(this.argumentIndex < 0)
            throw new RuntimeException("argumentIndex not configured");
		return argumentIndex;
	}

	public void setArgumentIndex(int argumentIndex)
	{
		this.argumentIndex = argumentIndex;
	}

	public String getArgumentPropertyPath()
    {
        return argumentPropertyPath;
    }

    public void setArgumentPropertyPath(String argumentPropertyPath)
    {
        this.argumentPropertyPath = argumentPropertyPath;
    }

    @Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString()).append(" [doNullCheck=").append(doNullCheck).append(", argumentIndex=")
				.append(argumentIndex).append(", fieldName=").append(fieldName).append(", argumentPropertyPath=")
				.append(argumentPropertyPath).append(", validators=").append(validators).append("]");
		return builder.toString();
	}

}
