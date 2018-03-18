package simple.chat.daemon.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Luca Mezzolla
 */
public class SimpleChatDaemonServer {

    /**
     * Runs the server.
     *
     * @param args
     * @throws java.io.IOException
     * @throws java.lang.ClassNotFoundException
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        System.out.println(">>> CUTA CHAT!!! <<<\n");
        new Monitor().start();
        new SimpleChatDaemonServer();
    }

    private ServerSocket serverSocket;
    public static ArrayList<Socket> sockets = new ArrayList<>();

    public SimpleChatDaemonServer() throws IOException, InterruptedException {
        serverSocket = new ServerSocket(9090);
        while(true) {
            Socket clientSocket = serverSocket.accept();
            sockets.add(clientSocket);
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println("Benvenuto in chat!");
            Chat chat = new Chat(clientSocket);
            chat.start();
            Thread.sleep(100);
        }
    }

}
