package videoscrambler;

import processing.video.*;
import processing.core.PApplet;

public class VideoSource extends processing.core.PImage {
	Movie videoFile;
	Capture cam;
	boolean useWebcam;
	boolean firstFrame = true;
	
	/**
	 * Constructs a VideoSource around an existing but not-yet-capturing camera
	 * @param camera an open Capture that is constructed but not started
	 * @throws NullPointerException if camera is null
	 */
	public VideoSource (Capture camera) throws NullPointerException {
		if (camera == null) throw new NullPointerException("No valid camera passed to VideoSource. Probably couldn't detect the webcam.");
		cam = camera;
		useWebcam = true;
	}
	
	/**
	 * Constructs a VideoSource around an existing but not-yet-playing video file
	 * @param movie an open movie file that is constructed but not started
	 * @throws NullPointerException if movie is null
	 */
	public VideoSource (Movie movie) throws NullPointerException {
		if (movie == null) throw new NullPointerException("Invalid video file passed to VideoSource. Probably couldn't open the selected movie.");
		videoFile = movie;
		useWebcam = false;
	}
	
	/**
	 * 
	 * @param parent usually use "this" 
	 * @param filename the filename of the video file to use
	 * @throws NullPointerException if the filename is null
	 */
	public VideoSource (PApplet parent, String filename) throws NullPointerException {
		this(new Movie(parent, filename));
	}
	
	/**
	 * Constructs a VideoSource around the last-used capture device (webcam), if possible
	 * @param parent usually use "this"
	 */
	public VideoSource(PApplet parent) {
		this(new Capture(parent));				
	}
	
	public synchronized boolean available() {
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
	
	public String getName() {
		if (useWebcam) return "webcam";
		return videoFile.filename;
	}
	
	/**	
	 * Loop a video. Invalid to call this on a webcam.
	 * @throws UnsupportedOperationException if used on a VideoSource that contains a webcam and not a video file
	 */
	public void loop() throws UnsupportedOperationException {
		if (useWebcam) throw new UnsupportedOperationException ("loop() called on a webcam");
		else videoFile.loop();
	}
	
}
