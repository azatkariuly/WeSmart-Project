const http = require("http")
const stomp = require("stompjs")
const sockjs = require("sockjs-client")

wsHost = process.argv[2] || "10.101.10.79"
wsPort = process.argv[3] || "8080"

const socket = sockjs(`http://${wsHost}:${wsPort}/telemetry-websocket`)
const stompClient = stomp.over(socket)

stompClient.connect({}, frame => {
    console.log(frame)
})

const server = http.createServer((req, res) => {
    let body = []
    req.on('error', (err) => {
        console.error(err)
    }).on('data', (chunk) => {
        body.push(chunk)
    }).on('end', () => {
        res.writeHead(200, {})
        res.end()

        body = Buffer.concat(body).toString()

        stompClient.send(`/parking/${JSON.parse(body).parkingId}/telemetry`, {}, body)
    })
})

const port = 3000
const host = "127.0.0.1"

console.log(`Listening at http://localhost:${port}`)
server.listen(port, host)
