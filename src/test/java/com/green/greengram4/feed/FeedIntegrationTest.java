package com.green.greengram4.feed;

import com.green.greengram4.BaseIntegrationTest;
import com.green.greengram4.common.ResVo;
import com.green.greengram4.feed.model.FeedInsDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
public class FeedIntegrationTest extends BaseIntegrationTest { //BaseIntegrationTest를상속
    @Test
    @Rollback(false) //실제 데이터 베이스에 값을 들가도록 함 @Rollback(true)면 실제 데이터 베이스에 값이 들어가지 않도록 함
    public void postFeed() throws Exception {
        FeedInsDto dto = new FeedInsDto();
        dto.setIuser(2);
        dto.setContents("통합 테스트 작업 중");
        dto.setLocation("그린컴퓨터학원");

        List<String> pics = new ArrayList<>();
        pics.add("https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMzEyMTdfMjc5%2FMDAxNzAyODE4NzY5MTQx.NfakGWVT0TEQOm4Wn0CJ1l97AI1l5fnn_xnq5bztSC4g.qOokq2aQEkZ1oYgGhM7Cz3w-my_sd9XjrhmSnTq2cekg.JPEG.tnrdls215%2FP20231217_084431722_C00CEFDD-43C2-4336-B38D-20EC129E1EE2.JPG&type=a340");
        pics.add("https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2F20150128_10%2Fshrinkles_14224321219420Hk2f_JPEG%2F%25B4%25E7%25B1%25D9_%25BB%25F6%25C4%25A5.jpg&type=a340");
        dto.setPics(pics);

        String json = om.writeValueAsString(dto);//dto를 제이슨으로 변경후 문자로 변경
        System.out.println("json: " + json);

        MvcResult mr = mvc.perform(
                        MockMvcRequestBuilders //대문자로 오면 클래스 이름 > 객체화하지않고 바로 사용하기 때문에 static메소드
                                .post("/api/feed") //방식에 따라 get/post로 사용 할 수 있다. 쿼리스트링으로 dto를 받을 경우 아래 메소드가 변경될 수 있다.
                                .contentType(MediaType.APPLICATION_JSON)//dto객체를 제이슨으로 날릴때. 데이터를 제이슨으로 날릴 경우 필수(제이슨으로 날릴거라는걸 뜻함)
                                .content(json) // 바디부분에 제이슨을 받는 것.
                )
                .andExpect(status().isOk()) //status() : 상태값, isOk(): 통신 성공
                .andDo(print())
                .andReturn();

        String content = mr.getResponse().getContentAsString(); //getResponse: 응답 : 보내주기
        log.info("content222 = {}", content);
        ResVo resVo = om.readValue(content, ResVo.class);
        assertEquals(true, resVo.getResult() > 0);
        log.info("resVo :{}", resVo.getResult());
        //assertEquals(dto.getIfeed(),resVo.getResult());
        //컨트롤러와 다른 곳이며 테스트는 메퍼로 도달할 수 없어서 ifeed값을 받아 올 수없다.
        //Assertions.assertTrue(resVo.getResult() > 0);
    }

    @Test
    @Rollback(false)
   // @Rollback(false) : 실제 값 입력
    //@Rollback(true) : 실제 값이 안들어감
    public void delFeed() throws Exception {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("iuser", "2"); //key값 values값
        params.add("ifeed", "116");
       /* MvcResult mr = mvc.perform(
                        MockMvcRequestBuilders
                                .delete("/api/feed?iuser={iuser}&ifeed={ifeed}",2,116)
                ).andExpect(status().isOk())
                .andDo(print())
                .andReturn();
                아래 코드와 같은 코드
        */
        MvcResult mr = mvc.perform(
                        MockMvcRequestBuilders
                                .delete("/api/feed") //
                                .params(params) // .delete("/api/feed?iuser=2&ifeed=116") 과 같은 코드이다.
                ).andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        String content = mr.getResponse().getContentAsString(); //응답이 오면 body를 string타입으로 가져오는 코드
        ResVo resVo = om.readValue(content, ResVo.class); //content : json형태 ,그 json형태가  ResVo.class로 변경 해달라는 요청(:
        assertEquals(true, resVo.getResult() > 0);
        assertEquals(1, resVo.getResult());
    }
}
