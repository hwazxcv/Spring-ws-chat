package com.stomp.chat.chat.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.stomp.chat.chat.entity.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomList {


    private String roomId;

    private String roomName;

    private String nickName;

    private int userCount;

    private int maxUserCnt;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime regDt;

    private boolean secretChk;


    public ChatRoomList (ChatRoom chatRoom){
        this.roomId = chatRoom.getRoomId();
        this.roomName =chatRoom.getRoomName();
    //    this.nickName = chatRoom.getUser().getNickname();
        this.userCount =chatRoom.getUserCount();
        this.maxUserCnt=chatRoom.getMaxUserCnt();
        this.regDt = chatRoom.getRegDt();
    }



}
