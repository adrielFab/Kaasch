/*
* Class Route
*
* 03/04/17
*/
package DirectionModel;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;
import java.util.List;

public class Route {

    private int id;
    private String title;
    private Date date; // Date variable include date and time
    private String endAddress;
    private LatLng endLocation;
    private String startAddress;
    private LatLng startLocation;
    private List<LatLng> points;
    private String userType;
    private String routeStatus;
    private Preference preference;
    private String durationText;
    private int durationValue;
    private String distanceText;
    private int distanceValue;

    public Route () {

    }

    /**
     * Mutator method for preference
     */
    public void setPreference(Preference preference) {

        this.preference = preference;
    }

    /**
     * Mutator method for preference
     */
    public Preference getPreference() {

        return this.preference;
    }


    /**
     * Mutator method for title
     */
    public void setTitle(String title) {

        this.title = title;
    }

    /**
     * Accessor method for title
     * @return String This returns the title
     */
    public String getTitle() {

        return this.title;
    }

    /**
     * Accessor method for date
     */
    public void setDate(Date inDate) {

        this.date = inDate;
    }

    /**
     * Accessor method for date
     * @return Date This returns the date
     */
    public Date getDate() {

        return this.date;
    }

    /**
     * Accessor method for id
     * @return int This returns the id
     */
    public int getId() {

        return id;
    }

    /**
     * Mutator method for id
     * @param id
     */
    public void setId(int id) {

        this.id = id;
    }

    /**
     * Accessor method for endAddress
     * @return String This returns the address of the destination
     */
    public String getEndAddress() {

        return endAddress;
    }

    /**
     * Mutator method for endAddress
     * @param endAddress
     */
    public void setEndAddress(String endAddress) {

        this.endAddress = endAddress;
    }

    /**
     * Accessor method for endLocation
     * @return LatLng This returns the latitude and longitude coordinate of the destination
     */
    public LatLng getEndLocation() {

        return endLocation;
    }

    /**
     * Mutator method for endLocation
     * @param endLocation
     */
    public void setEndLocation(LatLng endLocation) {

        this.endLocation = endLocation;
    }

    /**
     * Accessor method for startAddress
     * @return String This returns the address of the source
     */
    public String getStartAddress() {

        return startAddress;
    }

    /**
     * Mutator method for startAddress
     * @param startAddress
     */
    public void setStartAddress(String startAddress) {

        this.startAddress = startAddress;
    }

    /**
     * Accessor method for startLocation
     * @return LatLng This returns the latitude and longitude coordinate of the source
     */
    public LatLng getStartLocation() {

        return startLocation;
    }

    public String getStartLocationAsSting(){
        String locationAsString = startLocation.toString().replace("lat/lng: (", "");
        locationAsString = locationAsString.replace(")", "");
        return locationAsString;
    }

    public String getEndLocationAsSting(){
        String locationAsString = endLocation.toString().replace("lat/lng: (", "");
        locationAsString = locationAsString.replace(")", "");
        return locationAsString;
    }

    /**
     * Mutator method for startLocation
     * @param startLocation
     */
    public void setStartLocation(LatLng startLocation) {

        this.startLocation = startLocation;
    }

    /**
     * Accessor method for points
     * @return List<LatLng> This returns a list of latitude and longitude coordinates
     * of the route
     */
    public List<LatLng> getPoints() {

        return points;
    }

    /**
     * Mutator method for points
     * @param points
     */
    public void setPoints(List<LatLng> points) {

        this.points = points;
    }

    /**
     * Getter method for the route type
     * @return
     */
    public String getUserType() {
        return userType;
    }

    /**
     * Mutator method for the route type
     * @param userType
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }

    /**
     * Getter method for the route status
     * @return
     */
    public String getRouteStatus() {
        return routeStatus;
    }

    /**
     * Mutator method for the route status
     * @param routeStatus
     */
    public void setRouteStatus(String routeStatus) {
        this.routeStatus = routeStatus;
    }

    public String getDistanceText() {
        return distanceText;
    }

    public void setDistanceText(String distanceText) {
        this.distanceText = distanceText;
    }

    public String getDurationText() {
        return durationText;
    }

    public void setDurationText(String durationText) {
        this.durationText = durationText;
    }

    public int getDurationValue() {
        return durationValue;
    }

    public void setDurationValue(int durationValue) {
        this.durationValue = durationValue;
    }

    public int getDistanceValue() {
        return distanceValue;
    }

    public void setDistanceValue(int distanceValue) {
        this.distanceValue = distanceValue;
    }

}
