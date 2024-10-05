package org.simple4j.apiaopvalidator.beans;

import java.util.ArrayList;
import java.util.List;

public class ErrorDetails {
	
	public String errorId = null;
	public String errorType = null;
	public List<String> errorReason = new ArrayList<String>();
	public String errorDescription = null;

    @Override
    public String toString()
    {
        return "ErrorDetails [errorId=" + errorId + ", errorType=" + errorType + ", errorReason=" + errorReason
                + ", errorDescription=" + errorDescription + ", toString()=" + super.toString() + "]";
    }
	
}
