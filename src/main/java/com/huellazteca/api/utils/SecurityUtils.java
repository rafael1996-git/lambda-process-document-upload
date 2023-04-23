package com.huellazteca.api.utils;

import com.huellazteca.api.security.Credentials;
import com.huellazteca.api.utils.Exceptions.HuellAztException;
import com.huellazteca.core.domain.response.Response;
import com.huellazteca.core.models.ResponseModel;
import com.huellazteca.core.utils.Base64Utils;
import com.huellazteca.core.utils.GsonParserUtils;
import com.huellazteca.core.utils.StringGZipperUtils;


public class SecurityUtils {
    static {
    	
       try {
            if(Credentials.ENCRYPTION_KEYS == null) {
                throw new HuellAztException(
                        Constants.FAILED_GET_KEYS,
                        Constants.FAILED_GET_KEYS_CONFIG
                );
            }

            EncryptorKey.initEncryptorKey(
                    Credentials.ENCRYPTION_KEYS.getMainKey(),
                    Credentials.ENCRYPTION_KEYS.getPwdKey()
            );
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object decrypt(String data, Class<?> objectClass) {
        String jsonString = null;
        try {
            jsonString = EncryptorKey.decode(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (jsonString == null)
            return null;
        else
            return GsonParserUtils.getGson().fromJson(jsonString, objectClass);
    }

    public static Response parseResponse(ResponseModel responseModel, boolean gzip) {
        try {
            String jsonString = GsonParserUtils.getGson().toJson(responseModel);
            return gzip
                    ? new Response(Base64Utils.StringToBase64(new String(StringGZipperUtils.gzip(jsonString))))
                    : new Response(EncryptorKey.encode(jsonString));
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String encryption(String txt){
        try {
            return EncryptorKey.encode(txt);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public static String decryptText(String txt){
        try {
            return EncryptorKey.decode(txt);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
