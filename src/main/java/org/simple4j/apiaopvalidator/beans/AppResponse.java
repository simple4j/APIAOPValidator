package org.simple4j.apiaopvalidator.beans;


public class AppResponse<T>
{
    public T responseObject = null;
    public ErrorDetails errorDetails = null;
    public String apiCallName;
    
    @Override
    public String toString()
    {
        return "AppResponse [responseObject=" + responseObject + ", errorDetails=" + errorDetails + ", apiCallName="
                + apiCallName + ", toString()=" + super.toString() + "]";
    }

}
