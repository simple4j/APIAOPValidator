package org.simple4j.apiaopvalidator.validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * This Validator loops through elements of an array and execute the elementValidators against each elements.
 * This can be used when the API argument is an array or any field is an array in a nested object case.
 * 
 * @author jsrinivas108
 *
 */
@JsonRootName(value = "ArrayValidator")
public class ArrayValidator implements Validator<Object[]>
{
	private static String minSizeValidationSuffix = "-minsize";
	private static String maxSizeValidationSuffix = "-maxsize";
	
    /**
     * boolean configuration to do null check as the first step.
     * Validation will fail if this config is true and the array is null
     */
    private boolean doNullCheck = true;
    
    /**
     * Optional configuration to check if the length of the array is not below an expected number.
     * Negative value will turn of the minSize validation before proceeding to the element Validators. 
     */
    private Integer minSize = -1;
    
    /**
     * Optional configuration to check if the length of the array is not above an expected number.
     * Negative value will turn of the maxSize validation before proceeding to the element Validators. 
     */
    private Integer maxSize = -1;
    
    /**
     * List of validator implementations that will be executed against each of the elements in the array 
     */
    private List<Validator> elementValidators = null;
   
    public ArrayValidator()
    {
        super();
    }

    @Override
    public List<String> validate(String fieldName, Object[] arrayValue)
    {
    	List<String> ret = null;
        if(arrayValue == null)
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
        if(this.getMinSize() > 0 && arrayValue.length < this.getMinSize())
        {
        	ret = new ArrayList<String>();
        	ret.add(fieldName + minSizeValidationSuffix);
            return ret;
        }
        if(this.getMaxSize() > 0 && arrayValue.length > this.getMaxSize())
        {
        	ret = new ArrayList<String>();
        	ret.add(fieldName + maxSizeValidationSuffix);
            return ret;
        }
        if(this.getElementValidators() != null && this.getElementValidators().size() > 0)
        {
        	List<Object> colValue = Arrays.asList(arrayValue);
        	Iterator<Object> iterator = colValue.iterator();
        	while(iterator.hasNext())
        	{
        		Object elementObj = iterator.next();
        		
        		for(int i=0 ; i < this.getElementValidators().size() ; i++)
        		{
        			List<String> elementResult = this.getElementValidators().get(i).validate(fieldName + "[" + i + "]", elementObj);
        			if(elementResult != null)
        			{
        				if(ret == null)
        					ret = new ArrayList<String>();
        				ret.addAll(elementResult);
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

	public Integer getMinSize()
	{
		return minSize;
	}

	public void setMinSize(Integer minSize)
	{
		this.minSize = minSize;
	}

	public List<Validator> getElementValidators()
	{
		return elementValidators;
	}

	public void setElementValidators(List<Validator> elementValidators)
	{
		this.elementValidators = elementValidators;
	}

	public Integer getMaxSize()
    {
        return maxSize;
    }

    public void setMaxSize(Integer maxSize)
    {
        this.maxSize = maxSize;
    }

    @Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString()).append(" [doNullCheck=").append(doNullCheck).append(", minSize=").append(minSize)
				.append(", maxSize=").append(maxSize).append(", elementValidators=").append(elementValidators)
				.append("]");
		return builder.toString();
	}

}
