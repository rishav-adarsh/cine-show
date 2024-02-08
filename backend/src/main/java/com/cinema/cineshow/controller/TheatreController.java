package com.cinema.cineshow.controller;

import com.cinema.cineshow.model.movie.Theatre;
import com.cinema.cineshow.service.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/theatre")
@CrossOrigin("*")
public class TheatreController {
    @Autowired
    TheatreService theatreService;

    @PostMapping("")
    public Theatre createTheatre(@RequestBody Theatre theatre) throws Exception {
        return  theatreService.createTheatre(theatre);
    }

    @GetMapping("/{theatreId}")
    public Optional<Theatre> getTheatre(@PathVariable("theatreId") Long theatreId) {
        return theatreService.getTheatre(theatreId);
    }

    @GetMapping("")
    public List<Theatre> getAllTheatres() {
        return theatreService.getAllTheatres();
    }

    @PutMapping("")
    public Theatre updateTheatre(@RequestBody Theatre theatre) {
        return theatreService.updateTheatre(theatre);
    }

    @DeleteMapping("/{theatreId}")
    public void deleteTheatre(@PathVariable("theatreId") Long theatreId) {
        theatreService.deleteTheatre(theatreId);
    }
}
