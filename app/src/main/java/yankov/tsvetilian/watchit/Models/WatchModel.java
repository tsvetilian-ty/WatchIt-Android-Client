package yankov.tsvetilian.watchit.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

@Entity(tableName = "watch")
public class WatchModel implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    // Presentable name of the file
    @ColumnInfo(name = "presentable_name")
    private String presentableName;

    // The name of the file with extension
    @ColumnInfo(name = "file_name")
    private String fileName;

    // IP on the local network to access the sender
    @ColumnInfo(name = "ip")
    private String sourceIp;

    // The name of the machine that initiated the stream
    @ColumnInfo(name = "source_name")
    private String sourceName;

    // The AuthKey to access the file (The key is valid 30 days)
    @ColumnInfo(name = "auth_key")
    private String authKey;

    // The path to the file
    private String path;

    private String poster;

    // Description of the Tv show or the movie
    private String description;

    // The duration of the show or the movie
    private String duration;

    @ColumnInfo(name = "watch_later")
    private boolean watchLater;

    private boolean watched;

    @Nullable
    private String season;

    public WatchModel(String presentableName, String fileName, String sourceIp,
                      String sourceName, String authKey, String path,
                      String poster, String description, String duration,
                      boolean watchLater, boolean watched, @Nullable String season) {
        this.presentableName = presentableName;
        this.fileName = fileName;
        this.sourceIp = sourceIp;
        this.sourceName = sourceName;
        this.authKey = authKey;
        this.path = path;
        this.poster = poster;
        this.description = description;
        this.duration = duration;
        this.watchLater = watchLater;
        this.watched = watched;
        this.season = season;
    }

    protected WatchModel(Parcel in) {
        id = in.readInt();
        presentableName = in.readString();
        fileName = in.readString();
        sourceIp = in.readString();
        sourceName = in.readString();
        authKey = in.readString();
        path = in.readString();
        poster = in.readString();
        description = in.readString();
        duration = in.readString();
        watchLater = in.readByte() != 0;
        watched = in.readByte() != 0;
        season = in.readString();
    }

    public static final Creator<WatchModel> CREATOR = new Creator<WatchModel>() {
        @Override
        public WatchModel createFromParcel(Parcel in) {
            return new WatchModel(in);
        }

        @Override
        public WatchModel[] newArray(int size) {
            return new WatchModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPresentableName() {
        return presentableName;
    }

    public void setPresentableName(String presentableName) {
        this.presentableName = presentableName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSourceIp() {
        return sourceIp;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public boolean isWatchLater() {
        return watchLater;
    }

    public void setWatchLater(boolean watchLater) {
        this.watchLater = watchLater;
    }

    public boolean isWatched() {
        return watched;
    }

    public void setWatched(boolean watched) {
        this.watched = watched;
    }

    @Nullable
    public String getSeason() {
        return season;
    }

    public void setSeason(@Nullable String season) {
        this.season = season;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(presentableName);
        parcel.writeString(fileName);
        parcel.writeString(sourceIp);
        parcel.writeString(sourceName);
        parcel.writeString(authKey);
        parcel.writeString(path);
        parcel.writeString(poster);
        parcel.writeString(description);
        parcel.writeString(duration);
        parcel.writeByte((byte) (watchLater ? 1 : 0));
        parcel.writeByte((byte) (watched ? 1 : 0));
        parcel.writeString(season);
    }
}
