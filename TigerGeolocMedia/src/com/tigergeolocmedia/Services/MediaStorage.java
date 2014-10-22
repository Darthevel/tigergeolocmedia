package com.tigergeolocmedia.Services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

public class MediaStorage {

	private static final String CAMERA_DIR = "/Tiger/Camera/";
	private static final String AUDIO_DIR = "/Tiger/Audio/";
	
	public static void storeFile(String inputPath, String inputFile){
		InputStream in = null;
	    OutputStream out = null;
	    
	    String extension = inputFile.substring(0, 1);
	    System.out.println(extension);
	    
	    
	}
	
	public static void deleteFile() {
		
	}
}
