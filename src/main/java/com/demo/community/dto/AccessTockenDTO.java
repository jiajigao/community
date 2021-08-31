package com.demo.community.dto;

import lombok.Data;

@Data
public class AccessTockenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;

}
