package org.ad13.denarius.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ad13.denarius.model.Account;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles AJAX requests.
 * 
 * @author Andrew Dodd
 */
@Controller
@RequestMapping("/account")
public class AjaxResponseController {
	private static final Logger logger = LoggerFactory.getLogger(AjaxResponseController.class);

	private SessionFactory sessionFactory;

	@Autowired
	public AjaxResponseController(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}

//	@RequestMapping(method = RequestMethod.POST) 
//	public @ResponseBody Map<String, ? extends Object> create(@RequestBody Account account) {
//		return null;
//	}

	@RequestMapping(value = "/get/{accountId}", method = RequestMethod.GET)
	public @ResponseBody Map<String, ? extends Object> getAccount(@PathVariable String accountId) {
		// Try to parse the account id
		long parsedAccountId = Long.valueOf(accountId);
		
		Session s = sessionFactory.openSession();
		Account account = (Account)s.get(Account.class, parsedAccountId);
		
		s.close();
		
		// We could manually have a response marshaller?
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("accountId", account.getAccountId());
		result.put("accountName", account.getAccountName());
		result.put("ownerId", account.getOwner().getUserId());

		return result;
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody List<Account> getAccounts() {
		Session s = sessionFactory.openSession();
		Query q = s.createQuery("from Account where owner_id = 1");
		
		return q.list();
	}
}
