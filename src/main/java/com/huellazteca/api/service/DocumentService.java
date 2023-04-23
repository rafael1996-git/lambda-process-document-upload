package com.huellazteca.api.service;

import org.springframework.web.multipart.MultipartFile;

import com.huellazteca.api.domain.request.DocumentBase64;
import com.huellazteca.core.models.ResponseModel;

public interface DocumentService {
    ResponseModel<Object> uploadFiles_proof_of_address(MultipartFile documentBase64,Long idUser,Long idPet);
    ResponseModel<Object> uploadFiles_identification_official(MultipartFile documentBase64,Long idUser,Long idPet);
}
