package com.tigergeolocmedia;

public class GlobalRegister {
	
	private static GlobalRegister instance;
	
	private PictureController pictureController;

	private GlobalRegister() {
		super();
	}
	
	public static GlobalRegister getInstance() {
		if (instance == null) {
			instance = new GlobalRegister();
		}
		return instance;
			
	}

	public PictureController getPictureController() {
		return pictureController;
	}

	public void setPictureController(PictureController pictureController) {
		this.pictureController = pictureController;
	}

	
}
