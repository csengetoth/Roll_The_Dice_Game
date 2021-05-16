package rollingcubes.results;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Duration;
import java.time.ZonedDateTime;

/**
 * Class for printing the player's results.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class GameResult {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * Player's name.
     */
    @Column(nullable = false)
    private String player;

    /**
     * The player solved the game or not.
     */
    private boolean solved;

    /**
     * The number of steps to solve the game by the player.
     */
    private int steps;

    /**
     * Tells, how much time it takes to solve the game.
     */
    @Column(nullable = false)
    private Duration duration;

    /**
     * The date, when the game was played.
     */
    @Column(nullable = false)
    private ZonedDateTime created;

    @PrePersist
    protected void onPersist() {
        created = ZonedDateTime.now();
    }

}
