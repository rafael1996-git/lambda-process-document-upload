package com.huellazteca.api.service.implement;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.huellazteca.api.dao.FileDao;
import com.huellazteca.api.domain.response.File;
import com.huellazteca.api.service.DocumentService;
import com.huellazteca.api.utils.Constants;
import com.huellazteca.api.utils.UploadS3;
import com.huellazteca.core.OracleDBPool;
import com.huellazteca.core.enums.ResponseEnum;
import com.huellazteca.core.models.ResponseModel;
import com.huellazteca.core.utils.EnvironmentData;
import com.huellazteca.core.utils.ResponseUtils;

@Service
public class DocumentServiceImp implements DocumentService {

    private final FileDao fileDao;

    public DocumentServiceImp(FileDao fileDao) {
        this.fileDao = fileDao;
    }

   

	@Override
	public ResponseModel<Object> uploadFiles_proof_of_address(MultipartFile data, Long idUser, Long idPet) {
		   ResponseModel response;
	        String key = null;
	        String urlFile = null;
	        int typeFile = 0;
	        try {
	            Long idAdoption = fileDao.idAdoption(idUser, idPet);
	            System.out.println("idAdoption = " + idAdoption);

	            if (idAdoption == 0)
	                throw new Exception("No existe proceso de adopcion con el usuario: " +
	                        idUser + " y mascota: " + idPet);


	            String bucket = EnvironmentData.getPropertyValue("BucketFiles");

	           
	            if (data != null && !data.isEmpty()) {
	                System.out.println("comprobante de domicilio");
	                key = "proof-of-address/" + idAdoption + "-" + idUser + "-" + idPet + Constants.PDF_EXTENSION;
	                urlFile = new UploadS3().uploadFile(data,key,bucket,3);
	                typeFile = 4; /**Id Comprobante de domicilio**/
	            }
	            File message = new File(urlFile);
	            fileDao.saveFile(urlFile,key,bucket,idAdoption,typeFile);
	            response = ResponseUtils.createResponse(message,ResponseEnum.EXITO);
	        }catch (Exception e){
	            e.printStackTrace();
	            System.out.println(new StringBuilder("DocumentServiceImp - uploadFiles_proof_of_address: ").append(e.getMessage()));
	            response = ResponseUtils.createResponseWithMessage(null,ResponseEnum.ERROR, e.getMessage());
	        }finally {
	            OracleDBPool.closeSingletonConnection();
	        }
	        return response;
	}

	@Override
	public ResponseModel<Object> uploadFiles_identification_official(MultipartFile data, Long idUser,
			Long idPet) {
		   ResponseModel response;
	        String key = null;
	        String urlFile = null;
	        int typeFile = 0;
	        try {
	            Long idAdoption = fileDao.idAdoption(idUser, idPet);
	            System.out.println("idAdoption = " + idAdoption);

	            if (idAdoption == 0)
	                throw new Exception("No existe proceso de adopcion con el usuario: " +
	                        idUser + " y mascota: " + idPet);


	            String bucket = EnvironmentData.getPropertyValue("BucketFiles");

	            if (data!= null && !data.isEmpty()) {
	                System.out.println("Indentificacion oficial");
	                key = "identification-official/" + idAdoption + "-" + idUser + "-" + idPet + Constants.PNG_EXTENSION;
	                urlFile = new UploadS3().uploadFile(data,key,bucket,2);
	                typeFile = 3; /**Id Identificación Oficial**/
	            }
	           
	            File message = new File(urlFile);
	            fileDao.saveFile(urlFile,key,bucket,idAdoption,typeFile);
	            response = ResponseUtils.createResponse(message,ResponseEnum.EXITO);
	        }catch (Exception e){
	            e.printStackTrace();
	            System.out.println(new StringBuilder("DocumentServiceImp - uploadFiles_identification_official: ").append(e.getMessage()));
	            response = ResponseUtils.createResponseWithMessage(null,ResponseEnum.ERROR, e.getMessage());
	        }finally {
	            OracleDBPool.closeSingletonConnection();
	        }
	        return response;
	}

}
