package com.cinema.cineshow.model.movie;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "theatres")
public class Theatre {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long theatreId;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "theatre")
    @JsonIgnore
    private List<Show> shows = new ArrayList<>();
    private String location;
    private String theatreName;
    private Integer noOfRows = 10;
    private Integer noOfCols = 10;

    public Theatre() {
    }

    public Long getTheatreId() {
        return theatreId;
    }

    public void setTheatreId(Long theatreId) {
        this.theatreId = theatreId;
    }

    public List<Show> getShows() {
        return shows;
    }

    public void setShows(List<Show> shows) {
        this.shows = shows;
    }
    
    public void addShow(Show show) {
        getShows().add(show);
        show.setTheatre(this);
    }

    public void removeShow(Show show) {
        getShows().remove(show);
        show.setTheatre(null);
    }

    public Integer getNoOfRows() {
        return noOfRows;
    }

    public void setNoOfRows(Integer noOfRows) {
        this.noOfRows = noOfRows;
    }

    public Integer getNoOfCols() {
        return noOfCols;
    }

    public void setNoOfCols(Integer noOfCols) {
        this.noOfCols = noOfCols;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTheatreName() {
        return theatreName;
    }

    public void setTheatreName(String theatreName) {
        this.theatreName = theatreName;
    }
}
