package com.green.greengram4.feed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class FeedCommentInsDto {
    @JsonIgnore
    private int ifeedComment;
    @JsonIgnore
    private int iuser;

    @Range(min = 1, max = 10000)
    private int ifeed;
    //@Size = 배열 : Length와 비슷
    //@Range = Integer : 숫자타입 범위
    @NotEmpty (message = "댓글 내용을 입력해주세요")
    @Size(min = 3, message = "댓글내용은 3자리 이상이어야 합니다.")
    private String comment;
}

