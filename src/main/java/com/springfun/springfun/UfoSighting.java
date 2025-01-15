package com.springfun.springfun;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "ufo_sightings")
public class UfoSighting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate posted;
    private LocalDate date;
    private LocalTime time;
    private String city;
    private String state;
    private String shape;
    private String duration;

    @Column(length = 2000)
    private String summary;

    private Boolean images;
    private String imgLink;
    private Double lat;
    private Double lng;
    private Integer population;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getPosted() {
        return posted;
    }

    public void setPosted(LocalDate posted) {
        this.posted = posted;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Boolean getImages() {
        return images;
    }

    public void setImages(Boolean images) {
        this.images = images;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    // toString
    @Override
    public String toString() {
        return "UfoSighting{" +
                "id=" + id +
                ", posted=" + posted +
                ", date=" + date +
                ", time=" + time +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", shape='" + shape + '\'' +
                ", duration='" + duration + '\'' +
                ", summary='" + summary + '\'' +
                ", images=" + images +
                ", imgLink='" + imgLink + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", population=" + population +
                '}';
    }
}
