package DirectionModel;

import java.util.List;

public interface ObtainDirectionListener {
    void startObtainDirection();
    void successObtainDirection(List<Route> route);
}
