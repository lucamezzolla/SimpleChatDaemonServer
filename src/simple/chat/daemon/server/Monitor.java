package simple.chat.daemon.server;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luca Mezzolla
 */
public class Monitor extends Thread {

    public Monitor() {
        System.out.println("Monitor attivato!");
    }

    @Override
    public void run() {
        while(true) {
            System.out.println("Client connessi: "+SimpleChatDaemonServer.sockets.size());
            try {
                Thread.sleep(60000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    
    
}
