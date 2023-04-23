package com.huellazteca.api.dao.implement;

import com.huellazteca.api.dao.FileDao;
import com.huellazteca.api.security.Credentials;
import com.huellazteca.api.utils.Constants;
import com.huellazteca.api.utils.ConstantsSQL;
import com.huellazteca.api.utils.Exceptions.HuellAztException;
import com.huellazteca.core.OracleDBPool;
import oracle.jdbc.OracleTypes;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

@Repository
public class FileDaoImpl implements FileDao {
    static {
        try {
            if (Credentials.DB_CONFIG == null) {
                throw new HuellAztException(Constants.FAILED_GET_DB_CONFIG,
                        Constants.CODIGO_FAILED_GET_DB_CONFIG);
            }
            OracleDBPool.initSingletonConnectionCredentials(Credentials.DB_CONFIG.getUrl(),
                    Credentials.DB_CONFIG.getUser(),
                    Credentials.DB_CONFIG.getPass());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
	
    @Override
    public Long idAdoption(Long idUser, Long idPet) throws Exception {
        CallableStatement cs = null;
        ResultSet rs = null;
        Long idAdoption = 0L;
        try {
            Connection conn = OracleDBPool.getSingletonConnection(ConstantsSQL.DB_TIMEOUT, ConstantsSQL.GET_ID_ADOPTION);
            cs = conn.prepareCall(ConstantsSQL.GET_ID_ADOPTION);
            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.setLong(2, idUser);
            cs.setLong(3, idPet);
            cs.execute();
            rs = (ResultSet) cs.getObject(1);
            if (rs.next()) {
                idAdoption = rs.getLong("FIIDADOPTION");
            }
            return idAdoption;
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error method FileDaoImpl.idAdoption: " + e.getMessage());
            throw new Exception(e.getMessage());
        }finally {
            if (cs != null) { try { cs.close(); } catch (Exception e) {} }
            if (rs!= null) { try { rs.close(); } catch (Exception e) {}}
        }
    }

    @Override
    public void saveFile(String urlIdentification, String key, String bucket, Long idAdoption, Integer typeFile) throws Exception {
        CallableStatement cs = null;
        try {
            Connection conn = OracleDBPool.getSingletonConnection(ConstantsSQL.DB_TIMEOUT, ConstantsSQL.INSERT_DOCUMENT_ADOPTION);
            cs = conn.prepareCall(ConstantsSQL.INSERT_DOCUMENT_ADOPTION);
            cs.registerOutParameter(1, OracleTypes.NUMBER);
            cs.setLong(2, idAdoption);
            cs.setInt(3, typeFile);
            cs.setString(4, urlIdentification);
            cs.setString(5, key);
            cs.setString(6, bucket);
            cs.execute();
            System.out.println("Status insert :" + cs.getInt(1));
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error method FileDaoImpl.saveFile: " + e.getMessage());
            throw new Exception(e.getMessage());
        }finally {
            if (cs != null) { try { cs.close(); } catch (Exception e) {} }
        }
    }


    @Override
    public void updateStatusAdoption(Long idAdoption) throws Exception {
        CallableStatement cs = null;
        try {
            Connection conn = OracleDBPool.getSingletonConnection(ConstantsSQL.DB_TIMEOUT, ConstantsSQL.UPDATE_STATUS_PROCESS);
            cs = conn.prepareCall(ConstantsSQL.UPDATE_STATUS_PROCESS);
            cs.registerOutParameter(1, OracleTypes.NUMBER);
            cs.setLong(2, idAdoption);
            cs.setInt(3, 2); /** 2 = status presentation **/
            cs.execute();
            System.out.println("status update :" + cs.getInt(1));
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error method FileDaoImpl.updateStatusAdoption: " + e.getMessage());
            throw new Exception(e.getMessage());
        }finally {
            if (cs != null) { try { cs.close(); } catch (Exception e) {} }
        }
    }
}
