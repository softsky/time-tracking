/*
 * JPPF.
 * Copyright (C) 2005-2011 JPPF Team.
 * http://www.jppf.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package timetracking.idle;

import com.sun.jna.*;
import com.sun.jna.platform.unix.X11;
import com.sun.jna.platform.unix.X11.*;

import java.util.List;
import java.util.ArrayList;

/**
 * Instances of this class provide the computer idle time on a Linux system with
 * X11.
 * 
 * @author Laurent Cohen
 */
public class X11LinuxIdleTimeDetector implements IdleTimeDetector {
	/**
	 * Structure providing info on the XScreensaver.
	 */
	public class XScreenSaverInfo extends Structure {
		/**
		 * screen saver window
		 */
		public Window window;
		/**
		 * ScreenSaver{Off,On,Disabled}
		 */
		public int state;
		/**
		 * ScreenSaver{Blanked,Internal,External}
		 */
		public int kind;
		/**
		 * milliseconds
		 */
		public NativeLong til_or_since;
		/**
		 * milliseconds
		 */
		public NativeLong idle;
		/**
		 * events
		 */
		public NativeLong event_mask;


            public List<String> getFieldOrder(){
                List<String> list = new ArrayList<String>();
                list.add("window");
                list.add("state");
                list.add("kind");
                list.add("til_or_since");
                list.add("idle");
                list.add("event_mask");
                return list;
            }

	}

	/**
	 * Definition (incomplete) of the Xext library.
	 */
	public interface Xss extends Library {
		/**
		 * Instance of the Xext library bindings.
		 */
		Xss INSTANCE = (Xss) Native.loadLibrary("Xss", Xss.class);

		/**
		 * Allocate a XScreensaver information structure.
		 * 
		 * @return a {@link XScreenSaverInfo} instance.
		 */
		XScreenSaverInfo XScreenSaverAllocInfo();

		/**
		 * Query the XScreensaver.
		 * 
		 * @param display
		 *            the display.
		 * @param drawable
		 *            a {@link Drawable} structure.
		 * @param saver_info
		 *            a previously allocated {@link XScreenSaverInfo} instance.
		 * @return an int return code.
		 */
		int XScreenSaverQueryInfo(Display display, Drawable drawable,
				XScreenSaverInfo saver_info);
	}

	/**
	 * {@inheritDoc}
	 */
	public long getSystemIdleTime() {
		X11.Window window = null;
		XScreenSaverInfo info = null;
		Display display = null;

		long idleMillis = 0L;
		try {
			display = X11.INSTANCE.XOpenDisplay(null);
			window = X11.INSTANCE.XDefaultRootWindow(display);
			// info = Xss.INSTANCE.XScreenSaverAllocInfo();
			info = new XScreenSaverInfo();
			Xss.INSTANCE.XScreenSaverQueryInfo(display, window, info);
			idleMillis = info.idle.longValue();
		} finally {
			// if (info != null) X11.INSTANCE.XFree(info.getPointer());
			info = null;

			if (display != null)
				X11.INSTANCE.XCloseDisplay(display);
			display = null;
		}
		return idleMillis;
	}
}
