package org.ad13.denarius.repository;

import java.util.List;

import org.ad13.denarius.model.Account;
import org.ad13.denarius.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AccountRepository extends JpaRepository<Account, Long> {
    public List<Account> findAllByOwner(User owner);
}
