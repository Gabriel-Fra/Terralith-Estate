package MyApp;

import MyGui.AdminLoginFrame;
import MyGui.MainFrame;
import javax.swing.SwingUtilities;


public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminLoginFrame().setVisible(true);
            }
        });
    }
}