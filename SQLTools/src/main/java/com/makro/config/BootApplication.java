package com.makro.config;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
@MapperScan(basePackages = "com.makro.mapper")
@Configuration
@PropertySource(value = { "classpath:jdbc.properties" })
@ComponentScan(basePackages = "com.makro")
@SpringBootApplication
public class BootApplication {

	/*
	 * @Value("${jdbc.driver}") private String driverClassName;
	 * 
	 * @Value("${jdbc.url}") private String url;
	 * 
	 * @Value("${jdbc.username}") private String username;
	 * 
	 * @Value("${jdbc.password}") private String password;
	 * 
	 * // 配置数据源
	 * 
	 * @Bean(destroyMethod = "close") public DataSource dataSource() {
	 * BasicDataSource basicDataSource = new BasicDataSource();
	 * basicDataSource.setDriverClassName(driverClassName);
	 * basicDataSource.setUrl(url); basicDataSource.setUsername(username);
	 * basicDataSource.setPassword(password); return basicDataSource; }
	 */
	@Bean(DataSourceConstants.DS_KEY_MASTER)
    @ConfigurationProperties(prefix = "sqltools.master")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(DataSourceConstants.DS_KEY_POS)
    @ConfigurationProperties(prefix = "sqltools.pos")
    public DataSource slaveDataSource() {
        return DataSourceBuilder.create().build();
    }
    
    @Bean
    @Primary
    public DataSource dynamicDataSource() {
        Map<Object, Object> dataSourceMap = new HashMap<>(2);
        dataSourceMap.put(DataSourceConstants.DS_KEY_MASTER, masterDataSource());
        dataSourceMap.put(DataSourceConstants.DS_KEY_POS, slaveDataSource());
        //设置动态数据源
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setTargetDataSources(dataSourceMap);
        dynamicDataSource.setDefaultTargetDataSource(masterDataSource());
        return dynamicDataSource;
    }

	protected SpringApplicationBuilder springApplicationBuilder(SpringApplicationBuilder builder) {
		return builder.sources(BootApplication.class);
	}
}