package com.example.DongAisa.service;

import com.example.DongAisa.dto.TranslationDto;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.net.URL;

@Service
public class TranslateNewsService {

    @Value("${papago.clientId}")
    private String clientId;

    @Value("${papago.clientSecret}")
    private String clientSecret;

    private static final Logger logger = LoggerFactory.getLogger(TranslateNewsService.class);

    public TranslationDto getTranslatedNews(String title, String content) {
        if (title == null || title.trim().isEmpty() || content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("번역할 제목 또는 내용이 비어 있습니다.");
        }

        try {
            String titleTranslation = getTranslation(title);
            String contentTranslation = getTranslation(content);

            TranslationDto response = new TranslationDto();
            response.setTranslatedTitle(titleTranslation);
            response.setTranslatedContent(contentTranslation);

            return response;
        } catch (RuntimeException e) {
            logger.error("번역에 실패했습니다. 제목: {}, 내용: {}", title, content, e);
            throw new RuntimeException("번역에 실패했습니다.", e);
        }
    }


    private String getTranslation(String text) {
        String apiUrl = "https://openapi.naver.com/v1/papago/n2mt";
        Map<String, String> requestHeaders = new HashMap<>();

        try {
            String encodedText = URLEncoder.encode(text, StandardCharsets.UTF_8);
            requestHeaders.put("X-Naver-Client-Id", clientId);
            requestHeaders.put("X-Naver-Client-Secret", clientSecret);

            String responseBody = post(apiUrl, requestHeaders, encodedText);
            logger.info("Received API Response: {}", responseBody);

            if (isValidJson(responseBody)) {
                return extractTranslatedText(responseBody);
            } else {
                throw new RuntimeException("API 응답이 유효한 JSON이 아닙니다. 응답 내용: " + responseBody);
            }
        } catch (RuntimeException e) {
            logger.error("번역에 실패했습니다. 문장: {}", text, e);
            throw new RuntimeException("번역에 실패했습니다.", e);
        }
    }

    private boolean isValidJson(String json) {
        try {
            // JSON 문자열이 null 또는 빈 문자열인지 확인
            if (json == null || json.trim().isEmpty()) {
                return false;
            }

            JSONParser parser = new JSONParser();
            parser.parse(json);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private String post(String apiUrl, Map<String, String> requestHeaders, String text) {
        HttpURLConnection con = connect(apiUrl);
        String postParams = "source=ja&target=ko&text=" + text;

        try {
            con.setRequestMethod("POST");
            for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            con.setDoOutput(true);
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postParams.getBytes());
                wr.flush();
            }

            int responseCode = con.getResponseCode();
            System.out.println("API Response Code: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                return readBody(con.getInputStream());
            } else {
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            logger.error("API 요청과 응답 실패", e);
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private HttpURLConnection connect(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    private String readBody(InputStream body) {
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }

    private String extractTranslatedText(String responseBody) {
        try {
            JSONObject json = (JSONObject) new JSONParser().parse(responseBody);

            if (json.containsKey("message")) {
                JSONObject message = (JSONObject) json.get("message");

                if (message.containsKey("result")) {
                    JSONObject result = (JSONObject) message.get("result");

                    if (result.containsKey("translatedText")) {
                        return result.get("translatedText").toString();
                    } else {
                        throw new RuntimeException("API 응답에서 'translatedText'를 찾을 수 없습니다. 응답 내용: " + responseBody);
                    }
                } else {
                    throw new RuntimeException("API 응답에서 'result'를 찾을 수 없습니다. 응답 내용: " + responseBody);
                }
            } else {
                throw new RuntimeException("API 응답에서 'message'를 찾을 수 없습니다. 응답 내용: " + responseBody);
            }
        } catch (ParseException e) {
            logger.error("JSON 파싱 중 에러 발생", e);
            throw new RuntimeException("API 응답의 JSON 파싱에 실패했습니다.", e);
        }
    }
}

