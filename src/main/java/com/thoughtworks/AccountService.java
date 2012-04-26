package com.thoughtworks;

import java.util.List;

public class AccountService {

	private final AccountRepository repository;

	public AccountService(AccountRepository repository) {
		this.repository = repository;
	}

	public List<Account> getAccounts() {
		return repository.getAccounts();
	}

	public void disableAndDelete(Account diegoAccount) {
		diegoAccount.disable();
		repository.delete(diegoAccount);
	}

	public void delete(Account diegoAccount) {
		repository.delete(diegoAccount);
	}

}
