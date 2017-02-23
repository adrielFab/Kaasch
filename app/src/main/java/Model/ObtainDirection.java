package Model;

public class ObtainDirection {

    private static final String DIRECTION_URL_API = "https://maps.googleapis.com/maps/api/directions/json?";
    private static final String GOOGLE_API_KEY = "AIzaSyDnwLF2-WfK8cVZt9OoDYJ9Y8kspXhEHfI";
    private String start;
    private String destination;

    public ObtainDirection(String start, String destination){
        this.start = start;
        this.destination = destination;
    }
}
