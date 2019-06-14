package com.stanlong.y_transaction;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

public class AccountServiceImpl implements AccountService {

	private AccountDao accountDao;
	
	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}
	
	//需要Spring注入模板
	private TransactionTemplate transactionTemplate;
	
	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}
	
	@Override
	public void transfer(String outer, String inner, Integer money) {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				accountDao.out(outer, money);
				//模拟断电
				int i = 1/0;
				accountDao.in(inner, money);
			}
		});


	}

}
