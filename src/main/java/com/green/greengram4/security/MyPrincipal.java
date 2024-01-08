package com.green.greengram4.security;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
/*
jwt는 사용자 정보를 서버가 저장을 안하고 토큰에 내용을 박은 다음 00에게 주고 그것을 열어서 해석해서 권한없는 건 튕겨내고 권한있는거만 통과시킴
 */
@Getter
@Setter
@Builder
public class MyPrincipal {//로그인 한사람의 pk값을 넣을건데 로그인한 사람이 늘어날 경우 MyPrincipal을 늘리면 되기 때문에 사용
    private int iuser; //보통 권한까지 같이 담음 : 현코드는 권한은 없음
}
