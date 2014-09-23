/**
 *  This class implements a UDP Based Character Generator Service
 *  defined in RFC 865 Quote of the Day protocol.
 *
 *  @author xinzi
 */

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Rfc865UdpServer {

    static int PORT = 17;
    static int READ_BUFFER_SIZE = 512;
    static int SEND_BUFFER_SIZE = 512;
    static String QUOTE_OF_THE_DAY = "I love you.";

    static DatagramSocket socket;
    static DatagramPacket request, response;
    static byte[] readBuffer = new byte[READ_BUFFER_SIZE];
    static byte[] sendBuffer;
    static String requestMessage;

    /**
     * Static main entry method for the program.
     *
     * @param args run arguments.
     */
    public static void main(String[] args) {
        sendBuffer = QUOTE_OF_THE_DAY.getBytes();
        try {
            socket = new DatagramSocket(PORT);
        } catch (SocketException e) {
            System.err.println("Failed to create Datagram Socket.");
            e.printStackTrace();
        }
        while (true) {
            System.out.println("Waiting for message...");
            try {
                request = new DatagramPacket(readBuffer, READ_BUFFER_SIZE);
                socket.receive(request);
                requestMessage = new String(readBuffer, 0, READ_BUFFER_SIZE);
                readBuffer = new byte[READ_BUFFER_SIZE];  // clear buffer
                System.out.println("Received message \"" + requestMessage + "\".");
                response = new DatagramPacket(sendBuffer, sendBuffer.length, request.getAddress(), request.getPort());
                socket.send(response);
            } catch (IOException e) {
                System.err.println("Failed to receive/send packet.");
                e.printStackTrace();
            }
        }
    }

}
