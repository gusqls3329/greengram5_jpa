package com.green.greengram4.feed;

import com.green.greengram4.common.Const;
import com.green.greengram4.common.ResVo;
import com.green.greengram4.feed.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class) // 스프링컨테이너를 사용할 수 있도록
@Import({FeedService.class}) // 특정한 class를 사용 할 수 있도록 빈등록
//만약 위 두 어노테이션이 없다면,아래  @Autowired service를 사용할 수 없다.
class FeedServiceTest {

    @MockBean //가짜 빈을 만들어서 객체의 주소값을 service에 밀어 줄수 있도록
    private FeedMapper mapper;

    @MockBean
    private FeedPicsMapper picsMapper;

    @MockBean
    private FeedFavMapper favMapper;

    @MockBean
    private FeedCommentMapper commentMapper;

    @Autowired
    private FeedService service;

    @Test
    void postFeed() {
        when(mapper.insFeed(any())).thenReturn(1);
        when(picsMapper.insFeedPics(any())).thenReturn(3);

        FeedInsDto dto = new FeedInsDto();
        dto.setIfeed(110);
        FeedPicsInsDto insDto = service.postFeed(dto);
        assertEquals(dto.getIfeed(), insDto.getIfeed());

        verify(mapper).insFeed(any()); //안의 메소드 호출했는지 확인
        verify(picsMapper).insFeedPics(any());

        FeedInsDto dto2 = new FeedInsDto();
        dto.setIfeed(200);
        FeedPicsInsDto insDto1 = service.postFeed(dto2);
        assertEquals(dto2.getIfeed(), insDto1.getIfeed());

    }

    @Test
    void getFeedAll() {
        FeedSelVo feedSelVo1 = new FeedSelVo();
        feedSelVo1.setIfeed(1);
        feedSelVo1.setContents("1번 feedSelVo");

        FeedSelVo feedSelVo2 = new FeedSelVo();
        feedSelVo2.setIfeed(2);
        feedSelVo2.setContents("2번 feedSelVo");

        List<FeedSelVo> list = new ArrayList<>();
        list.add(feedSelVo1);
        list.add(feedSelVo2);

        when(mapper.selFeedAll(any())).thenReturn(list);

        List<String> feed1Pics = Arrays.stream(new String[]{"a.jpg", "b.jpg"}).toList(); //배열을 리스트로 변경

        List<String> feed2Pics = new ArrayList<>();
        feed2Pics.add("가.jpg");
        feed2Pics.add("나.jpg");

        List<List<String>> picsList = new ArrayList<>();
        picsList.add(feed1Pics);
        picsList.add(feed2Pics);

        List<String>[] picsArr = new List[2];
        picsArr[0] = feed1Pics;
        picsArr[1] = feed2Pics;

        when(picsMapper.selFeedPicsAll(1)).thenReturn(feed1Pics);
        when(picsMapper.selFeedPicsAll(2)).thenReturn(feed2Pics);

        FeedSelDto dto = new FeedSelDto();
        List<FeedSelVo> result = service.getFeedAll(dto, any());

        assertEquals(list, result); //ture라면 list만 검증하기


        for (int i = 0; i < list.size(); i++) {
            FeedSelVo vo = list.get(i);
            assertNotNull(vo.getPics());

            List<String> pics = picsList.get(i);
            assertEquals(vo.getPics(), pics);

            List<String> pics2 = picsArr[i];
            assertEquals(vo.getPics(), pics2);
        }
        //-------------- ifeed(1) 댓글
        List<FeedCommentSelVo> cmtsFeed1 = new ArrayList<>();

        FeedCommentSelVo cmtVo1_1 = new FeedCommentSelVo();
        cmtVo1_1.setIfeedComment(1);
        cmtVo1_1.setComment("1-cmtVo1_1");

        FeedCommentSelVo cmtVo1_2 = new FeedCommentSelVo();
        cmtVo1_2.setIfeedComment(2);
        cmtVo1_2.setComment("2-cmtVo1_2");

        cmtsFeed1.add(cmtVo1_1);
        cmtsFeed1.add(cmtVo1_2);

        FeedCommentSelDto fcDto1 = new FeedCommentSelDto();
        fcDto1.setStartIdx(0);
        fcDto1.setRowCount(Const.FEED_COMMENT_FIRST_CNT);
        fcDto1.setIfeed(1);
        when( commentMapper.selFeedCommentAll(fcDto1) ).thenReturn(cmtsFeed1);

        //-------------- ifeed(2) 댓글
        List<FeedCommentSelVo> cmtsFeed2 = new ArrayList<>();

        FeedCommentSelVo cmtVo2_1 = new FeedCommentSelVo();
        cmtVo2_1.setIfeedComment(3);
        cmtVo2_1.setComment("3-cmtVo2_1");

        FeedCommentSelVo cmtVo2_2 = new FeedCommentSelVo();
        cmtVo2_2.setIfeedComment(4);
        cmtVo2_2.setComment("4-cmtVo2_2");

        FeedCommentSelVo cmtVo2_3 = new FeedCommentSelVo();
        cmtVo2_3.setIfeedComment(5);
        cmtVo2_3.setComment("5-cmtVo2_3");

        FeedCommentSelVo cmtVo2_4 = new FeedCommentSelVo();
        cmtVo2_4.setIfeedComment(6);
        cmtVo2_4.setComment("6-cmtVo2_4");

        cmtsFeed2.add(cmtVo2_1);
        cmtsFeed2.add(cmtVo2_2);
        cmtsFeed2.add(cmtVo2_3);
        cmtsFeed2.add(cmtVo2_4);

        FeedCommentSelDto fcDto2 = new FeedCommentSelDto();
        fcDto2.setStartIdx(0);
        fcDto2.setRowCount(Const.FEED_COMMENT_FIRST_CNT);
        fcDto2.setIfeed(2);
        when( commentMapper.selFeedCommentAll(fcDto2) ).thenReturn(cmtsFeed2);

        FeedSelDto dto1 = new FeedSelDto();
        List<FeedSelVo> result1 = service.getFeedAll(dto1,any());

        assertEquals(list, result1);

        for(int i=0; i<list.size(); i++) {
            FeedSelVo vo = list.get(i);
            assertNotNull(vo.getPics());

            List<String> pics = picsList.get(i);
            assertEquals(vo.getPics(), pics);

            List<String> pics2 = picsArr[i];
            assertEquals(vo.getPics(), pics2);
        }

        List<FeedCommentSelVo> commentsResult1 = list.get(0).getComments();
        assertEquals(cmtsFeed1, commentsResult1, "ifeed(1) 댓글 체크");
        assertEquals(0, list.get(0).getIsMoreComment(), "ifeed(1) isMoreComment 체크");
        assertEquals(2,list.get(0).getComments().size());

        List<FeedCommentSelVo> commentsResult2 = list.get(1).getComments();
        assertEquals(cmtsFeed2, commentsResult2, "ifeed(2) 댓글 체크");
        assertEquals(1, list.get(1).getIsMoreComment(), "ifeed(2) isMoreComment 체크");


    }


    @Test
    void delFeed() {
    }

    @Test
    void toggleFeedFav() {
    }

}