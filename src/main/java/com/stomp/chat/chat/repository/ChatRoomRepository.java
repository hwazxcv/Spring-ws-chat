package com.stomp.chat.chat.repository;

import com.stomp.chat.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>, QuerydslPredicateExecutor<ChatRoom> {


    Optional<ChatRoom> findByRoomId(String roomId);
}
