# API

A Java / Spark API which takes incoming HTTP requests and sends them to a server using Google Protobuf.

## Installation and Running

Open up a terminal window in the API folder and type:

```
java -jar API.jar
```

You should see the console say
"API listening on http://localhost:4567/"

If the backend server is also running, you should see
"Connection established."

The API is now ready for HTTP requests. Currently, it only accepts POST requests in this format.

```
{
    "timestamp" : 1518609008,
    "userId" : 1123,
    "event" : "2 hours of downtime occurred due to the release of version 1.0.5 of the system"
}
```

type "exit" and hit enter to close the program.
