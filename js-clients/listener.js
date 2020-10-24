const SockJS = require("sockjs-client")
const Stomp = require("stompjs")

const localhost = "http://localhost:8080/telemetry-websocket"
const herokuapp = "https://boiling-coast-60811.herokuapp.com/telemetry-websocket"
const socket = SockJS(herokuapp)
const stompClient = Stomp.over(socket)

stompClient.connect({}, frame => {
    console.log(frame)

    stompClient.subscribe("/sensor/1/telemetry", message => {
        console.log(message.body)
    })
})
