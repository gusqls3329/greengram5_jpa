package com.green.greengram4.feed;

import com.green.greengram4.feed.model.FeedDelDto;
import com.green.greengram4.feed.model.FeedInsDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FeedPicsMapperTest {
    @Autowired
    private FeedPicsMapper mapper;


    @Test
    @DisplayName("현빈")
    void insFeedPics1() {
        final int ifeed = 102;

        FeedInsDto dto = new FeedInsDto();
        List<String> pics = new ArrayList<>();

        List<String> pic = mapper.selFeedPicsAll(ifeed);
        dto.setPics(pics);
        pics.add("103-1");
        pics.add("103-2");
        pics.add("103-3");
        dto.setIfeed(ifeed);

        int affectRows1 = mapper.insFeedPics(dto);
        assertEquals(dto.getPics().size(),affectRows1,"사진 insert");

        List<String> pic2 = mapper.selFeedPicsAll(dto.getIfeed());
        assertEquals(pic.size()+dto.getPics().size(),pic2.size());

        for (int i = 0; i < dto.getPics().size(); i++) {
            assertEquals(dto.getPics().get(i),pic2.get(pic.size()+i));
        }
    }

    private FeedInsDto dto;
    public FeedPicsMapperTest(){
        this.dto = new FeedInsDto();
        this.dto.setIfeed(6);
        List<String> pics = new ArrayList<>();
        pics.add("a.jpg");
        pics.add("b.jpg");
        this.dto.setPics(pics);

    }
    @BeforeEach
    void beforeEach() {
        FeedDelDto delDto = new FeedDelDto();
        delDto.setIfeed(this.dto.getIfeed());
        delDto.setIuser(4);
        int affectRows1 = mapper.delFeedPicsAll(delDto);

    }
    @Test
    @DisplayName("강사님")
    void insFeedPics2() {
        List<String> preList = mapper.selFeedPicsAll(dto.getIfeed());
        assertEquals(0,preList.size());

        int insAffectRows = mapper.insFeedPics(dto);
        assertEquals(dto.getPics().size(),insAffectRows);

        List<String> afterList = mapper.selFeedPicsAll(dto.getIfeed());
        assertEquals(dto.getPics().size(),afterList.size());

        for (int i = 0; i <dto.getPics().size(); i++) {
            assertEquals(dto.getPics().get(i),afterList.get(i));
        }

    }

    @Test
    void selFeedPicsAll() {
    }

    @Test
    void delFeedPicsAll() {
    }
}