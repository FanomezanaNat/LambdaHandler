package com.hei.springcloudtest.ResponseEvent;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hei.springcloudtest.HttpServletResponseWrapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LambdaUrlResponseEvent implements Serializable {
    @JsonProperty("statusCode")
    private int statusCode;

    @JsonProperty("headers")
    private Map<String, String> headers;

    @JsonProperty("body")
    private String body;

    @JsonProperty("cookies")
    private List<String> cookies;

    @JsonProperty("isBase64Encoded")
    private boolean isBase64Encoded;

    public LambdaUrlResponseEvent withStatusCode(int statusCode) {
        this.setStatusCode(statusCode);
        return this;
    }

    public LambdaUrlResponseEvent withHeaders(Map<String, String> headers) {
        this.setHeaders(headers);
        return this;
    }

    public LambdaUrlResponseEvent withBody(String body) {
        this.setBody(body);
        return this;
    }

    public LambdaUrlResponseEvent withCookies(List<String> cookies) {
        this.setCookies(cookies);
        return this;
    }

    public LambdaUrlResponseEvent withIsBase64Encoded(boolean isBase64Encoded) {
        this.setBase64Encoded(isBase64Encoded);
        return this;
    }

    public static LambdaUrlResponseEvent fromHttpServletResponseWrapper(HttpServletResponseWrapper responseWrapper) {
        LambdaUrlResponseEvent event = new LambdaUrlResponseEvent();
        event.setStatusCode(responseWrapper.getStatus());

        Map<String, String> headers = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : responseWrapper.getHeadersMap().entrySet()) {
            headers.put(entry.getKey(), String.join(", ", entry.getValue()));
        }
        event.setHeaders(headers);

        event.setBody(responseWrapper.getResponseBody());

        event.setBase64Encoded(false);
        return event;
    }


}
