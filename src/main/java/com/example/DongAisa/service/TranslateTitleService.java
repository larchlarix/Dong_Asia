package com.example.DongAisa.service;

import com.example.DongAisa.dto.TranslationDto;
import com.example.DongAisa.dto.TranslationTitleDto;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TranslateTitleService {

    @Value("${papago.clientId}")
    private String clientId;

    @Value("${papago.clientSecret}")
    private String clientSecret;

    private static final Logger logger = LoggerFactory.getLogger(TranslateTitleService.class);

    // 번역함수
    public TranslationTitleDto getTranslatedTitles(String[] titles) {
        try {
            if (titles == null) {
                // titles 배열이 null인 경우 적절한 처리를 추가 (예: 빈 TranslationTitleDto 반환)
                return new TranslationTitleDto(new String[0]); // 또는 null 대신 빈 배열 또는 다른 기본값을 사용
            }

            List<String> translatedTitles = new ArrayList<>();

            for (String title : titles) {
                String translatedTitle = getTranslation(title);
                translatedTitles.add(translatedTitle);
            }

            return new TranslationTitleDto(translatedTitles.toArray(new String[0]));
        } catch (RuntimeException e) {
            logger.error("번역 중 오류 발생", e);
            throw new RuntimeException("번역 중 오류 발생", e);
        }
    }

    private String getTranslation(String text) {

        String apiUrl = "https://naveropenapi.apigw.ntruss.com/nmt/v1/translation";

        Map<String, String> requestHeaders = new HashMap<>();

        try {
            String encodedText = URLEncoder.encode(text, StandardCharsets.UTF_8);

            requestHeaders.put("X-NCP-APIGW-API-KEY-ID", clientId);
            requestHeaders.put("X-NCP-APIGW-API-KEY", clientSecret);


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
        String postParams = "source=ja&target=ko&text=" + text; //원본언어: 일본어 (ja) -> 목적언어: 한국어 (ko)
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
                String responseBody = readBody(con.getInputStream());

                // API 응답이 비어 있는 경우 처리
                if (responseBody.isEmpty()) {
                    logger.warn("API 응답이 비어 있습니다.");
                    return "번역된 내용이 없습니다.";
                }

                // API 응답이 유효한 JSON인지 체크
                if (isValidJson(responseBody)) {
                    System.out.println("API Response Body: " + responseBody);
                    return responseBody;
                } else {
                    throw new RuntimeException("API 응답이 유효한 JSON이 아닙니다. 응답 내용: " + responseBody);
                }
            } else {
                String errorResponse = readBody(con.getErrorStream());
                System.out.println("API Error Response: " + errorResponse);
                return errorResponse;
            }
        } catch (IOException e) {
            logger.error("API 요청과 응답 실패", e);
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }



    private static HttpURLConnection connect(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
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
/*
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

 */
private String extractTranslatedText(String responseBody) {
    try {
        JSONObject json = (JSONObject) new JSONParser().parse(responseBody);

        logger.info("Parsed JSON: {}", json);

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
        throw new RuntimeException("API 응답의 JSON 파싱에 실패했습니다. 응답 내용: " + responseBody, e);
    }
}
}
