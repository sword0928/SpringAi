package org.asksword.ai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MysqlController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/mysql")
    public Object test() {
        return jdbcTemplate.queryForList("select 1 as ok");
    }
}
