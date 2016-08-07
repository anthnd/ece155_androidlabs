package lab3_201_14.uwaterloo.ca.lab3_201_14;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.LinearLayout;


import ca.uwaterloo.mapper.*;

import ca.uwaterloo.sensortoy.LineGraphView;

public class Lab3_201_14 extends AppCompatActivity {
    LineGraphView graph;
    private static Context context;
    MapView mv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Lab3_201_14.context = getApplicationContext();

        setContentView(R.layout.activity_lab3_201_14);
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
        layout.setOrientation(LinearLayout.VERTICAL);

        //ButtonEventListener();

//LIGHT DISPLAY
//        Sensor lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
//        TextView lightDisplay = (TextView) findViewById(R.id.lightLabel);
//        SensorEventListener lightListener = new LightSensorEventListener(lightDisplay);
//        sensorManager.registerListener(lightListener, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);

//ROTATION VECTOR DISPLAY
//        Sensor rotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
//        TextView rotationDisplay= (TextView) findViewById(R.id.rotationLabel);
//        SensorEventListener rotationListener = new RotationSensorEventListener(rotationDisplay);
//        sensorManager.registerListener(rotationListener, rotationSensor, SensorManager.SENSOR_DELAY_NORMAL);


//MAPVIEW
        MapView mv = new MapView(getApplicationContext(), 10000, 1000, 40, 40);
        registerForContextMenu(mv);

        NavigationalMap map = MapLoader.loadMap(getExternalFilesDir(null), "E2-3344.svg");
        mv.setMap(map);
        layout.addView(mv);
        mv.setVisibility(View.VISIBLE);

// MAGNETIC FIELD DISPLAY

//        //LINEGRAPHVIEW IMPLEMENTATION
//        //TODO delete linegraphview implementation: no need
//        graph = new LineGraphView(getApplicationContext(),
//                100,
//                Arrays.asList("x","y","z"));
//        layout.addView(graph);
//        graph.setVisibility(View.INVISIBLE);
//        //END LGV IMPLEMENTATION

        Sensor magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        TextView magneticDisplay= (TextView) findViewById(R.id.magneticLabel);
        SensorEventListener magneticListener = new MagneticFieldSensorEventListener(magneticDisplay);
        sensorManager.registerListener(magneticListener, magneticSensor, SensorManager.SENSOR_DELAY_FASTEST);

//ACCELEROMETER DISPLAY
        Sensor linearAccelerationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        TextView linearAccelerationDisplay = (TextView) findViewById(R.id.linearAccelerationLabel);
        TextView displacementVectorDisplay = (TextView) findViewById(R.id.displacementVector);
        TextView stepsDisplay = (TextView) findViewById(R.id.numberOfSteps);
        SensorEventListener linearAccelerationListener = new LinearAccelerationEventListener(linearAccelerationDisplay, stepsDisplay, displacementVectorDisplay);
        sensorManager.registerListener(linearAccelerationListener, linearAccelerationSensor, SensorManager.SENSOR_DELAY_FASTEST);

//RESET BUTTON
        final Button resetButton = (Button) findViewById(R.id.resetButton);
        if (resetButton != null) {
           resetButton.setOnClickListener(new View  .OnClickListener() {
                public void onClick(View v) {
//                    StepDetector.resetNumberOfSteps();
                    DisplacementManager.resetVector();
                }
            });
        }

//CLEAR BUTTON

        final Button clearButton = (Button) findViewById(R.id.clearButton);
        if (clearButton != null) {
            clearButton.setOnClickListener(new View  .OnClickListener() {
                public void onClick(View v) {
                    StepDetector.resetNumberOfSteps();
//                    DisplacementManager.resetVector();
                }
            });
        }

//REAL ACCELEROMETER
        Sensor realAccelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//        TextView realAccelerometerDisplay = (TextView) findViewById(R.id.realAccelerometerLabel);
        SensorEventListener realAccelerometerListener = new RealAccelerometerSensorEventListener();
        sensorManager.registerListener(realAccelerometerListener, realAccelerometerSensor, SensorManager.SENSOR_DELAY_FASTEST);


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        mv.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item) || mv.onContextItemSelected(item);
    }

//GET CONTEXT
    // Returns the application context
    public static Context getAppContext() {

        return Lab3_201_14.context;

    }

}

