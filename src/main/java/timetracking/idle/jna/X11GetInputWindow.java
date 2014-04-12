package timetracking.jna;

import com.sun.jna.*;
import com.sun.jna.platform.unix.X11;
import com.sun.jna.platform.unix.X11.*;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.NativeLongByReference;
import com.sun.jna.ptr.PointerByReference;

public class X11GetInputWindow {
    public String getInputWindowTitle(){
        X11.Window window = null;
        Display display = null;

        PointerByReference window_ref = new PointerByReference();
        IntByReference revert_to_return_ref = new IntByReference();

        long idleMillis = 0L;
        try {
            System.out.println("here");

            display = X11.INSTANCE.XOpenDisplay(null);
            //window = X11.INSTANCE.XDefaultRootWindow(display);
            //X11.INSTANCE.XGetInputFocus(display, window_ref, revert_to_return_ref);

            System.out.println("window:" + window_ref);
            //System.out.println("revert_to_return:" + revert_to_return_ref.getValue());
        }finally{
            if (display != null){
                X11.INSTANCE.XCloseDisplay(display);
                display = null;
            }
        };
        return "";
    }

}
