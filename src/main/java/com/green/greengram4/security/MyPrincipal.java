package com.green.greengram4.security;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/*
jwt는 사용자 정보를 서버가 저장을 안하고 토큰에 내용을 박은 다음 00에게 주고 그것을 열어서 해석해서 권한없는 건 튕겨내고 권한있는거만 통과시킴
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyPrincipal {//토큰에 넣을 때 사용
    //로그인 한사람의 pk값을 넣을건데 로그인한 사람이 늘어날 경우 MyPrincipal을 늘리면 되기 때문에 사용
    private int iuser; //보통 권한까지 같이 담음 : 현코드는 권한은 없음
    @Builder.Default //null이 들어갈수 없도록 함  =  Builder패턴을 쓸경우 디폴트로 new ArrayList<>();로 하겠다
    private List<String> roles = new ArrayList<>();
}
