package com.pjs.bankingusage.repository;

import com.pjs.bankingusage.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * UserRepository.java version 2019, 09. 18
 */
public interface UserRepository extends JpaRepository<User, String> {
	User findOneByUserId(String userId);
	User findOneByUserIdAndPassword(String userId, String password);
	User findOneByToken(String token);
}
