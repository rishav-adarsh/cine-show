package com.cinema.cineshow.api.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ShowDtos {
    private ShowDtos() {
    }

    public static class ShowUpsertRequest {
        private String movieId;
        private String theatreId;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private Double ticketPrice;
        private List<String> bookedSeatIds;

        public String getMovieId() {
            return movieId;
        }

        public void setMovieId(String movieId) {
            this.movieId = movieId;
        }

        public String getTheatreId() {
            return theatreId;
        }

        public void setTheatreId(String theatreId) {
            this.theatreId = theatreId;
        }

        public LocalDateTime getStartTime() {
            return startTime;
        }

        public void setStartTime(LocalDateTime startTime) {
            this.startTime = startTime;
        }

        public LocalDateTime getEndTime() {
            return endTime;
        }

        public void setEndTime(LocalDateTime endTime) {
            this.endTime = endTime;
        }

        public Double getTicketPrice() {
            return ticketPrice;
        }

        public void setTicketPrice(Double ticketPrice) {
            this.ticketPrice = ticketPrice;
        }

        public List<String> getBookedSeatIds() {
            return bookedSeatIds;
        }

        public void setBookedSeatIds(List<String> bookedSeatIds) {
            this.bookedSeatIds = bookedSeatIds;
        }
    }

    public static class ShowResponse {
        private String csid;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private Double ticketPrice;
        private List<String> bookedSeatIds;
        private MovieDtos.MovieResponse movie;
        private TheatreDtos.TheatreResponse theatre;

        public String getCsid() {
            return csid;
        }

        public void setCsid(String csid) {
            this.csid = csid;
        }

        public LocalDateTime getStartTime() {
            return startTime;
        }

        public void setStartTime(LocalDateTime startTime) {
            this.startTime = startTime;
        }

        public LocalDateTime getEndTime() {
            return endTime;
        }

        public void setEndTime(LocalDateTime endTime) {
            this.endTime = endTime;
        }

        public Double getTicketPrice() {
            return ticketPrice;
        }

        public void setTicketPrice(Double ticketPrice) {
            this.ticketPrice = ticketPrice;
        }

        public List<String> getBookedSeatIds() {
            return bookedSeatIds;
        }

        public void setBookedSeatIds(List<String> bookedSeatIds) {
            this.bookedSeatIds = bookedSeatIds;
        }

        public MovieDtos.MovieResponse getMovie() {
            return movie;
        }

        public void setMovie(MovieDtos.MovieResponse movie) {
            this.movie = movie;
        }

        public TheatreDtos.TheatreResponse getTheatre() {
            return theatre;
        }

        public void setTheatre(TheatreDtos.TheatreResponse theatre) {
            this.theatre = theatre;
        }
    }
}
