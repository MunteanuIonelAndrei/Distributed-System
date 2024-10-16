package monotoringSystemVersion2.monotoring.repository;

import monotoringSystemVersion2.monotoring.entity.ManagementAllDevices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ManagementDeviceRepository extends JpaRepository<ManagementAllDevices, Integer> {

    List<ManagementAllDevices> findByPersonId(UUID personID);
    ManagementAllDevices findFirstByDeviceId(int id);
}
