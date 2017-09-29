package com.ys.admin.util;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;

import java.lang.reflect.Method;
import java.util.Map;

public class BeanProxyFactory {

	public static Object createProxy(Class targetClass, Map<String, Object> beans) {
		ProxyFactory proxyFactory = new ProxyFactory();
		proxyFactory.setInterfaces(targetClass);
		proxyFactory.addAdvice(new VersionMethodInterceptor(targetClass, beans));
		return proxyFactory.getProxy();
	}

	static class VersionMethodInterceptor implements MethodInterceptor {
		private String classSwitch;
		private Object beanOfSwitchOn;
		private Object beanOfSwitchOff;

		public VersionMethodInterceptor(Class targetClass, Map<String, Object> beans){
			String interfaceName = targetClass.getSimpleName();
			this.beanOfSwitchOn = beans.get(this.buildBeanName(interfaceName, true));
			this.beanOfSwitchOff = beans.get(this.buildBeanName(interfaceName, false));
		}

		@Override
		public Object invoke(MethodInvocation methodInvocation) throws Throwable {
			Method method = methodInvocation.getMethod();
			return methodInvocation.getMethod().invoke(getTargetObject(true), methodInvocation.getArguments());
		}

		private String buildBeanName(String interfaceName, boolean isSwitchOn) {
			return interfaceName + "Impl" + (isSwitchOn ? "V2" : "v1");
		}

		private Object getTargetObject(boolean onOrOff) {
			return onOrOff ? this.beanOfSwitchOn : this.beanOfSwitchOff;
		}
	}
}
