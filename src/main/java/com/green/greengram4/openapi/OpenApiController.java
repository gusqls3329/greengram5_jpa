package com.green.greengram4.openapi;

import com.green.greengram4.openapi.model.ApartmantTransactionDetailDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/open")
public class OpenApiController {
    private final OpenApiService service;

    @GetMapping
    public List<ApartmantTransactionDetailDto> getApartment(ApartmantTransactionDetailDto dto){
return null;
    }
}
