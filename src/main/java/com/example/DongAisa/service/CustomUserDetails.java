package com.example.DongAisa.service;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


public class CustomUserDetails implements UserDetails {

    private Long userId; // 사용자 지정 필드 추가
    private String userEmail;
    private String userPassword;
    private Collection<? extends GrantedAuthority> authorities;




    public CustomUserDetails(Long userId, String userEmail, String userPassword, Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.authorities = authorities;
    }

    public Long getUserId() {
        return userId;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return userPassword;
    }

    @Override
    public String getUsername() {
        return userEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
