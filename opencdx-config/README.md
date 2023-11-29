# OpenCDX Config Microservice
> This module implements the Spring Configuration Server that provides configuration to OpenCDX services. Configuraiton settings are maintained in the src/main/resources/config folder. 

## Docker Image
opencdx/config

## Docker Port
Host 8888 : Container 8888

## Links
_**Links are part of the build not available from GitHub.**_
- Configuration URL: http://localhost:8888/{Applicaiton_Name}/{Profile}
## Interfaces

## Encrypt sensitive information
_**Followoing API to be used to encrypt sensitive information.**_

- Url: https://localhost:8888/encrypt
- Method: POST
- Header: Content-Type = application/x-www-form-urlencoded
- Body:
>  type= x-www-form-urlencoded,
>  key = sensitive data

This will encrypt the sensitive data and generate ciphered text which can be replaced with application.yml as '{cipher} encrypted text' 

example:
>spring:
   datasource:
      password: '{cipher}FKSAJDFGYOS8F7GLHAKERGFHLSAJ'
## 
