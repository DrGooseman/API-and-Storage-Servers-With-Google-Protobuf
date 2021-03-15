package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import services.EventService;
class EventServiceTest {
	EventService eventService = new EventService();

	@Test
	void testValidateJson_NoExceptions() throws Exception {
		String testString = "{\r\n" + 
				"    \"timestamp\":\"1524\",\r\n" + 
				"    \"userId\":\"123\",\r\n" + 
				"    \"event\":\"event\"\r\n" + 
				"}";
		eventService.validateJson(testString);
	}
	
	@Test
	void testValidateJson_NoTimestampThrowsException(){
		String testString = "{\r\n" + 
				"    \"othervariable\":\"1524\",\r\n" + 
				"    \"userId\":\"123\",\r\n" + 
				"    \"event\":\"event\"\r\n" + 
				"}";
		Exception exception = assertThrows(Exception.class, () -> {
			eventService.validateJson(testString);
	    });

	    String expectedMessage = "timestamp required!";
	    String actualMessage = exception.getMessage();

	    assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	void testValidateJson_NoUserIdThrowsException(){
		String testString = "{\r\n" + 
				"    \"timestamp\":\"1524\",\r\n" + 
				"    \"othervariable\":\"123\",\r\n" + 
				"    \"event\":\"event\"\r\n" + 
				"}";
		Exception exception = assertThrows(Exception.class, () -> {
			eventService.validateJson(testString);
	    });

	    String expectedMessage = "userId required!";
	    String actualMessage = exception.getMessage();

	    assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	void testValidateJson_NoEventThrowsException(){
		String testString = "{\r\n" + 
				"    \"timestamp\":\"1524\",\r\n" + 
				"    \"userId\":\"123\",\r\n" + 
				"    \"othervariable\":\"event\"\r\n" + 
				"}";
		Exception exception = assertThrows(Exception.class, () -> {
			eventService.validateJson(testString);
	    });

	    String expectedMessage = "event required!";
	    String actualMessage = exception.getMessage();

	    assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	void testValidateJson_InvalidJsonThrowsException(){
		String testString = "{This is not valid json}";
		Exception exception = assertThrows(Exception.class, () -> {
			eventService.validateJson(testString);
	    });

	    String expectedMessage = "Not valid Json!";
	    String actualMessage = exception.getMessage();

	    assertTrue(actualMessage.contains(expectedMessage));
	}
}
