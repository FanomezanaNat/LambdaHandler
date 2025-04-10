package com.hei.springcloudtest.RequestEvent;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Http {
    @JsonProperty("method")
    private String method;

    @JsonProperty("path")
    private String path;

    @JsonProperty("protocol")
    private String protocol;

    @JsonProperty("sourceIp")
    private String sourceIp;

    @JsonProperty("userAgent")
    private String userAgent;

}
