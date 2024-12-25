package com.stomp.chat.chat.service;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.stomp.chat.chat.ListData;
import com.stomp.chat.chat.SearchRoomData;
import com.stomp.chat.chat.dto.ChatRoomDto;
import com.stomp.chat.chat.dto.ChatRoomList;
import com.stomp.chat.chat.entity.ChatRoom;
import com.stomp.chat.chat.entity.QChatRoom;
import com.stomp.chat.chat.exception.ChatRoomNotFoundException;
import com.stomp.chat.chat.repository.ChatRoomRepository;
import com.stomp.chat.common.utils.Pagination;
import com.stomp.chat.config.MvcConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.data.domain.Sort.Order.desc;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ChatRoomInfoService {

    private final ChatRoomRepository chatRoomRepository;
    private final ModelMapper modelMapper;
    private final HttpServletRequest request;
    private final JPAQueryFactory jpaQueryFactory;
    private static final Logger logger = LoggerFactory.getLogger(ChatRoomInfoService.class);



    public ChatRoom getRoom(Long id){
        ChatRoom chatRoom = chatRoomRepository.findById(id).orElseThrow(ChatRoomNotFoundException::new);
        return chatRoom;
    }

    public ChatRoomDto findChatRoom(Long id){
        ChatRoom chatRoom = getRoom(id);
        ChatRoomDto roomData =modelMapper.map(chatRoom , ChatRoomDto.class);
     //   roomData.setNickname(chatRoom.getUser().getNickname());
        return roomData;
    }

    public ListData<ChatRoomList> getList(SearchRoomData searchData){
        int page = MvcConfig.getNumber(searchData.getPage() , 1 );
        int limit = MvcConfig.getNumber(searchData.getLimit() , 20);
        int offset = (page -1) * limit;


        QChatRoom chatRoom = QChatRoom.chatRoom;
        BooleanBuilder andBuilder = new BooleanBuilder();


        String room = searchData.getRoomName();

        if(StringUtils.hasText(room)){
            room = room.trim();
            andBuilder.and(chatRoom.roomName.eq(room));
        }

        List<ChatRoomList> items = jpaQueryFactory
                .selectFrom(chatRoom)
                .offset(offset)
                .limit(limit)
                .where(andBuilder)
                .fetch()
                .stream()
                .map(ChatRoomList::new)
                .collect(Collectors.toList());


        Pageable pageable = PageRequest.of( page - 1, limit , Sort.by(desc("regDt")));
        Page<ChatRoom> pageData = chatRoomRepository.findAll(andBuilder , pageable);

        Pagination pagination = new Pagination(page , (int) pageData.getTotalElements() , 10 , limit , request);
        ListData<ChatRoomList> listdata = new ListData<>();
        listdata.setContent(items);
        listdata.setPagination(pagination);

        return listdata;
    }

    //고유 번호로 채팅방 찾기
    public ChatRoomDto getNumberChatRoom(String roomId){
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId).orElseThrow(ChatRoomNotFoundException::new);
        return modelMapper.map(chatRoom , ChatRoomDto.class);
    }


    // 채팅방 유저 추가
    public String addUser(String roomId , String userNickName){
        ChatRoomDto room = getNumberChatRoom(roomId);
        String userUUID = UUID.randomUUID().toString();

        room.getUserList().put(userUUID , userNickName);
        return userNickName;
    }


    // 채팅방 유저 목록 조회
    public ArrayList<String> getRoomUserList(String roomId) {
        ArrayList<String> list = new ArrayList<>();

        ChatRoomDto room = getNumberChatRoom(roomId);
        room.getUserList().forEach((key , value) -> list.add(value));
        System.out.println("User List: " + room.getUserList());
        return list;
    }


    //채팅방에 들어간 유저 이름 조회
    public String getUserName (String roomId , String userUUID){
        ChatRoomDto chatRoom = getNumberChatRoom(roomId);
        return chatRoom.getUserList().get(userUUID);
    }

    //채팅방에 들어간 유저 삭제
    public void delUser(String roomId , String userUUID){
        ChatRoomDto room = getNumberChatRoom(roomId);
        room.getUserList().remove(userUUID);
    }

    //채팅방 인원수 더하기
    public void plusCount(String roomId){
        ChatRoomDto room = getNumberChatRoom(roomId);
        room.setUserCount(room.getMaxUserCnt()+1);
    }

    //채팅방 인원수 빼기
    public void minusCount(String roomNumber){
        ChatRoomDto room = getNumberChatRoom(roomNumber);

        int count = room.getUserCount()-1;
        if(count < 0) {
            count = 0;
        }
        room.setUserCount(count);
    }

    // maxUserCnt 에 따른 채팅방 입장 여부
    public boolean chkRoomUserCnt(String roomId){
        ChatRoomDto room = getNumberChatRoom(roomId);

        log.info("참여인원 확인 [{}, {}]", room.getUserCount(), room.getMaxUserCnt());

        if (room.getUserCount() + 1 > room.getMaxUserCnt()) {
            return false;
        }

        return true;
    }


    // 채팅방 비밀번호 조회

    public boolean confirmPwd(ChatRoomDto roomDto) {
        ChatRoomDto room= getNumberChatRoom(roomDto.getRoomId());
        String pwd= roomDto.getRoomPwd();

        return true;
    }

    // 채팅방 삭제
    public void delChatRoom(String roomId) {
        ChatRoomDto room = getNumberChatRoom(roomId);
        //  chatRoomRepository.delete();
    }

}
