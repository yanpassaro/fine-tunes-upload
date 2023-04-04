package com.desafio.ai.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {
    private final ChatBotTemplate chatBotTemplate;
    private final ObjectMapper objectMapper;

    public Object createFile(MultipartFile file) throws IOException {
        chatBotTemplate.setHeaders(MediaType.MULTIPART_FORM_DATA);
        return chatBotTemplate.execute(file.getName(), file.getOriginalFilename(), file.getBytes(),
                "https://api.openai.com/v1/files", HttpMethod.POST);
    }

    public Object findAllFiles() {
        chatBotTemplate.setHeaders(MediaType.MULTIPART_FORM_DATA);
        chatBotTemplate.execute("https://api.openai.com/v1/files", HttpMethod.GET);
        return filterFiles(chatBotTemplate.execute("https://api.openai.com/v1/files", HttpMethod.GET));
    }

    public Object findFile(String id) {
        chatBotTemplate.setHeaders(MediaType.MULTIPART_FORM_DATA);
        return filterFile(chatBotTemplate.execute("https://api.openai.com/v1/files/" + id, HttpMethod.GET));
    }

    public Object deleteFile(String id) {
        chatBotTemplate.setHeaders(MediaType.MULTIPART_FORM_DATA);
        return chatBotTemplate.execute("https://api.openai.com/v1/files/" + id, HttpMethod.DELETE);
    }

    public Object findContentFile(String id) {
        chatBotTemplate.setHeaders(MediaType.MULTIPART_FORM_DATA);
        return filterFile(chatBotTemplate.execute(
                "https://api.openai.com/v1/files/" + id + "/content", HttpMethod.GET));
    }

    private Object filterFile(Object data) {
        chatBotTemplate.setHeaders(MediaType.MULTIPART_FORM_DATA);
        return objectMapper.convertValue(data, Map.class);
    }

    private List<?> filterFiles(Object data) {
        Map<?, ?> filterData = objectMapper.convertValue(data, Map.class);
        return (List<?>) filterData.get("data");
    }


}
