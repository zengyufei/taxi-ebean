package com.zzsim.taxi.admin;

import io.ebean.EbeanServer;
import io.ebean.EbeanServerFactory;
import io.ebean.config.ServerConfig;
import io.ebean.config.UnderscoreNamingConvention;
import io.ebean.spring.txn.SpringJdbcTransactionManager;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

/**
 * Created by zengyufei on 2017/7/20.
 */
@SpringBootApplication
public class Application {

	@Value("${ebeans.packages}")
	private String packages;

	@Value("${ebeans.dbName}")
	private String dbName;

	@Bean(autowire = Autowire.BY_TYPE)
	@Scope(SCOPE_SINGLETON)
	public EbeanServer getEbeanServer()  {
		ServerConfig config = new ServerConfig();
		config.setName(dbName);
		config.loadFromProperties();
		config.setDdlGenerate(!"db".equals(dbName));
		config.setDdlRun(!"db".equals(dbName));
		config.addPackage(packages);
		config.setExternalTransactionManager(new SpringJdbcTransactionManager());// 事务支持
		config.setNamingConvention(new UnderscoreNamingConvention());// 下划线
		return EbeanServerFactory.create(config);
	}

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Application.class);
		app.run(args);
	}
}
