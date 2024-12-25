package com.stomp.chat.chat.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageData {

    private Long message_id;

    private String message_content;

    private String writer;

    private Long room_id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime regDt;

}
