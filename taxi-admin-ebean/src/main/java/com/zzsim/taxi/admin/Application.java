package com.zzsim.taxi.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.ebean.EbeanServer;
import io.ebean.EbeanServerFactory;
import io.ebean.config.ServerConfig;
import io.ebean.config.UnderscoreNamingConvention;
import io.ebean.spring.txn.SpringJdbcTransactionManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

/**
 * Created by zengyufei on 2017/7/20.
 */
@SpringBootApplication
public class Application {

	@Value("${spring.profiles.active}")
	private String env;

	@Value("${ebeans.packages}")
	private String packages;

	@Value("${ebeans.dbName}")
	private String dbName;

	@Bean(autowire = Autowire.BY_TYPE)
	@Scope(SCOPE_SINGLETON)
	public EbeanServer getEbeanServer() {
		boolean isGenerateSqlTxt = !"db".equals(dbName) || "create".equals(env);
		ServerConfig config = new ServerConfig();
		config.setName(StringUtils.isNotBlank(dbName) ? dbName : "db");
		config.loadFromProperties();
		config.setDdlGenerate(isGenerateSqlTxt);
		config.setDdlRun(isGenerateSqlTxt);
		config.addPackage(packages);
		config.setExternalTransactionManager(new SpringJdbcTransactionManager());// 事务支持
		config.setNamingConvention(new UnderscoreNamingConvention());// 下划线
		EbeanServer ebeanServer = EbeanServerFactory.create(config);
		return ebeanServer;
	}

	/**
	 * 方法级别的单个参数验证
	 */
	@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor() {
		return new MethodValidationPostProcessor();
	}

	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(mapper);
		return converter;
	}

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Application.class);
		app.run(args);
	}

}
