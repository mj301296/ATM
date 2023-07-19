package assignment03;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class ATMgui {
	JButton[] leftButtons = new JButton[5];
	JButton[][] keypad = new JButton[4][3];
	JFrame frame = new JFrame("ATM");
	ATMInternals aTMInternals;
	DisplayPanel displayPanel = new DisplayPanel(frame);
	State state = State.INIT;
	public static PrintWriter TEST_LOG;
	static List<Function<State, State>> stateTransList = new ArrayList<>();
	static String[] keyLabels = {"7", "8", "9", "4","5","6","1","2","3",".","0","E"};
	static List<Function<State, State>> keyTransList = new ArrayList<>();
	static {
		stateTransList.add(state -> state.buttonA());
		stateTransList.add(state -> state.buttonB());
		stateTransList.add(state -> state.buttonC());
		stateTransList.add(state -> state.buttonD());
		stateTransList.add(state -> state.buttonE());
		for(String str : keyLabels)
			keyTransList.add(state -> state.acceptKey(str));
	}

	private JPanel buildTopPanel() {
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout(8, 4));
		topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		topPanel.setBackground(Color.LIGHT_GRAY);
		JPanel leftButtonPanel = new JPanel();
		leftButtonPanel.setBackground(Color.LIGHT_GRAY);
		leftButtonPanel.setLayout(new BoxLayout(leftButtonPanel, BoxLayout.Y_AXIS));
		topPanel.add(leftButtonPanel, BorderLayout.LINE_START);
		topPanel.add(displayPanel);
		for(int i = 0; i < leftButtons.length; i++) {
			leftButtons[i] = new JButton("            >> ");
			leftButtons[i].setBackground(Color.WHITE);
			leftButtons[i].setFont(new Font("Sansserif", Font.BOLD, 20));
			Border brd = BorderFactory.createMatteBorder(4, 4, 4, 4, Color.LIGHT_GRAY);			
			leftButtons[i].setBorder(brd);
			leftButtonPanel.add(leftButtons[i]);
			leftButtons[i].addActionListener(new ButtonListener(i));
		}
		return topPanel;
	}
	
	private class ButtonListener implements ActionListener {
		int index;
		ButtonListener(int i) {
			index = i;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			state = stateTransList.get(index).apply(state);
			System.out.println(state + aTMInternals.info());
			TEST_LOG.println("Pressed Button " + ("A" + index));
			TEST_LOG.println(state + aTMInternals.info());			
		}	
	}

	private JPanel buildLowerPanel() {
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
		bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		bottomPanel.setBackground(Color.LIGHT_GRAY);
		JPanel keyPadPanel = new JPanel();
		keyPadPanel.setBackground(Color.LIGHT_GRAY);
		keyPadPanel.setLayout(new GridLayout(0,3));
		JPanel leftBlankPanel = new JPanel();
		leftBlankPanel.setBackground(Color.LIGHT_GRAY);
		bottomPanel.add(leftBlankPanel);
		bottomPanel.add(keyPadPanel);
		JPanel rightBlankPanel = new JPanel();
		rightBlankPanel.setBackground(Color.LIGHT_GRAY);
		bottomPanel.add(rightBlankPanel);
		for(int i = 0; i < keypad.length; i++) {
			for(int j = 0; j < keypad[i].length; j++) {
				int count = i*keypad[0].length + j;
				keypad[i][j] = new JButton(keyLabels[count]);
				keypad[i][j].setBackground(Color.WHITE);
				keypad[i][j].setFont(new Font("Sansserif", Font.BOLD, 20));
				Border brd = BorderFactory.createMatteBorder(4, 4, 4, 4, Color.LIGHT_GRAY);		
				keypad[i][j].setBorder(brd);
				keyPadPanel.add(keypad[i][j]);
				keypad[i][j].addActionListener(new KeyPadListener(count));
			}
		}
		return bottomPanel;
	}

	private class KeyPadListener implements ActionListener {
		int index;
		KeyPadListener(int i) {
			index = i;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			state = keyTransList.get(index).apply(state);
			System.out.println(state + aTMInternals.info());
			TEST_LOG.println("Pressed Button " + keyLabels[index]);
			TEST_LOG.println(state + aTMInternals.info());			
		}	
	}
	
	void createAndShowGUI() {
		frame.setSize(700,500);
		frame.add(buildTopPanel(), BorderLayout.CENTER);
		frame.add(buildLowerPanel(), BorderLayout.PAGE_END);

		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(WindowListenerFactory.windowClosingFactory(e -> exit()));
		//The following can be used to remove the control bar of the JFrame
		//the preceding 2 lines would become irrelevant, since the window closing
		//button disappears
		//frame.setUndecorated(true);
		frame.setLocationRelativeTo(null);
		System.out.println(state + aTMInternals.info());
		frame.setVisible(true);		
	}

	public void exit() { // method executed when user exits the program
		int decision = JOptionPane.showConfirmDialog(
				frame, "Do you really wish to Exit the program?",
				"Confirmation", JOptionPane.YES_NO_OPTION);
		if (decision == JOptionPane.YES_OPTION) {
			TEST_LOG.close();
			System.exit(0);
		}
	}
}

