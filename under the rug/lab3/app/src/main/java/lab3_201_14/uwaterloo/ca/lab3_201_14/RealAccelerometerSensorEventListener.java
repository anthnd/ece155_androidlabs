package lab3_201_14.uwaterloo.ca.lab3_201_14;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.widget.TextView;

/**
 * Created by frederick on 6/25/2016.
 */
public class RealAccelerometerSensorEventListener extends EventListener {

    static float x;
    static float y;
    static float z;

    TextView output;

    public RealAccelerometerSensorEventListener(TextView outputView){
        output = outputView;
    }

    public void onSensorChanged(SensorEvent se){

        if(se.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            // Storing sensor readings into fields
            x = se.values[0];
            y = se.values[1];
            z = se.values[2];

            output.setText("x: " + String.valueOf(x) + " y: " + String.valueOf(y)+ " z: " + String.valueOf(z));

        }

    }

    public static float[] getAccelerometerReading(){

        float[]  accelerometerReadings = {x, y, z};

        return accelerometerReadings;

    }
}
