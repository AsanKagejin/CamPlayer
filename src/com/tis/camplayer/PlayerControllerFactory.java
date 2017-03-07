package com.tis.camplayer;

import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;

import com.sun.jna.Native;

/**
 * Created by Asan on 03.03.2017.
 */

class PlayerControllerFactory {
	static PlayerController newPlayerController(Canvas canvas){
		long canvasID = Native.getComponentID(canvas);
		try {
			return new PlayerController(startNewJVM(canvasID), canvas);
		} catch (IOException e) {
			throw new RuntimeException("Unable to start new player", e);
		}
	}

	static OutputStream startNewJVM(long canvasID) throws IOException{
		String separator = System.getProperty("file.separator");
		String classpath = System.getProperty("java.class.path");
		String classname = OutOfProcessPlayer.class.getName();
		String javapath = System.getProperty("java.home")+ separator + "bin" + separator + "java";
		Process process = (new ProcessBuilder(javapath, "-cp", classpath,
				classname, Long.toString(canvasID))).start();
		return process.getOutputStream();
	}
}
