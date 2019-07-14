package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.validator.EventValidator;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer rank;

    @OneToOne
    @Cascade(value = org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private Event event;

    @OneToOne
    @Cascade(value = org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private User user;

    private Score() {

    }

    private Score(User user, Event event, Integer rank) {
        this.user = user;
        this.event = event;
        this.rank = rank;
    }

    public static Score create(Event event, User user, Integer rank) {
        validateScore(rank);
        return new Score(user, event, rank);
    }

    public static void validateScore(Integer rank) {
        if (rank == 0)
            throw invalidScore();
    }

    private static RuntimeException invalidScore() {
        return new RuntimeException(EventValidator.ERROR_THE_GIVEN_SCORE_MUST_BE_GREATER_THAN_ZERO);
    }

    public Integer rank() {
        return rank;
    }

    public void changeRank(Integer rank) {
        this.rank = rank;
    }
}
