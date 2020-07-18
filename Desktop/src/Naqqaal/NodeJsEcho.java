/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Naqqaal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NodeJsEcho {
// socket object

    private static Socket socket = null;

    public static void main(String[] args) throws UnknownHostException,
            IOException, ClassNotFoundException {
        // class instance 
        NodeJsEcho client = new NodeJsEcho();
        // socket tcp connection 
        String ip = "127.0.0.1";
        int port = 3000;
        client.socketConnect(ip, port);
        // writes and receives the message

        String message = "message123";
        System.out.println("Sending: " + message);
        String returnStr = client.echo(message);
        System.out.println("Receiving: " + returnStr);
        socket.close();
    }
// make the connection with the socket 

    private void socketConnect(String ip, int port) throws UnknownHostException,
            IOException {
        System.out.println("[Connecting to socket...]");
        this.socket = new Socket(ip, port);
    }
// writes and receives the full message int the socket (String) 

    public String echo(String message) {
        try {
            // out & in 
            PrintWriter out = new PrintWriter(getSocket().getOutputStream(),
                    true);
            BufferedReader in = new BufferedReader(new InputStreamReader(getSocket().getInputStream()));
            // writes str in the socket and read 
            out.println(message);
            String returnStr = in.readLine();
            in.close();
            out.close();
            return returnStr;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    } // get the socket instance 

    private Socket getSocket() {
        return socket;
    }

}
