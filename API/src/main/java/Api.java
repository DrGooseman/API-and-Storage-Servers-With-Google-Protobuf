import static spark.Spark.*;

import services.EventService;

public class Api {
	public static void main(String[] args) {
		Api api = new Api();
		
		api.setupRoutes();
	}
	
	private EventService eventService;

	public Api() {
		eventService = new EventService();
	}

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
	
}
