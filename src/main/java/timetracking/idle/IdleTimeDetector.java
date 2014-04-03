package timetracking.idle;

public interface IdleTimeDetector {

	/**
	 * Get the System Idle Time from the OS.
	 * 
	 * @return The System Idle Time in milliseconds.
	 */
	public long getSystemIdleTime();
}
