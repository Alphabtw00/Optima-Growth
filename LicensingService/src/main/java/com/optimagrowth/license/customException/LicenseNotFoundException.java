package com.optimagrowth.license.customException;

public class LicenseNotFoundException extends RuntimeException{
    public LicenseNotFoundException(String message){
        super(message);
    }
}
