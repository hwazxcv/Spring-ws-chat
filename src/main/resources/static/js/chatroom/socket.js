var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');

var stompClient = null;
var username = null;

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

//// roomNumber 가져오기
//
//const url = new URL(window.location.href).searchParams;
//const roomNumber = url.get('roomNumber') || 'defaultRoomNumber';  // roomId가 없을 경우 기본값 설정
//console.log(roomNumber);  // roomId 값을 확인
//
//// roomId 값을 출력
//console.log('roomNumber:', roomNumber);
//
//function connect(event){
//
//username = document.querySelector('#name').value.trim();
//
//usernamePage.classList.add('hidden');
//chatPage.classList.remove('hidden');
//
//// 연결하고 싶은 WebSocket의 엔드포인트
//const socket = new SockJS('http://localhost:3000/ws-stomp');
//const stompClient = Stomp.over(socket);
//
//stompClient.connect({},onConnected , onError);
//event.preventDefault();
//}


// roomNumber 가져오기
const url = new URL(window.location.href).searchParams;
const roomNumber = url.get('roomNumber') || 'defaultRoomNumber';  // roomId가 없을 경우 기본값 설정
console.log(roomNumber);  // roomId 값을 확인

// roomNumber 값을 출력
console.log('roomNumber:', roomNumber);

function connect(event) {
    // 사용자 이름 가져오기
    const username = document.querySelector('#name').value.trim();

    usernamePage.classList.add('hidden');
    chatPage.classList.remove('hidden');

    // JWT 토큰을 로컬 스토리지에서 가져오기
    const token = localStorage.getItem('jwtToken'); // 로컬 스토리지에서 JWT 토큰 가져오기

    // 연결하고 싶은 WebSocket의 엔드포인트
    const socket = new SockJS('http://localhost:3000/ws-stomp');
    const stompClient = Stomp.over(socket);

    // STOMP 연결 시 Authorization 헤더에 JWT 토큰 추가
    stompClient.connect(
        { Authorization: `Bearer ${token}` }, // 헤더에 토큰 추가
        onConnected,
        onError
    );

    event.preventDefault();
}

function onConnected(){
    //sub 할 url => /sub/chat/room/roomNumber 로 구독한다.
    stompClient.subscribe('/sub/chat/room/'+roomNumber,onMessageReceived);

    stompClient.send("/pub/chat/enterUser",
                    {}
                    ,JSON.stringify({
                    "roomNumber":roomNumber,
                    sender:username,
                    type:'ENTER'
        })
    )
    connectingElement.classList.add('hidden');
}

function getUserList(){

    const $list = $("#list");

    $.ajax({
    type: "GET",
    url: "/chat/userList",
    data: {
        "roomNumber":roomNumber
    },
    success: function(data){
            var users = "";
            for(let i=0; i<data.length; i++){
               console.log("data[i] : "+data[i]);
               users += "<li class='dropdown-item'>"+data[i]+"</li>"
            }
            $list.html(users);
        }
    })
}


function onError(error){
    connectingElement.textContent='Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}

function sendMessage(event){
    var messageContent = messageInput.value.trim();

    if(messageContent && stompClient){
        var chatMessage = {
            "roomNumber":roomNumber,
            sender: username,
            message: messageInput.value,
            type: 'TALK'
        };
        stompClient.send("/pub/chat/sendMessage", {}, JSON.stringify(chatMessage));
        messageInput.value='';
    }
    event.preventDefault();
}


function onMessageReceived(payload){
    console.log("들어오는 payload 값 확인 : "+payload);
    const chat = JSON.parse(payload.body);

    const messageElement = document.createElement('li');

    if(chat.type === 'ENTER'){
        messageElement.classList.add('event-message');
        chat.content = chat.sender + chat.message;
        getUserList();
    }else if (chat.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        chat.content= chat.sender + chat.message;
        getUserList();
    }else {
        messageElement.classList.add('chat-message');

        var avatarElement = document.createElement('i');
        var avatarText = document.createTextNode(chat.sender[0]);
        avatarElement.appendChild(avatarText);
        avatarElement.style['background-color'] = getAvatarColor(chat.sender);

        messageElement.appendChild(avatarElement);

        var usernameElement = document.createElement('span');
        var usernameText = document.createTextNode(chat.sender);

        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
    }
        var textElement =document.createElement('p');
        var messageText = document.createTextNode(chat.message);
        textElement.appendChild(messageText);

        messageElement.appendChild(textElement);

        messageArea.appendChild(messageElement);
        messageArea.scrollTop = messageArea.scrollHeight;
}

function getAvatarColor(messageSender){
    var hash = 0;

    for(const i =0; i<messageSender.length; i++){
        hash = 31 * hash + messageSender.chatCodeAt(i);
    }

    var index = Math.abs(hash % colors.length);
    return colors[index];
}
usernameForm.addEventListener('submit',connect,true);
messageForm.addEventListener('submit',sendMessage, true);





//function isDuplicateName() {
//
//    $.ajax({
//        type: "GET",
//        url: "/chat/duplicateName",
//        data: {
//            "username": username,
//            "roomId": roomId
//        },
//        success: function (data) {
//            console.log("함수 동작 확인 : " + data);
//
//            username = data;
//        }
//    })
//}


// JWT 토큰을 헤더에 추가
//const headers = {
//    Authorization: 'Bearer '
//};
//
//stompClient.connect(headers, function(frame) {
//    console.log('STOMP 연결 성공:', frame);
//
//    // 구독할 주제 설정
//    stompClient.subscribe('/topic/chat', function(message) {
//        console.log('메시지 수신:', message.body);
//    });
//
//    // 서버에 메시지 전송
//    stompClient.send('/app/chat', {}, JSON.stringify({ 'message': '안녕하세요, 서버!' }));
//}, function(error) {
//    console.error('STOMP 연결 실패:', error);
//});