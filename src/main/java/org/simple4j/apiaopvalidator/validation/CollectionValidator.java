package org.simple4j.apiaopvalidator.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author jsrinivas108
 *
 */
public class CollectionValidator implements Validator<Collection>
{
	private static String minSizeValidationSuffix = "-minsize";
	private static String maxSizeValidationSuffix = "-maxsize";

	private boolean doNullCheck = true;
    private Integer minSize = -1;
    private Integer maxSize = -1;
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
