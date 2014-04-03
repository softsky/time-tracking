package timetracking.idle;

import com.sun.jna.*;
import com.sun.jna.win32.*;

import java.util.List;
import java.util.ArrayList;

public class WindowsIdleTimeDetector implements IdleTimeDetector {

	// kernel mapping
	public interface Kernel32 extends StdCallLibrary {
		Kernel32 INSTANCE = (Kernel32) Native.loadLibrary("kernel32",
				Kernel32.class);

		// returns milliseconds since system was started
		public long GetTickCount();
	}

	// user mapping
	public interface User32 extends StdCallLibrary {
		User32 INSTANCE = (User32) Native.loadLibrary("user32", User32.class);

		// contains time of last input
		public static class LASTINPUTINFO extends Structure {
			public int cbSize = 8;

			// Tick count of when the last input event was received.
			public int dwTime;

                    public List<String> getFieldOrder(){
                        List<String> list = new ArrayList<String>();
                        list.add("cbSize");
                        list.add("dwTime");
                        return list;
                    }
		}

		// returns time of last input
		public boolean GetLastInputInfo(LASTINPUTINFO result);
	}

	// returns system idle time in milliseconds
	public long getSystemIdleTime() {
		User32.LASTINPUTINFO lastInputInfo = new User32.LASTINPUTINFO();
		User32.INSTANCE.GetLastInputInfo(lastInputInfo);
		return Kernel32.INSTANCE.GetTickCount() - lastInputInfo.dwTime;
	}
}
