package com.green.greengram4.feed;

import com.green.greengram4.common.Const;
import com.green.greengram4.common.MyFileUtils;
import com.green.greengram4.common.ResVo;
import com.green.greengram4.feed.model.*;
import com.green.greengram4.security.AuthenticationFacade;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedService {
    private final FeedMapper mapper;
    private final FeedPicsMapper picsMapper;
    private final FeedFavMapper favMapper;
    private final FeedCommentMapper commentMapper;
    private final AuthenticationFacade authenticationFacade; //서비스에서 로그인을 안해도 괜찮을 경우에 오류가 발생
    private final MyFileUtils myFileUtils;

    public FeedPicsInsDto postFeed(FeedInsDto dto) {
        dto.setIuser(authenticationFacade.getLoginUserPk());
        log.info("dto.getIuser : {}",dto.getIuser());
        int feedAffectedRows = mapper.insFeed(dto);
        String target = "/feed/" + dto.getIfeed();

        FeedPicsInsDto insDto = new FeedPicsInsDto();
        insDto.setIfeed(dto.getIfeed());
        for (MultipartFile file : dto.getPics()){
            String saveFileNm = myFileUtils.transferTo(file, target); //컴퓨터에 저장
            insDto.getPics().add(saveFileNm); //데이터베이스에 저장
        }
        int feedPicsAffectedRows = picsMapper.insFeedPics(insDto);
        return insDto;
    }

    public List<FeedSelVo> getFeedAll(FeedSelDto dto) {
        List<FeedSelVo> list = mapper.selFeedAll(dto);

        FeedCommentSelDto fcDto = new FeedCommentSelDto();
        fcDto.setStartIdx(0);
        fcDto.setRowCount(Const.FEED_COMMENT_FIRST_CNT);
        for (FeedSelVo vo : list) {
            List<String> pics = picsMapper.selFeedPicsAll(vo.getIfeed());
            vo.setPics(pics);

            fcDto.setIfeed(vo.getIfeed());
            List<FeedCommentSelVo> comments = commentMapper.selFeedCommentAll(fcDto);
            vo.setComments(comments);

            if (comments.size() == Const.FEED_COMMENT_FIRST_CNT) {
                vo.setIsMoreComment(1);
                comments.remove(comments.size() - 1);
            }
        }
        return list;
    }

    public ResVo delFeed(FeedDelDto dto) {
        //1 이미지
        int picsAffectedRows = picsMapper.delFeedPicsAll(dto);
        if(picsAffectedRows == 0) {
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
        if(delAffectedRows == 1) {
            return new ResVo(Const.FEED_FAV_DEL);
        }
        int insAffectedRows = favMapper.insFeedFav(dto);
        return new ResVo(Const.FEED_FAV_ADD);
    }
}
