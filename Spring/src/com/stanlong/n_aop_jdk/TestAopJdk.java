package com.stanlong.n_aop_jdk;

import org.junit.Test;

public class TestAopJdk {
	/**
	 * JDK动态代理，对"装饰者"设计模式简化，使用前提，必须有接口
	 * 目标类：接口+实现类
	 * 代理类：用于存通知
	 * 工厂类：编写工厂类生成代理
	 * 测试
	 */
	@Test
	public void demo01(){
		UserService userService = MyBeanFactory.createUserService();
		userService.addUser();
		userService.updateUser();
		userService.deleteUser();
	}

}
