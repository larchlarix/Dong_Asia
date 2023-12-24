package com.example.DongAisa.service;

import org.springframework.stereotype.Service;

@Service
public class NewService {
    public String processFormData(String id, String password, String email) {
        // 여기에 실제로 필요한 비즈니스 로직을 추가
        // 예제에서는 받은 데이터를 그대로 반환하도록 했습니다.
        return "ID: " + id + ", Password: " + password + ", Email: " + email;
    }
}
