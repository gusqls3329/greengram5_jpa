package com.green.greengram4.feed;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.greengram4.MocMvcConfig;
import com.green.greengram4.common.ResVo;
import com.green.greengram4.feed.model.FeedInsDto;
import com.green.greengram4.feed.model.FeedPicsInsDto;
import com.green.greengram4.feed.model.FeedSelVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@MocMvcConfig
@WebMvcTest({FeedController.class})
class FeedControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private FeedService service;
    @Autowired
    private ObjectMapper mapper; //객체를 제이슨 형태로 바꿀때 사용

    @Test
    void postFeed() throws Exception { //throws Exception : 예외가 발생할 수 있어서 던지
        FeedPicsInsDto insDto = new FeedPicsInsDto();
        given(service.postFeed(any())).willReturn(insDto); // given &when 은 같은 역할
        //when(service.postFeed(any())).thenReturn(result);
        FeedInsDto dto = new FeedInsDto();
        String json = mapper.writeValueAsString(dto);//dto를 제이슨으로 변경후 문자로 변경
        System.out.println("json: " + json);

        mvc.perform(
                        MockMvcRequestBuilders //대문자로 오면 클래스 이름 > 객체화하지않고 바로 사용하기 때문에 static메소드
                                .post("/api/feed") //방식에 따라 get/post로 사용 할 수 있다. 쿼리스트링으로 dto를 받을 경우 아래 메소드가 변경될 수 있다.
                                .contentType(MediaType.APPLICATION_JSON)//dto객체를 제이슨으로 날릴때. 데이터를 제이슨으로 날릴 경우 필수(제이슨으로 날릴거라는걸 뜻함)
                                .content(json) // 바디부분에 제이슨을 받는 것.
                )
                .andExpect(status().isOk()) //status() : 상태값, isOk(): 통신 성공
                .andExpect(content().string(mapper.writeValueAsString(insDto))) //위에 when으로 호출 하면 result로 리턴,
                .andDo(print()); //결과를 프린트 하라

        verify(service).postFeed(any()); //서비스에있는 postFeed가 실행되었는지 검증
    }

    @Test
    void getFeedAll() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("page", "1"); //key값 values값
        params.add("loginedIuser", "3");

        List<FeedSelVo> vos = new ArrayList<>();
        FeedSelVo vo = new FeedSelVo();
        vo.setIfeed(1);
        vo.setLocation("한글");
        vo.setWriterNm("사용");
        vos.add(vo);
        //when(service.getFeedAll(any(),any())).thenReturn(vos);

        mvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/feed")
                                .params(params)
                ).andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(vos)))
                .andDo(print());
        verify(service).getFeedAll(any(),any());
    }

    @Test
    void delFeed() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("ifeed", "1");
        params.add("iuser", "2");
        //주소값에 ifeed=1&iuser=2로 주소값에 입력됨
        ResVo resVo = new ResVo(3);

        when(service.delFeed(any())).thenReturn(resVo);

        mvc.perform(
                        MockMvcRequestBuilders
                                .delete("/api/feed")
                                .params(params)
                ).andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(resVo)))
                .andDo(print());

        verify(service).delFeed(any());
    }

    @Test
    void toggleFeedFav() {
    }
}