package com.huellazteca.api.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DBConfig implements Serializable {
    private static final long serialVersionUID = 1L;
    private String url;
    private String user;
    private String pass;
}
