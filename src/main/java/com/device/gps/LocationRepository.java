package com.device.gps;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LocationRepository extends JpaRepository<Location, Long> {
    @Query("SELECT l FROM Location l WHERE l.deviceId = :deviceId ORDER BY l.timestamp ASC")
    List<Location> findByDeviceIdOrdered(@Param("deviceId") String deviceId);
    
    @Query("SELECT l FROM Location l WHERE l.deviceId = :deviceId " +
    	       "AND l.timestamp BETWEEN :startDate AND :endDate " +
    	       "ORDER BY l.timestamp ASC")
    	List<Location> findByDeviceIdAndTimestampBetween(
    	    @Param("deviceId") String deviceId,
    	    @Param("startDate") LocalDateTime startDate,
    	    @Param("endDate") LocalDateTime endDate
    	);
    
    
    @Query("SELECT l FROM Location l WHERE l.deviceId = :deviceId ORDER BY l.timestamp DESC LIMIT 1")
    Optional<Location> findLatestByDeviceId(@Param("deviceId") String deviceId);
}

