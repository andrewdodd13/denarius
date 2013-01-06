package org.ad13.denarius.repository;

import static org.junit.Assert.assertEquals;

import org.ad13.denarius.model.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:beans/repository-context.xml")
@Transactional
public class AccountRepositoryTest {
	@Autowired
	AccountRepository accountRepository;

	@Test
	public void testCreate() {
		Account account = new Account();
		account.setAccountName("Test Account");

		account = accountRepository.save(account);

		Account persistedAccount = accountRepository.findOne(account.getAccountId());
		assertEquals(account, persistedAccount);
	}
}
