package ManagementComunication.communicationCSV.Service;

import ManagementComunication.communicationCSV.DTO.JsonDto;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.Date;

@Service
public class ReadingCSV implements ValueChangeListener, InitializingBean {
    @Autowired
    private MessageSenderService messageSenderService;

    @Autowired
    private RandomCSVReader randomCSVReader;

    @Override
    public void afterPropertiesSet() throws Exception {
        randomCSVReader.addValueChangeListener(this);
        randomCSVReader.startReading();
    }

    @Override
    public void onValueChanged(double newValue) {
        System.out.println("Value changed: " + newValue);
        JsonDto message = new JsonDto();
        message.setValue(newValue);
        message.setTimestamp(new Date());
        Integer deviceId = null; // Use Integer to allow null values

        try {
            deviceId = Integer.parseInt(readDataFromFile("src/main/java/ManagementComunication/communicationCSV/configs/config.txt"));
            System.out.println("The device id is: " + deviceId);
        } catch (NumberFormatException e) {
            System.out.println("The string is not a valid device id.");
        }

        message.setDeviceId(deviceId); // This will set deviceId to null if there's an exception


        messageSenderService.sendMessage(message);
    }
    public String readDataFromFile(String filePath) {
        StringBuilder dataBuilder = new StringBuilder();
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                if (line.length() > 8) {
                    int startIndex = 8;
                    int endIndex = line.indexOf('"', startIndex);
                    if (endIndex != -1) {
                        String data = line.substring(startIndex, endIndex);
                        dataBuilder.append(data);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataBuilder.toString().trim();
    }


}
