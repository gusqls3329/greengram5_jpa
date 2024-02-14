package com.green.greengram4.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@Embeddable //복합키때문에
@EqualsAndHashCode
public class UserFollowIds implements Serializable {
    private Long fromIuser;
    private Long toIuser;
}
