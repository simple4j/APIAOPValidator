package org.simple4j.apiaopvalidator.validation;

import java.util.List;

public interface Validator<T>
{
	public List<String> validate(String fieldName, T value);
}
