package timetracking.handlers;

import timetracking.services.TimeTrackingService;

import timetracking.services.StatusListener;
import timetracking.services.IdleListener;
import timetracking.services.CaptureListener;

import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import java.net.MalformedURLException;
import java.util.*;

import org.ektorp.*;
import org.ektorp.impl.*;
import org.ektorp.http.*;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import timetracking.dialog.IdleDialog;
import timetracking.Main;

public class TimeTrackingHandlers {
    private static final Logger log = LoggerFactory.getLogger(TimeTrackingHandlers.class);

    static IdleDialog idleDialog;

    static {
         idleDialog = new IdleDialog(null);
    }

    public static class StatusListenerHandler implements StatusListener{
        public void onChange(TimeTrackingService.Status value){
            System.out.println("Your status has been changed to:" + value);

            if(value == TimeTrackingService.Status.ACTIVE){
                idleDialog.hide();
                idleDialog.setText("");
            }
        }
    }

    public static class IdleListenerHandler implements IdleListener{
        public void onIdle(long time){
            String str = timeToString(time);
            idleDialog.setText(str);
            idleDialog.show();

            System.out.println(str);
            System.out.println(time);
        }
    }

    public static class CaptureListenerHandler implements CaptureListener{
        private final int TUMBNAIL_WIDTH = 250;
        private final String SCREENSHOT_DIRECTORY = 
            Main.config.getProperty("SCREENSHOT_DIRECTORY", System.getProperty("user.home") + "/.timetracking"); // 5 minutes for idle timeout
        private final String DATABASE_NAME = 
            Main.config.getProperty("DATABASE_NAME", "softsky_timetracking"); // 5 minutes for idle timeout
        private final String IMAGE_TYPE = 
            Main.config.getProperty("IMAGE_TYPE", "jpg"); // 5 minutes for idle timeout
        private final String SERVER_URL = 
            Main.config.getProperty("SERVER_URL"); // server URL to connect to
        private final String username;

        
        public CaptureListenerHandler(String userName, String password) throws MalformedURLException{
            username = userName;
            Main.httpClient = new StdHttpClient.Builder()
                .url(SERVER_URL)
                .username(userName)
                .password(password)
                .build();                       
        }
        public void onCapture(){
            log.info("SHOT captured! " + SCREENSHOT_DIRECTORY);
            try {
                //Take a screenshot of every monitor
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                GraphicsDevice[] screens = ge.getScreenDevices();
  
                long now = new Date().getTime();
                File parentDir = new File(SCREENSHOT_DIRECTORY);
                parentDir.mkdirs();
                String ext = IMAGE_TYPE;
                for (GraphicsDevice screen : screens) {
                    Robot robotForScreen = new Robot(screen);
                    Rectangle screenBounds = screen.getDefaultConfiguration().getBounds();
   
                    //The screen bounds have an annoying tendency to have an offset x/y; we want (0,0) => (width, height)
                    screenBounds.x = 0;
                    screenBounds.y = 0;
                    BufferedImage screenShot = robotForScreen.createScreenCapture(screenBounds);
                    String outputFile = now 
                        + "_"
                        + normalizeDeviceId(screen.getIDstring())
                        + "."
                        + ext;   
                    ImageIO.write(screenShot, ext, new File(parentDir, outputFile));

                    Image thumbnail = screenShot.getScaledInstance(TUMBNAIL_WIDTH, -1, Image.SCALE_SMOOTH);
                    BufferedImage bufferedThumbnail = new BufferedImage(thumbnail.getWidth(null),
                                                                        thumbnail.getHeight(null),
                                                                        BufferedImage.TYPE_INT_RGB);
                    bufferedThumbnail.getGraphics().drawImage(thumbnail, 0, 0, null);
                    String thumbnailFile = now 
                        + "_"
                        + normalizeDeviceId(screen.getIDstring())
                        + "_thumbnail."
                        + ext;
                    ImageIO.write(bufferedThumbnail, "jpeg", new File(parentDir, thumbnailFile));
                }

                for(String file : parentDir.list()){ 
                    // TODO this should be changed somehow
                    Map<String, String> data = new HashMap<String,String>();
                    data.put("username", username);
                    publishFile(parentDir, file, data);
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        private final void publishFile(File parentDir, String fileName, Map<String, String> options) throws FileNotFoundException{
            CouchDbInstance dbInstance = new StdCouchDbInstance(Main.httpClient);
            // if the second parameter is true, the database will be created if it doesn't exists
            CouchDbConnector couchdb = dbInstance.createConnector(DATABASE_NAME, true);
            log.info("CouchDB connection established");

            log.info("publishing file:" + fileName);

            File file = new File(parentDir, fileName);
            String id = fileName.split("_")[0];
            HashMap<String, String> data = new HashMap<String,String>();
            try {
                data = couchdb.get(HashMap.class, id);
            } catch (DocumentNotFoundException ex){
                // putting document if not found
                data.put("_id", id);
                data.putAll(options);
                couchdb.update(data);
            }
            couchdb.createAttachment(id, data.get("_rev"), new AttachmentInputStream(fileName, new FileInputStream(file), "image/" + IMAGE_TYPE));

            file.delete();
        
        }

    }

    private static final String timeToString(Long time){
        long hours, minutes, seconds;

        hours = time / (3600 * 1000);
        minutes = (time - (hours * 3600 * 1000)) / (60 * 1000);
        seconds = (time - (hours * 3600 * 1000) - minutes * (60 * 1000)) / 1000;
            
        return ((hours > 0) ? hours + " hours ": "") +
            ((minutes > 0) ? minutes + " minutes ":"") + 
            (seconds + " seconds");
    }

    public static String normalizeDeviceId(final String monitorId){
        return monitorId.replaceAll("[:\\\\]", "-");
    }
            
}
