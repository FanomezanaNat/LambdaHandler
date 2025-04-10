package com.hei.springcloudtest.RequestEvent;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class LambdaUrlRequestEvent {
    @JsonProperty("version")
    private String version;

    @JsonProperty("routeKey")
    private String routeKey;

    @JsonProperty("rawPath")
    private String rawPath;

    @JsonProperty("rawQueryString")
    private String rawQueryString;

    @JsonProperty("cookies")
    private List<String> cookies;

    @JsonProperty("headers")
    private Map<String, String> headers;

    @JsonProperty("queryStringParameters")
    private Map<String, String> queryStringParameters;

    @JsonProperty("requestContext")
    private RequestContext requestContext;

    @JsonProperty("body")
    private String body;

    @JsonProperty("pathParameters")
    private Map<String, String> pathParameters;

    @JsonProperty("isBase64Encoded")
    private boolean isBase64Encoded;

    @JsonProperty("stageVariables")
    private Map<String, String> stageVariables;

}
