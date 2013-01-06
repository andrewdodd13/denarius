package org.ad13.denarius.repository;

import static org.junit.Assert.assertEquals;

import org.ad13.denarius.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:beans/repository-context.xml")
@Transactional
public class UserRepositoryTest {
	@Autowired
	UserRepository userRepository;

	@Test
	public void testCreate() {
		User user = new User();
		user.setUsername("test-user");
		user.setPassword("test-pass");
		user.setFullname("test-name");
		user.setEmail("test-email");

		user = userRepository.save(user);

		User persistedUser = userRepository.findOne(user.getUserId());
		assertEquals(user, persistedUser);
	}

	@Test
	public void testFindByUsername() {
		User user = new User();
		user.setUsername("test-user");
		user.setPassword("test-pass");
		user.setFullname("test-name");
		user.setEmail("test-email");

		user = userRepository.save(user);

		User persistedUser = userRepository.findByUsername(user.getUsername());
		assertEquals(user, persistedUser);
	}
}
