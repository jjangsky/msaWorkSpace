package com.example.userserivce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableDiscoveryClient
public class UserSerivceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserSerivceApplication.class, args);
    }

    /**
     * 의존성을 주입할 때 객체는 어디에서든지 최초 1회 초기화 과정이 필요하다.
     * -> 초기화 과정 없이 의존성 주입(Autowired)시 오류 발생
     * BCrypt같은 암호화 라이브러리는 프로젝트 내에서 초기화 할 일이 거의 없으므로
     * 스프링이 시작되는 최초의 지점인 여기 부분에서 Bean 어노테이션을 사용해서
     * 초기화를 시켜줄 수 있다
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
