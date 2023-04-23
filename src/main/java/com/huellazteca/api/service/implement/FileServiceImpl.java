package com.huellazteca.api.service.implement;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.huellazteca.api.dao.FileDao;
import com.huellazteca.api.domain.response.File;
import com.huellazteca.api.service.FileService;
import com.huellazteca.api.utils.Constants;
import com.huellazteca.api.utils.UploadS3;
import com.huellazteca.core.OracleDBPool;
import com.huellazteca.core.enums.ResponseEnum;
import com.huellazteca.core.models.ResponseModel;
import com.huellazteca.core.utils.EnvironmentData;
import com.huellazteca.core.utils.ResponseUtils;

@Service
public class FileServiceImpl implements FileService {
    private final FileDao fileDao;

    public FileServiceImpl(FileDao fileDao) {
        this.fileDao = fileDao;
    }

    @Override
    public ResponseModel<Object> uploadFile(MultipartFile data, Long idUser, Long idPet, int typeFile, String mainKey) {
        ResponseModel response;
        try {
            Long idAdoption = fileDao.idAdoption(idUser,idPet);
            System.out.println("idAdoption = " + idAdoption);
            System.out.println("typeFile = " + typeFile);


            if (idAdoption == 0)
                throw new Exception("No existe proceso de adopcion con el usuario: " + idUser + " y mascota: " + idPet);

            String key = mainKey + idAdoption + "-" + idUser + "-" + idPet + Constants.PNG_EXTENSION;
            String bucket = EnvironmentData.getPropertyValue("BucketFiles");
            String urlFile = new UploadS3().uploadFile(data,key,bucket,typeFile);
            fileDao.saveFile(urlFile,key,bucket,idAdoption,typeFile);
            if (typeFile == 2){
                fileDao.updateStatusAdoption(idAdoption);
            }
            File message = new File(urlFile);
            response = ResponseUtils.createResponse(message,ResponseEnum.EXITO);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(new StringBuilder("FileServiceImpl.DBException - uploadFile: ").append(e.getMessage()));
            response = ResponseUtils.createResponseWithMessage(null, ResponseEnum.ERROR, e.getMessage());
        }finally {
            OracleDBPool.closeSingletonConnection();
        }
        return response;
    }

	@Override
	public ResponseModel<Object> uploadFileVideo(MultipartFile file, Long idUser, Long idPet, int typeFile,
			String mainKey) {
		 ResponseModel response;
	        try {
	            Long idAdoption = fileDao.idAdoption(idUser,idPet);
	            System.out.println("idAdoption = " + idAdoption);
	            System.out.println("typeFile = " + typeFile);


	            if (idAdoption == 0)
	                throw new Exception("No existe proceso de adopcion con el usuario: " + idUser + " y mascota: " + idPet);


	            String key = mainKey + idAdoption + "-" + idUser + "-" + idPet +".mp4";
	            String bucket = EnvironmentData.getPropertyValue("BucketFiles");

	            String urlFile = new UploadS3().uploadFile(file,key,bucket,typeFile);
	            fileDao.saveFile(urlFile,key,bucket,idAdoption,typeFile);
	            if (typeFile == 2){
	                fileDao.updateStatusAdoption(idAdoption);
	            }
	            File message = new File(urlFile);
	            response = ResponseUtils.createResponse(message,ResponseEnum.EXITO);
	        }catch (Exception e){
	            e.printStackTrace();
	            System.out.println(new StringBuilder("FileServiceImpl.DBException - uploadFile: ").append(e.getMessage()));
	            response = ResponseUtils.createResponseWithMessage(null, ResponseEnum.ERROR, e.getMessage());
	        }finally {
	            OracleDBPool.closeSingletonConnection();
	        }
	        return response;
	}


}
