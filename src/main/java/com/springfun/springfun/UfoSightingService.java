package com.springfun.springfun;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UfoSightingService {

    @Autowired
    private UfoSightingRepository repository;

    // Import CSV file
    public void importCsv(MultipartFile file) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            List<UfoSighting> sightings = new ArrayList<>();
            reader.readLine(); // Skip header row

            while ((line = reader.readLine()) != null) {
                try {
                    String[] fields = line.split(",");
                    UfoSighting sighting = parseSighting(fields);
                    sightings.add(sighting);
                } catch (Exception e) {
                    System.err.println("Skipping malformed row: " + line + " - " + e.getMessage());
                }
            }
            repository.saveAll(sightings);
        } catch (Exception e) {
            throw new RuntimeException("Error processing CSV file: " + e.getMessage());
        }
    }

    private UfoSighting parseSighting(String[] fields) {
        UfoSighting sighting = new UfoSighting();
        sighting.setPosted(LocalDate.parse(fields[0], DateTimeFormatter.ofPattern("MM/dd/yy")));
        sighting.setDate(LocalDate.parse(fields[1], DateTimeFormatter.ofPattern("MM/dd/yy")));
        sighting.setTime(LocalTime.parse(fields[2]));
        sighting.setCity(fields[3]);
        sighting.setState(fields[4]);
        sighting.setShape(fields[5]);
        sighting.setDuration(fields[6]);
        sighting.setSummary(fields[7]);
        sighting.setImages(fields[8].equalsIgnoreCase("Yes"));
        sighting.setImgLink(fields[9]);
        sighting.setLat(Double.parseDouble(fields[10]));
        sighting.setLng(Double.parseDouble(fields[11]));
        sighting.setPopulation(Integer.parseInt(fields[12]));
        return sighting;
    }


    // Get all sightings
    public List<UfoSighting> getAllSightings() {
        return repository.findAll();
    }

    // Get a sighting by ID
    public UfoSighting getSightingById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Sighting not found with ID: " + id));
    }

    public void updateSighting(Long id, UfoSighting sighting) {
        UfoSighting existingSighting = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sighting with ID " + id + " not found"));
        updateFields(existingSighting, sighting);
        repository.save(existingSighting);
    }

    private void updateFields(UfoSighting existing, UfoSighting updated) {
        existing.setPosted(updated.getPosted());
        existing.setDate(updated.getDate());
        existing.setTime(updated.getTime());
        existing.setCity(updated.getCity());
        existing.setState(updated.getState());
        existing.setShape(updated.getShape());
        existing.setDuration(updated.getDuration());
        existing.setSummary(updated.getSummary());
        existing.setImages(updated.getImages());
        existing.setImgLink(updated.getImgLink());
        existing.setLat(updated.getLat());
        existing.setLng(updated.getLng());
        existing.setPopulation(updated.getPopulation());
    }


    // Delete a sighting
    public void deleteSighting(Long id) {
        repository.deleteById(id);
    }

    // Search sightings by city and state
    public List<UfoSighting> searchSightings(String city, String state) {
        return repository.findAll().stream()
                .filter(s -> s.getCity().equalsIgnoreCase(city) && s.getState().equalsIgnoreCase(state))
                .toList();
    }

    // Count all sightings
    public long countSightings() {
        return repository.count();
    }

    // Get unique shapes
    public List<String> getShapes() {
        return repository.findAll().stream()
                .map(UfoSighting::getShape)
                .distinct()
                .toList();
    }

    // Get cities and states
    public List<String> getCities() {
        return repository.findAll().stream()
                .map(UfoSighting::getCity)
                .distinct()
                .toList();
    }

    public List<String> getStates() {
        return repository.findAll().stream()
                .map(UfoSighting::getState)
                .distinct()
                .toList();
    }

    public List<UfoSighting> filterAndSortSightings(String city, String state, LocalDate startDate, LocalDate endDate, String shape, String sortBy, String order) {
        Sort sort = Sort.by(order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy);
        return repository.findByFilters(city, state, startDate, endDate, shape, sort);
    }


    public Map<Integer, Long> getSightingsPerYear() {
        return repository.findAll().stream()
                .collect(Collectors.groupingBy(s -> s.getDate().getYear(), Collectors.counting()));
    }

    public Map<String, Long> getSightingsByShape() {
        return repository.findAll().stream()
                .collect(Collectors.groupingBy(UfoSighting::getShape, Collectors.counting()));
    }

    public Map<String, Long> getSightingsByState() {
        return repository.findAll().stream()
                .collect(Collectors.groupingBy(UfoSighting::getState, Collectors.counting()));
    }




    public List<UfoSighting> findSightingsWithinRadius(double lat, double lng, double radius) {
        return repository.findAll().stream()
                .filter(s -> calculateDistance(lat, lng, s.getLat(), s.getLng()) <= radius)
                .toList();
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final double R = 6371; // Earth's radius in km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        return R * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }


    public UfoSighting addUserSighting(UfoSighting sighting) {
        validateSighting(sighting);
        return repository.save(sighting);
    }

    private void validateSighting(UfoSighting sighting) {
        if (sighting.getDate() == null || sighting.getCity() == null || sighting.getState() == null) {
            throw new IllegalArgumentException("Missing required fields: date, city, or state");
        }
        if (sighting.getLat() == null || sighting.getLng() == null) {
            throw new IllegalArgumentException("Latitude and Longitude are required");
        }
    }

    public Map<Integer, Long> compareSightingsBetweenYears(int year1, int year2) {
        return repository.findAll().stream()
                .filter(s -> s.getDate().getYear() == year1 || s.getDate().getYear() == year2)
                .collect(Collectors.groupingBy(s -> s.getDate().getYear(), Collectors.counting()));
    }

    public List<UfoSighting> advancedFilter(String city, String state, Integer minPopulation, Integer maxPopulation) {
        return repository.findAll().stream()
                .filter(s -> (city == null || s.getCity().contains(city)))
                .filter(s -> (state == null || s.getState().contains(state)))
                .filter(s -> (minPopulation == null || s.getPopulation() >= minPopulation))
                .filter(s -> (maxPopulation == null || s.getPopulation() <= maxPopulation))
                .toList();
    }


}
