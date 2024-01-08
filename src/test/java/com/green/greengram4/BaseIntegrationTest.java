package com.green.greengram4;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
//@Import(CharEncodingConfig.class)
@MocMvcConfig //위//Import중하나 사용 둘다 같은 한글깨지지 않도록 사용
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) //렌덤한 포트를 이용해서
@AutoConfigureMockMvc
@Transactional //트랜젝션 걸때사용
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //DB를 바꾸지 않겠다
public class BaseIntegrationTest { //컨트롤러, 서비스, 메퍼까지 테스트함 // 공통된것을 상속해 사용하려고
    @Autowired protected MockMvc mvc; //통신을 시도
    @Autowired protected ObjectMapper om; //젝슨 라이브러리 : 객체를 제이슨으로 제이슨을 객체로 바꿔주는것 : 통신할때 데이터를 주고받을 때,
}
