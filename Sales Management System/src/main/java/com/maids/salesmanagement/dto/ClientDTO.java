package com.maids.salesmanagement.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ClientDTO {
    private Long id;

    @NotNull
    @NotEmpty
    @Size(max = 100)
    private String name;

    @NotNull
    @NotEmpty
    @Size(max = 100)
    private String lastName;

    @NotNull
    @NotEmpty
    @Size(max = 20)
    private String mobile;

    @NotNull
    @NotEmpty
    @Email
    @Size(max = 100)
    private String email;

    @NotNull
    @NotEmpty
    @Size(max = 255)
    private String address;
}
