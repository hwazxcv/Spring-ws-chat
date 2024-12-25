package com.stomp.chat.chat.entity;


import com.stomp.chat.common.BaseMember;
import com.stomp.chat.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashMap;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ChatRoom extends BaseMember {

    @Id
    @GeneratedValue
    private Long roomNumber;

    @Column(nullable = false)
    private String roomId = UUID.randomUUID().toString(); // 채팅방 고유 번호


    @Column(length = 50 , nullable = false)
    private String roomName;

    private int userCount;

    private int maxUserCnt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private User user;

    @Transient
    private HashMap<String, String> userList = new HashMap<>();

}
