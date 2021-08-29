package com.stanlong.zb_annotiation_transaction;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.DEFAULT)
public class AccountServiceImpl implements AccountService {

	private AccountDao accountDao;
	
	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}

	@Override
	public void transfer(String outer, String inner, Integer money) {
		accountDao.out(outer, money);
		//模拟断电
		int i = 1/0;
		accountDao.in(inner, money);
	}
	
	

}
