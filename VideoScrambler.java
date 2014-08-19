package videoscrambler;

import processing.core.*;
import processing.video.*;

public class VideoScrambler extends PApplet {	
	private final int FRAME_RATE = 30;
	ParamWindow controlPanel;  
	VideoSource video;
	
	public VideoScrambler() {
		controlPanel = new ParamWindow();	
	}
	
	public void setup() {	
		//openMovie(controlPanel.getFilename());
		size(640,480);
		
		/*cam = new Capture(this);
		video = new VideoSource(cam);
		video.start();*/
		
	    frameRate(FRAME_RATE);
	    frame.setResizable(true);
	}
	
	private void openMovie(String filename) {
		if (filename != null) {
			if (video != null)
				video.dispose();
			video = new VideoSource(this, filename);
			video.start();
			//if (inputMovie != null)
				//inputMovie.loop();
		}
	}
	
	public void draw () { 
		if (controlPanel.newFileSelected()) {
			String filename = controlPanel.getFilename();
			openMovie(filename);
		}
		if (controlPanel.switchToWebcam()) {
			//Capture cam = new Capture(this);
			video = new VideoSource(this);
			video.start();
		} 
	    if (video != null && video.available()) {
			video.loadPixels();
			video.read();
		    frame.setSize(video.width, video.height); // annoying to have to set this every frame, but it may be unavoidable
		    image(video,0,0);
			
		    if (glitchFrame()) { 
		    	loadPixels();
		    	selectAndPlaceSamples();		       
		        if (controlPanel.saveFrames())
			        saveFrame(video.getName() + "-########" + ".png");
		    } 	    	
	    }  
	} 
	
	boolean glitchFrame() {
		return random(1) < controlPanel.getGlitchProbability();
	}
	
	void selectAndPlaceSamples() {
		// we don't want the values of these parameters to change within the loop, since this is all within one frame
		int samples = controlPanel.getNumSamples();
    	boolean snap = controlPanel.snapToGrid();
    	int uniformHeight = 0;
    	if (snap) uniformHeight = controlPanel.getMaxSampleHeight();
    	
        for (int i = 1; i <= samples; i++) {
            int selectionHeight, selectionWidth;
        	if (snap) {
        		selectionHeight = uniformHeight;
        		selectionWidth = selectionHeight;
        	}
            else {
            	selectionHeight = randomHeight();
            	selectionWidth = randomWidth(selectionHeight); 
            }
              
            PImage temp_image = selectSample(selectionWidth, selectionHeight); 
            int new_x = randomXCoord(selectionWidth, snap);
            int new_y = randomYCoord(selectionHeight, snap);
            image(temp_image,new_x,new_y);
        } 
	}

	int randomWidth(int selectionHeight) {
	    int selectionWidth = (int)(random(controlPanel.getHorizWarp())*selectionHeight);
	    if (selectionWidth > width) 
	        return width;      
	    return selectionWidth;
	}

	int randomHeight() {
	    int selection_height = (int)(random(controlPanel.getMaxSampleHeight()));
	    if (selection_height > height)
	        return height;
	    return selection_height;
	}

	PImage selectSample(int selection_width, int selection_height) {
	    PImage sample = new PImage(selection_width,selection_height);
	    int start_x = (int)(random(width-selection_width));
	    int start_y = (int)(random(height-selection_height));
	    int temp_image_loc = 0;
	    
	    sample.loadPixels();
	    for (int y = start_y; y < (start_y + selection_height); y++) {
	        for (int x = start_x; x < (start_x + selection_width); x++) {
	            int loc = x + y * width;
	            sample.pixels[temp_image_loc] = pixels[loc];                    
	            temp_image_loc++;
	        } 
	    }   
	    return sample;
	}

	/**
	 * Precondition: sampleWidth > 0
	 * @param sampleWidth
	 * @return
	 */
	int randomXCoord(int sampleWidth, boolean snap) {
	    int x;
	    if (snap) {
	    	x = (int)(random(width));
	    	x -= x % sampleWidth;
	    } else x = (int)(random(width)) - sampleWidth/2;
	    if (x < 0)
	        return 0;
	    return x;
	}
	
	/**
	 * Precondition: sampleHeight >= 0
	 * @param sampleHeight
	 * @return
	 */
	int randomYCoord(int sampleHeight, boolean snap) {
	    int y;
	    if (snap) {
	    	y = (int)(random(height));
	    	y -= y % sampleHeight;
	    } else 
	    	y = (int)(random(height)) - sampleHeight/2;
	    if (y < 0)
	        return 0;
	    return y;
	}

	public void mousePressed() {
	    exit();
	}
	
	public static void main(String _args[]) {
		PApplet.main(new String[] { videoscrambler.VideoScrambler.class.getName() });
	}
}
