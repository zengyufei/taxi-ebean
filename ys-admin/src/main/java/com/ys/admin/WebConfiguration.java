package com.ys.admin;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ys.admin.base.converters.StringToLocalDateConverter;
import com.ys.admin.base.converters.StringToLocalDateTimeConverter;
import com.ys.admin.base.interceptors.AllowOriginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import java.nio.charset.Charset;

@EnableAutoConfiguration
public class WebConfiguration extends WebMvcConfigurerAdapter {

	private static final Charset UTF8 = Charset.forName("UTF-8");

	@Autowired
	private RequestMappingHandlerAdapter handlerAdapter;

	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(WebConfiguration.class);
	}

	@PostConstruct
	public void addConversionConfig() {
		ConfigurableWebBindingInitializer initializer = (ConfigurableWebBindingInitializer) handlerAdapter.getWebBindingInitializer();
		if (initializer.getConversionService() != null) {
			GenericConversionService genericConversionService = (GenericConversionService) initializer.getConversionService();
			genericConversionService.addConverter(new StringToLocalDateConverter());
			genericConversionService.addConverter(new StringToLocalDateTimeConverter());
		}
	}

	/**
	 * 上传下载配置
	 *
	 * @return
	 */
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize("50MB");
		factory.setMaxRequestSize("50MB");
		return factory.createMultipartConfig();
	}


	/**
	 * 方法级别的单个参数验证
	 */
	@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor() {
		return new MethodValidationPostProcessor();
	}

	@Bean
	public FastJsonHttpMessageConverter mappingJackson2HttpMessageConverter() {
		ObjectMapper mapper = new ObjectMapper();
		// 解决 Msg 输出空 result 问题
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		// 使用 fastJson 才能格式化 LocalDateTime
		FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
		return converter;
	}

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.ys.admin.controller"))
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

	/**
	 * 直接访问jsp的路由
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/api").setViewName("redirect:/swagger-ui.html");
		super.addViewControllers(registry);
	}


}