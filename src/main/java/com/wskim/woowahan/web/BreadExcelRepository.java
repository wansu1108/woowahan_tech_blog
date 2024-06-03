package com.wskim.woowahan.web;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.wskim.woowahan.web.dto.BreadDto;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class BreadExcelRepository {
    private final JdbcTemplate template;

    public void initTable(){
        template.execute("create table bread(brand varchar, productName varchar, price integer, rating double)");
    }

    public void save(BreadDto dto){
        template.update("insert into bread(brand, productName, price, rating) values(?,?,?,?)"
        , dto.getBrand(), dto.getProductName(), dto.getPrice(), dto.getRating());
    }

    public List<BreadDto> findAll(){
        return template.query("select brand, productName, price, rating from bread", 
            BeanPropertyRowMapper.newInstance(BreadDto.class));
    }
}
