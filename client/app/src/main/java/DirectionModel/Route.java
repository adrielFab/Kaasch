/*
* Class Route
*
* 03/04/17
*/
package DirectionModel;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class Route {

    public Distance getDistance() {

        return distance;
    }

    public void setDistance(Distance distance) {

        this.distance = distance;
    }

    public List<LatLng> getPoints() {

        return points;
    }

    public void setPoints(List<LatLng> points) {

        this.points = points;
    }

    public String getStartAddress() {

        return startAddress;
    }

    public void setStartAddress(String startAddress) {

        this.startAddress = startAddress;
    }

    public LatLng getStartLocation() {

        return startLocation;
    }

    public void setStartLocation(LatLng startLocation) {

        this.startLocation = startLocation;
    }

    public LatLng getEndLocation() {

        return endLocation;
    }

    public void setEndLocation(LatLng endLocation) {

        this.endLocation = endLocation;
    }

    public String getEndAddress() {

        return endAddress;
    }

    public void setEndAddress(String endAddress) {

        this.endAddress = endAddress;
    }

    public Duration getDuration() {

        return duration;
    }

    public void setDuration(Duration duration) {

        this.duration = duration;
    }

    private Distance distance;
    private Duration duration;
    private String endAddress;
    private LatLng endLocation;
    private String startAddress;
    private LatLng startLocation;

    private List<LatLng> points;
}
