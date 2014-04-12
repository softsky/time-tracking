package timetracking.dialog;

import timetracking.Main;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import java.io.IOException;
import javax.imageio.*;
import java.net.URL;
 
public class IdleDialog extends JFrame {

    private final JLabel label;

    private static class JAAPanel extends JPanel {
        public JAAPanel(){
            super();
        }
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                                RenderingHints.VALUE_RENDER_QUALITY);

            super.paintComponent(g2);
            // // now draw the background image
            // URL url = Main.class.getResource("/images/background.jpg");
            // Image img = Toolkit.getDefaultToolkit().getImage(url);
            // g2.drawImage(img, 0, 0, this);
        }
    }
 
    public IdleDialog(Frame parent) {
        super("Login");
        //
        JPanel panel = new JAAPanel();
        label = new JLabel("Please, login");
        label.setFont(new Font("Tahoma", Font.PLAIN, 36));
        panel.add(label);

        getContentPane().add(panel);


        Dimension dim = new Dimension(500, 100);
        setMinimumSize(dim);
        pack();
        setResizable(false);
        setLocationRelativeTo(parent);

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        Dimension screenSize = new Dimension(gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());

        System.out.println(screenSize);

        //set Stage boundaries to the lower right corner of the visible bounds of the main screen
        Point pt = new Point((int)(screenSize.getWidth() - dim.getWidth()),
                                   (int)(screenSize.getHeight() - dim.getHeight()));
        this.setLocation(pt);
    }
    
    public void setText(final String text){
        label.setText(text);
    }
}

