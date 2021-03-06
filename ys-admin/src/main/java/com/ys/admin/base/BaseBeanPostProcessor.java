package com.ys.admin.base;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;

import java.util.regex.Pattern;

// @Component
public class BaseBeanPostProcessor implements BeanPostProcessor{

	@Autowired
	private ApplicationContext applicationContext;

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		String controllerRegex = "Controller$";
		Pattern pattern = Pattern.compile(controllerRegex);
		String basicErrorController = "basicErrorController";
		boolean b = !basicErrorController.equals(beanName) && pattern.matcher(beanName).find();
		if(b){
			System.out.println("对象" + beanName + "实例化完成");
		}
		return bean;
	}
}
