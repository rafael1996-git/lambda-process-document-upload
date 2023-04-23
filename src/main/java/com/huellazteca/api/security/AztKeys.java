package com.huellazteca.api.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AztKeys implements Serializable {

    private static final long serialVersionUID = 1L;
    private String mainKey;
    private String pwdKey;
}
