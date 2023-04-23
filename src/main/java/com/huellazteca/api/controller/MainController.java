package com.huellazteca.api.controller;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.huellazteca.api.domain.request.DocumentBase64;
import com.huellazteca.api.domain.request.DocumentRequest;
import com.huellazteca.api.service.DocumentService;
import com.huellazteca.api.service.FileService;
import com.huellazteca.api.utils.SecurityUtils;
import com.huellazteca.core.controllers.BaseController;
import com.huellazteca.core.utils.ConstantsUtils;
import com.huellazteca.core.utils.JsonConstantsUtils;

@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET})
@EnableWebMvc
@RestController
@RequestMapping("/file")
public class MainController extends BaseController {

    private final FileService fileService;
    private final DocumentService documentService;

    public MainController(FileService fileService, DocumentService documentService) {
        this.fileService = fileService;
        this.documentService = documentService;
    }

    @PostMapping(value = "/video/presentation/{idUser}",consumes = "multipart/form-data")
    public Object uploadVideoS3(@RequestPart(value = "file") MultipartFile file,
                                @PathVariable("idUser") Long idUser,
                                @RequestParam("idPet") Long idPet){
        System.out.println("data: " + file + ", idUser: " + idUser + ", idPet: " + idPet);
        int typeFile = 1; /** type file (Video)**/
        String mainKey = "videos-presentation/";
        return SecurityUtils.parseResponse(fileService.uploadFileVideo(file,idUser,idPet,typeFile, mainKey),false);
    }

    @PostMapping(value = "/selfie/identification/{idUser}",consumes = "multipart/form-data")
    public Object uploadSelfieIdS3(@RequestPart(value = "file") MultipartFile file,
                                @PathVariable("idUser") Long idUser,
                                @RequestParam("idPet") Long idPet){
        System.out.println("data: " + file + ", idUser: " + idUser + ", idPet: " + idPet);
        int typeFile = 2; /** type file (selfie identification)**/
        String mainKey = "selfie-identification/";
        return SecurityUtils.parseResponse(fileService.uploadFile(file,idUser,idPet,typeFile, mainKey),false);
    }

    @PostMapping(value = "/document/upload-proof-of-address/{idUser}",consumes = "multipart/form-data")
    public Object updateDocument(@RequestPart(value = "file") MultipartFile file,
                                 @PathVariable("idUser") Long idUser,
                                 @RequestParam("idPet") Long idPet)throws IOException  {
        System.out.println("request = " + file + " idPet = " + idPet + " idUser = " + idUser);
        return SecurityUtils.parseResponse(documentService.uploadFiles_proof_of_address(file, idUser, idPet),false);
    }
    
    @PostMapping(value = "/document/upload-identification-official/{idUser}",consumes = "multipart/form-data")
    public Object upload(@RequestPart(value = "file") MultipartFile file,
    					@PathVariable("idUser") Long idUser,
    					@RequestParam("idPet") Long idPet)throws IOException{
        System.out.println("request = " + file + " idPet = " + idPet + " idUser = " + idUser);
    	
        return SecurityUtils.parseResponse(documentService.uploadFiles_identification_official(file, idUser, idPet),false);
    }


    @Override
    @RequestMapping(ConstantsUtils.ENDPOINT_HEALTH_CHECK)
    public HashMap healthCheck() { return getStatus(true);
    }

    @Override
    public HashMap getStatus(boolean withDB) {
        HashMap map = new HashMap();
        map.put(JsonConstantsUtils.PROPERTY_NAME_SUCCESS, Boolean.TRUE);
        map.put(JsonConstantsUtils.PROPERTY_NAME_MESSAGE, ConstantsUtils.HEALTH_CHECK_OK);
        return map;
    }
}
