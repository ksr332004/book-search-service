package com.search.book.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
public class UserRequest {
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