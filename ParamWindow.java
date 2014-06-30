package videoscrambler;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.Dimension;
import javax.swing.JCheckBox;

public class ParamWindow {
	private JFrame window;
	private JSpinner samples;
	private JSpinner maxSampleHeight;
	private JSpinner horizontalWarp;
	private JSpinner glitchProbability;
	private JCheckBox chckbxSaveFrames;
	private final int WIDTH = 500;
	private final int HEIGHT = 300;
	
	public ParamWindow() {
		window = new JFrame("Parameters");
		window.getContentPane().setLayout(null);
		window.getContentPane().setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		JLabel lblSamples = new JLabel("Samples");
		lblSamples.setBounds(6, 11, 52, 16);
		window.getContentPane().add(lblSamples);
		
		JLabel lblMaxSampleHeight = new JLabel("Max sample height");
		lblMaxSampleHeight.setBounds(156, 11, 126, 16);
		window.getContentPane().add(lblMaxSampleHeight);
		
		JLabel lblSampleHorizontalWarp = new JLabel("Sample width/height ratio");
		lblSampleHorizontalWarp.setBounds(6, 45, 164, 16);
		window.getContentPane().add(lblSampleHorizontalWarp);
		
		samples = new JSpinner();
		samples.setModel(new SpinnerNumberModel(new Integer(40), new Integer(1), null, new Integer(1)));
		samples.setBounds(60, 6, 65, 28);
		window.getContentPane().add(samples);
		
		maxSampleHeight = new JSpinner();
		maxSampleHeight.setModel(new SpinnerNumberModel(new Integer(50), new Integer(1), null, new Integer(1)));
		maxSampleHeight.setBounds(279, 6, 65, 28);
		window.getContentPane().add(maxSampleHeight);
		
		horizontalWarp = new JSpinner();
		horizontalWarp.setModel(new SpinnerNumberModel(new Float(5), new Float(0), null, new Float(0.05)));
		horizontalWarp.setBounds(170, 39, 65, 28);
		window.getContentPane().add(horizontalWarp);
		
		JLabel lblGlitchProbability = new JLabel("Glitch probability");
		lblGlitchProbability.setBounds(256, 45, 109, 16);
		window.getContentPane().add(lblGlitchProbability);
		
		glitchProbability = new JSpinner();
		glitchProbability.setModel(new SpinnerNumberModel(new Float(0.5), new Float(0), new Float(1), new Float(0.01)));
		glitchProbability.setBounds(377, 39, 65, 28);
		window.getContentPane().add(glitchProbability);
		
		chckbxSaveFrames = new JCheckBox("Save Frames");
		chckbxSaveFrames.setBounds(6, 73, 128, 23);
		window.getContentPane().add(chckbxSaveFrames);
		
		window.setVisible(true);
		window.validate();
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
}