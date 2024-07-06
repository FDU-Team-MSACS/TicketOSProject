import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class MetroTicketSystemSingleThread extends JFrame {
    private JTextField nameField;
    private JComboBox<String> zoneComboBox;
    private JComboBox<String> passComboBox;
    private JButton purchaseButton;
    private JTextArea resultArea;

    public MetroTicketSystemSingleThread() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Metro Ticket Purchase System - Single Threaded");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(5, 2));
        formPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Zone:"));
        String[] zones = {"zone1", "zone2", "zone3"};
        zoneComboBox = new JComboBox<>(zones);
        formPanel.add(zoneComboBox);

        formPanel.add(new JLabel("Pass Type:"));
        String[] passTypes = {"daypass", "weekpass", "monthpass"};
        passComboBox = new JComboBox<>(passTypes);
        formPanel.add(passComboBox);

        purchaseButton = new JButton("Purchase Ticket");
        purchaseButton.addActionListener(e -> handlePurchase());
        formPanel.add(purchaseButton);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        add(formPanel, BorderLayout.NORTH);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        pack();
        setVisible(true);
    }

    private void handlePurchase() {
        String name = nameField.getText();
        String zone = (String) zoneComboBox.getSelectedItem();
        String passType = (String) passComboBox.getSelectedItem();

        int price = calculatePrice(zone, passType);
        String result = String.format("Ticket purchased by %s for %s: %s with price $%d%n", name, zone, passType, price);
        resultArea.append(result);

        writeToLogFile(name, zone, passType, price);
    }

    private int calculatePrice(String zone, String passType) {
        int basePrice = switch (zone) {
            case "zone1" -> 1;
            case "zone2" -> 2;
            case "zone3" -> 3;
            default -> 0;
        };

        return basePrice * switch (passType) {
            case "daypass" -> 1;
            case "weekpass" -> 5;
            case "monthpass" -> 20;
            default -> 0;
        };
    }

    private void writeToLogFile(String name, String zone, String passType, int price) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("tickets_1.txt", true))) {
            writer.write(String.format("%s purchased a %s for %s with price $%d%n", name, passType, zone, price));
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MetroTicketSystem::new);
    }
}

