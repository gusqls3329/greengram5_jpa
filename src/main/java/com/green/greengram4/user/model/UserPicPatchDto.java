package com.green.greengram4.user.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserPicPatchDto {
    private int iuser;
    private String pic;
}
