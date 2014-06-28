package videoscrambler;

import processing.core.*;
import processing.video.*;
import java.awt.FileDialog;

public class VideoScrambler extends PApplet {
	final int FRAME_RATE = 30;
	
	int samples = 40;
	int max_sample_size = 50;
	float horizontalWarp = 5;
	float glitchProbability = (float)0.5;
	boolean saveFrames = false;
	
	Movie inputMovie;
	String filename;
	
	
	public void setup() {
		filename = getFilename();
	    inputMovie = new Movie(this, filename); 
	    frameRate(FRAME_RATE);
	    inputMovie.loop();
	    frame.setResizable(true);
	}
	
	String getFilename() {
	    FileDialog fileChooser;
	    fileChooser = new FileDialog(frame, "Choose source movie", FileDialog.LOAD);
	    fileChooser.setDirectory(dataPath(""));
	    fileChooser.setVisible(true);
	    return fileChooser.getDirectory() + "/" + fileChooser.getFile();
	}
	
	public void draw () { 
	    inputMovie.read();
	    frame.setSize(inputMovie.width, inputMovie.height); //annoying to have to set this every frame
	    image(inputMovie,0,0);
	      
	    loadPixels();
	  
	    if (glitchFrame()) {  
	        for (int i = 1; i <= samples; i++) {
	            int selection_width = randomWidth();   
	            int selection_height = randomHeight();
	            PImage temp_image = selectSample(selection_width, selection_height); 
	            int new_x = randomXCoord();
	            int new_y = randomYCoord();
	            image(temp_image,new_x,new_y);
	        } 
	    } 
	    
	    if (saveFrames)
	        saveFrame(filename + "-####" + ".png");  
	} 
	
	boolean glitchFrame() {
	    if (random(1) > glitchProbability)
	        return true;
	    return false;
	}

	int randomWidth() {
	    int selection_width = (int)(random(horizontalWarp*max_sample_size));
	    if (selection_width > width) 
	        return width;      
	    return selection_width;
	}

	int randomHeight() {
	    int selection_height = (int)(random(max_sample_size));
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
	    int x = (int)(random(width)) - max_sample_size;
	    if (x < 0)
	        return 0;
	    return x;
	}

	int randomYCoord() {
	    int y = (int)(random(height - max_sample_size/2)) ;
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
