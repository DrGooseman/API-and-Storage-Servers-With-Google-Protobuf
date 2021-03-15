package services;

import java.io.IOException;
import java.io.OutputStream;

import org.json.*;

import models.EventProto.Event;

/**
 * A class which handles the transformation of json events to protobuf output
 * @author James
 *
 */
public class EventService {
	private OutputStream outputStream;

	/**
	 * Validates a json string to ensure it contains the correct properties for
	 * an event Object.
	 * @param jsonString
	 * @throws Exception
	 */
	public void validateJson(String jsonString) throws Exception {
    	JSONObject jsonObj;
    	try {
        	jsonObj = new JSONObject(jsonString);
        	}
    	catch (Exception e) {
    		throw new Exception("Not valid Json!");
        }
    	if (!jsonObj.has("userId")) throw new Exception("userId required!");
    	if (!jsonObj.has("timestamp")) throw new Exception("timestamp required!");
    	if (!jsonObj.has("event")) throw new Exception("event required!");
    }
    
	/**
	 * Converts a json Event string to a protobuf Event and sends it to the output stream.
	 * @param jsonString
	 * @throws IOException
	 */
    public void send(String jsonString) throws IOException {
    	JSONObject jsonObj = new JSONObject(jsonString);
    	
    	int userId = Integer.parseInt(jsonObj.get("userId").toString());
    	long timestamp = Long.valueOf(jsonObj.get("timestamp").toString());
    	String eventMessage = jsonObj.get("event").toString();
    	
    	Event event =
    			Event.newBuilder()
    			    .setUserId(userId)
    			    .setTimestamp(timestamp)
    			    .setEvent(eventMessage)
    			    .build();
    	
    	event.writeTo(outputStream);
    }

    /**
     * Sets the output stream that protobuf Events will write to.
     * @param outputStream
     */
	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}
}