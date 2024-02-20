package com.green.greengram4.feed;

import com.green.greengram4.common.Const;
import com.green.greengram4.common.MyFileUtils;
import com.green.greengram4.common.ResVo;
import com.green.greengram4.entity.FeedEntity;
import com.green.greengram4.entity.FeedPicsEntity;
import com.green.greengram4.entity.UserEntity;
import com.green.greengram4.exception.FeedErrorCode;
import com.green.greengram4.exception.RestApiException;
import com.green.greengram4.feed.model.*;
import com.green.greengram4.security.AuthenticationFacade;
import com.green.greengram4.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedService {
    private final FeedMapper mapper;
    private final FeedPicsMapper picsMapper;
    private final FeedFavMapper favMapper;
    private final FeedCommentMapper commentMapper;
    private final FeedRepository repository;
    private final UserRepository userRepository;
    private final FeedCommentRepository CommentRepository;
    private final AuthenticationFacade authenticationFacade; //서비스에서 로그인을 안해도 괜찮을 경우에 오류가 발생
    private final MyFileUtils myFileUtils;

    @Transactional // 예외가 터졌을때만 롤백, 아닐경우는 커밋함
    public FeedPicsInsDto postFeed(FeedInsDto dto) {
        if (dto.getPics() == null) {
            throw new RestApiException(FeedErrorCode.PICS_MORE_THEN_ONE);
        }

        log.info("dto.getIuser : {}", dto.getIuser());

        FeedEntity entity = new FeedEntity();

        entity.setUserEntity(userRepository.getReferenceById((long) authenticationFacade.getLoginUserPk()));
        entity.setContents(dto.getContents());
        entity.setLocation(dto.getLocation());

        repository.save(entity);

        String target = "/feed/" + dto.getIfeed();

        FeedPicsInsDto pDto = new FeedPicsInsDto();
        pDto.setIfeed(entity.getIfeed().intValue());
        for (MultipartFile file : dto.getPics()) {
            String saveFileNm = myFileUtils.transferTo(file, target); //컴퓨터에 저장
            pDto.getPics().add(saveFileNm); //데이터베이스에 저장
        }
        List<FeedPicsEntity> feedPicsEntityList = pDto.getPics().stream().map(
                item -> FeedPicsEntity.builder().
                        feedEntity(entity)
                        .pic(item).build()
        ).collect(Collectors.toList());
        entity.getFeedPicsEntityList().addAll(feedPicsEntityList);
        return pDto;
    }

    @Transactional
    public List<FeedSelVo> getFeedAll(FeedSelDto dto, Pageable pageable) {
        List<FeedEntity> feedEntityList = null;
        if (dto.getIsFavList() == 0 && dto.getTargetIuser() > 0) {
            UserEntity userEntity = new UserEntity();
            userEntity.setIuser((long) dto.getTargetIuser());
            feedEntityList = repository.findAllByUserEntityOrderByIfeedDesc(userEntity, pageable);
        }
        return feedEntityList == null ? new ArrayList<>() :
                feedEntityList.stream().map(item -> {

                    List<FeedCommentSelVo> cmtList = CommentRepository.findAllTop4ByFeedEntity(item).stream().map(cmt ->
                            FeedCommentSelVo.builder()
                                    .comment(cmt.getComment())
                                    .createdAt(cmt.getCreatedAt().toString())
                                    .writerNm(cmt.getUserEntity().getNm())
                                    .writerPic(cmt.getUserEntity().getPic())
                                    .writerIuser(cmt.getUserEntity().getIuser().intValue())
                                    .ifeedComment(cmt.getIfeedComment().intValue())
                                    .build()).collect(Collectors.toList());


                    return FeedSelVo.builder()
                            .ifeed(item.getIfeed().intValue())
                            .contents(item.getContents())
                            .location(item.getLocation())
                            .createdAt(item.getCreatedAt().toString())
                            .writerIuser(item.getUserEntity().getIuser().intValue())
                            .writerNm(item.getUserEntity().getNm())
                            .writerPic(item.getUserEntity().getPic())
                            .comments(cmtList)
                            .build();
                }).collect(Collectors.toList());

    }


    public ResVo delFeed(FeedDelDto dto) {
        //1 이미지
        int picsAffectedRows = picsMapper.delFeedPicsAll(dto);
        if (picsAffectedRows == 0) {
            return new ResVo(Const.FAIL);
        }

        //2 좋아요
        int favAffectedRows = favMapper.delFeedFavAll(dto);

        //3 댓글
        int commentAffectedRows = commentMapper.delFeedCommentAll(dto);

        //4 피드
        int feedAffectedRows = mapper.delFeed(dto);
        return new ResVo(Const.SUCCESS);
    }

    //--------------- t_feed_fav
    public ResVo toggleFeedFav(FeedFavDto dto) {
        //ResVo - result값은 삭제했을 시 (좋아요 취소) 0, 등록했을 시 (좋아요 처리) 1
        int delAffectedRows = favMapper.delFeedFav(dto);
        if (delAffectedRows == 1) {
            return new ResVo(Const.FEED_FAV_DEL);
        }
        int insAffectedRows = favMapper.insFeedFav(dto);
        return new ResVo(Const.FEED_FAV_ADD);
    }
}
