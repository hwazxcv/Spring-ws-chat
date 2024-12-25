package com.stomp.chat.chat.controller;


import com.stomp.chat.chat.dto.ChatDto;
import com.stomp.chat.chat.service.ChatRoomInfoService;
import com.stomp.chat.common.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Controller
@RequiredArgsConstructor
@Slf4j
/**
 * 별도의 handler 없이 WebSocket으로 들어오는 메시지를 발행
 */
public class StompChatController {

    private final SimpMessageSendingOperations simpMessageSendingOperations;
    private final ChatRoomInfoService chatRoomInfoService;
    private final UserInfo userInfo;

    @MessageMapping("/chat/enterUser")
    public void enterUser(@Payload ChatDto chatDto , SimpMessageHeaderAccessor headerAccessor){

        //채팅방 입장 +1
        chatRoomInfoService.plusCount(chatDto.getRoomNumber());
        //채팅방에 유저 추가
        //String userNickName = userInfo.getUser().getNickname();
        //String userUUID = chatRoomInfoService.addUser(chatDto.getRoomNumber(),userNickName);

        //반환 결과를 socket session에 저장
        //headerAccessor.getSessionAttributes().put("userUUID", userUUID);
        headerAccessor.getSessionAttributes().put("roomNumber", chatDto.getRoomNumber());

        chatDto.setMessage(chatDto.getSender() + "님 입장!");
        simpMessageSendingOperations.convertAndSend("sub/chat/room/"+ chatDto.getRoomNumber(),chatDto);

    }
    @MessageMapping("/chat/sendMessage")
    public void sendMessage(@Payload ChatDto chat){
        log.info("Chat {}" , chat);
        chat.setMessage(chat.getMessage());
        simpMessageSendingOperations.convertAndSend("sub/chat/room/"+ chat.getRoomNumber() , chat);
    }

    @EventListener
    public void webSocketDisconnectListener(SessionDisconnectEvent event){
        log.info("DisConnEvent {}",event);
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        // stomp 세션에 있던 uuid와 roomid를 확인해서 채팅방 유저 리스트 room에서 해당 유저 삭제
        String userUUID =(String) headerAccessor.getSessionAttributes().get("userUUID");
        String roomNumber = (String)headerAccessor.getSessionAttributes().get("roomNumber");

        log.info("headerAccessor {}",headerAccessor);

        // 채팅방 유저 -1
        chatRoomInfoService.minusCount(roomNumber);


        String userName = chatRoomInfoService.getUserName(roomNumber , userUUID );
        chatRoomInfoService.delUser(roomNumber , userUUID);

        ChatDto chat = ChatDto.builder()
                .type(ChatDto.MessageType.LEAVE)
                .sender(userName)
                .message(userName + " 님 퇴장!!")
                .build();

        simpMessageSendingOperations.convertAndSend("/sub/chat/room/" + roomNumber, chat);
    }



}
