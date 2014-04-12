package timetracking.services;

import java.util.EventListener;

public interface IdleListener extends EventListener{
    void onIdle(long time);
}
