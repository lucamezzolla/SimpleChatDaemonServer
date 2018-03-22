package simple.chat.daemon.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        System.out.println(">>> CUTA CHAT!!! <<<\n");
        new Monitor().start();
        new SimpleChatDaemonServer();
    }

    private final ServerSocket serverSocket;
    public static ArrayList<Socket> sockets = new ArrayList<>();
    public static ArrayList<String> nicknames = new ArrayList<>();

    public SimpleChatDaemonServer() throws IOException, InterruptedException {
        serverSocket = new ServerSocket(9090);
        while(true) {
            Socket clientSocket = serverSocket.accept();
            //Controllo se il nome è già stato preso
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String text = in.readLine();
            String nickName = "";
            if(text.startsWith("NICKNAME")) {
                String[] split = text.split(":");
                nickName = split[1];
                if(nicknames.contains(nickName)) {
                    out.println("NICKNAME_IN_USE");
                } else {
                    //Altrimenti continuo...
                    out.println("WELCOME");
                    sockets.add(clientSocket);
                    nicknames.add(nickName);
                    out.println("Benvenuto in chat!");
                    Chat chat = new Chat(clientSocket, nickName);
                    chat.start();
                    Thread.sleep(100);
                }
            }
        }
    }

}
