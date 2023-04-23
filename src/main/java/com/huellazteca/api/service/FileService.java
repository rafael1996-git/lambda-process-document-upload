package com.huellazteca.api.service;

import org.springframework.web.multipart.MultipartFile;

import com.huellazteca.api.domain.request.DocumentRequest;
import com.huellazteca.core.models.ResponseModel;

public interface FileService {
    ResponseModel<Object> uploadFile(MultipartFile data, Long idUser, Long idPet, int typeFile, String mainKey);

	ResponseModel<Object> uploadFileVideo(MultipartFile file, Long idUser, Long idPet, int typeFile, String mainKey);

}
