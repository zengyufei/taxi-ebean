/*
package com.ys.admin.base.interceptors;

import com.ys.common.entitys.rbac.SysOrg;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.regex.Pattern;

@Component
public class MyArgumentsResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		if (parameter.getParameterType().equals(SysOrg.class)) {
			return true;
		}
		return false;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
	                              NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		Class aClass = (Class) ((ParameterizedTypeImpl) parameter.getContainingClass().getGenericSuperclass()).getActualTypeArguments()[0];
		Class[] declaredClasses = aClass.getDeclaredClasses();
		for (Class declaredClass : declaredClasses) {
			if(Pattern.compile("dto$", Pattern.CASE_INSENSITIVE).matcher(declaredClass.getSimpleName()).find()){

				Object o1 = convertMap(declaredClass, webRequest.getParameterMap());
				Object o = declaredClass.newInstance();

				org.springframework.beans.BeanUtils.copyProperties(webRequest.getParameterMap(), o);
				*/
/*BeanMap beanMap = BeanMap.create(o);
				beanMap.putAll(webRequest.getParameterMap());*//*

				System.out.println(o1);
			}
		}
		return webRequest.getAttribute("sysOrg", RequestAttributes.SCOPE_REQUEST);
	}
	*/
/**
	 * 将一个 Map 对象转化为一个 JavaBean
	 * @param type 要转化的类型
	 * @param map 包含属性值的 map
	 * @return 转化出来的 JavaBean 对象
	 * @throws IntrospectionException 如果分析类属性失败
	 * @throws IllegalAccessException 如果实例化 JavaBean 失败
	 * @throws InstantiationException 如果实例化 JavaBean 失败
	 * @throws InvocationTargetException 如果调用属性的 setter 方法失败
	 *//*

	@SuppressWarnings("rawtypes")
	public static Object convertMap(Class type, Map<String, String[]> map)
			throws IntrospectionException, IllegalAccessException,
			InstantiationException, InvocationTargetException {
		Object obj = type.newInstance(); // 创建 JavaBean 对象

		Field[] fields = type.getDeclaredFields();
		for (Field field : fields) {
			if (map.containsKey(field.getName())) {
				// 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
				String[] value = map.get(field.getName());
				field.setAccessible(true);
				field.set(obj, value[0]);
			}
		}
		return obj;
	}
}*/
