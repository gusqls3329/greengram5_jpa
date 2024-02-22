package com.green.greengram4.user;

import com.green.greengram4.entity.FeedEntity;
import com.green.greengram4.feed.model.FeedCommentSelVo;
import com.green.greengram4.user.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    int insUser(UserSignupProcDto dto);
    UserModel selUser(UserSelDto dto);
    UserInfoVo selUserInfo(UserInfoSelDto dto);

    int updUserFirebaseToken(UserFirebaseTokenPatchDto dto);
    int updUserPic(UserPicPatchDto dto);

    int insUserFollow(UserFollowDto dto);
    int delUserFollow(UserFollowDto dto);
}
