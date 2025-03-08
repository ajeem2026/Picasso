package picasso.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BorderFactory;

import picasso.model.Pixmap;
import picasso.parser.ParseException;
import picasso.util.Command;
import picasso.view.commands.*;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Main container for the Picasso application.
 *
 * @author Robert Duvall (rcd@cs.duke.edu)
 * @author Abid & Medaly (extensions and enhancements)
 */
@SuppressWarnings("serial")
public class Frame extends JFrame {

	private static final int CLEANING_PAUSE_MS = 2000;

	// List to track the history of user-entered expressions
	private final List<String> history = new ArrayList<>();

	// UI components for displaying the history
	private final DefaultListModel<String> historyModel = new DefaultListModel<>();
	private final JList<String> historyList = new JList<>(historyModel);

	public Frame(Dimension size) {
		setDefaultCloseOperation(EXIT_ON_CLOSE);


		Canvas canvas = new Canvas(this);
		canvas.setSize(size);
		
		// Setting title AFTER initializing the canvas
		setTitle("GameChangers");

		// Input handler
		JTextField inputField = createInputField();

		// Alternative of prompt text
		// This label will guide user to enter an expression
		JLabel inputLabel = createInputLabel();
		
		// add commands to test here
		ButtonPanel commands = new ButtonPanel(canvas);
		
		// Initialize evaluator object with empty string
		Evaluator evaluator = new Evaluator("");

		setupCommands(commands, inputField, evaluator, canvas);
		setupInputField(inputField, evaluator, canvas);

		JPanel inputPanel = createInputPanel(inputField, inputLabel, commands);
		JPanel historyPanel = createHistoryPanel(inputField);

		// Add components to the main frame
		getContentPane().add(inputPanel, BorderLayout.NORTH);
		getContentPane().add(canvas, BorderLayout.CENTER);
		getContentPane().add(historyPanel, BorderLayout.EAST);
		pack();
	}

	/**
	 * 
	 * For the input field (this is where users type the expression)
	 * 
	 */
	private JTextField createInputField() {
		return new JTextField(30);
	}

	/**
	 * This input label is an alternative to transparent background prompt text
	 */
	private JLabel createInputLabel() {
		JLabel inputLabel = new JLabel("Enter your expression:");
		inputLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		return inputLabel;
	}

	/*
	 * Input panel with all the buttons (can easily add new ones)
	 * 
	 */
	private JPanel createInputPanel(JTextField inputField, JLabel inputLabel, ButtonPanel commands) {
		JPanel inputPanel = new JPanel(new BorderLayout());
		inputPanel.add(inputField, BorderLayout.CENTER);
		inputPanel.add(inputLabel, BorderLayout.WEST);
		inputPanel.add(commands, BorderLayout.EAST);
		return inputPanel;
	}

	/**
	 * 
	 * Here, you add buttons and create a unique command for each 
	 * 
	 */
	private void setupCommands(ButtonPanel commands, JTextField inputField, Evaluator evaluator, Canvas canvas) {
		commands.add("Open", createOpenCommand(inputField, evaluator, canvas));
		commands.add("Evaluate", createEvaluateCommand(inputField, evaluator, canvas));
		commands.add("Clear", createClearCommand(canvas));
	    commands.add("Save", createSaveCommand(inputField)); 
	}

	/**
	 * 
	 * Sets up the input field to handle actions when Enter is pressed
	 * 
	 */
	private void setupInputField(JTextField inputField, Evaluator evaluator, Canvas canvas) {
		inputField.addActionListener(event -> handleInput(inputField.getText(), evaluator, canvas));
	}

