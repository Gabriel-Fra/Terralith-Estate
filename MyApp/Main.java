package MyApp;

import javax.swing.SwingUtilities;

import GUI.MainFrame;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
            {
                new MainFrame().setVisible(true);
            }
        );
    }
}