package org.simple4j.apiaopvalidator.validation;

import java.util.List;

/**
 * Main interface that all Validator types implement
 * @author jsrinivas108
 * @param <T>
 */
public interface Validator<T>
{
	/**
	 * This method takes a fieldName and field value as parameters.
	 * If any validation fails, the reason is returned as strings of <fieldName>-<reason>.
	 * For example, order-empty  
	 * 
	 * @param fieldName
	 * @param value
	 * @return List of validation failure reason codes
	 */
	public List<String> validate(String fieldName, T value);
}
