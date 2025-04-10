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
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.*;

@Slf4j
public class LambdaHandler implements RequestStreamHandler {

    public static final String SPRING_SERVER_PORT_FOR_RANDOM_VALUE = "0";

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().
            configure(JsonParser.Feature.IGNORE_UNDEFINED, true)
            .configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


    private final ConfigurableApplicationContext applicationContext;
    private static DispatcherServlet dispatcherServlet;


    public LambdaHandler() {

        this.applicationContext = applicationContext();
        dispatcherServlet = applicationContext.getBean(DispatcherServlet.class);
        dispatcherServlet.setApplicationContext(applicationContext);

    }


    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {

        Map<String, List<String>> headers = new HashMap<>();
        headers.put("Content-Type", List.of("application/json"));
        headers.put("X-Custom-Header", List.of("application/json"));


        String requestAsString = IOUtils.toString(inputStream, Charset.defaultCharset());
        LambdaUrlRequestEvent event = OBJECT_MAPPER.readValue(requestAsString, LambdaUrlRequestEvent.class);


        HttpServletRequest request = new HttpServletRequestWrapper(event);


        HttpServletResponseWrapper response = new HttpServletResponseWrapper((ByteArrayOutputStream) outputStream, headers);
        try {
            dispatcherServlet.service(request, response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }


    }

    private ConfigurableApplicationContext applicationContext() {
        SpringApplication application = new SpringApplication(SpringCloudTestApplication.class);
        application.setDefaultProperties(Map.of("server.port", SPRING_SERVER_PORT_FOR_RANDOM_VALUE));
        return application.run();
    }

}