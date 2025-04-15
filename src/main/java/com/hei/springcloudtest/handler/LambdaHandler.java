package com.hei.springcloudtest.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hei.springcloudtest.HttpServletRequestWrapper;
import com.hei.springcloudtest.HttpServletResponseWrapper;
import com.hei.springcloudtest.RequestEvent.LambdaUrlRequestEvent;
import com.hei.springcloudtest.SpringCloudTestApplication;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.ServletRequestPathUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.*;

public class LambdaHandler implements RequestStreamHandler {

    public static final String SPRING_SERVER_PORT_FOR_RANDOM_VALUE = "0";

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().
            configure(JsonParser.Feature.IGNORE_UNDEFINED, true)
            .configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


    private final ConfigurableApplicationContext applicationContext;
    private final RequestMappingHandlerMapping handlerMapping;
    private final RequestMappingHandlerAdapter handlerAdapter;


    public LambdaHandler() {
        this.applicationContext = applicationContext();
        this.handlerAdapter = applicationContext.getBean(RequestMappingHandlerAdapter.class);
        this.handlerMapping = applicationContext.getBean(RequestMappingHandlerMapping.class);

    }


    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {

        Map<String, List<String>> headers = new HashMap<>();
        headers.put("Content-Type", List.of("application/json"));
        headers.put("X-Custom-Header", List.of("application/json"));


        String requestAsString = IOUtils.toString(inputStream, Charset.defaultCharset());
        LambdaUrlRequestEvent event = OBJECT_MAPPER.readValue(requestAsString, LambdaUrlRequestEvent.class);


        HttpServletRequest request = new HttpServletRequestWrapper(event);

        ServletRequestPathUtils.parseAndCache(request);


        HttpServletResponseWrapper response = new HttpServletResponseWrapper((ByteArrayOutputStream) outputStream, headers);

        try {
            HandlerExecutionChain executionChain = handlerMapping.getHandler(request);
            if (executionChain == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            Object handler = executionChain.getHandler();
            handlerAdapter.handle(request, response, handler);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    private ConfigurableApplicationContext applicationContext() {
        SpringApplication application = new SpringApplication(SpringCloudTestApplication.class);
        application.setDefaultProperties(Map.of("server.port", SPRING_SERVER_PORT_FOR_RANDOM_VALUE));
        return application.run();
    }

}