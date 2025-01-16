package org.simple4j.apiaopvalidator.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * This Validator loops through elements of a Collection and execute the elementValidators against each elements.
 * This can be used when the API argument is a Collection or any field is a Collection in a nested object case.
 * 
 * @author jsrinivas108
 *
 */
@JsonRootName(value = "CollectionValidator")
public class CollectionValidator implements Validator<Collection>
{
	private static String minSizeValidationSuffix = "-minsize";
	private static String maxSizeValidationSuffix = "-maxsize";

    /**
     * boolean configuration to do null check as the first step.
     * Validation will fail if this config is true and the collection is null
     */
	private boolean doNullCheck = true;
    
    /**
     * Optional configuration to check if the size of the collection is not below an expected number.
     * Negative value will turn of the minSize validation before proceeding to the element Validators. 
     */
    private Integer minSize = -1;
    
    /**
     * Optional configuration to check if the size of the collection is not above an expected number.
     * Negative value will turn of the maxSize validation before proceeding to the element Validators. 
     */
    private Integer maxSize = -1;
    
    /**
     * List of validator implementations that will be executed against each of the elements in the Collection object 
     */
    private List<Validator> elementValidators = null;
   
    public CollectionValidator()
    {
        super();
    }

    @Override
    public List<String> validate(String fieldName, Collection colValue)
    {
    	List<String> ret = null;
        if(colValue == null)
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
        if(this.getMinSize() > 0 && colValue.size() < this.getMinSize())
        {
        	ret = new ArrayList<String>();
        	ret.add(fieldName + minSizeValidationSuffix);
            return ret;
        }
        if(this.getMaxSize() > 0 && colValue.size() > this.getMaxSize())
        {
        	ret = new ArrayList<String>();
        	ret.add(fieldName + maxSizeValidationSuffix);
            return ret;
        }
        if(this.getElementValidators() != null && this.getElementValidators().size() > 0)
        {
        	Iterator iterator = colValue.iterator();
        	int elementIndex = 0;
        	while(iterator.hasNext())
        	{
        		Object elementObj = iterator.next();
        		
        		for(int i=0 ; i < this.getElementValidators().size() ; i++)
        		{
        			List elementResult = this.getElementValidators().get(i).validate(fieldName + "[" + elementIndex + "]", elementObj);
        			if(elementResult != null)
        			{
        				if(ret == null)
        					ret = new ArrayList<String>();
        				ret.addAll(elementResult);
        			}
        		}
        		elementIndex++;
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
