# APIAOPValidator

APIAOPValidator is a simple configurable validation library for web services API validation using AOP.

It can be used to validate API parameters without embedding validation code in the business logic code. It uses AOP to intercept API calls and execute validation code. The validation codes are written as sub class of Validator interface. The Validator implementations can be plugged to the API interface through Spring ApplicationContext xml.

The interceptor takes multiple MethodArgumentValidator for every method signature in the API interface. Each of the MethodArgumentValidator will be executed based on the argument index configured in MethodArgumentValidator. The validator configurations can be reused across API calls so there is only one set of validation rules for a given field.

For nested complex API parameters, other Validator implementations can be injected. For example, BeanValidator, ArrayValidator, CollectionValidator, MapValidator.

Only requirement for the API interface is to return a type of AppResponse and the error details need to be of the predefined structure under ErrorDetails class.

The src/test has all the possible validation cases along with configuration and shows in action in the unit tests.
