import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MetroTicketSystemNoUI {
    public static void main(String[] args) {
        String inputFile = "Input.txt"; 
        ExecutorService executorService = Executors.newFixedThreadPool(4); 
        
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0].trim();
                    String zone = parts[1].trim();
                    String passType = parts[2].trim();
                    executorService.submit(() -> processTicketPurchase(name, zone, passType));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }

    private static void processTicketPurchase(String name, String zone, String passType) {
        int price = calculatePrice(zone, passType);
        String result = String.format("Ticket purchased by %s for %s: %s with price $%d%n", name, zone, passType, price);
        System.out.println(result);
        writeToLogFile(result);
    }

    private static int calculatePrice(String zone, String passType) {
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

    private static synchronized void writeToLogFile(String result) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("tickets_0.txt", true))) {
            writer.write(result);
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }
}
