package DirectionModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.mrides.userDomain.User;

public class Preference implements Parcelable{

    private boolean wantsSmoker = false;
    private boolean wantsBoy = false;
    private boolean wantsGirl = false;

    public Preference(boolean wantsBoy, boolean wantsGirl, boolean wantsSmoker){
        this.wantsBoy = wantsBoy;
        this.wantsGirl = wantsGirl;
        this.wantsSmoker = wantsSmoker;
    }

    protected Preference(Parcel in) {
        wantsSmoker = in.readByte() != 0;
        wantsBoy = in.readByte() != 0;
        wantsGirl = in.readByte() != 0;
    }

    public static final Creator<Preference> CREATOR = new Creator<Preference>() {
        @Override
        public Preference createFromParcel(Parcel in) {
            return new Preference(in);
        }

        @Override
        public Preference[] newArray(int size) {
            return new Preference[size];
        }
    };

    public boolean matchPreferences(User user){
        if (!this.wantsSmoker && user.isSmoker()) {
            return false;
        }
        if (!this.wantsBoy && user.isFemale()) {
            return false;
        }
        if (!this.wantsGirl && user.isMale()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (wantsSmoker ? 1 : 0));
        dest.writeByte((byte) (wantsBoy ? 1 : 0));
        dest.writeByte((byte) (wantsGirl ? 1 : 0));
    }
}
