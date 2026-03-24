const express = require("express")
const mongoose = require("mongoose")
const cors = require("cors")
const http = require("http")
const { Server } = require("socket.io")

const app = express()
const server = http.createServer(app)
const io = new Server(server, { cors: { origin: "*" } })

app.use(cors())
app.use(express.json())

mongoose.connect("mongodb://127.0.0.1:27017/flowforge")

app.use("/auth", require("./routes/auth"))
app.use("/tasks", require("./routes/tasks"))

io.on("connection", (socket) => {
  socket.on("taskUpdated", () => io.emit("refresh"))
})

server.listen(5000, () => console.log("Server running on 5000"))