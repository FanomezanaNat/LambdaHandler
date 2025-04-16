package com.hei.springcloudtest.model.RequestEvent;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Getter
@Setter

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RequestContext {
    @JsonProperty("accountId")
    private String accountId;

    @JsonProperty("apiId")
    private String apiId;

    @JsonProperty("authentication")
    private Object authentication;

    @JsonProperty("authorizer")
    private Authorizer authorizer;

    @JsonProperty("domainName")
    private String domainName;

    @JsonProperty("domainPrefix")
    private String domainPrefix;

    @JsonProperty("http")
    private Http http;

    @JsonProperty("requestId")
    private String requestId;

    @JsonProperty("routeKey")
    private String routeKey;

    @JsonProperty("stage")
    private String stage;

    @JsonProperty("time")
    private String time;

    @JsonProperty("timeEpoch")
    private long timeEpoch;
    // Getters et Setters


}
