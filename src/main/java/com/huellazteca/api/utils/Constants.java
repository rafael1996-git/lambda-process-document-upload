package com.huellazteca.api.utils;

import com.huellazteca.core.utils.EnvironmentData;

public class Constants {

    public static final String FAILED_GET_DB_CONFIG = "Failed to get DB configuration.";
    public static final String CODIGO_FAILED_GET_DB_CONFIG = "-10";
    public static final String MESSAGE_SUCCESS = "Successful registration";


    public static final String FAILED_GET_KEYS = "Failed to get Main and pass Keys.";
    public static final String FAILED_GET_KEYS_CONFIG = "-11";
    public static final String REGEX_NUMBER_TELEPHONE= "^\\d{10}$";

    public static final String PDF_EXTENSION = ".pdf";
    public static final String PNG_EXTENSION = ".png";


    private static String initializeVariable(String value) {
        String result;
        try {
            result = EnvironmentData.getPropertyValue(value);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            result = null;
        }

        return result;
    }

}
