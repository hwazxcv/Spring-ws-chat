package com.stomp.chat.chat.service;


import com.stomp.chat.chat.dto.ChatRoomDto;
import com.stomp.chat.chat.entity.ChatRoom;
import com.stomp.chat.chat.repository.ChatRoomRepository;
import com.stomp.chat.common.UserInfo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Transactional
@Service
@RequiredArgsConstructor
public class ChatRoomSaveService {


    private final ChatRoomRepository chatRoomRepository;
    private final UserInfo userInfo;


    public void createRoom(ChatRoomDto data){
        save(data);
    }

    public void save(ChatRoomDto data){


        //User user = userInfo.getUser();
            ChatRoom room = ChatRoom.builder()
                    .roomId(data.getRoomId())
                    .roomName(data.getRoomName())
         //           .user(user)
                    .userCount(data.getUserCount())
                    .maxUserCnt(data.getMaxUserCnt())
                    .build();
            chatRoomRepository.saveAndFlush(room);
    }

}
