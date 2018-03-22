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
        System.out.println("Aggiornamento ogni 5 minuti");
    }

    @Override
    public void run() {
        while(true) {
            System.out.println("Utenti connessi: "+SimpleChatDaemonServer.sockets.size());
            SimpleChatDaemonServer.nicknames.forEach((nickname) -> {
                System.out.println(nickname);
            });
            System.out.println("+--------------------------------------+");
            try {
                Thread.sleep(300000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    
    
}
