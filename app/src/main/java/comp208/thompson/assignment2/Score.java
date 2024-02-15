package comp208.thompson.assignment2;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Score is a Room entity that represents a score in the game.
 * It implements Parcelable to allow Score objects to be passed between activities.
 * It also implements Comparable to allow sorting of Score objects.
 */
@Entity(tableName = "score", indices = {@Index(value = {"playerName"}, unique = true)})
public class Score implements Parcelable, Comparable<Score> {

    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "playerName")
    String playerName;

    @ColumnInfo(name = "score")
    int score;

    /**
     * Constructs a new Score with the given player name and score.
     * @param playerName the player's name
     * @param score the player's score
     */
    public Score(String playerName, int score) {
        this.playerName = playerName;
        this.score = score;
    }

    public String getPlayerName() {
        return playerName;
    }
    public int getScore() {
        return score;
    }
    public int getId() {
        return id;
    }
    public void setId(int idCounter) {
    }
    /** Added by copilot to implement comparable
     * Compares this Score to another Score.
     * Scores are compared based on their score in descending order.
     * @param other the other Score
     * @return a negative integer, zero, or a positive integer as this Score's score is less than, equal to, or greater than the other Score's score
     */
    @Override
    public int compareTo(Score other) {
        return Integer.compare(other.score, this.score);
    }

    /** Added by copilot to implement Parcelable
     * Parcelable.Creator for creating Score objects from Parcels.
     */
    public static final Parcelable.Creator<Score> CREATOR = new Parcelable.Creator<Score>() {
        public Score createFromParcel(Parcel in) {
            return new Score(in);
        }

        public Score[] newArray(int size) {
            return new Score[size];
        }
    };

    /** Added by copilot to implement Parcelable
     * Constructs a new Score from a Parcel.
     * @param in the Parcel
     */
    private Score(Parcel in) {
        id = in.readInt();
        playerName = in.readString();
        score = in.readInt();
    }

    /** Added by copilot to implement Parcelable
     * Describes the kinds of special objects contained in this Parcelable's marshalled representation.
     * @return a bitmask indicating the set of special object types marshalled by this Parcelable
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /** Added by copilot to implement Parcelable
     * Writes this Score to a Parcel.
     * @param dest the Parcel
     * @param flags additional flags about how the object should be written
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(playerName);
        dest.writeInt(score);
    }
}