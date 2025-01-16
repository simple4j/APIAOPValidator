package org.simple4j.apiaopvalidator.validation.config;

import java.io.File;
import java.lang.invoke.MethodHandles;

import org.simple4j.apiaopvalidator.validation.MethodArgumentValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JSONConfigValidatorFactory
{
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	/**
	 * JSON file location which contains the configuration for the creation of
	 * org.simple4j.wsclient.caller.Caller The structure of JSON follows
	 * FreeMarkerJSONCallerFactoryConfiguration If both jSONConfigFile and
	 * jSONConfig are configured, jSONConfig will take precedence
	 */
	private File jSONConfigFile = null;

	/**
	 * JSON configuration for the creation of org.simple4j.wsclient.caller.Caller
	 * The structure of JSON follows FreeMarkerJSONCallerFactoryConfiguration If
	 * both jSONConfigFile and jSONConfig are configured, jSONConfig will take
	 * precedence
	 */
	private String jSONConfig = null;

	public MethodArgumentValidator[] getMethodArgumentValidators()
	{
		MethodArgumentValidator[] ret = null;
		return ret;
	}
}
