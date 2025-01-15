package com.springfun.springfun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ufo")
@CrossOrigin(origins = "http://localhost:5173")
public class UfoSightingController {

    @Autowired
    private UfoSightingService service;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadCsv(@RequestParam("file") MultipartFile file) {
        service.importCsv(file);
        return ResponseEntity.ok("File uploaded and data saved successfully!");
    }

    @GetMapping
    public ResponseEntity<?> getAllSightings() {
        return ResponseEntity.ok(service.getAllSightings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSightingById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getSightingById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSighting(@PathVariable Long id, @RequestBody UfoSighting sighting) {
        try {
            service.updateSighting(id, sighting);
            return ResponseEntity.ok("Sighting updated successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error updating sighting: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSighting(@PathVariable Long id) {
        try {
            service.deleteSighting(id);
            return ResponseEntity.ok("Sighting deleted successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error deleting sighting: " + e.getMessage());
        }
    }


    @GetMapping("/shapes")
    public ResponseEntity<List<String>> getShapes() {
        return ResponseEntity.ok(service.getShapes());
    }

    @GetMapping("/search")
    public ResponseEntity<List<UfoSighting>> searchSightings(@RequestParam String city, @RequestParam String state) {
        return ResponseEntity.ok(service.searchSightings(city, state));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countSightings() {
        return ResponseEntity.ok(service.countSightings());
    }

    @GetMapping("/cities")
    public ResponseEntity<List<String>> getCities() {
        return ResponseEntity.ok(service.getCities());
    }

    @GetMapping("/states")
    public ResponseEntity<List<String>> getStates() {
        return ResponseEntity.ok(service.getStates());
    }

    @GetMapping("/filter")
    public ResponseEntity<?> filterAndSort(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String shape,
            @RequestParam(defaultValue = "date") String sortBy,
            @RequestParam(defaultValue = "asc") String order
    ) {
        return ResponseEntity.ok(service.filterAndSortSightings(city, state, startDate, endDate, shape, sortBy, order));
    }


    @GetMapping("/analytics/yearly")
    public ResponseEntity<?> getSightingsPerYear() {
        return ResponseEntity.ok(service.getSightingsPerYear());
    }

    @GetMapping("/analytics/shapes")
    public ResponseEntity<?> getSightingsByShape() {
        return ResponseEntity.ok(service.getSightingsByShape());
    }

    @GetMapping("/analytics/states")
    public ResponseEntity<?> getSightingsByState() {
        return ResponseEntity.ok(service.getSightingsByState());
    }


    @GetMapping("/geo")
    public ResponseEntity<List<UfoSighting>> getSightingsWithinRadius(
            @RequestParam double lat,
            @RequestParam double lng,
            @RequestParam double radius) {
        return ResponseEntity.ok(service.findSightingsWithinRadius(lat, lng, radius));
    }


    @PostMapping("/contribute")
    public ResponseEntity<UfoSighting> contributeSighting(@RequestBody UfoSighting sighting) {
        return ResponseEntity.ok(service.addUserSighting(sighting));
    }

    @GetMapping("/compare")
    public ResponseEntity<Map<Integer, Long>> compareSightings(
            @RequestParam int year1,
            @RequestParam int year2) {
        return ResponseEntity.ok(service.compareSightingsBetweenYears(year1, year2));
    }

    @GetMapping("/advanced-filter")
    public ResponseEntity<List<UfoSighting>> advancedFilter(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) Integer minPopulation,
            @RequestParam(required = false) Integer maxPopulation) {
        return ResponseEntity.ok(service.advancedFilter(city, state, minPopulation, maxPopulation));
    }



}
