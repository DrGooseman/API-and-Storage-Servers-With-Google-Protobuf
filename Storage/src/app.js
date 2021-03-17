const net = require("net");
const dotenv = require("dotenv");

const { saveIncomingEvent } = require("./controllers/events");

dotenv.config();

const server = net.createServer();

server.on("connection", (conn) => {
  console.log("Connection established.");
  conn.write("Connection established.\n");

  conn.on("data", (data) => {
    saveIncomingEvent(conn, data);
  });

  conn.once("close", () => {
    console.log("Connection closed.");
    conn.write("Connection closed.\n");
  });

  conn.on("error", (err) => {
    console.log("Connection error. " + err.message);
  });
});

const port = process.env.SERVER_PORT || 5000;
const address = process.env.SERVER_ADDRESS || "127.0.0.1";

server.listen(port, address, () => {
  console.log(`server started at ${address}:${port}`);
});
