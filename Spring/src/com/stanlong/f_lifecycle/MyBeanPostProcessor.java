package com.stanlong.f_lifecycle;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class MyBeanPostProcessor implements BeanPostProcessor {

	/**
	 * BeanPostProcessor 后处理 Bean
	 * Spring 提供一种机制，只要实现此接口 BeanPostProcessor, 并将实现类提供给Spring容器， Spring容器将自动执行，在初始化方法前执行before
	 * 在初始化方法后执行 after(). 配置<bean class="">
	 * Spring 提供工厂勾子，用于修改实例对象，可以生成代理对象，是AOP底层
	 */
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException{
		System.out.println("前方法: " + beanName);
		return bean;
	}
	
	@Override
	public Object postProcessAfterInitialization(final Object bean, String beanName) throws BeansException{
		System.out.println("后方法: " + beanName);
		//生成jdk代理
		return Proxy.newProxyInstance(MyBeanPostProcessor.class.getClassLoader()
					, bean.getClass().getInterfaces()
					, new InvocationHandler() {
						@Override
						public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
							System.out.println("--------------开启事务");
							//执行目标方法
							Object obj = method.invoke(bean, args);
							System.out.println("--------------提交事务");
							return obj;
						}
					});
	}
}
