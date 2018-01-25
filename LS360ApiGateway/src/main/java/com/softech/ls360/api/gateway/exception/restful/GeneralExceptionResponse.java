package com.softech.ls360.api.gateway.exception.restful;

public class GeneralExceptionResponse {

    private String status;
    private String message;
   
    
    public GeneralExceptionResponse() {
    }
    
    public GeneralExceptionResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

   
    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

	
    
}