package timetracking.services;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask; 

import timetracking.event.Announcer;
import timetracking.Main;

import java.util.EventListener;

import timetracking.idle.*;


public class TimeTrackingService {
    public static enum Status { IDLE, ACTIVE }

    public static long IDLE_CHECKER_INTERVAL
        = Long.parseLong(Main.config.getProperty("IDLE_CHECKER_INTERVAL", "1000")); // 1 second for idle checker interval 
    public static long CAPTURE_INTERVAL
        = Long.parseLong(Main.config.getProperty("CAPTURE_INTERVAL", "300000")); // 5 minutes for capture checker interval 
    public static long IDLE_TIMEOUT
        = Long.parseLong(Main.config.getProperty("IDLE_TIMEOUT", "300000")); // 5 minutes for idle timeout

    private TimeTrackingService.Status status = TimeTrackingService.Status.IDLE;

    private Announcer<StatusListener> statusAnnouncer;
    private Announcer<IdleListener> idleAnnouncer;
    private Announcer<CaptureListener> captureAnnouncer;

    private final Timer idleTimer;
    private final Timer captureTimer;


    public TimeTrackingService(){
        statusAnnouncer = Announcer.to(StatusListener.class);
        idleAnnouncer = Announcer.to(IdleListener.class);
        captureAnnouncer = Announcer.to(CaptureListener.class);

        //Now create the time and schedule it
        idleTimer = new Timer();
        captureTimer = new Timer();

        setupCheckIdleTimer();
    }

    public void addListener(EventListener listener){
        if(listener instanceof StatusListener)
            statusAnnouncer.addListener((StatusListener)listener);
        else if(listener instanceof IdleListener)
            idleAnnouncer.addListener((IdleListener)listener);
        else if(listener instanceof CaptureListener)
            captureAnnouncer.addListener((CaptureListener)listener);
    }

    public void removeListener(EventListener listener){
        if(listener instanceof StatusListener)
            statusAnnouncer.removeListener((StatusListener)listener);
        else if(listener instanceof IdleListener)
            idleAnnouncer.removeListener((IdleListener)listener);
        else if(listener instanceof CaptureListener)
            captureAnnouncer.removeListener((CaptureListener)listener);
    }

    public TimeTrackingService.Status getStatus() {
        return this.status;
    }


    protected void setupCheckIdleTimer(){
        Date now = new Date();
        //Use this if you want to execute it once
        idleTimer.schedule(new CheckIdle(), now, IDLE_CHECKER_INTERVAL);
        captureTimer.schedule(new Capture(), now, CAPTURE_INTERVAL);
    }

    private class CheckIdle extends TimerTask{
        public void run(){
            long imIdleFor = IdleTimeDetectorFactory.getInstance().getSystemIdleTime();
            if(imIdleFor >= IDLE_TIMEOUT && TimeTrackingService.this.status == TimeTrackingService.Status.ACTIVE){
                TimeTrackingService.this.status = TimeTrackingService.Status.IDLE;
                statusAnnouncer.announce().onChange(TimeTrackingService.Status.IDLE);
                captureAnnouncer.announce().onCapture(); // making shot on getting idle
            } else 
                if(imIdleFor < IDLE_TIMEOUT && TimeTrackingService.this.status == TimeTrackingService.Status.IDLE){
                    TimeTrackingService.this.status = TimeTrackingService.Status.ACTIVE;
                    statusAnnouncer.announce().onChange(TimeTrackingService.Status.ACTIVE);
                    captureAnnouncer.announce().onCapture(); // making shot on getting active
                } else
                    if(TimeTrackingService.this.status == TimeTrackingService.Status.IDLE){
                        idleAnnouncer.announce().onIdle(imIdleFor);
                    }

        }
    }

    private class Capture extends TimerTask{
        public void run(){
            // Capturing only if status is ACTIVE
            if(TimeTrackingService.this.status == TimeTrackingService.Status.ACTIVE)
                captureAnnouncer.announce().onCapture();
        }
    }


}
