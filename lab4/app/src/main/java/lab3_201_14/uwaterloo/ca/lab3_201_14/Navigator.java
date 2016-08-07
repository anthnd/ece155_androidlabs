package lab3_201_14.uwaterloo.ca.lab3_201_14;

import android.graphics.PointF;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ca.uwaterloo.mapper.MapView;
import ca.uwaterloo.mapper.NavigationalMap;

/**
 * Created by Anthony on 2016-07-07.
 */

public class Navigator {

    public static TextView outputOrigin;
    public static TextView outputDestination;

    private static PointF origin;
    private static PointF destination;

    private static NavigationalMap navMap;
    private static MapView mapView;

    public Navigator(NavigationalMap nm, MapView mv)
    {
        navMap = nm;
        mapView = mv;
    }

    public static void determineRoute(PointF orig, PointF dest)
    {
        List<PointF> route = new ArrayList<PointF>();
        if(navMap.calculateIntersections(orig, dest).size() == 0 && orig.x != 0 && dest.x != 0) {
            route.add(orig);
            route.add(dest);
        }
        mapView.setUserPath(route);
    }

    public static void setOrigin(PointF orig)
    {
        origin = orig;
    }

    public static void setDestination(PointF dest)
    {
        destination = dest;
    }

    public static PointF getOrigin()
    {
        return origin;
    }

    public static PointF getDestination()
    {
        return destination;
    }


}
