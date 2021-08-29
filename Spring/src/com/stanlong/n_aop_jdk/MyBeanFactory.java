package com.stanlong.n_aop_jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyBeanFactory {
	
	public static UserService createUserService(){
		//1、目标类
		UserService userService = new UserServiceImpl();
		//2、切面类
		MyAspect myAspect = new MyAspect();
		//3、代理类：将目标类（切入点）和切面类（通知）结合--》切面
		/**
		 * 参数1：loader，类加载器，动态代理类，运行时创建，任何类都需要类加载器并将其加载到内存
		 * 		一般情况下： 当前类.class.getClassLoader();
		 * 				目标类.class.getClassLoader();
		 * 参数2：Class[] interfaces:代理类需要实现的所有接口
		 * 		方式1：目标类.getClass().getInterfaces() 注意：只能获得自己的接口，不能获得父类接口
		 * 		方式2：new Class[]{UserService.class} 例如：jdbc驱动
		 * 参数3：h： InvocationHandler 处理类，接口，必须进行实现类一般采用匿名内部类的方式
		 * 		提供 invoke 方法，代理类的每一个方法执行时，都将调用一次 invoke
		 * 		参数 : Object proxy :代理对象
		 * 		参数：Method method：代理对象当前执行方法的描述对象（反射）
		 * 			执行方法名：method.getName()
		 * 			执行方法：method.invoke(对象，实际参数)
		 * 		参数：Object[] args 方法实际参数
		 */
		UserService proxyService = (UserService)Proxy.newProxyInstance(MyBeanFactory.class.getClassLoader()
				, userService.getClass().getInterfaces()
				, new InvocationHandler() {
					
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						//前执行
						myAspect.before();
						
						//执行目标类的方法
						Object obj = method.invoke(userService, args);
						
						//后执行
						myAspect.after();
						return obj;
					}
				});
		return proxyService;
	}
}
