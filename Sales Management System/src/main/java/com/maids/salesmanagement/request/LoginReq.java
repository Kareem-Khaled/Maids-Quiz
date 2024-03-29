package com.maids.salesmanagement.request;

import lombok.Data;

@Data
public class LoginReq {
    private String email;
    private String password;
}