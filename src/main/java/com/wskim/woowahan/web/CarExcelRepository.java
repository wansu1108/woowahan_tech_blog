package com.wskim.woowahan.web;

import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.wskim.woowahan.web.dto.CarExcelDto;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@AllArgsConstructor
@Slf4j
public class CarExcelRepository {

    private final JdbcTemplate template;

    public void initTable(){
        template.execute("create table car(company varchar, name varchar, price integer, rating double)");
    }

    public void save(CarExcelDto dto){
        log.debug("{}", dto.getCompany());
        template.update("insert into car(company, name, price, rating) values(?,?,?,?)"
        , dto.getCompany(), dto.getName(), dto.getPrice(), dto.getRating());
    }

    public List<CarExcelDto> findAll(){
        return template.query("select company, name, price, rating from car", 
            BeanPropertyRowMapper.newInstance(CarExcelDto.class));
    }
}
