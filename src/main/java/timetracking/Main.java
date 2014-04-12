package timetracking;

import timetracking.dialog.LoginDialog;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import timetracking.config.Config;
import timetracking.services.TimeTrackingService;
import timetracking.handlers.TimeTrackingHandlers;

import java.io.IOException;
import java.net.MalformedURLException;

import org.ektorp.http.*;

/**
 * @author archer
 * Created: Tue Apr 01 11:04:04 EEST 2014
 */

public class Main {
    public static HttpClient httpClient;

    public static Config config;
    public static void main(String[] args) throws Exception{
        final JFrame frame = new JFrame("JDialog Demo");
        final JButton btnLogin = new JButton("Click to login");

        config = new Config(args.length > 1?args[0]: "timetracking.properties");
        config.list(System.out);

 
        btnLogin.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    LoginDialog loginDlg = new LoginDialog(frame);
                    loginDlg.setVisible(true);
                    // if logon successfully
                    if(loginDlg.isSucceeded()){
                        TimeTrackingService service = new TimeTrackingService();

                        try {
                            service.addListener(new TimeTrackingHandlers.StatusListenerHandler());
                            service.addListener(new TimeTrackingHandlers.IdleListenerHandler());
                            service.addListener(new TimeTrackingHandlers.CaptureListenerHandler(loginDlg.getUsername(), loginDlg.getPassword()));
                        } catch(MalformedURLException ex){
                            throw new RuntimeException("Something is wrong with authentication", ex);
                        }
                    }
                }
            });
 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 100);
        frame.setLayout(new FlowLayout());
        frame.getContentPane().add(btnLogin);
        frame.setVisible(true);
    }

}



