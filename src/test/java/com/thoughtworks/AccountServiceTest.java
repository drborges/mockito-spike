package com.thoughtworks;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

	private AccountService service;

	private @Mock AccountRepository repository;
	private @Mock Account diegoAccount;
	private @Mock Account carlosAccount;

	private List<Account> accountsInDatabase;

	@Before
	public void setup() {
		accountsInDatabase = Arrays.asList(diegoAccount, carlosAccount);
		when(repository.getAccounts()).thenReturn(accountsInDatabase);

		service = new AccountService(repository);
	}

	/**
	 * Verifying state
	 */
	@Test
	public void shouldGetAccountsList() {
		List<Account> accounts = service.getAccounts();

		assertTrue(accounts.contains(diegoAccount));
		assertTrue(accounts.contains(carlosAccount));
	}

	/**
	 * Verifying behavior's frequency
	 */
	@Test
	public void shouldUseRepositoryToGetAccountsList() {
		service.getAccounts();
		verify(repository, times(1)).getAccounts();
	}

	/**
	 * Verifying algorithms steps (ordered steps)
	 */
	@Test
	public void shouldDisableAndDeleteAccount() {
		service.disableAndDelete(diegoAccount);

		InOrder inOrder = Mockito.inOrder(diegoAccount, repository);
		inOrder.verify(diegoAccount).disable();
		inOrder.verify(repository).delete(diegoAccount);
	}

	/**
	 * Verifying that a behavior never happens
	 */
	@Test
	public void shouldDeleteAccountWithoutDisablingIt() {
		service.delete(diegoAccount);

		InOrder inOrder = Mockito.inOrder(diegoAccount, repository);
		inOrder.verify(diegoAccount, never()).disable();
		inOrder.verify(repository).delete(diegoAccount);
	}
}
