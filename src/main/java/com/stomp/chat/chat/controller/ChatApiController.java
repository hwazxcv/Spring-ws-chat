package com.stomp.chat.chat.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat/api")
@Log4j2
public class ChatApiController {

//    private final ChatRoomInfoService chatRoomInfoService;
//    private final ChatRoomSaveService chatRoomSaveService;
//
//
//    @GetMapping("/test/{id}")
//    public ResponseRestData<?> getroom(@PathVariable String id){
//        ChatRoomDto data = chatRoomInfoService.getNumberChatRoom(id);
//        return new ResponseRestData<>(data , "채팅방 조회 성공");
//
//    }
//
//
//    @GetMapping("/view/{id}")
//    public ResponseRestData<?> findChatRoom(@PathVariable Long id){
//        ChatRoomDto data = chatRoomInfoService.findChatRoom(id);
//        return new ResponseRestData<>(data , "채팅방 조회 성공");
//    }
//
//    @GetMapping("/user/list")
//    public ResponseRestData<?> findUserList(String roomId){
//        ArrayList<String> list = chatRoomInfoService.getRoomUserList(roomId);
//        return new ResponseRestData<>(list , " 성공");
//    }
//
//    @GetMapping("/room/list")
//    public ListData<ChatRoomList> findChatRoomList(SearchRoomData searchRoomData){
//        ListData<ChatRoomList> list = chatRoomInfoService.getList(searchRoomData);
//        return new ListData<>(list.getContent() , list.getPagination());
//    }
//
//
//    @PostMapping
//    public ResponseRestData<ChatRoomDto> createRoom(@RequestBody @Valid ChatRoomDto data , Errors errors){
//        errorProcess(errors);
//        chatRoomSaveService.createRoom(data);
//        return new ResponseRestData<>(data , "채팅방 생성 완료");
//    }
//
//
//
//        private void errorProcess(Errors errors) {
//        if(errors.hasErrors()){
//            throw new BadRequestException(errors);
//        }
//    }
}
