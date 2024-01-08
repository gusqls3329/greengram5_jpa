package com.green.greengram4.feed;

import com.green.greengram4.feed.model.FeedDelDto;
import com.green.greengram4.feed.model.FeedFavDto;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//스프링은 데이터 베이스 테스트 할때 h2데이터 베이스를 사용.
        //위어노테이션은 h2데이터베이스로 바꾸지말고 기존 데이터 베이스를 사용하겠다는 의미
class FeedFavMapperTest { // 트렌젝션에 걸려서 롤백됨 (값이 들어갔다가 삭제)
    @Autowired //di할때 사용, 빈등록 된 주소값을 달라고할 때 사용
    private FeedFavMapper mapper;

    @Test
    public void insFeedFavTest() {
        FeedFavDto dto = new FeedFavDto();
        dto.setIfeed(97);
        dto.setIuser(2);

        List<FeedFavDto> result = mapper.selFeedFavForTest(dto); // 없었는지 확인
        assertEquals(0,result.size());

        int affectRows1 = mapper.insFeedFav(dto);
        assertEquals(1,affectRows1,"첫 insert");

        List<FeedFavDto> result2 = mapper.selFeedFavForTest(dto);
        assertEquals(1,result2.size());

        dto.setIfeed(5);
        dto.setIuser(2);
        int affectRows2 = mapper.insFeedFav(dto);
        assertEquals(1,affectRows2);

        List<FeedFavDto>  result3 = mapper.selFeedFavForTest(dto);
        assertEquals(1,result3.size());
    }

    @Test
    public void delFeedFavTest(){
        FeedFavDto dto = new FeedFavDto();
        dto.setIfeed(97);
        dto.setIuser(1);

        int affectRows1 = mapper.delFeedFav(dto); //삭제가 가능했으면 1
        assertEquals(1,affectRows1);

        int affectRows2 = mapper.delFeedFav(dto);//삭제가 가능했으면 0
        assertEquals(0,affectRows2);

        List<FeedFavDto> result2 = mapper.selFeedFavForTest(dto); //삭제가 성공했으니 값이 null
        assertEquals(0,result2.size());
    }

    @Test
    public void delFeedFavAllTest(){
        final int ifeed = 203;
        FeedDelDto dto = new FeedDelDto();
        dto.setIfeed(ifeed);

        FeedFavDto dto1 = new FeedFavDto();
        dto1.setIfeed(ifeed);
        List<FeedFavDto>  result1 = mapper.selFeedFavForTest(dto1);
        int affectRows1 = mapper.delFeedFavAll(dto);
        assertEquals(result1.size(),affectRows1);

        int affectRows2 = mapper.delFeedFavAll(dto);
        assertEquals(0,affectRows2);


        List<FeedFavDto> result2 = mapper.selFeedFavForTest(dto1);
        assertEquals(0,result2.size());
        ;
    }
}