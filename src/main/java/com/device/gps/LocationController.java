package com.device.gps;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.util.Collections;

@Controller
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @GetMapping("/gps")
    public String index() {
        return "gps";
    }

    @GetMapping("/track")
    public String trackDevice(@RequestParam String deviceId, Model model) {
        model.addAttribute("deviceId", deviceId);
        return "gps";
    }
    
    @GetMapping("/api/locations/{deviceId}")
    @ResponseBody
    public ResponseEntity<?> getLocationsApi(@PathVariable String deviceId) {
        List<Location> locations = locationService.getLocationsByDeviceId(deviceId);
        if (locations.isEmpty()) {
            return ResponseEntity.ok(Collections.singletonMap("message", "No locations found for device: " + deviceId));
        }
        return ResponseEntity.ok(locations);
    }
    
    @GetMapping("/api/locations/{deviceId}/date/{date}")
    @ResponseBody
    public ResponseEntity<?> getLocationsByDate(
            @PathVariable String deviceId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Location> locations = locationService.getLocationsByDeviceIdAndDate(deviceId, date);
        if (locations.isEmpty()) {
            return ResponseEntity.ok(Collections.singletonMap("message", "No locations found for device " + deviceId + " on " + date));
        }
        return ResponseEntity.ok(locations);
    }
    
    @GetMapping("/api/locations/{deviceId}/latest")
    @ResponseBody
    public ResponseEntity<?> getLatestLocation(@PathVariable String deviceId) {
        Optional<Location> location = locationService.getLatestLocationByDeviceId(deviceId);
        if (location.isEmpty()) {
            return ResponseEntity.ok(Collections.singletonMap("message", "No locations found for device: " + deviceId));
        }
        return ResponseEntity.ok(location.get());
    }
    
    @PostMapping("/api/locations")
    @ResponseBody
    public Location saveLocation(@RequestBody Location location) {
        return locationService.saveLocation(location);
    }
} 