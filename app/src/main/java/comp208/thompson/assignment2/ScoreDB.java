package comp208.thompson.assignment2;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * ScoreDB is a RoomDatabase that contains the Score table.
 * It provides a singleton instance of the database, which can be accessed using the getInstance method.
 */
@Database(entities = {Score.class}, version = 2, exportSchema = false)
public abstract class ScoreDB extends RoomDatabase {
    // Name of the database
    public static final String DB_NAME = "score_db";

    // Singleton instance of the database
    private static ScoreDB INSTANCE = null;

    /**
     * Returns the instance of the database.
     * If the instance is null, it creates a new instance.
     * @param context the application context
     * @return the instance of ScoreDB
     */
    public static ScoreDB getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ScoreDB.class, DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

    /**
     * Returns the DAO for the Score table.
     * @return the ScoreDAO
     */
    public abstract ScoreDAO scoreDAO();
}