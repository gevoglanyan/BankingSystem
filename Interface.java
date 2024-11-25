import javax.swing.*;
import java.awt.*;

public class Interface extends JFrame {

    public Interface() {
        setTitle("Banking System");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        setLayout(new BorderLayout());
        setResizable(false);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Interface frame = new Interface();
            frame.setVisible(true);
        });
    }
}