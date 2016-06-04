package lab2_201_14.uwaterloo.ca.lab2_201_14;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;
import android.widget.TextView;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import ca.uwaterloo.sensortoy.LineGraphView;

/**
 * Created by frederick on 5/17/2016.
 */
public class AccelerometerEventListener  implements SensorEventListener {

    // FIELDS //
    TextView output;
    LineGraphView graph;

    PrintWriter outputStream = null;
    boolean written = false;

    float x;
    float y;
    float z;
    // END OF FIELDS //

    // Constructor
    public AccelerometerEventListener(TextView outputView,LineGraphView graphView) {

        output = outputView;
        graph = graphView;

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

            // Screen output
            output.setText("Accelerometer: " + String.valueOf(x + " m/s^2, " + y + " m/s^2, " + z + " m/s^2") + "\n" + String.valueOf(accelerometerMax.getMaxString()) + "\n");

            // Compare current sensor readings to current max
            accelerometerMax.calcMaxX(x);
            accelerometerMax.calcMaxY(y);
            accelerometerMax.calcMaxZ(z);
            // se.values[0] is x-axis, 1 y, 2 z; units m/s^2

            // Add point to graph
            graph.addPoint(se.values);

            try {
                if (!written) {
                    printData();
                    written = true;
                }
            } catch (IOException ex) {
                Log.d("CATCHING IOEXCEPTION", "IOException caught!");
            }


        }

    }

    // Print data to .txt file
    public void printData() throws IOException {

        try {
            outputStream =  new PrintWriter(new FileWriter("accelerometer_data.txt"));
            outputStream.println("Hello.");
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }

    }
//JUNE 1
//    Context context = Context.MODE_PRIVATE;
//
//    File file = new File(context.getFilesDir(),file);
//    PrintWriter printWriter = new PrintWriter("file.txt");
//    printWriter.append("Hello World" );
//    printWriter.close();
    //File file = new File(context.getFilesDir(), filename);
    ;

}
