/*
 * Chris Chi
 * Act 4 - Server Part
 */
package projectServer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class ProjectServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try ( ServerSocket serverSocket = new ServerSocket(1234)) { // server is on port 1234
            System.out.println("Server is listening on port #" + serverSocket.getLocalPort());
            try ( Socket clientSocket = serverSocket.accept()) { // wait, listen and accept connection
                String clientHostName = clientSocket.getInetAddress().getHostName(); // client name
                int clientPortNumber = clientSocket.getLocalPort(); // port used
                System.out.println("Connected from " + clientHostName + " on #" + clientPortNumber);
                BufferedReader inStream; // input stream from client
                inStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                DataOutputStream outStream; // output stream to client
                outStream = new DataOutputStream(clientSocket.getOutputStream());

                String acc = "account" + "password";
                System.out.println("Default Password: " + acc);
                
                while (true) { // chatting loop
                    
                    String inLine = inStream.readLine(); // read a line from client
                    System.out.println("Received from client: " + inLine);
                    
                    var hashAcc = acc.hashCode();
                    var received = inLine.hashCode();

                    if (hashAcc == received) {
                        System.out.println("Client logged In");
                        String outLine = "logged in";
                        outStream.writeBytes(outLine); // send a message to client
                        outStream.write(13); // carriage return
                        outStream.write(10); // line feed
                        outStream.flush(); // flush the stream line
                        System.out.println(hashAcc);
                        System.out.println(received);
                        break; // disconnect
                    } else {
                        System.out.println("Client Entered incorrect password");
                        String outLine = "Try again... Incorrect Password";
                        outStream.writeBytes(outLine); // send a message to client
                        outStream.write(13); // carriage return
                        outStream.write(10); // line feed
                        outStream.flush(); // flush the stream line
                        System.out.println(hashAcc);
                        System.out.println(received);

                    }

                }
                inStream.close();
                outStream.close();
            }
        } catch (IOException e) {
            System.err.println("IOException occurred" + e);
        }
    } // main
}
