const protobuf = require("protobufjs");

const Event = require("../models/event.js");

protobuf.util.Long = undefined;
protobuf.configure();

let message;
let event;

exports.saveIncomingEvent = async (conn, data) => {
  try {
    await decodeMessage(data);
    const err = message.verify(event);
    //if message is only partially full, return and wait for the rest of the message
    if (err) return;

    const newEvent = new Event(event);
    await newEvent.save();

    console.log("Event saved!");
    console.log(event);
    event = null;
    conn.write("Event saved!\n");
  } catch (err) {
    console.log(`Error: ${err}`);
    conn.write(`Error: ${err}\n`);
  }
};

const decodeMessage = async (data) => {
  try {
    const decodedMessage = message.decode(data);
    event = message.toObject(decodedMessage);
  } catch (err) {
    if (!event) event = message.toObject(err.instance);
    else {
      const partialEvent = message.toObject(err.instance);
      event = { ...event, ...partialEvent };
    }
  }
};

const setupProtobuf = async (data) => {
  const root = await protobuf.load("./src/models/event.proto");
  message = root.lookupType("models.Event");
};

setupProtobuf();
