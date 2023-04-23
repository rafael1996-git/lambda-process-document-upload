package com.huellazteca.api.utils;

public class ConstantsSQL {
    public static final String INSERT_DOCUMENT_ADOPTION = "{? = call  HUELLITAS.PAADOPTION.FNINSERTDOCUMENTADOPTION(?,?,?,?,?)}";
    public static final String GET_ID_ADOPTION = "{? = call  HUELLITAS.PAADOPTION.FNGETSTATUSPROCESSADOPTION(?,?)}";
    public static final String UPDATE_STATUS_PROCESS = "{? = call  HUELLITAS.PAADOPTION.FNUPDATESTATUSPROCESS(?,?)}";
    public static final int DB_TIMEOUT = 7;
}
