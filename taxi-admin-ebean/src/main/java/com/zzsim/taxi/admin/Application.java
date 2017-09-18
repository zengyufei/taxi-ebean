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
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@EnableSwagger2
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

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.zzsim.taxi.admin.controller"))
				.paths(PathSelectors.any())
				.build();
	}

	private ApiInfo apiInfo() {
		/*
		 说明：
			@Api：用在类上，说明该类的作用
			@ApiOperation：用在方法上，说明方法的作用
			@ApiImplicitParams：用在方法上包含一组参数说明
			@ApiImplicitParam：用在@ApiImplicitParams注解中，指定一个请求参数的各个方面
			paramType：参数放在哪个地方
			header-->请求参数的获取：@RequestHeader
			query-->请求参数的获取：@RequestParam
			path（用于restful接口）-->请求参数的获取：@PathVariable
			body（不常用）
			form（不常用）
			name：参数名
			dataType：参数类型
			required：参数是否必须传
			value：参数的意思
			defaultValue：参数的默认值
			@ApiResponses：用于表示一组响应
			@ApiResponse：用在@ApiResponses中，一般用于表达一个错误的响应信息
			code：数字，例如400
			message：信息，例如"请求参数没填好"
			response：抛出异常的类
			@ApiModel：描述一个Model的信息（这种一般用在post创建的时候，使用@RequestBody这样的场景，请求参数无法使用@ApiImplicitParam注解进行描述的时候）
			@ApiModelProperty：描述一个model的属性
		 */
		return new ApiInfoBuilder()
				.title("Spring Boot中使用Swagger2构建RESTful APIs")
				.description("这里是内容------")
				.termsOfServiceUrl("wwwwwww....")
				.contact("作者")
				.version("1.0")
				.build();
	}

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Application.class);
		app.run(args);
	}

}
