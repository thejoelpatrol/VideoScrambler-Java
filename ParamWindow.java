package videoscrambler;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.Dimension;
import javax.swing.JCheckBox;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;

public class ParamWindow implements ActionListener, ItemListener {
	private JFrame window;
	private JSpinner samples;
	private JSpinner maxSampleHeight;
	private JSpinner horizontalWarp;
	private JSpinner glitchProbability;
	private JCheckBox chckbxSaveFrames;
	private JButton btnChooseSourceMovie;
	private JCheckBox chckbxSnapSamplesTo;
	private FileDialog fileChooser;
	private boolean newFileChosen = false;
	private boolean switchToWebcam = false;
	private final String movieButtonText = "Choose source movie file";
	private final String webcamButtonText = "Use webcam";
	private final String snapSampleText = "Snap to square grid";
	private final int WIDTH = 450;
	private final int HEIGHT = 150;
	
	public ParamWindow() {
		window = new JFrame("Parameters");
		window.getContentPane().setLayout(null);
		window.getContentPane().setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		JLabel lblSamples = new JLabel("Samples");
		lblSamples.setBounds(6, 11, 52, 16);
		window.getContentPane().add(lblSamples);
		
		JLabel lblMaxSampleHeight = new JLabel("Max sample height");
		lblMaxSampleHeight.setBounds(6, 45, 126, 16);
		window.getContentPane().add(lblMaxSampleHeight);
		
		JLabel lblSampleHorizontalWarp = new JLabel("Max sample width/height ratio");
		lblSampleHorizontalWarp.setBounds(137, 11, 203, 16);
		window.getContentPane().add(lblSampleHorizontalWarp);
		
		samples = new JSpinner();
		samples.setModel(new SpinnerNumberModel(new Integer(40), new Integer(1), null, new Integer(1)));
		samples.setBounds(60, 6, 65, 28);
		window.getContentPane().add(samples);
		
		maxSampleHeight = new JSpinner();
		maxSampleHeight.setModel(new SpinnerNumberModel(new Integer(50), new Integer(1), null, new Integer(1)));
		maxSampleHeight.setBounds(126, 39, 65, 28);
		window.getContentPane().add(maxSampleHeight);
		
		horizontalWarp = new JSpinner();
		horizontalWarp.setModel(new SpinnerNumberModel(new Float(5), new Float(0), null, new Float(0.05)));
		horizontalWarp.setBounds(333, 6, 65, 28);
		window.getContentPane().add(horizontalWarp);
		
		JLabel lblGlitchProbability = new JLabel("Glitch probability");
		lblGlitchProbability.setBounds(218, 45, 109, 16);
		window.getContentPane().add(lblGlitchProbability);
		
		glitchProbability = new JSpinner();
		glitchProbability.setModel(new SpinnerNumberModel(new Float(0.5), new Float(0), new Float(1), new Float(0.01)));
		glitchProbability.setBounds(333, 39, 65, 28);
		window.getContentPane().add(glitchProbability);
		
		chckbxSaveFrames = new JCheckBox("Save Frames (more CPU)");
		chckbxSaveFrames.setBounds(6, 102, 185, 23);
		window.getContentPane().add(chckbxSaveFrames);
		
		btnChooseSourceMovie = new JButton(movieButtonText);
		btnChooseSourceMovie.setBounds(195, 72, 203, 29);
		window.getContentPane().add(btnChooseSourceMovie);
		btnChooseSourceMovie.addActionListener(this);
		
		chckbxSnapSamplesTo = new JCheckBox(snapSampleText);
		chckbxSnapSamplesTo.setBounds(6, 73, 164, 23);
		window.getContentPane().add(chckbxSnapSamplesTo);
		chckbxSnapSamplesTo.addItemListener(this);
		
		JButton btnUseWebcam = new JButton(webcamButtonText);
		btnUseWebcam.setBounds(195, 101, 117, 29);
		window.getContentPane().add(btnUseWebcam);
		btnUseWebcam.addActionListener(this);
		
		fileChooser = new FileDialog(window, "Choose source movie", FileDialog.LOAD);
	    //fileChooser.setVisible(true);    
	    
		window.setVisible(true);
		window.pack();
	}
	
	public boolean saveFrames() {
		return chckbxSaveFrames.isSelected();
	}
	
	public int getNumSamples() {
		return (Integer)samples.getValue();
	}
	
	public int getMaxSampleHeight() {
		return (Integer)maxSampleHeight.getValue();
	}
	
	public float getHorizWarp() {
		return (Float)horizontalWarp.getValue();
	}
	
	public float getGlitchProbability() {
		return (Float)glitchProbability.getValue();
	}
	
	public boolean snapToGrid() {
		return chckbxSnapSamplesTo.isSelected();
	}
	
	/** Returns the pathname of the last selected file. The caller is expected to act on this, 
	 * as calls to newFileSelected() after this will return false, until another new file
	 * is selected.
	 * Postcondition: newFileSelected() == false
	 * @return the currently selected video file path, or null if none
	 */
	public String getFilename() {
		newFileChosen = false;
		if (fileChooser.getFile() != null)
			return fileChooser.getDirectory() + fileChooser.getFile();
		return null;
	}
	
	/** 
	 * 
	 * @return whether a new video file has been selected since the last call to getFilename()
	 */
	public boolean newFileSelected() {
		return newFileChosen;
	}

	/** 
	 * Postcondition: will return false on next call, unless the button is clicked again. i.e. it is assumed that 
	 * the caller acts on a return value of true, so it won't be returned again
	 * @return whether the webcam button has been clicked
	 */
	public boolean switchToWebcam() {
		boolean webcam = switchToWebcam;
		if (switchToWebcam) switchToWebcam = false;
		return webcam;
	}
	
	public void actionPerformed(ActionEvent e) {
	    if (movieButtonText.equals(e.getActionCommand())) {
	    	//String previousFile = fileChooser.getDirectory() + fileChooser.getFile();
	    	fileChooser.setVisible(true);
	    	//if (!previousFile.equals(fileChooser.getDirectory() + fileChooser.getFile())) 
	    		newFileChosen = true;
	    } else if (webcamButtonText.equals(e.getActionCommand())) {
	    	switchToWebcam = true;
	    }
	}
	
	public void itemStateChanged(ItemEvent e) {
	    if (e.getItemSelectable() == chckbxSnapSamplesTo && e.getStateChange() == ItemEvent.SELECTED) {
	    	horizontalWarp.setValue(new Float(1));
	    }
	}
}