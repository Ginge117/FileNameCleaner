package com.home.filescrubber;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.home.utils.FileUtils;
import com.home.utils.UpdateFileException;

/* 
 * ButtonDemo.java requires the following files:
 *   images/right.gif
 *   images/middle.gif
 *   images/left.gif
 */
public class FileNameScrubber extends JPanel
                        implements ActionListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(FileNameScrubber.class);
	protected JLabel label;
    protected JTextField field;
    protected JButton submit;
    protected static JFrame frame;
    protected static GridBagConstraints gbc = new GridBagConstraints();
    protected JLabel successLabel;

    public FileNameScrubber() {
    	
    	label = new JLabel("Please enter a Directory");
		
		field = new JTextField();
		field.setToolTipText("Please input a Directory");
		Dimension dim = new Dimension();
		dim.setSize(250, 75);
		field.setMinimumSize(dim);
		field.setPreferredSize(dim);
		dim = new Dimension();
		dim.setSize(300, 150);
		field.setMaximumSize(dim);
		
		successLabel = new JLabel();
		
		submit = new JButton("Submit");
		submit.addActionListener(this);
		submit.setToolTipText("Submit Directory");
		add(label, gbc);
		add(field, gbc);
		add(submit, gbc);
		add(successLabel, gbc);
    }
    


    /**
     * Create the GUI and show it.  For thread safety, 
     * this method should be invoked from the 
     * event-dispatching thread.
     */
    public void createAndShowGUI() {

        //Create and set up the window.
        frame = new JFrame("Update Directory");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        FileNameScrubber newContentPane = new FileNameScrubber();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
        
        URL iconURL = getClass().getResource("/icon.png");
		LOG.info("URL :" + iconURL);
		// iconURL is null when not found
		ImageIcon icon = new ImageIcon(iconURL);
		frame.setIconImage(icon.getImage());

        //Display the window.
        frame.pack();
		Dimension dim = frame.getSize();
		frame.setSize(dim.width + 50, dim.height + 50);
        
        frame.setLayout(new GridBagLayout());
        
        frame.setVisible(true);
    }

	public void actionPerformed(ActionEvent e) {
		successLabel.setText("");
		frame.pack();
		Dimension dim = frame.getSize();
		frame.setSize(dim.width + 50, dim.height + 50);
		
		frame.setVisible(true);
		LOG.info("Command :" + e.getActionCommand());
		LOG.info("Dir :" + field.getText());
		try {
			int files = FileUtils.updateFileNames(field.getText(), false);
			successLabel.setText("Updated :" + files + " files in Directory :" + field.getText());
			frame.pack();
			dim = frame.getSize();
			frame.setSize(dim.width + 50, dim.height + 50);
			
			frame.setVisible(true);
		} catch (UpdateFileException e1) {
			LOG.error("UpdateFileException occured when updating Directory '" + field.getText() + "'", e1);
			frame.setVisible(false);
			frame.setContentPane(getErrorPanel(e1));
			frame.pack();
			dim = frame.getSize();
			frame.setSize(dim.width + 50, dim.height + 50);
			
			frame.setVisible(true);
		}
	}
	
	private JPanel getErrorPanel(Exception e) {
		JPanel panel = new JPanel();
		JLabel errorLabel = new JLabel(e.getMessage());
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(errorLabel, gbc);
		JLabel stackLabel;
		String lineNum;
		for (StackTraceElement ele : e.getStackTrace()) {
			lineNum = String.valueOf(ele.getLineNumber());
			if (ele.getLineNumber() < 0) {
				lineNum = "Native Method";
			}
			stackLabel = new JLabel("at " + ele.getClassName() + ele.getMethodName() + "(" + lineNum + ")");
			panel.add(stackLabel, gbc);
		}
		
		return panel;
	}
	
	
}