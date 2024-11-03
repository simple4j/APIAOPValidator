package org.simple4j.apiaopvalidator.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Error details bean encapsulating the error data component when business error is reached in the API logic
 * 
 * @author jsrinivas108
 */
public class ErrorDetails {
	
	/**
	 * Unique error id to be able to searchable in the logs of the application for investigation
	 */
	public String errorId = null;
	
	/**
	 * Type of the error. For example PARAMETER_ERROR, <somethiig>_NOTFOUND, etc.
	 */
	public String errorType = null;
	
	/**
	 * List of error reason especially for PARAMETER_ERROR where it can have many reasons. For exanple, email-missing, firstName-maxlength etc.
	 */
	public List<String> errorReason = new ArrayList<String>();
	
	/**
	 * Any addition description for the error
	 */
	public String errorDescription = null;

    @Override
    public String toString()
    {
        return "ErrorDetails [errorId=" + errorId + ", errorType=" + errorType + ", errorReason=" + errorReason
                + ", errorDescription=" + errorDescription + ", toString()=" + super.toString() + "]";
    }
	
}
