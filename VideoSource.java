package videoscrambler;

import processing.video.*;
import processing.core.PApplet;

public class VideoSource extends processing.core.PImage {
	Movie videoFile;
	Capture cam;
	boolean useWebcam;
	boolean firstFrame = true;
	
	public VideoSource (Capture camera) throws NullPointerException {
		if (camera == null) throw new NullPointerException("No valid camera passed to VideoSource. Probably couldn't detect the webcam.");
		cam = camera;
		useWebcam = true;
	}
	
	public VideoSource (Movie movie) throws NullPointerException {
		if (movie == null) throw new NullPointerException("Invalid video file passed to VideoSource. Probably couldn't open the selected movie.");
		videoFile = movie;
		useWebcam = false;
	}
	
	public VideoSource (PApplet parent, String filename) throws NullPointerException {
		this(new Movie(parent, filename));
	}
	
	public VideoSource(PApplet parent) {
		this(new Capture(parent));				
	}
	
	public boolean available() {
		if (useWebcam) return cam.available();
		return videoFile.available();
	}
	
	public synchronized void read() {
		if (useWebcam) cam.read();
		else videoFile.read();
		if (firstFrame) {
			if (useWebcam) super.init(cam.width, cam.height, ARGB);
			else super.init(videoFile.width, videoFile.height, ARGB);
		}
		updatePixels();
	}
	
	@Override
	public synchronized void loadPixels() {
		super.loadPixels();
		if (useWebcam) {
			cam.loadPixels();
			pixels = cam.pixels;
		} else {
			videoFile.loadPixels();
			pixels = videoFile.pixels;
		}
	}
	
	@Override
	public synchronized void updatePixels() {
		if (useWebcam) pixels = cam.pixels;
		else pixels = videoFile.pixels;
		super.updatePixels();
	}
	
	public void start() {
		if (useWebcam) cam.start();
		else videoFile.play();
	}
	
	public void stop() {
		if (useWebcam) cam.stop();
		else videoFile.stop();
	}
	
	public void dispose() {
		if (useWebcam) cam.dispose();
		else videoFile.dispose();		
	}
	
	/*public void pause() throws UnsupportedOperationException {
		if (useWebcam) throw new UnsupportedOperationException ("pause() called on a webcam");
		else videoFile.pause();
	}*/
	
	
	
}
