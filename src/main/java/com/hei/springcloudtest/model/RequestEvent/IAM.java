package com.hei.springcloudtest.model.RequestEvent;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IAM {
    @JsonProperty("accessKey")
    private String accessKey;

    @JsonProperty("accountId")
    private String accountId;

    @JsonProperty("callerId")
    private String callerId;

    @JsonProperty("cognitoIdentity")
    private String cognitoIdentity;

    @JsonProperty("principalOrgId")
    private String principalOrgId;

    @JsonProperty("userArn")
    private String userArn;

    @JsonProperty("userId")
    private String userId;

}
