package org.example.webServer;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;


public class webServer {

    private static String PAGES_DIR = "/public";

    private static final int PORT_NUMBER = 8080;
    private static Javalin server;

    public static void main(String[] args) {
        server = configServer();
        server.start(PORT_NUMBER);
        System.out.println("Web server started....");
    }


    public static Javalin configServer(){
        server = Javalin.create(config -> {
            config.staticFiles.add(staticFiles -> {
                staticFiles.directory = PAGES_DIR;
                staticFiles.location = Location.CLASSPATH;
            });
        });

        return server;
    }
}


