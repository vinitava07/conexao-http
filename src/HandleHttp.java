package src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.sql.*;

public class HandleHttp implements HttpHandler {
    String url = "jdbc:postgresql://localhost/teste";
    private final String user = "vinicius";
    private final String password = "2003";

    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        Connection connection;
        Headers headers = exchange.getResponseHeaders();
        headers.set("Access-Control-Allow-Origin", "http://127.0.0.1:5500");
        headers.set("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type, Authorization");
        exchange.getResponseHeaders().add("Access-Control-Allow-Credentials", "true");
        
        connection = connect();

        if ("GET".equals(exchange.getRequestMethod())) {

            String resposta = "RESPONDIDO";
            exchange.sendResponseHeaders(200, resposta.length());
            OutputStream os = exchange.getResponseBody();
            os.write(resposta.getBytes());
            os.flush();
        } else if ("POST".equals(exchange.getRequestMethod())) {

            String request = inputStreamToString(exchange.getRequestBody());
            String postString = "POST RECEBIDO";
            exchange.sendResponseHeaders(200, postString.getBytes().length);
                        
            OutputStream os = exchange.getResponseBody();
            os.write(postString.getBytes());
            os.flush();

        } else if ("OPTIONS".equals(exchange.getRequestMethod())) {
            String resposta = "Opcoes Validas: GET, POST";
            exchange.sendResponseHeaders(200, resposta.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(resposta.getBytes());
            os.flush();
        } else {
            String resposta = "METODO INVÁLIDO";
            exchange.sendResponseHeaders(405, resposta.length());// 405 Method Not Allowed
            OutputStream os = exchange.getResponseBody();
            os.write(resposta.getBytes());
            os.flush();

        }
        exchange.close();
        throw new UnsupportedOperationException("Unimplemented method 'handle'");
    }

    private String inputStreamToString(InputStream iReader) {

        StringBuilder response = new StringBuilder("");
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(iReader));

            String line = null;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response.toString();
    }

}
