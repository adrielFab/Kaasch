package DirectionModel;

import java.util.HashMap;
import java.util.Map;



public interface IPersistanceObject {

    public Map<String,String> parameters = new HashMap<>();

    public Map<String,String> getParameters();
    public Map<String,String> setParameters();

}
