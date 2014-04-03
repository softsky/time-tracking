package timetracking;

import timetracking.dialog.LoginDialog;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import timetracking.services.TimeTrackingService;
import timetracking.services.StatusListener;
import timetracking.services.IdleListener;
import timetracking.services.CaptureListener;

/**
 * @author archer
 * Created: Tue Apr 01 11:04:04 EEST 2014
 */


public class Main {
    public static void main(String... args){
        final JFrame frame = new JFrame("JDialog Demo");
        final JButton btnLogin = new JButton("Click to login");
 
        btnLogin.addActionListener(
                                   new ActionListener(){
                                       public void actionPerformed(ActionEvent e) {
                                           LoginDialog loginDlg = new LoginDialog(frame);
                                           loginDlg.setVisible(true);
                                           // if logon successfully
                                           if(loginDlg.isSucceeded()){
                                               btnLogin.setText("Hi " + loginDlg.getUsername() + "!");
                                           }
                                       }
                                   });
 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 100);
        frame.setLayout(new FlowLayout());
        frame.getContentPane().add(btnLogin);
        frame.setVisible(true);

        TimeTrackingService service = new TimeTrackingService();

        service.addListener(new StatusListener(){
                public void onChange(TimeTrackingService.Status value){
                    System.out.println("Your status has been changed to:" + value);
                }
            });
        service.addListener(new IdleListener(){
                public void onIdle(long time){
                    System.out.println("Your are idle for " + time/1000 + " seconds");
                }
            });
        service.addListener(new CaptureListener(){
                public void onCapture(){
                    System.out.println("SHOT!");
                }
            });
    }

}



