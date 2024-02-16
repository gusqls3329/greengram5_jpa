package com.green.greengram4.user.model;

import lombok.Data;

@Data
public class UserFollowDto {
    private long fromIuser;
    private long toIuser;
}
