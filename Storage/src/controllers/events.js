const protobuf = require("protobufjs");

protobuf.util.Long = undefined;
protobuf.configure();

let message;
let event;

exports.saveIncomingEvent = async (conn, data) => {
  try {
    await decodeMessage(data);
    //Validate Event against future Event model to check if we have built the whole object
    //console.log("saveIncomingEvent");
    console.log(event);
    //event = undefined;
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
