package Travel_App;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Arrays;

/**
 * A simple GUI-based user interface for my Prototype Travel system.
 * Replaces the original text-based interface with a Swing GUI.
 *
 * Provides interactive buttons for users to utilise travel-related features
 * from the Controller interface and displays results User-friendly format.
 *
 * @author Usman
 */
public class GUI {

	/**
	 * The Controller interface instance used to delegate travel system operations.
	 */
	private Controller controller;

	/**
	 * Constructs a TUI with a given Controller and launches the GUI.
	 *
	 * @param controller The Controller instance providing application logic.
	 */
	public GUI(Controller controller) {
		this.controller = controller;
		createAndShowGUI();
	}

	/**
	 * Creates and displays the GUI frame containing buttons and output area.
	 * Sets up event listeners for buttons to perform travel-related actions.
	 */
	private void createAndShowGUI() {
		JFrame frame = new JFrame("Usman's Travel App");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(700, 500);
		frame.setLocationRelativeTo(null);

		// Text area for displaying results, set to monospaced font for better alignment
		JTextArea outputArea = new JTextArea();
		outputArea.setEditable(false);
		outputArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
		JScrollPane scrollPane = new JScrollPane(outputArea);

		// Create buttons for menu options
		JButton btnListDestinations = new JButton("1. List Destination(s)");
		JButton btnNearby = new JButton("2. Nearby Destinations");
		JButton btnDistances = new JButton("3. List Distances");
		JButton btnShortestPath = new JButton("4. Shortest Travel Path");
		JButton btnExit = new JButton("5. Exit");

		// Arrange buttons vertically with spacing
		JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 10, 10));
		buttonPanel.add(btnListDestinations);
		buttonPanel.add(btnNearby);
		buttonPanel.add(btnDistances);
		buttonPanel.add(btnShortestPath);
		buttonPanel.add(btnExit);

		// Add padding around buttons panel to separate from text area visually
		JPanel paddedButtonPanel = new JPanel(new BorderLayout());
		paddedButtonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		paddedButtonPanel.add(buttonPanel, BorderLayout.CENTER);

		// Layout the main frame with buttons on the left and output area on the right
		frame.setLayout(new BorderLayout());
		frame.add(paddedButtonPanel, BorderLayout.WEST);
		frame.add(scrollPane, BorderLayout.CENTER);

		/*
		 * Button 1: List Destinations
		 * Prompts user for a city name or 'all' to list all destinations.
		 * Displays the result in the text area.
		 */
		btnListDestinations.addActionListener(e -> {
			String city = JOptionPane.showInputDialog(frame,
					"Enter a city name (or type 'all'):", "City Input", JOptionPane.QUESTION_MESSAGE);
			if (city != null && !city.isBlank()) {
				outputArea.setText("Listing information for: " + city + "\n\n");
				outputArea.append(controller.listDestinations(city.trim()));
			}
		});

		/*
		 * Button 2: Nearby Destinations
		 * Prompts user for latitude, longitude, and range.
		 * Displays nearby destinations within the specified range.
		 */
		btnNearby.addActionListener(e -> {
			try {
				String latStr = JOptionPane.showInputDialog(frame, "Enter latitude (e.g., 51.9):");
				String lonStr = JOptionPane.showInputDialog(frame, "Enter longitude (e.g., -2.34):");
				String rangeStr = JOptionPane.showInputDialog(frame, "Enter range in km (e.g., 900):");

				if (latStr != null && lonStr != null && rangeStr != null) {
					double lat = Double.parseDouble(latStr.trim());
					double lon = Double.parseDouble(lonStr.trim());
					int range = Integer.parseInt(rangeStr.trim());

					outputArea.setText("Nearby destinations from (" + lat + ", " + lon + ") within " + range + " km:\n\n");
					outputArea.append(controller.listNearbyDestinations(lat, lon, range));
				}
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(frame, "Invalid number input.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		});

		/*
		 * Button 3: List Distances
		 * Shows distances between all pairs of destinations.
		 */
		btnDistances.addActionListener(e -> {
			outputArea.setText("List and store the distances between destinations:\n\n");
			outputArea.append(controller.listDistances());
		});

		/*
		 * Button 4: Shortest Travel Path
		 * Prompts user to enter a list of city names separated by commas.
		 * Calculates and displays the shortest travel path covering the listed cities.
		 */
		btnShortestPath.addActionListener(e -> {
			String input = JOptionPane.showInputDialog(frame,
					"Enter city names separated by commas (e.g., Bath,Oxford,London):");
			if (input != null && !input.isBlank()) {
				String[] cities = Arrays.stream(input.split(","))
						.map(String::trim)
						.filter(s -> !s.isEmpty())
						.toArray(String[]::new);

				if (cities.length >= 2) {
					outputArea.setText("Calculating shortest path for: " + String.join(", ", cities) + "\n\n");
					outputArea.append(controller.listShortestPath(cities));
				} else {
					JOptionPane.showMessageDialog(frame,
							"Please enter at least two valid city names.",
							"Input Error",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		/*
		 * Button 5: Exit
		 * Shows a goodbye message and closes the application.
		 */
		btnExit.addActionListener(e -> {
			JOptionPane.showMessageDialog(frame, "Goodbye!");
			System.exit(0);
		});

		// Display the GUI frame
		frame.setVisible(true);
	}
}
