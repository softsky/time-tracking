package timetracking;

import timetracking.dialog.LoginDialog;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import timetracking.config.Config;
import timetracking.services.TimeTrackingService;
import timetracking.handlers.TimeTrackingHandlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import org.ektorp.http.*;

import org.apache.commons.cli.*;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * @author archer
 * Created: Tue Apr 01 11:04:04 EEST 2014
 */

public class Main {
    /** Options **/
    public static final Option opConfig =
        OptionBuilder.hasArg(true)
        .withArgName("config_file")
        .isRequired(false)
        .withDescription("load configuration from file")
        .create("config");

    public static final Option opHelp =
        OptionBuilder.hasArg(false)
        .isRequired(false)
        .withDescription("prints this page")
        .create("help");

    public static final Option opDebug =
        OptionBuilder.hasArg(false)
        .isRequired(false)
        .withDescription("prints debug information")
        .create("debug");

    /** Logger **/
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    /** Common httpClient **/
    public static HttpClient httpClient;

    public static Config config;
    public static void main(String[] args) throws Exception{
        final JFrame frame = new JFrame("JDialog Demo");
        final JButton btnLogin = new JButton("Click to login");

        analyzeCmdLine(args);
 
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

    private static void analyzeCmdLine(String[] args) throws Exception{
        // create Options object
        Options options = new Options();

        // adding options
        options.addOption(opConfig).addOption(opHelp).addOption(opDebug);

        CommandLineParser parser = new PosixParser();
        CommandLine cmd = parser.parse( options, args);

        if(cmd.hasOption("help")){
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp( "ProgramName", options );
            System.exit(0);
        }

        if(cmd.hasOption("config")){
            final String filePath = cmd.getOptionValue("c");
            if(new File(filePath).exists())
                config = new Config(new FileInputStream(new File(filePath)));
        } else {
            config = new Config();
        }

        if(cmd.hasOption("debug")){
            config.list(System.out);
        }
        
    }

}



