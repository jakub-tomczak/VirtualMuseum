package Light;


import java.util.*;
import Models.*;

public class LightHandler {
    private ArrayList<Light> lightArray=new ArrayList<Light>();


    public void addLight(Light light)
    {
        lightArray.add(light);
    }

    public void turnOnLight(Model m)
    {
        for(Light l : lightArray)
        {
            m.loadLight(l);
        }
    }
    public List getLightArray() {
        return lightArray;
    }
}
