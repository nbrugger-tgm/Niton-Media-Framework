package com.niton.media.utilitys;

/**
 * This is the OSUtility Class
 * 
 * @author Nils Brugger
 * @version 2018-09-10
 */
public final class OSUtility {
	private OS operatingSystem;
	
	public OS getOS() {
		if(operatingSystem == null) {
			
		}
		return operatingSystem;
	}
	
	public enum OS {
		Windows, MacOS, Linux, Solaris, Other
	};
}
