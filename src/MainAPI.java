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

import javax.net.ServerSocketFactory;

class HandleHttp implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String resposta = "RESPONDIDO";
        Headers headers = exchange.getResponseHeaders();
        headers.set("Access-Control-Allow-Origin", "http://127.0.0.1:5500");
        headers.set("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type, Authorization");
        exchange.getResponseHeaders().add("Access-Control-Allow-Credentials", "true");

        System.out.println("protocolo: " + exchange.getProtocol());
        System.out.println("request method: " + exchange.getRequestMethod());
        System.out.println("remote: " + exchange.getRemoteAddress().getHostName());
        System.out.println("local: " + exchange.getLocalAddress().getHostName());
        System.out.println(exchange.getResponseHeaders().keySet());
        System.out.println(exchange.getResponseHeaders().values());
        // System.out.println(arg0.getRequestBody().);

        if ("GET".equals(exchange.getRequestMethod())) {

            exchange.sendResponseHeaders(200, resposta.length());
            OutputStream os = exchange.getResponseBody();
            os.write(resposta.getBytes());
            os.flush();
        } else if ("POST".equals(exchange.getRequestMethod())) {

            String request = inputStreamToString(exchange.getRequestBody());
            String postString = "POST RECEBIDO";
            exchange.sendResponseHeaders(200, postString.length());
            OutputStream os = exchange.getResponseBody();
            os.write(postString.getBytes());
            os.flush();
        } else {
            System.out.println("RECEBIDA MENSAGEM INVÁLIDA");
            System.out.println(exchange.getRequestMethod());
            exchange.sendResponseHeaders(200, resposta.length());// 405 Method Not Allowed
            OutputStream os = exchange.getResponseBody();
            os.write(resposta.getBytes());
            os.flush();
            // OutputStream os = exchange.getResponseBody();
            // os.write("ERRO".getBytes());
            // os.flush();
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
class MainRequest implements Runnable {
    @Override
    public void run() {

        System.out.println("Hello World");
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
            server.createContext("/teste", new HandleHttp());
            server.setExecutor(null); // creates a default executor
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // @Override
    // public void run() {
    // // TODO Auto-generated method stub
    // throw new UnsupportedOperationException("Unimplemented method 'run'");
    // }

}
// exchange -> {
// Headers headers = exchange.getResponseHeaders();
// headers.set("Access-Control-Allow-Origin", "http://127.0.0.1:5500"); //
// Replace with your actual
// // frontend URL
// headers.set("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
// headers.set("Access-Control-Allow-Headers", "Content-Type");
// if ("GET".equals(exchange.getRequestMethod())) {
// String responseText = "Hello World! from our framework-less REST API\n";
// exchange.sendResponseHeaders(200, responseText.getBytes().length);
// OutputStream output = exchange.getResponseBody();
// output.write(responseText.getBytes());
// output.flush();
// } else {
// exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
// }
// exchange.close();
// }));

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
