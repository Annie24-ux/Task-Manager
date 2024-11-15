package org.example;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;


public class app {

    private static String PAGES_DIR = "/public";

    private static final int PORT_NUMBER = 7000;
    private static Javalin server;

    public static void main(String[] args) {
        server = configServer();
        server.start(PORT_NUMBER);
        System.out.println("Server started....");
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

    public String serverUrl(){
        return "Server started at" + PORT_NUMBER;

    }
    }


