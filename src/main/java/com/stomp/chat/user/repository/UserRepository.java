package com.stomp.chat.user.repository;

import com.stomp.chat.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface UserRepository extends JpaRepository<User,Long>, QuerydslPredicateExecutor<User> {
}
