package com.huellazteca.api.dao;

public interface FileDao {
    Long idAdoption(Long idUser, Long idPet) throws Exception;
    void saveFile(String urlIdentification, String key, String bucket, Long idAdoption, Integer typeFile) throws Exception;
    void updateStatusAdoption(Long idAdoption) throws Exception;
}
