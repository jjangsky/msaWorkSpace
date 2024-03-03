package com.example.userserivce.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseUser {

    // 성공적으로 회원가입이 완료 되었을 경우 반환되는 객체

    private String email;
    private String name;
    private String userId;

    private List<ResponseOrder> orders;
}
