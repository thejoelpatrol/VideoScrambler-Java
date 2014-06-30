package videoscrambler;

import processing.core.*;
import processing.video.*;

public class VideoScrambler extends PApplet {
	final int FRAME_RATE = 30;
	
	ParamWindow controlPanel;  
	Movie inputMovie;
	String filename;
	
	public VideoScrambler() {
		controlPanel = new ParamWindow();
		
	}
	
	public void setup() {	
		filename = controlPanel.getFilename();
		openMovie(filename);
	    frameRate(FRAME_RATE);
	    frame.setResizable(true);
	}
	
	private void openMovie(String filename) {
		if (filename != null) {
			inputMovie = new Movie(this, filename);
			if (inputMovie != null)
				inputMovie.loop();
		}
	}
	
	public void draw () { 
		if (controlPanel.newFileSelected()) {
			String filename = controlPanel.getFilename();
			if (filename != null)
				openMovie(filename);
		}
	    if (inputMovie != null && inputMovie.available()) {
			inputMovie.read();
		    frame.setSize(inputMovie.width, inputMovie.height); //annoying to have to set this every frame
		    image(inputMovie,0,0);
		      
		    loadPixels();
		    
		    if (glitchFrame()) { 
		    	int samples = controlPanel.getNumSamples();
		    		    	
		        for (int i = 1; i <= samples; i++) {
		            int selection_width = randomWidth();   
		            int selection_height = randomHeight();
		            PImage temp_image = selectSample(selection_width, selection_height); 
		            int new_x = randomXCoord();
		            int new_y = randomYCoord();
		            image(temp_image,new_x,new_y);
		        } 
		    } 
		    
		    if (controlPanel.saveFrames())
		        saveFrame(filename + "-####" + ".png");	
	    }  
	} 
	
	boolean glitchFrame() {
	    if (random(1) > controlPanel.getGlitchProbability())
	        return true;
	    return false;
	}

	int randomWidth() {
		
	    int selection_width = (int)(random(controlPanel.getHorizWarp()*controlPanel.getMaxSampleHeight()));
	    if (selection_width > width) 
	        return width;      
	    return selection_width;
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

	int randomXCoord() {
	    int x = (int)(random(width)) - controlPanel.getMaxSampleHeight();
	    if (x < 0)
	        return 0;
	    return x;
	}

	int randomYCoord() {
	    int y = (int)(random(height - controlPanel.getMaxSampleHeight()/2)) ;
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
