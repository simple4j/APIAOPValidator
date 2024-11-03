package org.simple4j.apiaopvalidator.beans;


/**
 * Application resonse bean to return a responseObject or errorDetaisl object without throwing exception for business failure cases
 * 
 * @author jsrinivas108
 * @param <T>
 */
public class AppResponse<T>
{
	/**
	 * Response object of a given API call. It is a generic type that can be defined in the API
	 */
    public T responseObject = null;
    
    /**
     * Error details in case of business error
     */
    public ErrorDetails errorDetails = null;
    
    /**
     * 
     */
    public String apiCallName;
    
    @Override
    public String toString()
    {
        return "AppResponse [responseObject=" + responseObject + ", errorDetails=" + errorDetails + ", apiCallName="
                + apiCallName + ", toString()=" + super.toString() + "]";
    }

}
