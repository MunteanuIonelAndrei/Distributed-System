package monotoringSystemVersion2.monotoring.Service;

import monotoringSystemVersion2.monotoring.entity.ManagementAllDevices;
import monotoringSystemVersion2.monotoring.repository.ManagementDeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ManageDB {

    private ManagementDeviceRepository repositoryDevice;
    @Autowired
    public ManageDB(ManagementDeviceRepository managementDeviceRepository)
    {
        this.repositoryDevice=managementDeviceRepository;
    }

    public void insertInDB(int deviceID, UUID personID, Date timestamp, double meanValue, double maxHours,Double[] readValues){
        ManagementAllDevices object= new ManagementAllDevices(deviceID,personID,timestamp,meanValue,maxHours,readValues);
        repositoryDevice.save(object);
    }

//    public void deleteDevicesForDeletedPersons(UUID personID){
//        List<ManagementAllDevices> devices = repositoryDevice.findAll();
//        for (ManagementAllDevices device:devices
//             ) {if(device.getPersonId()==personID){
//                 repositoryDevice.delete(device);
//        }
//
//        }
//
//    }
public List<ManagementAllDevices> findAll() {
    return repositoryDevice.findAll();
}
    @Transactional
    public void deleteDevicesForDeletedPersons(UUID personID) {
        // Find devices by personID directly using a query method
        List<ManagementAllDevices> devices = repositoryDevice.findByPersonId(personID);

        // Delete all fetched devices
        // The deletion is handled in a batch, which is more performance-efficient
        repositoryDevice.deleteAll(devices);
    }
    public void deleteItem(int deviceID){

        ManagementAllDevices device = repositoryDevice.findById(deviceID).get();
        repositoryDevice.delete(device);

    }
//    public void updateMaxHours(int deviceID,double maxHours){
//        System.out.println("Trying to update");
//        ManagementAllDevices device=repositoryDevice.findById(deviceID).get();
//        device.setMaxHours(maxHours);
//        repositoryDevice.save(device);
//
//    }
@Transactional
public void updateMaxHours(int deviceID, double maxHours) {
    System.out.println("trying to update device "+deviceID+"with max hours"+maxHours);
    ManagementAllDevices deviceOptional = repositoryDevice.findFirstByDeviceId(deviceID);

    if (deviceOptional!=null) {
        ManagementAllDevices device = deviceOptional;
        device.setMaxHours(maxHours);
        repositoryDevice.save(device);
    } else {
        System.out.println("did not succed to update device "+deviceID+"with max hours"+maxHours);
        // Optionally, you might throw an exception or handle this case as per your application's requirements
    }
}
    public void updateDevice(ManagementAllDevices updatedDevice){
        repositoryDevice.save(updatedDevice);
    }

    public List<ManagementAllDevices> getAllDevices() {

        List<ManagementAllDevices> unordered= repositoryDevice.findAll();
        unordered.sort(Comparator.comparing(ManagementAllDevices::getDeviceId));
        return unordered;
    }
    public boolean isEmpty(){
        if (repositoryDevice.count()==0){
            return true;
        }
        return false;
    }
    public ManagementAllDevices getDevice(int id){
        return repositoryDevice.findById(id).get();
    }

    public Double[] getValues(int uuid)
    {
        Optional<ManagementAllDevices> managementAllDevices = repositoryDevice.findById(uuid);

        if(managementAllDevices.isPresent())
        {
            ManagementAllDevices managementAllDevices1 = managementAllDevices.get();
            return managementAllDevices1.getDoubleValues();
        }
        return  null;
    }
/*    public Double[] getValues(int deviceID){
        ManagementAllDevices device=repositoryDevice.findById(deviceID).get();
        return device.getDoubleValues();
    }*/
}
