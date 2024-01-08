package com.green.greengram4.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component //Component : 빈등록(역할은 없음)
//상수클래스로 만들어도 상관없음  : Const랑 사용하는 방식과 같음
public class JwtTokenProvider {//암호화 할때 사용하는 키
    private final String secret;
    private final String headerSchemeName;
    private final String tokenType;
    private Key key;

    /*
    아래 코드는
    application의 springboot:
        jwt:
          secret:
          부분이 주입되도록 하는 코드
          */
    /*
    아래 코드말고 주입 되는 방법 :  (아래 public를 사용한 이유는 final을 붙이고 싶어서..아래코드는 final을 붙일 수 없다.
    @Value("${springboot.jwt.secret}")
    private String secret;
    */

    public JwtTokenProvider(@Value("${springboot.jwt.secret}") String secret
    ,@Value("${springboot.jwt.header-scheme-name}") String headerSchemeName
            ,@Value("${springboot.jwt.tokenType}") String tokenType){
        this.secret = secret;
        this.headerSchemeName = headerSchemeName;
        this.tokenType = tokenType;
        log.info("secret:{}", secret);
    }

    //DI는 외부에서 객체 간의 관계(의존성)를 결정해 주는데 즉, 객체를 직접 생성하는 것이 아니라 외부에서 생성 후 주입시켜 주는 방식
    @PostConstruct //@Component : 사용하기 위해선 빈등록이 되어야함. private~ : 호출되기전에 di부터 이루어짐,  public void init() : 스프링 컨테이너가 호출됨
    // 빈등록이 되고 생성자를 통해 di받고 난 후 생성자를 통해서 di를 받지 않더라도  di가 된 후에 호출 됨,빈등록이 되었기 때문에 의미가 있음.
    public void init() {
        log.info("secret : {}", secret);
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        log.info("keyBytes : {}", keyBytes);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        //key : jwt토큰 만들때 사용할 예정
    }
    /*
    @PostConstruct
    생성자가 호출되고 객체는 생성된 상태에서 스프링 컨테이너가 등록되기전에 실행: bean은 초기화 전이다.(DI가 이루어 지기 전) @PostConstruct 를 사용하면,
    bean이 초기화 됨과 동시에 의존성을 확인할 수 있다.
    bean lifeCycle에서 오직 한 번만 수행된다. (여러 번 초기화 방지)
     */

    //토큰/JWT 만드는것
    public String generateToken(MyPrincipal principal, long tokenValidMs) {//tokenValidMs 엡세스토큰:시간  리프레시 토큰:날을
        Date now = new Date();
        return Jwts.builder()
                .claims(createClasims(principal))
                .issuedAt(new Date(System.currentTimeMillis()))//issuedAt: 발행일 : 만든날짜
                .expiration(new Date(System.currentTimeMillis()+tokenValidMs))
                .signWith(this.key)
                .compact();
    }
    public Claims createClasims(MyPrincipal principal){
       return Jwts.claims()
               .add("iuser",principal.getIuser())
               .build();
    }
    //토큰/JWT 만드는것을 해석하는것
    //1. 토큰 뽑아내는 것
    public String resolbeToken(HttpServletRequest req){//HttpServletRequest가 요청이 서버에서 오면 무조건 만들어지는 객체, 모든 요청과 관련된 주소(정보)가 다 입력되어있음
        String auth = req.getHeader(headerSchemeName); //key값으로 달려있는것을 value값을 요청

        if(auth == null){
            return null;
        }
        //Bearer Asjkadjflaksfw309jasdklfj
        if(auth.startsWith(tokenType)){
            return auth.substring(tokenType.length()).trim();
        }
        return null;
    }
}
