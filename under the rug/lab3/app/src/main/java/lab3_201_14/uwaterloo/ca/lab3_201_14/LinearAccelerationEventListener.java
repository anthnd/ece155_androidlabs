package lab3_201_14.uwaterloo.ca.lab3_201_14;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;
import android.widget.TextView;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import ca.uwaterloo.sensortoy.LineGraphView;

/**
 * Created by frederick on 5/17/2016.
 */
public class LinearAccelerationEventListener  implements SensorEventListener {

    // FIELDS //
    TextView output;
    TextView outputSteps;
    TextView outputDisplacement;
    //LineGraphView graph;
    Context context;

    FileOutputStream os;
    PrintWriter osw;

    static StepDetector stepCounter;
    static DisplacementManager displacementTing;

    int maxDataPoints = 0;
    int currentAmountOfDataPoints = 0;

    static float x;
    static float y;
    static float z;

    float smoothX;
    float smoothY;
    float smoothZ;
    int smoothing = 10; // Smoothing factor

    private float[] orientation;
    // END OF FIELDS //


    // Constructor
    public LinearAccelerationEventListener(TextView outputView, TextView stepCounterView, TextView displacementView) {

        output = outputView;
//        graph = graphView;

        context = Lab3_201_14.getAppContext();

        // Initializing the output stream and print writer
        try {
            os = new FileOutputStream(new File(context.getExternalFilesDir(null), "external.txt"));
            osw = new PrintWriter(new OutputStreamWriter(os));
        } catch (IOException e) {
            Log.d("CATCHING IOEXCEPTION", "IOException caught!! AHHH!! OMG PANIC!");
        }

        // Step-counter setup
        outputSteps = stepCounterView;
        stepCounter = new StepDetector();

        outputDisplacement = displacementView;
        displacementTing = new DisplacementManager();

    }

    // Initializing sensor max value class
    sensorMaxValue accelerometerMax = new sensorMaxValue();

    public void onAccuracyChanged(Sensor s, int i) {

    }

    public void onSensorChanged(SensorEvent se){

        if(se.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
            // Storing sensor readings into fields
            x = se.values[0];
            y = se.values[1];
            z = se.values[2];

            // Smoothing algorithm
            filterData(x, y, z);

            stepCounter.inputData(smoothY, currentAmountOfDataPoints);

            OrientationSensorEventListener.calcOrientation();
            orientation = OrientationSensorEventListener.getOrientationMatrix();


            // Output data to file
            // A safety precaution to make sure it doesn't output data forever
            if (currentAmountOfDataPoints < maxDataPoints) {
                // Printing to file
                osw.println(x + " " + y + " " + z + " " + smoothX + " " + smoothY + " " + smoothZ + " " + orientation[0] + " " + orientation[1] + " " + orientation[2] + " " + StepDetector.numberOfSteps);
            }
            // Close file after desired number of data points is reached
            else if (currentAmountOfDataPoints == maxDataPoints) {
                osw.close();
            }
            currentAmountOfDataPoints++;

            // Screen output
            //output.setText("Accelerometer: " + String.valueOf(x + " m/s^2, " + y + " m/s^2, " + z + " m/s^2") + "\n" + String.valueOf(accelerometerMax.getMaxString()) + "\n" + "Current number of data points: " + currentAmountOfDataPoints + "\n");
            //output.setText("Current number of data points: " + currentAmountOfDataPoints + "\n" + String.valueOf(orientation[0]) + " " + String.valueOf(orientation[1]) + " " + String.valueOf(orientation[2]));
            outputSteps.setText("# steps: " + stepCounter.getNumberOfSteps() + "\n");
            outputDisplacement.setText("(" + DisplacementManager.getX() + ", " + DisplacementManager.getY() + ")" + "\nStep Angle: " + DisplacementManager.getStepAngle());

            // Compare current sensor readings to current max
            accelerometerMax.calcMaxX(x);
            accelerometerMax.calcMaxY(y);
            accelerometerMax.calcMaxZ(z);
            // se.values[0] is x-axis, 1 y, 2 z; units m/s^2

            // Add point to graph
//            graph.addPoint(se.values);

        }

    }

    public static float[] getLinearAccelerationReading(){

        float[] linearAccelerationReadings = {x, y, z};

        return linearAccelerationReadings;

    }

    public void filterData(float x, float y, float z) {
        if (currentAmountOfDataPoints == 0) {
            smoothX = x;
            smoothY = y;
            smoothZ = z;
        } else {
            smoothX += (x-smoothX) / smoothing;
            smoothY += (y-smoothY) / smoothing;
            smoothZ += (z-smoothZ) / smoothing;
        }
    }

}
