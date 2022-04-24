import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Jeux Demineur
 *
 * Author: Dielya WANE, Idrissa SOW, Assane DIATTA
 * 
 */

public class Jeux extends JFrame {

    private JLabel barreDeStatus;

    public Jeux() {

        initialiserGUI();
    }

    private void initialiserGUI() {

        barreDeStatus = new JLabel("");
        barreDeStatus.setFont(new Font("Open Sans", Font.BOLD, 20));
        add(barreDeStatus, BorderLayout.SOUTH);

        add(new DemineurGUI(barreDeStatus));
        setResizable(false);
        pack();

        setTitle("Démineur");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {

            var maPartie = new Jeux();
            maPartie.setVisible(true);
        });
    }
}
