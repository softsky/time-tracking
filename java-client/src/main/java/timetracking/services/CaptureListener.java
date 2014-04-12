package timetracking.services;

import java.util.EventListener;

public interface CaptureListener extends EventListener{
    void onCapture();
}
