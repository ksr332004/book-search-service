package com.search.book.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AuthDTO {

    @Setter
    @Getter
    public static class Req {
        @NotNull
        @Email
        @Size(min = 8, max = 50)
        private String email;

        @NotNull
        @Size(min = 5, max = 15)
        private String password;
    }

    @Getter
    @Setter
    public static class Res {
        private String accessToken;
        private String tokenType = "Bearer";

        public Res(String accessToken) {
            this.accessToken = accessToken;
        }
    }

}
