package com.huellazteca.api.utils;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.huellazteca.core.S3Utils;
import com.huellazteca.core.enums.ContentTypeEnum;



import org.springframework.web.multipart.MultipartFile;

public class UploadS3 {

	 public String uploadFile(MultipartFile file, String key,String BUCKET_NAME, Integer type){
	        ContentTypeEnum contentTypeEnum ;
	        switch (type){
	            case 1:
	                contentTypeEnum = ContentTypeEnum.VIDEO_MP4;
	                break;
	            case 2:
	                contentTypeEnum = ContentTypeEnum.IMAGE_PNG;
	                break;
	            case 3:
	                contentTypeEnum = ContentTypeEnum.APPLICATION_PDF;
	                break;
	            default:
	                throw new IllegalArgumentException("Invalid type");

	        }
	        try {
	        	S3Utils.uploadFileToS3(BUCKET_NAME,
	        			key,
	        			file.getInputStream(),
	        			contentTypeEnum,
                        CannedAccessControlList.PublicRead);
	            String url = GetUrlS3File.getUrlFromS3File(BUCKET_NAME,key);
	            return url;
	        } catch (Exception e) {
	            throw new RuntimeException(e);
	        }
	    }

}
