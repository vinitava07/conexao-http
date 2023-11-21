package src;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;


public class MainAPI {

    public static void main(String[] args) {
        try {

            Thread server = new Thread(new MainRequest());
            server.run();

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
    }

}
