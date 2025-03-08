package picasso.view;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class SplashScreen {

	private final JWindow window;

	public SplashScreen() {
		window = new JWindow();

		// Layout for the splash screen 
		window.setSize(400, 300);
		window.setLayout(new BorderLayout());
		window.setLocationRelativeTo(null);

		// Background image
		JLabel backgroundLabel = new JLabel();

		// Can change this to any image to our liking as long as it is in the project root 
		String relativePath = "images/AmoebaMorris.png";
		ImageIcon backgroundImage = new ImageIcon(relativePath);

		backgroundLabel.setIcon(backgroundImage);
		backgroundLabel.setLayout(new BorderLayout());

		// Add content to the splash screen
		JPanel contentPanel = new JPanel();

		// Make the panel transparent to show the background
		contentPanel.setOpaque(false);
		contentPanel.setLayout(new BorderLayout());

		// This is the stylesheet for the title
		
		JLabel titleLabel = new JLabel("Welcome to our PICASSO!", SwingConstants.CENTER);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10)); // Padding
		contentPanel.add(titleLabel, BorderLayout.NORTH);

		// Stylesheet for "Loading..: text
		JLabel loadingLabel = new JLabel("Loading...", SwingConstants.CENTER);
		loadingLabel.setFont(new Font("Arial", Font.ITALIC, 18));
		loadingLabel.setForeground(Color.WHITE);
		contentPanel.add(loadingLabel, BorderLayout.CENTER);

		// Footer message
		JLabel footerLabel = new JLabel("GameChangers © 2024", SwingConstants.CENTER);
		
		// Footer stylesheet
		footerLabel.setFont(new Font("Arial", Font.PLAIN, 12));
		footerLabel.setForeground(Color.WHITE);
		footerLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding
		contentPanel.add(footerLabel, BorderLayout.SOUTH);

		// Add the content panel on top of the background
		backgroundLabel.add(contentPanel);

		// Add the background label to the window
		window.add(backgroundLabel);
	}


	public void showSplash() {
		// Play music in a separate thread
		new Thread(this::playMusic).start();

		// Show the splash screen
		window.setVisible(true);

		// Simulate loading time 
		// 8 seconds if we still keep the music 
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Close the splash screen
		window.dispose();
	}

	private void playMusic() {
		
		// WARNING: THIS WILL ONLY WORK IF OUR SELECTED MUSIC IS IN .WAV FORMAT 
		// there are free ways on the internet to convert mp3 to .wav and it is quite easy

		// Relative path for the music file
		String musicPath = "music/intro.wav";
		try {
			File musicFile = new File(musicPath);
			if (musicFile.exists()) {
				AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);
				Clip clip = AudioSystem.getClip();
				clip.open(audioStream);
				clip.start();
			} else {
				System.err.println("Error: Music file not found at " + musicPath);
			}
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}
}
