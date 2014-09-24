/**
 *  This class implements a UDP Based Character Generator Service
 *  defined in RFC 865 Quote of the Day protocol.
 *
 *  Name: Zhou Xinzi
 *  Group: TS1
 *  Client IP: 172.21.144.129
 *
 *  @author xinzi
 */

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Rfc865UdpClient {

    static int PORT = 17;
    static String HOST = "hw1-tech";
    static int READ_BUFFER_SIZE = 512;

    static DatagramSocket socket;
    static DatagramPacket request, response;
    static byte[] readBuffer = new byte[READ_BUFFER_SIZE];
    static byte[] sendBuffer;
    static String messageToSend, responseMessage;
    static Scanner scanner;
    static InetAddress updServer;

    /**
     * Static main entry method for the program.
     *
     * @param args run arguments.
     */
    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        try {
            updServer = InetAddress.getByName(HOST);
        } catch (UnknownHostException e) {
            System.err.println("Unknown host.");
            e.printStackTrace();
        }
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            System.err.println("Failed to create Datagram Socket.");
            e.printStackTrace();
        }
        while (true) {
            System.out.println("Please input the message to send, input \"q\" to leave.");
            messageToSend = scanner.nextLine();
            if (messageToSend.equals("q")) {
                break;
            }
            // messageToSend = "Zhou Xinzi, TS1, 172.21.144.129";
            try {
                sendBuffer = messageToSend.getBytes();
                request = new DatagramPacket(sendBuffer, sendBuffer.length, updServer, PORT);
                socket.send(request);
                System.out.println("Message \"" + messageToSend + "\" has been sent.");
                response = new DatagramPacket(readBuffer, READ_BUFFER_SIZE);
                socket.receive(response);
                responseMessage = new String(readBuffer, 0, READ_BUFFER_SIZE);
                System.out.println("Received message \"" + responseMessage.trim() + "\".");
            } catch (IOException e) {
                System.err.println("Failed to receive/send packet.");
                e.printStackTrace();
            }
        }
    }

}
