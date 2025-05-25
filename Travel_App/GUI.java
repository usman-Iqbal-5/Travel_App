package Travel_App;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Arrays;

/**
 * GUI class provides a simple graphical user interface for the Travel_App.
 * It is designed to be user-friendly, simple and intuitive.
 *
 * @author Usman
 */
public class GUI {

	/**
	 * Controller interface instance to deal with the operations.
	 */
	private Controller controller;

	/**
	 * GUI constructor - provides the controller and initialises the GUI components.
	 *
	 * @param controller Controller instance providing backend travel logic.
	 */
	public GUI(Controller controller) {
		this.controller = controller;
		createAndShowGUI();
	}

	/**
	 * Creates and displays the main GUI window with buttons and output area.
	 * Sets up action listeners on buttons to deal with the functionality.
	 */
	private void createAndShowGUI() {
		JFrame frame = new JFrame("Travel App");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 500);
		frame.setLocationRelativeTo(null);

		// Displays the welcome label at the top
		JLabel welcomeLabel = new JLabel("Travel App", SwingConstants.CENTER);
		welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
		welcomeLabel.setBorder(new EmptyBorder(15, 0, 15, 0));

		// Text area for displaying output and help with the layout alignment
		JTextArea outputArea = new JTextArea();
		outputArea.setEditable(false);
		outputArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
		outputArea.setMargin(new Insets(0, 10, 10, 10));
		outputArea.setText("Welcome to Travel App!\n\nGet to your locations more quickly and efficiently!");
		JScrollPane scrollPane = new JScrollPane(outputArea);

		// Panel containing the output text area
		JPanel outputPanel = new JPanel(new BorderLayout());
		outputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		outputPanel.add(scrollPane, BorderLayout.CENTER);

		// Buttons for each travel function
		JButton btnListDestinations = new JButton("1. List Destination(s)");
		JButton btnNearby = new JButton("2. Nearby Destinations");
		JButton btnDistances = new JButton("3. List Distances");
		JButton btnShortestPath = new JButton("4. Shortest Travel Path");
		JButton btnExit = new JButton("5. Exit");

		// Panel to deal with the layout of the buttons
		JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 10, 10));
		buttonPanel.add(btnListDestinations);
		buttonPanel.add(btnNearby);
		buttonPanel.add(btnDistances);
		buttonPanel.add(btnShortestPath);
		buttonPanel.add(btnExit);

		// Container panel for buttons with fixed width and padding
		JPanel buttonContainer = new JPanel(new BorderLayout());
		buttonContainer.setPreferredSize(new Dimension(220, 0));
		buttonContainer.setBorder(new EmptyBorder(10, 10, 10, 10));
		buttonContainer.add(buttonPanel, BorderLayout.NORTH);

		// Set up the main frame layout: welcome label top, buttons left, text-oupt in the centre
		frame.setLayout(new BorderLayout());
		frame.add(welcomeLabel, BorderLayout.NORTH);
		frame.add(buttonContainer, BorderLayout.WEST);
		frame.add(outputPanel, BorderLayout.CENTER);

		/*
		 * Action listener for "List Destination(s)"
		 * Prompts the user to enter a city or 'all' and displays matching destinations.
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
		 * Action listener for "Nearby Destinations"
		 * Prompts user for latitude, longitude, and range to find nearby destinations.
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
		 * Action listener for "List Distances"
		 * Displays distances between all pairs of destinations.
		 */
		btnDistances.addActionListener(e -> {
			outputArea.setText("List and store the distances between destinations:\n\n");
			outputArea.append(controller.listDistances());
		});

		/*
		 * Action listener for "Shortest Travel Path"
		 * Prompts for multiple city names separated by commas,
		 * then calculates and displays the shortest travel path.
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
		 * Action listener for "Exit"
		 * Displays goodbye message and terminates the application.
		 */
		btnExit.addActionListener(e -> {
			JOptionPane.showMessageDialog(frame, "Goodbye! Thanks for using the Travel App.");
			System.exit(0);
		});

		// Show the GUI window
		frame.setVisible(true);
	}
}

