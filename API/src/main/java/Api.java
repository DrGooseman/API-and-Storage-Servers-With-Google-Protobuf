import static spark.Spark.*;

public class Api {
	
	public static void main(String[] args) {
		Api api = new Api();
		
		api.setupRoutes();
	}

	public void setupRoutes() {
		post("/event", (req, res) -> {
		
			res.status(201);
			res.type("application/json");
		    return "{\"message\":\"New event sent!\"}";
		});
		notFound((req, res) -> {
		    res.type("application/json");
		    return "{\"message\":\"Route doesn't exist.\"}";
		});
		System.out.println("API listening on http://localhost:4567/");
	}
	
}
