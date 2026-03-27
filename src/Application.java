

import ui.MainWindow;

import javax.swing.*;

public class Application {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            MainWindow window = new MainWindow();
            window.setLocationRelativeTo(null);
            window.setVisible(true);
        });
    }
}