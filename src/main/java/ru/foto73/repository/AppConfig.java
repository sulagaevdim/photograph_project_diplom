//package ru.foto73.repository;
//
//import lombok.extern.slf4j.Slf4j;
//import org.h2.tools.Server;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.sql.SQLException;
//
//@Configuration
//@Slf4j
//public class AppConfig {
//    @Bean(initMethod = "start", destroyMethod = "stop")
//    public Server h2Server() throws SQLException {
//        System.out.println("Start H2 TCP Server");
//        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
//    }
//}
