package com.green.greengram4.openapi;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.green.greengram4.common.OpenApiProperties;
import com.green.greengram4.openapi.model.ApartmantTransactionDetailDto;
import com.green.greengram4.openapi.model.ApartmantTransactionDetailVo;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.reactive.JettyClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class OpenApiService {
    //+ = 은 url로 사용할 수 없음 > 인코딩이 필요 =은 인코딩이 가능하지만 +는
    //url에 %의 존재는 인코딩을 했다는 것을 뜻함
    private final OpenApiProperties openApiProperties;

    // 인코딩이 2번되면 인터넷에서는 디코딩을 한번만 하기때문에 오류가 발생함
    //인코딩이 된것을 또 인코딩을 해버리면 인코딩이 2번되기 때문에 인코딩된걸 보낼때 인코딩을 하지말라는 코드가 아래3줄임
    public List<ApartmantTransactionDetailVo> getapartmantTransactionList(ApartmantTransactionDetailDto dto) throws Exception {
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(openApiProperties.getApartment().getBaseUrl());
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);
        //통신을 위한 코드
        WebClient webClient = WebClient.builder()
                .filters(exchangeFilterFunctions -> exchangeFilterFunctions.add(logRequest()))
                //.clientConnector(new JettyClientHttpConnector(httpClient))
                .uriBuilderFactory(factory)
                .baseUrl(openApiProperties.getApartment().getBaseUrl())
                .build();

        String data = webClient.get().uri(uriBuilder -> {
                    UriBuilder ud = uriBuilder.path(openApiProperties.getApartment().getDataUrl())
                            .queryParam("DEAL_YMD", dto.getDealYm())
                            .queryParam("LAWD_CD", dto.getLawdCd())
                            .queryParam("serviceKey", openApiProperties.getApartment().getServiceKey());
                    if (dto.getPageNo() > 0) {
                        ud.queryParam("pageNo", dto.getPageNo());
                    }

                    if (dto.getNumOfRows() > 0) {
                        ud.queryParam("numOfRows", dto.getNumOfRows());
                    }

                    return ud.build();
                }
        ).retrieve().bodyToMono(String.class).block();
        ObjectMapper om = new XmlMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        JsonNode jsonNode = om.readTree(data);
        List<ApartmantTransactionDetailVo> list = om.convertValue(jsonNode.path("body").path("items").path("item"), new TypeReference<List<ApartmantTransactionDetailVo>>(){});

        return list;
    }

    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            if (log.isDebugEnabled()) {
                StringBuilder sb = new StringBuilder("Request: \n");
                //append clientRequest method and url
                clientRequest
                        .headers()
                        .forEach((name, values) -> values.forEach(value -> log.info(value)));
                log.debug(sb.toString());
            }
            return Mono.just(clientRequest);
        });
    }
}
