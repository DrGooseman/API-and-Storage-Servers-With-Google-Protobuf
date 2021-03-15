import static spark.Spark.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketTimeoutException;

import services.EventService;

/**
 * The main class that will start the API to receive Events and send them out 
 * to another server for storage using Google Protobuf.
 * @author James
 *
 */
public class Api {
	public static void main(String[] args) {
		Api api = new Api();
		
		api.setupServerConnection("localhost", 5000);
		api.setupRoutes();
	}
	
	private EventService eventService;
	private Socket socket;

	public Api() {
		eventService = new EventService();
	}

	/**
	 * Sets up routes using the Spark framework on http://localhost:4567/
	 */
	public void setupRoutes() {
		post("/event", (req, res) -> {
			res.type("application/json");
			
			try {
				eventService.validateJson(req.body());
			} catch (Exception e) {
				res.status(422);
			    return "{\"message\":\"" + e.getMessage() + "\"}";
			}
			try {
				eventService.send(req.body());
			} catch (Exception e) {
				res.status(500);
			    return "{\"message\":\"" + e.getMessage() + "\"}";
			}
		
			res.status(201);
		    return "{\"message\":\"New event sent!\"}";
		});
		notFound((req, res) -> {
		    res.type("application/json");
		    return "{\"message\":\"Route doesn't exist.\"}";
		});
		System.out.println("API listening on http://localhost:4567/");
	}
	
	/**
	 * Establishes a TCP socket connection to a server at the provided address:port 
	 * on a new thread and listens for responses.
	 * @param address
	 * @param port
	 */
	public void setupServerConnection(String address, int port) {
		Thread thread = new Thread() {
			public void run() {
				try (Socket newSocket = new Socket(address, port)) {
					socket = newSocket;
					BufferedReader echoes = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					eventService.setOutputStream(socket.getOutputStream());

					while (true) {
						String response = echoes.readLine();
						System.out.println(response);
					}
				} catch (SocketTimeoutException e) {
					System.out.println("The socket timed out!");
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
		};
		thread.start();
	}
}
