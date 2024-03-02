package com.example.userserivce.service;

import com.example.userserivce.dto.UserDto;
import com.example.userserivce.jpa.UserEntity;
import com.example.userserivce.jpa.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());

        // modelMapper 라이브러리를 사용해서 사용자로부터 받은 정보(Dto)를 Entity 타입으로 변환 처리
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = mapper.map(userDto, UserEntity.class);

        // Password 암호화 처리
        userEntity.setEncryptedPwd("test_encrypted_pwd");

        userRepository.save(userEntity);

        UserDto retrunUserDto = mapper.map(userEntity, UserDto.class);

        return retrunUserDto;
    }
}
