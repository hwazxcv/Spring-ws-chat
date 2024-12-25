package com.stomp.chat.chat.controller;

import com.stomp.chat.chat.ListData;
import com.stomp.chat.chat.SearchRoomData;
import com.stomp.chat.chat.dto.ChatRoomDto;
import com.stomp.chat.chat.dto.ChatRoomList;
import com.stomp.chat.chat.service.ChatRoomInfoService;
import com.stomp.chat.chat.service.ChatRoomSaveService;
import com.stomp.chat.common.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatController {

    private final ChatRoomInfoService chatRoomInfoService;
    private final ChatRoomSaveService chatRoomSaveService;
    private final UserInfo userInfo;

    @GetMapping("/index")
    public String mainChat(){
        return "front/chat/room";
    }


    @GetMapping
    public String goChatRoom(@ModelAttribute SearchRoomData searchRoomData, Model model){
        ListData<ChatRoomList> data =  chatRoomInfoService.getList(searchRoomData);
        model.addAttribute("list" , data.getContent());
        model.addAttribute("pagination",data.getPagination());
     //   model.addAttribute("user",userInfo.getUser().getNickname());

       // log.debug("user[{}]",userInfo.getUser().getNickname());
        return "/front/chat/roomlist";
    }

    @PostMapping("/createroom")
    public String createRoom(ChatRoomDto room , RedirectAttributes rttr){
        chatRoomSaveService.save(room);
        rttr.addFlashAttribute("roomName", room);
        return "redirect:/front/chat/roomlist";
    }

    @GetMapping("/room")
    public String roomDetail(Model model, String roomNumber){
        log.info("Room Test {}", roomNumber);
        ChatRoomDto room = chatRoomInfoService.getNumberChatRoom(roomNumber);

        model.addAttribute("room", room);
        return "/front/chat/room";
    }

    @GetMapping("/delRoom/{roomNumber}")
    public String delChatRoom(@PathVariable String roomNumber){
        chatRoomInfoService.delChatRoom(roomNumber);
        return "redirect:/front/chat/roomlist";
    }


    //유저 카운트
    @GetMapping("/chkUserCnt/{roomNumber}")
    public boolean chkUserCnt(@PathVariable String roomNumber){

        return chatRoomInfoService.chkRoomUserCnt(roomNumber);
    }

    @GetMapping("/userList")
    public ArrayList<String> userList(String roomNumber){
        return chatRoomInfoService.getRoomUserList(roomNumber);
    }

}
