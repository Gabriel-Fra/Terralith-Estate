package MyApp;

import MyGui.AdminLoginFrame;
import MyGui.MainFrame;
import javax.swing.SwingUtilities;


public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
            {
                new AdminLoginFrame().setVisible(true);
            }
        ); 
    }
}