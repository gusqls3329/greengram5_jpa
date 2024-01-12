package com.green.greengram4.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.greengram4.common.AppProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.conscrypt.ct.DigitallySigned;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;

import java.util.Date;

@Slf4j
@Component //Component : 빈등록(역할은 없음)
@RequiredArgsConstructor
//상수클래스로 만들어도 상관없음  : Const랑 사용하는 방식과 같음
public class JwtTokenProvider {//암호화 할때 사용하는 키
    /*private final String secret;
    private final String headerSchemeName;
    private final String tokenType;*/
    private final ObjectMapper om; //젝슨 라이브러리에 있는것 : 제이슨에서 객체로 객체에서 제이슨으로 바꾸어주는것.
    private final AppProperties appProperties;
    private SecretKeySpec secretKeySpec;

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

    /*public JwtTokenProvider(@Value("${springboot.jwt.secret}") String secret
    ,@Value("${springboot.jwt.header-scheme-name}") String headerSchemeName
            ,@Value("${springboot.jwt.tokenType}") String tokenType){
        this.secret = secret;
        this.headerSchemeName = headerSchemeName;
        this.tokenType = tokenType;
        log.info("secret:{}", secret);
    }*/

    //DI는 외부에서 객체 간의 관계(의존성)를 결정해 주는데 즉, 객체를 직접 생성하는 것이 아니라 외부에서 생성 후 주입시켜 주는 방식
    @PostConstruct //@Component : 사용하기 위해선 빈등록이 되어야함. private~ : 호출되기전에 di부터 이루어짐,  public void init() : 스프링 컨테이너가 호출됨
    // 빈등록이 되고 생성자를 통해 di받고 난 후 생성자를 통해서 di를 받지 않더라도  di가 된 후에 호출 됨,빈등록이 되었기 때문에 의미가 있음.
    public void init() {
        this.secretKeySpec = new SecretKeySpec(appProperties.getJwt().getSecret().getBytes()
                , SignatureAlgorithm.HS256.getJcaName());
        //key : jwt토큰 만들때 사용할 예정
    }
    /*
    @PostConstruct
    생성자가 호출되고 객체는 생성된 상태에서 스프링 컨테이너가 등록되기전에 실행: bean은 초기화 전이다.(DI가 이루어 지기 전) @PostConstruct 를 사용하면,
    bean이 초기화 됨과 동시에 의존성을 확인할 수 있다.
    bean lifeCycle에서 오직 한 번만 수행된다. (여러 번 초기화 방지)
     */
    public String generateRefreshToken(MyPrincipal principal){
        return generateToken(principal, appProperties.getJwt().getRefreshTokenExpiry());

    }
    public String generateAccessToken(MyPrincipal principal){
        return generateToken(principal, appProperties.getJwt().getAccessTokenExpiry());

    }
    //토큰/JWT 만드는것
    private String generateToken(MyPrincipal principal, long tokenValidMs) {//tokenValidMs 엡세스토큰:시간  리프레시 토큰:날을
        Date now = new Date();
        return Jwts.builder()
                .claims(createClasims(principal)) //토큰에 담김
                .issuedAt(new Date(System.currentTimeMillis()))//issuedAt: 발행일 : 만든날짜
                .expiration(new Date(System.currentTimeMillis() + tokenValidMs)) // 만료일
                .signWith(secretKeySpec)
                .compact();
    }

    public Claims createClasims(MyPrincipal principal) { //Claims = hashmap스타일 : extends함.
        try {
            String json = om.writeValueAsString(principal);//예외처리를 해야함
            return Jwts.claims()
                    .add("user", json)//key ("iuser")값과 valuse값(json)을 저장할 수 있는 것
                    .build();
        } catch (Exception e) {
            return null;
        }
    }

    //토큰/JWT 만드는것을 해석하는것
    //1. 토큰 뽑아내는 것
    public String resolbeToken(HttpServletRequest req) {//HttpServletRequest가 요청이 서버에서 오면 무조건 만들어지는 객체, 모든 요청과 관련된 주소(정보)가 다 입력되어있음
        String auth = req.getHeader(appProperties.getJwt().getHeaderSchemeName()); //key값으로 달려있는것을 value값을 요청

        if (auth == null) {
            return null;
        }
        //Bearer Asjkadjflaksfw309jasdklfj
        if (auth.startsWith(appProperties.getJwt().getTokenType())) {
            //auth : Bearer Asjkadjflaksfw309jasdklfj가 담김 // appProperties.getJwt().getTokenType(): Bearer로 시작한다
            return auth.substring(appProperties.getJwt().getTokenType().length()).trim();//trim() : 문자의 앞뒤의 공백을 제거(중간의 공백은 제거하지 않음)
            //appProperties.getJwt().getTokenType().length() : 6개(Bearer로 6글자이기 때문) > 6.trim() : 6부터 끝까지짤라내서 리턴(원본은 변경되지않음 : Asjkadjflaksfw309jasdklfj(공백 포함X)
        }//substring : 문자열 짜르기 : 인자를 한개 보내(처음부터 끝까지)나 두개 보낼수() 있는데 지금은 한개 보내기 때문에 시작점부터 끝까지라는 뜻
        return null;
    }

    public boolean isValidateToken(String token) { //만료되었는지 확인하는 코드
        try {
            return !getAllClaims(token).getExpiration().before(new Date()); //만료기간이 현재시간보다 전이면 false(현재시간이 만료기간을 넘었다), 만료기간이 현재시간보다 후면 true(현재시간이 만료기간을 넘지않았다)
        } catch (Exception e) {
            return false;
        }

    }

    private Claims getAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(secretKeySpec)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = getUserDetailsFromToken(token);
        return userDetails == null ? null : new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());

    }

    public UserDetails getUserDetailsFromToken(String token) {
        try {
            Claims claims = getAllClaims(token);
            String json = (String) claims.get("user");
            MyPrincipal myPrincipal = om.readValue(json, MyPrincipal.class);
            return MyUserDetails.builder()
                    .myPrincipal(myPrincipal).build();
        } catch (Exception e) {
            return null;
        }

    }
}
