package lab3_201_14.uwaterloo.ca.lab3_201_14;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.TextView;

import ca.uwaterloo.sensortoy.LineGraphView;

/**
 * Created by frederick on 5/15/2016.
 */

public class MagneticFieldSensorEventListener implements SensorEventListener {

    TextView output;
    LineGraphView graph;

    static float x;
    static float y;
    static float z;


    private float[] orientation;
    int currentAmountOfDataPoints = 0;
    public MagneticFieldSensorEventListener(TextView outputView) {
        output = outputView;
    }



    sensorMaxValue magneticMax = new sensorMaxValue();

    public void onAccuracyChanged(Sensor s, int i) {
    }

    public void onSensorChanged(SensorEvent se) {
        if(se.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {

            x = se.values[0];
            y = se.values[1];
            z = se.values[2];

//            magneticMax.calcMaxX(x);
//            magneticMax.calcMaxY(y);
//            magneticMax.calcMaxZ(z);


            OrientationSensorEventListener.calcOrientation();
            orientation = OrientationSensorEventListener.getOrientationMatrix();

            if(currentAmountOfDataPoints % 10 == 0) {
                DisplacementManager.setStepAngle(orientation[0]);
            }

            output.setText("Orientation: " + String.format("%.2f", orientation[0]));
            //output.setText(String.valueOf("Magnetic Field: " + x + "μT, " + y + "μT, " + z + "μT") + "\n" + String.valueOf(magneticMax.getMaxString()) + "μT \n");
            // se.values[0] is x-axis, 1 y, 2 z; units microTesla

        }
        //graph.addPoint(orientation);
    }

    public static float[] getMagneticSensorReading(){

        float[] magneticSensorReadings = {x, y, z};

        return magneticSensorReadings;

    }
}
