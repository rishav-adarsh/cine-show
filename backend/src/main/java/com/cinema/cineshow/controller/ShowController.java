package com.cinema.cineshow.controller;

import com.cinema.cineshow.model.movie.Movie;
import com.cinema.cineshow.model.movie.Show;
import com.cinema.cineshow.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/show")
@CrossOrigin("*")
public class ShowController {
    @Autowired
    ShowService showService;

    @PostMapping("")
    public Show createShow(@RequestBody Show show) throws Exception {
        return  showService.createShow(show);
    }

    @GetMapping("/{showId}")
    public Optional<Show> getShow(@PathVariable("showId") Long showId) {
        return showService.getShow(showId);
    }

    @GetMapping("")
    public List<Show> getAllShows() { return showService.getAllShows(); }

    @GetMapping("/movie/{movieId}")
    public List<Show> getShowsByMovieId(@PathVariable("movieId") Long movieId) {
        return showService.getShowsByMovieId(movieId);
    }

    @PutMapping("")
    public Show updateShow(@RequestBody Show show) {
        return showService.updateShow(show);
    }

    @DeleteMapping("/{showId}")
    public void deleteShow(@PathVariable("showId") Long showId) {
        showService.deleteShow(showId);
    }

}
