function randint(min, max) {
    return min + Math.floor(Math.random() * (max - min))
}

const SockJS = require("sockjs-client")
const Stomp = require("stompjs")

const localhost = "http://localhost:8080/telemetry-websocket"
const herokuapp = "https://boiling-coast-60811.herokuapp.com/telemetry-websocket"
const socket = SockJS(herokuapp)
const stompClient = Stomp.over(socket)

stompClient.connect({}, frame => {
    console.log(frame)

    setInterval(() => {
        let sensorTelemetry = JSON.stringify({
            sensorId: 1,
            current: randint(215, 225) + Math.random(),
            voltage: randint(5, 10) + Math.random(),
            ageingRate: randint(100, 150) + Math.random(),
            power: randint(1215, 1525) + Math.random(),
            vibration: randint(100, 150) + Math.random(),
            cosphi: randint(0, 1) + Math.random(),
            measuredAt: Date.now()
        })
        stompClient.send(`/sensor/1/telemetry`, {}, sensorTelemetry)
        stompClient.send("/app/telemetry", {}, sensorTelemetry)
        console.log(sensorTelemetry)
    }, 1000)
})
