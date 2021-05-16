package rollingcubes.results;

import com.google.inject.persist.Transactional;
import util.jpa.GenericJpaDao;

import java.util.List;

/**
 * DAO for the {@link GameResult} class entity.
 */
public class GameResultDao extends GenericJpaDao<GameResult> {

    public GameResultDao() {
        super(GameResult.class);
    }

    /**
     * The best {@code n} results according to how much time it takes to solve the game.
     * The less time to solve the game, the better position the player gets.
     *
     * @param n the limit of the rows presented in the result table
     * @return the ordered list {@code n} of the best results
     * The list ordered by the game's duration.
     */
    @Transactional
    public List<GameResult> findBest(int n) {
        return entityManager.createQuery("SELECT r FROM GameResult r WHERE r.solved = true ORDER BY r.duration ASC, r.created DESC", GameResult.class)
                .setMaxResults(n)
                .getResultList();
    }

}
