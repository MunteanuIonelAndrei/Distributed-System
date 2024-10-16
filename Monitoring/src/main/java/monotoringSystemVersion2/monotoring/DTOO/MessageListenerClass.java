package monotoringSystemVersion2.monotoring.DTOO;

import com.fasterxml.jackson.databind.ObjectMapper;
import monotoringSystemVersion2.monotoring.Service.ManageDB;
import monotoringSystemVersion2.monotoring.configs.DeviceWebSocketHandler;
import monotoringSystemVersion2.monotoring.entity.ManagementAllDevices;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;


@Component
@Service
public class MessageListenerClass {
    @Autowired
    ManageDB manageDB;
    @Autowired
    public  DeviceWebSocketHandler deviceWebSocketHandler;
    SendToClient sendToClient;
    final int maxStoreCapacity=5;
    int lastDeviceHeartBeat=0;
//    public final MyWebSocketHandler myWebSocketHandler;
//
//    @Autowired
//    public MessageListenerClass(MyWebSocketHandler myWebSocketHandler) {
//        this.myWebSocketHandler = myWebSocketHandler;
//    }

    @RabbitListener(queues = "csvValues")
    public void onMessageReceived(JsonDto message) {
        Double toInsert=message.getValue();
        System.out.println("Received timestamp: " + message.getTimestamp());
        System.out.println("Received value: " +message.getValue() );
        List<ManagementAllDevices> nrDev=null;
        if(!manageDB.isEmpty()) {
            nrDev = manageDB.getAllDevices();
            int lenght1 = nrDev.size();

            if(message.getDeviceId()<0){
                if (lastDeviceHeartBeat >= lenght1) {
                    lastDeviceHeartBeat = 0;
                }

                if (lastDeviceHeartBeat < lenght1) {
                    ManagementAllDevices device = nrDev.get(lastDeviceHeartBeat);
                    workOnDevice(device,toInsert);

                    lastDeviceHeartBeat++;

            }

            }else{
                boolean exist=false;
                for (ManagementAllDevices device:nrDev
                     ) {
                    if(device.getDeviceId()==message.getDeviceId()){
                        ManagementAllDevices device1= manageDB.getDevice(message.getDeviceId());
                        workOnDevice(device1,toInsert);
                        exist=true;
                        break;

                    }

                }
                if(!exist){
                    System.out.println("The device doesn't exist in the DB!");
                }

        }
    }
    }

    private void workOnDevice(ManagementAllDevices device,double toInsert){
        Double[] arrayValues = device.getDoubleValues();
       // System.out.println(arrayValues);
        int sizeArray = 0;
        for (Double element : arrayValues) {
            if (element != null) {
                sizeArray++;
            }
        }
        if(sizeArray==maxStoreCapacity) {
            for(int i=0;i<sizeArray-1;i++){
                arrayValues[i]=arrayValues[i+1];
            }
            arrayValues[sizeArray-1] = toInsert;


        }else {
            arrayValues[sizeArray] = toInsert;
        }
        int newSizeArray=0;
        double mean=0.0;
        for (Double element : arrayValues) {
            if (element != null) {
                newSizeArray++;
                mean+=element;
            }
        }

        mean/=newSizeArray;

        Map<String, Object> customJsonMap = new HashMap<>();
        boolean meanExceedsMax = mean > device.getMaxHours();
        customJsonMap.put("meanExceedsMax", meanExceedsMax);
        customJsonMap.put("personId", device.getPersonId());
        System.out.println("personId"+device.getPersonId());
        customJsonMap.put("deviceId", device.getDeviceId());
        customJsonMap.put("meanValue", mean);
        customJsonMap.put("maxHours", device.getMaxHours());
        customJsonMap.put("doubleValues", arrayValues);
        customJsonMap.put("timestamp", device.getTimestamp());

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String customJson = objectMapper.writeValueAsString(customJsonMap);
            System.out.println(customJson + " json");
            // Send JSON to all connected WebSocket clients
            deviceWebSocketHandler.broadcastUpdate(customJson);
        } catch (Exception e) {
            // Handle exceptions
            e.printStackTrace();
        }

        System.out.println("Device: "+device.getDeviceId() +" has values :");
        for (Double element : arrayValues) {
            if (element != null) {
                System.out.println( element);

            }
        }
        System.out.println("And their mean: "+mean);
        //System.out.println("Device: "+device.getDeviceId() +" has values :"+ arrayValues.toString() + "and their mean: "+ mean);
        if(device.getMaxHours()>mean){

            System.out.println("The mean "+mean+" excedeed the maxHors" +device.getMaxHours());
           /* System.out.println(device.getMaxHours()+"device max hours");
            System.out.println(mean+"meannnn");*/
         //   String message ="The values is too high";
      //      myWebSocketHandler.broadcastMessage("message");

        }
       // System.out.println("For device "+device.getDeviceId()+" the new value received is "+toInsert+" and the current mean is "+mean);
        device.setMeanValue(mean);
        device.setDoubleValues(arrayValues);
        manageDB.updateDevice(device);

    }

    public SendToClient getSendToClient(){
        return this.sendToClient;
    }



    @RabbitListener(queues = "deviceToMonitor")
    public void processAnotherQueue(JsonDtoDevice messageData) {
        // Process message from anotherQueue
       // System.out.println("Received from anotherQueue: " + messageData.getPersonId());
       // System.out.println("Received from anotherQueue id: " + messageData.getPersonId()+" with action: "+ messageData.getTypeOfMessage());

        int action=messageData.getTypeOfMessage();
        System.out.println("action is "+ action);
        UUID personID= messageData.getPersonId();
        System.out.println(messageData);
        int deviceId = messageData.getDeviceId();
        double maxHours= messageData.getMaxHours();
        Double[] readValues;
        readValues= new Double[maxStoreCapacity];
        Date now=new Date();

        //0-insert, 1-delete,2-update,3 delete person
        if(action==0){
            manageDB.insertInDB(deviceId,personID,now,0,maxHours,readValues);
            System.out.println("Inserted device with id: " + deviceId);


        }
        else if (action==1) {

            manageDB.deleteItem(deviceId);
            System.out.println("Deleted device with id: " + deviceId);


        } else if (action==2) {
            manageDB.updateMaxHours(deviceId,maxHours);
            System.out.println("Updated device with id: " + deviceId + "new Max hours "+ maxHours);


        } else if (action==3)
        {
            manageDB.deleteDevicesForDeletedPersons(personID);
            System.out.println("Deletet devices with personID: " + personID);

        }
    }

}
