package com.springmvcproject.concertio.dao.impl;

import javax.sql.DataSource;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.springmvcproject.concertio.dao.AccountDao;
import com.springmvcproject.concertio.models.Account;

@Repository("accountDao")
public class HbnAccountDao extends AbstractHbnDao<Account>  implements AccountDao {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired PasswordEncoder passwordEncoder;	
	
	
	public JdbcTemplate getJdbcTemplate() {
	    return jdbcTemplate;
	}

	@Autowired
	public void setJdbcTemplate(final DataSource dataSource) {
	    this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public void createAccount(Account account) {
		account.setPassword(passwordEncoder.encode(account.getPassword()));
		create(account);
	}

	@SuppressWarnings({ "deprecation", "rawtypes" })
	@Override
	public Account findAccountByEmail(String email) {
		Query q = getSession().getNamedQuery("findAccountByEmail");
		q.setParameter("email", email);
		return (Account) q.uniqueResult();
	}

}
