package net.c2001.base.ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;

import net.c2001.base.MessageProcessor;

/**
 * A window to show debug informations.
 * 
 * @author Lin Dong
 * 
 */
public class DebugFrame extends JFrame {

	private static final long serialVersionUID = 666644845639451098L;
	private DebugPanel debugPanel;
	
	/**
	 * Get the {@link DebugPanel} in this frame. It is a {@link MessageProcessor}.
	 * @return {@link DebugPanel}, which implemented  {@link MessageProcessor}.
	 */
	public DebugPanel getMessageProcessor(){
		return debugPanel;
	}
	
	
	/**
	 * Stop receiving messages.
	 */
	public synchronized void stop() {
		debugPanel.stop();
	}

	/**
	 * Star to receive messages.
	 */
	public synchronized void start() {
		debugPanel.start();
	}

	/**
	 * Create the frame.
	 */
	public DebugFrame() {
		setBounds(100, 100, 800, 600);
		getContentPane().setLayout(new BorderLayout());
		setTitle("Debug infos");
		debugPanel = new DebugPanel();
		debugPanel.setBorder(new EmptyBorder(5, 5, 5, 5));		
		setContentPane(debugPanel);			
	}
	
	/**
	 * Decide which messages are to be shown in this panel.
	 * @param filters an array of {@code booleans}, stand for
	 * text, debug text, warning, debug warning, error, debug error, fatal,
	 * debug fatal, exception, debug exception, progress, debug progress successively.
	 * If the value is {@code true}, then corresponding type of messages will be shown,
	 * {@code false} otherwise. The default value is true. 
	 */
	public void setFilters(boolean[] filters){
		debugPanel.setFilters(filters);
	}
	
}
