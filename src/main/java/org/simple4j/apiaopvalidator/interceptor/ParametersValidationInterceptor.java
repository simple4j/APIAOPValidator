package org.simple4j.apiaopvalidator.interceptor;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.simple4j.apiaopvalidator.beans.AppResponse;
import org.simple4j.apiaopvalidator.beans.ErrorDetails;
import org.simple4j.apiaopvalidator.validation.MethodArgumentValidator;

/**
 * This interceptor will trigger field validations using AOP before java API is called and 
 * returns any failures to web API layer. The validation rules can be configured using Spring xml
 * without any hardcoding or hardwiring in the java API implementation class.
 * 
 * @author jsrinivas108
 *
 */
public class ParametersValidationInterceptor implements MethodInterceptor
{
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private Map<String, MethodArgumentValidator[]> method2Validators = null;
    
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable
    {
        Method method = methodInvocation.getMethod();
        Parameter[] parameters = method.getParameters();
        Object[] arguments = methodInvocation.getArguments();
        
        String targetMethodSignature = method.toString();
        
        MethodArgumentValidator[] parameterValidators = this.getMethod2Validators().get(targetMethodSignature);
        
        if(parameterValidators == null)
        {
            LOGGER.info("No validator configured for method {}", targetMethodSignature);
        }
        else
        {
//            HashMap<String, Object> parametersMap = new HashMap<>();
//            for (int i = 0 ; i < parameters.length ; i++)
//            {
//                if(parameterValidators[i] != null)
//                {
//                    String fieldName = parameterValidators[i].getFieldName();
//                    LOGGER.trace("fieldName {}", fieldName);
//                    Object argument = arguments[i];
//                    parametersMap.put(fieldName, argument);
//                }
//            }
//            LOGGER.debug("parametersMap {}", parametersMap);
            List<String> errorReason = null;

            for(int i = 0 ; i < parameterValidators.length ; i++)
            {
                List<String> result = parameterValidators[i].validate(null,arguments);
                if(result != null && result.size() > 0)
                {
                	if(errorReason == null)
                		errorReason = new ArrayList<String>();
    				errorReason.addAll(result);
                }
            }
            
            if(errorReason != null && errorReason.size() > 0)
            {
                AppResponse ret = new AppResponse();
                ErrorDetails errorDetails = new ErrorDetails();
                errorDetails.errorType = "PARAMETER_ERROR";
                errorDetails.errorId = System.currentTimeMillis() + "@" + InetAddress.getLocalHost().getHostName();
                errorDetails.errorReason = errorReason;
                ret.errorDetails = errorDetails;
                return ret;
            }
        }
        return methodInvocation.proceed();
    }

    public Map<String, MethodArgumentValidator[]> getMethod2Validators()
    {
        if(this.method2Validators == null)
            throw new RuntimeException("method2Validators not configured in ParametersValidationInterceptor");
        return method2Validators;
    }

    public void setMethod2Validators(Map<String, MethodArgumentValidator[]> method2Validators)
    {
        this.method2Validators = method2Validators;
    }

}
