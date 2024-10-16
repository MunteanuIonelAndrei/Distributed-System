package ManagementComunication.communicationCSV.Service;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
@Service
public class RandomCSVReader {
    private double value;
    private List<String> allLines;
    private Random random = new Random();
    private List<ValueChangeListener> listeners = new ArrayList<>();


    public RandomCSVReader() throws Exception {
        // Read all lines from the CSV file into a list

        String filePath = "src/main/resources/static/sensor.csv";
        allLines = Files.readAllLines(Paths.get(filePath));
    }

    public void addValueChangeListener(ValueChangeListener listener) {
        listeners.add(listener);
    }

    private void notifyValueChange(double newValue) {
        for (ValueChangeListener listener : listeners) {
            listener.onValueChanged(newValue);
        }
    }
    public void startReading() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        // Schedule a task to run every 10 seconds
        executorService.scheduleAtFixedRate(this::readRandomValue, 0,5 , TimeUnit.SECONDS);
    }


    private void readRandomValue() {
        // Select a random index from the list
        int randomIndex = random.nextInt(allLines.size());
        // Get the value at the random index
        String value1 = allLines.get(randomIndex);
        // Perform the action with the value (for example, print it)
        value=Double.parseDouble(value1);
        notifyValueChange(value);
    }
    public double getValue(){
        return value;
    }
}