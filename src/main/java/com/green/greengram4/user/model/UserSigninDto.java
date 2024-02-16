package com.green.greengram4.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserSigninDto {
    @Schema(title = "유저아이디", example = "mic")
    private String uid;
    @Schema(title = "유저비번", example = "1212")
    private String upw;
}