	/**
	 * 
	 * Logic for the OPEN command 
	 * 
	 */
	private Command<Pixmap> createOpenCommand(JTextField inputField, Evaluator evaluator, Canvas canvas) {
		return target -> {
			JFileChooser fileChooser = new JFileChooser();
			int result = fileChooser.showOpenDialog(Frame.this);
			if (result == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fileChooser.getSelectedFile();
				try {
					if (selectedFile.getName().endsWith(".jpg") || selectedFile.getName().endsWith(".png")) {
						handleImageFile(selectedFile, inputField, evaluator, canvas);
					} else {
						handleExpressionFile(selectedFile, evaluator, canvas);
					}
				} catch (IOException ex) {
					JOptionPane.showMessageDialog(Frame.this, "Error loading file: " + ex.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		};
	}

	/**
	 * 
	 * Handles image file (think jpg, png, jpeg, etc) input
	 * 
	 */
	private void handleImageFile(File file, JTextField inputField, Evaluator evaluator, Canvas canvas) {
		String inputFromImage = "\"" + file.getName() + "\"";
		inputField.setText(inputFromImage);
		handleInput(inputFromImage, evaluator, canvas);
	}

	/**
	 * 
	 * Handles expression file (think reading from a txt file) input
	 * 
	 */
	private void handleExpressionFile(File file, Evaluator evaluator, Canvas canvas) throws IOException {
		evaluator.setInputFromFile(file);
		handleInput(evaluator.getInputString(), evaluator, canvas);
	}

	/**
	 * 
	 * Logic for clicking the EVALUATE button 
	 * 
	 */
	private Command<Pixmap> createEvaluateCommand(JTextField inputField, Evaluator evaluator, Canvas canvas) {
		return target -> {
			handleInput(inputField.getText(), evaluator, canvas);
		};
	}

	/**
	 * 
	 * Logic for clicking the CLEAR button (small animation added here!)
	 * 
	 */
	private Command<Pixmap> createClearCommand(Canvas canvas) {
		return target -> {
			Graphics2D g = (Graphics2D) canvas.getGraphics();
			String message = "Cleaning... 🧹";
			g.setFont(new java.awt.Font("Serif", java.awt.Font.ITALIC, 36));

			// Center text with background highlight
			FontMetrics metrics = g.getFontMetrics();
			int textWidth = metrics.stringWidth(message);
			int textHeight = metrics.getHeight();
			int x = (canvas.getWidth() - textWidth) / 2;
			int y = canvas.getHeight() / 2;

			g.setColor(new Color(255, 255, 150));
			g.fillRect(x - 10, y - textHeight + 5, textWidth + 20, textHeight);
			g.setColor(new Color(0, 128, 255));
			g.drawString(message, x, y);

			pause(CLEANING_PAUSE_MS);

			clearCanvas(target);
			canvas.repaint();
		};
	}
	
	/**
	 * 
	 * Logic for clicking the SAVE button.
	 * Saves the user input expression to a .txt file
	 * If you save with the same file name, your expression will get overriden!
	 */
	private Command<Pixmap> createSaveCommand(JTextField inputField) {
	    return target -> {
	    	String userInput = inputField.getText().trim();

	        // Validate the input
	        if (userInput.isEmpty()) {
	            JOptionPane.showMessageDialog(Frame.this,
	                "Cannot save an empty expression.",
	                "Save Error",
	                JOptionPane.ERROR_MESSAGE);
	            return;
	        }
	        
	        JFileChooser fileChooser = new JFileChooser();
	        fileChooser.setDialogTitle("Save Expression");
	        int result = fileChooser.showSaveDialog(Frame.this);
	        if (result == JFileChooser.APPROVE_OPTION) {
	            File selectedFile = fileChooser.getSelectedFile();

	            try {
	                // Ensure the file has a .txt extension
	                String fileName = selectedFile.getAbsolutePath();
	                if (!fileName.endsWith(".txt")) {
	                    fileName += ".txt";
	                }

	                // Save the expression to the .txt file
	                try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
	                    writer.write(inputField.getText());
	                }

	                JOptionPane.showMessageDialog(Frame.this, 
	                    "Expression saved successfully: " + fileName, 
	                    "Save Successful", 
	                    JOptionPane.INFORMATION_MESSAGE);
	            } catch (IOException ex) {
	                JOptionPane.showMessageDialog(Frame.this, 
	                    "Error saving expression: " + ex.getMessage(), 
	                    "Save Error", 
	                    JOptionPane.ERROR_MESSAGE);
	            }
	        }
	    };
	}


	/**
	 * 
	 * Pauses the thread for the specified duration
	 * 
	 */
	private void pause(int durationMs) {
		try {
			Thread.sleep(durationMs);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * 
	 * Clears the canvas by setting all pixels to black
	 * 
	 */
	private void clearCanvas(Pixmap target) {
		Dimension size = target.getSize();
		for (int y = 0; y < size.height; y++) {
			for (int x = 0; x < size.width; x++) {
				target.setColor(x, y, new Color(0, 0, 0));
			}
		}
	}

	/**
	 * 
	 * Handles input by evaluating the expression and updating the canvas
	 * 
	 */
	private void handleInput(String input, Evaluator evaluator, Canvas canvas) {
		evaluator.setInputString(input);
		new Thread(() -> {
			try {
				evaluator.execute(canvas.getPixmap());
				canvas.repaint();
				addExpressionToHistory(input);

				// Prompt user to open the evaluated expression in a new window
				int response = JOptionPane.showConfirmDialog(Frame.this,
						"Would you like to open this expression in a new window?", "Open in New Window",
						JOptionPane.YES_NO_OPTION);

				if (response == JOptionPane.YES_OPTION) {
					openNewWindow(canvas.getSize(), canvas.getPixmap(), input);
				}
			} catch (ParseException ex) {
				JOptionPane.showMessageDialog(Frame.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}).start();
	}

	/**
	 * 
	 * Creates the history panel to display and manage past expressions
	 * 
	 */
	private JPanel createHistoryPanel(JTextField inputField) {
		JPanel historyPanel = new JPanel(new BorderLayout());
		historyPanel.setBorder(BorderFactory.createTitledBorder("History Log"));

		JScrollPane scrollPane = new JScrollPane(historyList);
		historyPanel.add(scrollPane, BorderLayout.CENTER);

		JButton clearHistoryButton = new JButton("Clear History");
		clearHistoryButton.addActionListener(actionEvent -> {
			history.clear();
			historyModel.clear();
		});
		historyPanel.add(clearHistoryButton, BorderLayout.SOUTH);

		setupHistorySelection(inputField);

		return historyPanel;
	}

	/**
	 * 
	 * Configures the history panel to allow selection of past expressions
	 * 
	 */
	private void setupHistorySelection(JTextField inputField) {
		historyList.addListSelectionListener(selectionEvent -> {
			if (!selectionEvent.getValueIsAdjusting()) {
				String selectedExpression = historyList.getSelectedValue();
				if (selectedExpression != null) {
					inputField.setText(selectedExpression);
				}
			}
		});
	}

	/**
	 * 
	 * Opens a new window to display the evaluated expression
	 *
	 * @param size       The dimensions of the new canvas
	 * @param target     The Pixmap to render the expression
	 * @param expression The expression to evaluate
	 */
	private void openNewWindow(Dimension size, Pixmap target, String expression) {
		JFrame newFrame = new JFrame("New Expression View");
		Canvas newCanvas = new Canvas(newFrame);
		newCanvas.setSize(size);

		Evaluator newEvaluator = new Evaluator(expression);
		newEvaluator.execute(newCanvas.getPixmap());

		newFrame.getContentPane().add(newCanvas);
		newFrame.setSize(size);
		newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		newFrame.setLocationRelativeTo(null);
		newFrame.setVisible(true);
	}

	/**
	 * 
	 * Adds an expression to the history log if it's valid and not already present
	 *
	 * @param expression The expression to add to the history log
	 */
	private void addExpressionToHistory(String expression) {
		if (expression != null && !expression.trim().isEmpty() && !history.contains(expression)) {
			history.add(expression);
			historyModel.addElement(expression);
		}
	}
}
