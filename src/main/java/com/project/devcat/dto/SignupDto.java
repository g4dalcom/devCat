package com.project.devcat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class SignupDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class request {

        @Email
        @NotBlank
        private String email;

        @NotBlank
        private String nickname;

        @NotBlank
        private String password;

        @NotBlank
        private String passwordConfirm;

    }
}
