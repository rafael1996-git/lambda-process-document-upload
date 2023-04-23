package com.huellazteca.api.security;

import com.huellazteca.core.utils.EnvironmentData;
import com.huellazteca.core.utils.GsonParserUtils;
import com.huellazteca.core.utils.SecretManagerAWSUtils;

public class Credentials {
    public static final DBConfig DB_CONFIG = initCredentials("OraCredKey", DBConfig.class);

    public static final AztKeys ENCRYPTION_KEYS = initCredentials("EncryptionKey", AztKeys.class);

    private static <TType> TType initCredentials(String keyName, Class<TType> clasType)  {
        try {
            String encryption = SecretManagerAWSUtils.getParameter(EnvironmentData.getPropertyValue(keyName));
            return GsonParserUtils.getGson().fromJson(encryption, clasType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
