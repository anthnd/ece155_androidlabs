package lab2_201_14.uwaterloo.ca.lab2_201_14;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Anthony on 2016-06-06.
 */

public class WalkingData {

    static double[] xAccel = new double[2500];
    static double[] yAccel = new double[2500];
    static double[] zAccel = new double[2500];
    static double[] filteredxAccel = new double[2500];
    static double[] filteredyAccel = new double[2500];
    static double[] filteredzAccel = new double[2500];

    private static final int stateInitial = 0;
    private static final int statePeak = 1;
    private static final int stateDrop = 2;
    private static final int stateReturn = 3;
    private static int currentState = stateInitial;

    static int numberOfSteps = 0;

    public static void main(String[] args) throws IOException {

        // Initializing input and output
        BufferedReader inputStream = null;
        //PrintWriter outputStream = null;

        try {
            inputStream = new BufferedReader(new FileReader("walking_data.txt"));

            String line = inputStream.readLine();
            int i = 0;

            while((line = inputStream.readLine()) != null) {
                String[] tempArray = line.split(" "); // Separate data

                // Store data
                xAccel[i] = Double.parseDouble(tempArray[0]);
                yAccel[i] = Double.parseDouble(tempArray[1]);
                zAccel[i] = Double.parseDouble(tempArray[2]);
                filteredxAccel[i] = Double.parseDouble(tempArray[3]);
                filteredyAccel[i] = Double.parseDouble(tempArray[4]);
                filteredzAccel[i] = Double.parseDouble(tempArray[5]);

                i++; // Increment
            }



        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }

        for (int z = 0; z < 2500; z++) {
            inputData(filteredyAccel[z], z);
        }

        System.out.println(numberOfSteps);

    }

    static public void inputData(double y, int index) {

        switch(currentState) {

            case stateInitial:
                if (y > -0.5 && y < 0) {
                    System.out.println("Initial to Peak: " + y + " at " + index);
                    currentState = statePeak;
                }
                break;
            case statePeak:
                if (y > 0.37 && y < 1.25) {
                    System.out.println("Peak to Drop: " + y + " at " + index);
                    currentState = stateDrop;
                }
                break;
            case stateDrop:
                if (y < -0.75 && y > -1.25) {
                    // currentState = stateReturn;
                    currentState = stateInitial;
                    numberOfSteps++;
                    System.out.println("Step complete: " + y + " at " + index);
                }
                break;
            case stateReturn:
                if (y > -0.5 && y < 0) {
                    currentState = stateInitial;
                    numberOfSteps++;
                    System.out.println("Step at " + index);
                }
                break;
            default:
                break;

        }

    }

}