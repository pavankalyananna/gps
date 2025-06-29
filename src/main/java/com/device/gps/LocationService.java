package com.device.gps;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;
    
    public List<Location> getLocationsByDeviceId(String deviceId) {
        return locationRepository.findByDeviceIdOrdered(deviceId);
    }
    
    public List<Location> getTodayLocationsByDeviceId(String deviceId) {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);
        
        return locationRepository.findByDeviceIdAndTimestampBetween(
            deviceId, startOfDay, endOfDay
        );
    }
    
    public List<Location> getLocationsByDeviceIdAndDate(String deviceId, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
        return locationRepository.findByDeviceIdAndTimestampBetween(deviceId, startOfDay, endOfDay);
    }
      
    public Optional<Location> getLatestLocationByDeviceId(String deviceId) {
        return locationRepository.findLatestByDeviceId(deviceId);
    }
    
    public Location saveLocation(Location location) {
        return locationRepository.save(location);
    }
}