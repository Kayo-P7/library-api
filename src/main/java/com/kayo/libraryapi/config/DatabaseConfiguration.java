package com.kayo.libraryapi.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@Profile("config-db")
public class DatabaseConfiguration {

    @Value("${spring.datasource.url}")
    String url;
    @Value("${spring.datasource.username}")
    String username;
    @Value("${spring.datasource.password}")
    String password;
    @Value("${spring.datasource.driver-class-name}")
    String driver;

    //@Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();   //não é muito utilizado em produção!
        // serve para resolver problemas rápidos
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setDriverClassName(driver);
        return ds;

    }


    @Bean
    public DataSource hikariDataSource() { //springboot usa por padrão
        HikariConfig configHikari = new HikariConfig();  //Muito utilizado em produção!

        configHikari.setJdbcUrl(url);
        configHikari.setUsername(username);
        configHikari.setPassword(password);
        configHikari.setDriverClassName(driver);

        configHikari.setMaximumPoolSize(10); //pode resolver o problema do projeto
        //Se 10 usuários estiverem fazendo consultas ao mesmo tempo, as 10 conexões estarão ocupadas.
        // Se um 11º usuário chegar, ele espera alguns milissegundos até que uma das 10 conexões termine
        // a tarefa e seja devolvida para o "pool"
        //mas não é liberado automaticamente

        configHikari.setMinimumIdle(1);//tamanho inicial do pool
        configHikari.setMaxLifetime(600000);
        configHikari.setConnectionTimeout(100000); //timeout para conseguir uma conexão
        configHikari.setConnectionTestQuery("select 1"); //query de teste
        return new HikariDataSource(configHikari);

    }
}