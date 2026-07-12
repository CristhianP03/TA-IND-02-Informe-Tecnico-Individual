package com.banco.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Primary
    @Bean(name = "cuencaDataSource")
    @ConfigurationProperties(prefix = "banco.cuenca")
    public DataSource cuencaDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "quitoDataSource")
    @ConfigurationProperties(prefix = "banco.quito")
    public DataSource quitoDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "cuencaJdbcTemplate")
    public JdbcTemplate cuencaJdbcTemplate(
            @Qualifier("cuencaDataSource") DataSource ds) {
        return new JdbcTemplate(ds);
    }

    @Bean(name = "quitoJdbcTemplate")
    public JdbcTemplate quitoJdbcTemplate(
            @Qualifier("quitoDataSource") DataSource ds) {
        return new JdbcTemplate(ds);
    }

    @Bean(name = "guayaquilDataSource")
    @ConfigurationProperties(prefix = "banco.guayaquil")
    public DataSource guayaquilDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "guayaquilJdbcTemplate")
    public JdbcTemplate guayaquilJdbcTemplate(
            @Qualifier("guayaquilDataSource") DataSource ds) {
        return new JdbcTemplate(ds);
    }
}
