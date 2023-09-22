package com.example.jdbcclientproposal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import javax.sql.DataSource;

import java.security.spec.NamedParameterSpec;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class JdbcClientTest {
    @Autowired
    private JdbcClient jdbcClient;

    @Test
    void worksWithNamedParamters() {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcClient.sql("insert into persons(name) values (:name)")
                .param("name", "test")
                .update(keyHolder);
        assertThat(keyHolder.getKey().longValue()).isPositive();
    }

    @Test
    void doesNotWorkWithPositionalParamters() {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcClient.sql("insert into persons(name) values (?)")
                .param("test")
                .update(keyHolder);
        assertThat(keyHolder.getKey().longValue()).isPositive();
    }
}
