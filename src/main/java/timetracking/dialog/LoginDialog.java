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
import java.net.*;

import org.ektorp.*;
import org.ektorp.impl.*;
import org.ektorp.http.*;

 
public class LoginDialog extends JDialog {

    private static class Login {
        private final String SERVER_URL = 
            Main.config.getProperty("SERVER_URL"); // server URL to connect to

        public static boolean authenticate(String userName, String password) throws Exception {
            Main.httpClient = new StdHttpClient.Builder()
                .url("http://vm81.softsky.com.ua:5984")
                .username(userName)
                .password(password)
                .build();

            CouchDbInstance dbInstance = new StdCouchDbInstance(Main.httpClient);

            return dbInstance.checkIfDbExists(new DbPath("softsky_timetracking"));
        }
    }

    private static class JAAPanel extends JPanel {
        public JAAPanel(LayoutManager layoutManager){
            super(layoutManager);
        }
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                                RenderingHints.VALUE_RENDER_QUALITY);

            super.paintComponent(g2);
            // now draw the background image
            URL url = Main.class.getResource("/images/background.jpg");
            Image img = Toolkit.getDefaultToolkit().getImage(url);
            g2.drawImage(img, 0, 0, this);
        }
    }
 
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JLabel lbUsername;
    private JLabel lbPassword;
    private AbstractButton btnLogin;
    private AbstractButton btnCancel;
    private boolean succeeded;
 
    public LoginDialog(Frame parent) {
        super(parent, "Login", true);
        //
        JPanel panel = new JAAPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
 
        cs.fill = GridBagConstraints.HORIZONTAL;

        lbUsername = new JLabel("Please, login");
        lbUsername.setFont(new Font("Tahoma", Font.PLAIN, 20));
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(lbUsername, cs);
 
        lbUsername = new JLabel("Username:");
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(lbUsername, cs);
 
        tfUsername = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        panel.add(tfUsername, cs);
 
        lbPassword = new JLabel("Password:");
        cs.gridx = 0;
        cs.gridy = 2;
        cs.gridwidth = 1;
        panel.add(lbPassword, cs);
 
        pfPassword = new JPasswordField(20);
        cs.gridx = 1;
        cs.gridy = 2;
        cs.gridwidth = 2;
        panel.add(pfPassword, cs);
        panel.setBorder(new LineBorder(Color.GRAY));

        URL url = Main.class.getResource("/icons/login.png");
        Image img = Toolkit.getDefaultToolkit().getImage(url);
        btnLogin = new JButton("Login");
        //btnLogin.setIcon(new ImageIcon(img));
 
        btnLogin.addActionListener(new ActionListener() {
 
                public void actionPerformed(ActionEvent e) {
                    try {
                        if (Login.authenticate(getUsername(), getPassword())) {
                            JOptionPane.showMessageDialog(LoginDialog.this,
                                                          "Hi " + getUsername() + "! You have successfully logged in.",
                                                          "Login",
                                                          JOptionPane.INFORMATION_MESSAGE);
                            succeeded = true;
                            dispose();
                        }  else {
                            JOptionPane.showMessageDialog(LoginDialog.this,
                                                          "Invalid username or password",
                                                          "Login",
                                                          JOptionPane.ERROR_MESSAGE);
                            // reset username and password
                            tfUsername.setText("");
                            pfPassword.setText("");
                            succeeded = false;
                        }
                    }catch(Exception ex) {
                        JOptionPane.showMessageDialog(LoginDialog.this,
                                                      ex.getMessage(),
                                                      "Login",
                                                      JOptionPane.ERROR_MESSAGE);
                        succeeded = false;
 
                    }
                }
            });
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });
        JPanel bp = new JPanel(){
                public void paintComponent(Graphics g) {
                    g.setColor(getBackground());
                    Rectangle r = g.getClipBounds();
                    g.fillRect(r.x, r.y, r.width, r.height);
                    super.paintComponent(g);
                }
            };
        bp.setOpaque(false);
        bp.add(btnLogin);
        bp.add(btnCancel);
 
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.PAGE_END);
 
        setMinimumSize(new Dimension(500,300));
        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }
 
    public String getUsername() {
        return tfUsername.getText().trim();
    }
 
    public String getPassword() {
        return new String(pfPassword.getPassword());
    }
 
    public boolean isSucceeded() {
        return succeeded;
    }
}
