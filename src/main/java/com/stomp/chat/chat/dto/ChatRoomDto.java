package com.stomp.chat.chat.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomDto {

    private String roomId = UUID.randomUUID().toString();

    private String roomName;

    private int userCount = 1;

    private int maxUserCnt;

    private String nickname;

    private String roomPwd;

    private boolean secretChk;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime regDt;

    private HashMap<String ,String> userList = new HashMap<String , String >();

}
