package lab3_201_14.uwaterloo.ca.lab3_201_14;

import java.lang.Math;

/**
 * Created by frederick on 6/25/2016.
 */

public class DisplacementManager {

    private static double westToEast = 0;
    private static double southToNorth = 0;
    private static double wTESteps = 0;
    private static double sTNSteps = 0;

    private static double stepAngle = 0;
    private static double stepSize = 0.63; // meters

    public DisplacementManager() { }

    public static double calculateXComponent(double angle) {
        return stepSize*Math.cos(angle);
    }

    public static double calculateYComponent(double angle) {
        return stepSize*Math.sin(angle);
    }

    public static double calculateXStep(double angle) { return Math.cos(angle); }

    public static double calculateYStep(double angle) { return Math.sin(angle); }

    public static void calculateDisplacement() {
        //stepAngle = OrientationSensorEventListener.getAzimuth();
        southToNorth += calculateXComponent(stepAngle);
        westToEast += calculateYComponent(stepAngle);
        wTESteps += calculateXStep(stepAngle);
        sTNSteps += calculateYStep(stepAngle);
    }

    public static double getX() {
        return southToNorth;
    }

    public static double getY() {
        return westToEast;
    }

    public static double getXSteps() { return wTESteps; }

    public static double getYSteps() { return sTNSteps; }

    public static double getStepAngle() { return stepAngle; }

    public static void setStepAngle(double angle) { stepAngle = angle; }

    static public void resetVector() {
        southToNorth = 0;
        westToEast = 0;
        sTNSteps = 0;
        wTESteps = 0;
        stepAngle = 0;
    }

}