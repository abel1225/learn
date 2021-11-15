package me.abel.fluentMybatis.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.abel.fluentMybatis.entity.HelloEntity;
import me.abel.fluentMybatis.mapper.HelloMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HelloService {
    @Autowired
    HelloMapper helloMapper;

    public void findAll () throws JsonProcessingException {
        List<HelloEntity> entities = helloMapper.listPoJos(HelloEntity.class, helloMapper.query().select.ssn().ssnName().end());
        System.out.println(new ObjectMapper().writeValueAsString(entities));
    }
}
