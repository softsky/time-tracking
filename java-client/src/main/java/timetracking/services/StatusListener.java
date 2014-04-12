package timetracking.services;

import java.util.EventListener;

public interface StatusListener extends EventListener{
    void onChange(TimeTrackingService.Status value);
}
