package com.cinema.cineshow.model.movie;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "shows")
public class Show {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long showId;
    private String startTime, endTime;

    private Long ticketPrice = 300L;

    // Before with applying @JsonIgnore for movie and theatre, they weren't getting linked
    // while creating a post request for creating a show instance. It was linking fine on
    // removing @JsonIgnore for both as done below.
    // Also facing linking issue with (cascade = CascadeType.ALL) ... Refer IMPORTANT section at bottom
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)  // linking fine
//            (fetch = FetchType.EAGER)  // linking fine
//            (cascade = CascadeType.ALL, fetch = FetchType.EAGER)  // no linking
//    @JsonIgnore
    private Movie movie;
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)  // linking fine
//            (fetch = FetchType.EAGER)  // linking fine
//            (cascade = CascadeType.ALL, fetch = FetchType.EAGER)  // no linking
//    @JsonIgnore
    private Theatre theatre;

//    @JsonIgnore
    private boolean[][] isSeatBooked = new boolean[10][10];

    public Show() {
    }

    public Long getShowId() {
        return showId;
    }

    public void setShowId(Long showId) {
        this.showId = showId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public boolean[][] getIsSeatBooked() {
        return isSeatBooked;
    }

    public void setIsSeatBooked(boolean[][] isSeatBooked) {
        this.isSeatBooked = isSeatBooked;
    }


    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Theatre getTheatre() {
        return theatre;
    }

    public void setTheatre(Theatre theatre) {
        this.theatre = theatre;
    }

    public Long getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(Long ticketPrice) {
        this.ticketPrice = ticketPrice;
    }
}

/*
    ******************************* IMPORTANT ***************************************

Here,
childClass: Transaction == Show
parentClass: Account == Movie/Theatre

Why? By saying "cascade ALL" on the child entity Transaction you require that every
DB operation gets propagated to the parent entity Account. If you then do
persist(transaction), persist(account) will be invoked as well.

But only transient (new) entities may be passed to persist (Transaction in this case).
The detached (or other non-transient state) ones may not (Account in this case, as it's
already in DB).

Therefore you get the exception "detached entity passed to persist". The Account entity
is meant! Not the Transaction you call persist on.

You generally don't want to propagate from child to parent. Unfortunately there are
many code examples in books (even in good ones) and through the net, which do exactly that.
I don't know, why... Perhaps sometimes simply copied over and over without much thinking...

Guess what happens if you call remove(transaction) still having "cascade ALL" in that
@ManyToOne? The account (btw, with all other transactions!) will be deleted from the
DB as well. But that wasn't your intention, was it?

 */
