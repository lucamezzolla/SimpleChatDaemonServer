package simple.chat.daemon.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luca Mezzolla
 */
public class Chat extends Thread implements Serializable {

    String nickname;
    Socket socket;
    private BufferedReader in;

    public Chat(Socket s, String n) throws IOException {
        this.socket = s;
        this.nickname = n;
    }

    @Override
    public void run() {
        try {
            String message;
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (!this.isInterrupted()) {
                message = in.readLine();
                if(!message.equals("bye")) {
                    for (Socket s : SimpleChatDaemonServer.sockets) {
                        //if(!s.equals(socket)) {
                            PrintWriter printWriter = new PrintWriter(s.getOutputStream(), true);
                            //System.out.println(message);
                            printWriter.println(message);
                        //}
                    }
                    Thread.sleep(100);
                } else {
                    this.interrupt();
                    break;
                }
            }
        } catch (Exception e) {
            Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if(in != null) in.close();
                SimpleChatDaemonServer.sockets.remove(socket);
                SimpleChatDaemonServer.nicknames.remove(nickname);
                if(socket != null) socket.close();
            } catch (IOException ex) {
                Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
