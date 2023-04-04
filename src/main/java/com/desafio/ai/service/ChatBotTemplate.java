package com.desafio.ai.service;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class ChatBotTemplate {
    private HttpHeaders headers;

    public void setHeaders(MediaType mediaType) {
        this.headers = new HttpHeaders();
        this.headers.set("Authorization", "Bearer k");
        this.headers.setAccept(List.of(mediaType));
        this.headers.setContentType(mediaType);
    }

    public Object execute(String url, HttpMethod method) {
        return new RestTemplate().exchange(url, method,
                        new HttpEntity<>(headers), Object.class)
                .getBody();
    }

    public Object execute(String filename, String name, byte[] someByteArray, String url, HttpMethod method) {
        MultiValueMap<String, String> fileMap = new LinkedMultiValueMap<>();
        ContentDisposition contentDisposition = ContentDisposition
                .builder("form-data")
                .name(filename)
                .filename(name)
                .build();
        fileMap.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
        HttpEntity<byte[]> fileEntity = new HttpEntity<>(someByteArray, fileMap);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", fileEntity);
        body.add("purpose", "fine-tune");
        return new RestTemplate().exchange(url, method,
                        new HttpEntity<>(body, headers), Object.class)
                .getBody();
    }
}
