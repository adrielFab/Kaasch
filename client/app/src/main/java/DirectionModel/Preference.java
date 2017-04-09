package DirectionModel;

public class Preference {

    private String wantsSmoker = "";
    private String wantsBoy = "";
    private String wantsGirl = "";

    public Preference(String wantsBoy, String wantsGirl, String wantsSmoker){
        this.wantsBoy = wantsBoy;
        this.wantsGirl = wantsGirl;
        this.wantsSmoker = wantsSmoker;
    }

    public String getWantsSmoker() {
        return this.wantsSmoker;
    }

    public String getWantsBoy() {
        return this.wantsBoy;
    }

    public String getWantsGirl() {
        return this.wantsGirl;
    }
}
