/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javahttpserver;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Paths;

/**
 *
 * @author fsmblm0
 */
public class JavaHTTPServer 
{
    static HttpServer serverSocket;
    static String absolutePath=(Paths.get("").toAbsolutePath().toString());
    
    public static void start() throws IOException
    {      
        serverSocket=HttpServer.create(new InetSocketAddress(8900),0);
        serverSocket.createContext("/info",new InfoHandler());
        serverSocket.createContext("/index", new IndexHandler());     
        serverSocket.start();
          
        
    }
    
    static class InfoHandler implements HttpHandler
    {
    
        @Override
        public void handle(HttpExchange he) throws IOException 
        {
            String responseText="FSMVU";
            
            he.sendResponseHeaders(200, responseText.length());
            OutputStream os=he.getResponseBody();
            os.write(responseText.getBytes());
            os.close();                   
        }
    }
    
    static class IndexHandler implements HttpHandler
    {

        @Override
        public void handle(HttpExchange he) throws IOException 
        {
            Headers h=he.getResponseHeaders();
            h.add("Content-Type", "text/html");
            
            File file=new File(absolutePath+"/src/file/index.html");
            byte [] byteArray=new byte [(int)file.length()];
            FileInputStream fstream=new FileInputStream(file);
            fstream.read(byteArray,0,byteArray.length);
            
            he.sendResponseHeaders(200, file.length());
            OutputStream os=he.getResponseBody();
            os.write(byteArray,0,byteArray.length);
            os.close();
                              
                    
        }
        
    }
    
    public static void main(String[] args) throws IOException
    {
        JavaHTTPServer.start();
    }
    
}
