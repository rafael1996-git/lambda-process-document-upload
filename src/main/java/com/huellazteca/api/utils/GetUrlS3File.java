package com.huellazteca.api.utils;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;

public class GetUrlS3File {

    private static S3Client amazonS3Client2 = null;

    private static void initializeAmazons3Client() throws Exception {
        if (amazonS3Client2 == null) {
            amazonS3Client2 = S3Client.builder().build();
        }
    }

    public static String getUrlFromS3File(String bucket,
                                          String key) throws Exception {
        initializeAmazons3Client();
        return amazonS3Client2.utilities().getUrl(getUrlRequest(bucket, key)).toExternalForm();
    }

    private static GetUrlRequest getUrlRequest(String bucket , String key) {
        return GetUrlRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();
    }
}
