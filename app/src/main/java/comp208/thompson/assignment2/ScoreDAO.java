package comp208.thompson.assignment2;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

/**
 * ScoreDAO is an interface that defines the methods for accessing the Score table in the database.
 * It is annotated with @Dao to indicate that it's a Room Data Access Object.
 */
@Dao
public interface ScoreDAO {
    /**
     * Returns all scores in the Score table.
     * @return a list of all scores
     */
    @Query("SELECT * FROM score")
    List<Score> findAllScores();

    /**
     * Returns the score with the given ID.
     * @param id the ID of the score
     * @return the score with the given ID
     */
    @Query("SELECT * FROM score WHERE id=:id")
    Score findScoreById(int id);

    /**
     * Deletes all scores in the Score table.
     */
    @Query("DELETE FROM score")
    void deleteAll();

    /**
     * Inserts the given score into the Score table, or updates it if a score with the same ID already exists.
     * @param score the score to insert or update
     */
    @Insert(onConflict= OnConflictStrategy.REPLACE)
    void insertOrUpdate (Score score);
}