package lab3_201_14.uwaterloo.ca.lab3_201_14;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.TextView;

/**
 * Created by frederick on 5/11/2016.
 */
public class LightSensorEventListener implements SensorEventListener {
   TextView output;
    float intensity;

    public LightSensorEventListener(TextView outputView) {
        output = outputView;
    }

    public void onAccuracyChanged(Sensor s, int i) {
    }

    public void onSensorChanged(SensorEvent se) {
        intensity = se.values[0];
        if(se.sensor.getType() == Sensor.TYPE_LIGHT) {
           output.setText(" ");
            output.setText("Light: " + String.valueOf(intensity) + " lx \n");
            //TODO se.values[0] as an indiviudal variable??--

            //basically, this needs to output a nice hard-coded string which gives the value.  indicate units!
        }
    }

}