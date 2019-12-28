package com.search.book.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDTO {

    @Getter
    @Setter
    public static class Res {
        @Email
        @NotNull
        @Size(min = 8, max = 50)
        private String email;

        @NotNull
        @Size(min = 5, max = 15)
        private String password;

        @NotNull
        @Size(min = 2, max = 20)
        private String name;
    }

}