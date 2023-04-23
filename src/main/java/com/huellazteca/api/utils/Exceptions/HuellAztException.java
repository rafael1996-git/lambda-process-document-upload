package com.huellazteca.api.utils.Exceptions;

public class HuellAztException extends Exception{
    
    private static final long serialVersionUID = 1L;
    private String code;

    public String getCode() {
        return code;
    }

    public HuellAztException(String message, String code, Throwable t) {
        super(message,t);
        this.code = code;
    }    

    public HuellAztException(String message, String code){
        super(message);
        this.code = code;
    }
}
