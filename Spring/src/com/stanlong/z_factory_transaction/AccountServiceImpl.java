package com.stanlong.z_factory_transaction;

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
