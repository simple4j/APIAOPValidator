package org.simple4j.apiaopvalidator.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * This Validator loops through all entries of a Map and execute the entryValidators against each entry.
 * This can be used when the API argument is a Map or any field is a Map in a nested object case.
 * 
 * @author jsrinivas108
 *
 */
public class MapValidator implements Validator<Map>
{
	private static String minSizeValidationSuffix = "-minsize";
	private static String maxSizeValidationSuffix = "-maxsize";

    /**
     * boolean configuration to do null check as the first step.
     * Validation will fail if this config is true and the Map is null
     */
    private boolean doNullCheck = true;
    
    /**
     * Optional configuration to check if the size of the Map is not below an expected number.
     * Negative value will turn of the minSize validation before proceeding to the entry Validators. 
     */
    private Integer minSize = -1;
    
    /**
     * Optional configuration to check if the size of the Map is not above an expected number.
     * Negative value will turn of the maxSize validation before proceeding to the entry Validators. 
     */
    private Integer maxSize = -1;
    
    /**
     * List of validator implementations that will be executed against each of the entries in the Map object 
     */
    private List<Validator> entryValidators = null;
   
    public MapValidator()
    {
        super();
    }

    @Override
    public List<String> validate(String fieldName, Map mapValue)
    {
    	List<String> ret = null;
        if(mapValue == null)
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
        if(this.getMinSize() > 0 && mapValue.size() < this.getMinSize())
        {
        	ret = new ArrayList<String>();
        	ret.add(fieldName + minSizeValidationSuffix);
            return ret;
        }
        if(this.getMaxSize() > 0 && mapValue.size() > this.getMaxSize())
        {
        	ret = new ArrayList<String>();
        	ret.add(fieldName + maxSizeValidationSuffix);
            return ret;
        }
        if(this.getEntryValidators() != null && this.getEntryValidators().size() > 0)
        {
        	
        	Iterator<Entry> iterator = mapValue.entrySet().iterator();
        	while(iterator.hasNext())
        	{
        		Entry entryObj = iterator.next();
        		
        		for(int i=0 ; i < this.getEntryValidators().size() ; i++)
        		{
        			List entryResult = this.getEntryValidators().get(i).validate(fieldName + "[" + entryObj.getKey() + "]", entryObj);
        			if(entryResult != null)
        			{
        				if(ret == null)
        					ret = new ArrayList<String>();
        				ret.addAll(entryResult);
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

	public List<Validator> getEntryValidators()
	{
		return entryValidators;
	}

	public void setEntryValidators(List<Validator> entryValidators)
	{
		this.entryValidators = entryValidators;
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
				.append(", maxSize=").append(maxSize).append(", entryValidators=").append(entryValidators)
				.append("]");
		return builder.toString();
	}

}
