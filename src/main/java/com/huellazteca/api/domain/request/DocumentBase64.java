package com.huellazteca.api.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentBase64 {
    @JsonProperty
    private String officialIdentification;
    @JsonProperty
    private String proofOfAddress;
}
