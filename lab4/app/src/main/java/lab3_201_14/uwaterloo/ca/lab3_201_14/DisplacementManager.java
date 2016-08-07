package lab3_201_14.uwaterloo.ca.lab3_201_14;

import java.lang.Math;

/**
 * Created by frederick on 6/25/2016.
 */

public class DisplacementManager {

    // Fields
    private static double westToEast = 0;
    private static double southToNorth = 0;
    private static double roomY = 0;
    private static double roomX = 0;
    private static double wTESteps = 0;
    private static double sTNSteps = 0;
    private static double stepAngle = 0;
    private static double offsetStepAngle = 0;

    private static final double stepSize = 0.63; // meters\
    private static final double angleOffset = 25*(Math.PI/180); //offset angle converted to radians


    private static final double south = (7.0/8.0)*Math.PI;
    private static final double sWest = (5.0/8.0)*Math.PI;
    private static final double west = (5.0/8.0)*Math.PI;
    private static final double nWest = (1.0/8.0)*Math.PI;
    private static final double north = (-1.0/8.0)*Math.PI;
    private static final double nEast = (-3.0/8.0)*Math.PI;
    private static final double east = (-5.0/8.0)*Math.PI;
    private static final double sEast = (-7.0/8.0)*Math.PI;

    // Empty constructor
    public DisplacementManager() { }

    // Displacement calculations
    //    One version in terms of meters
    //    Other version in terms of steps
    public static double calculateXComponent(double angle) {
        return stepSize*Math.cos(angle);
    }

    public static double calculateYComponent(double angle) {
        return stepSize*Math.sin(angle);
    }

    public static double calculateXStep(double angle) { return Math.cos(angle); }

    public static double calculateYStep(double angle) { return Math.sin(angle); }

    // Calculated when a step has been detected: stateEnd in StepDetector's inputData method
    public static void calculateDisplacement() {
        southToNorth += calculateXComponent(stepAngle);
        westToEast += calculateYComponent(stepAngle);
        wTESteps += calculateXStep(stepAngle);
        sTNSteps += calculateYStep(stepAngle);
        roomX += calculateXStep(offsetStepAngle);
        roomY += calculateYStep(offsetStepAngle);
    }

    // Getters
    public static double getX() {
        return southToNorth;
    }

    public static double getY() {
        return westToEast;
    }

    public static double getXSteps() { return wTESteps; }

    public static double getYSteps() { return sTNSteps; }

    public static double getRoomX() { return roomX; }

    public static double getRoomY() { return roomY; }

    public static double getStepAngle() { return stepAngle; }

    public static double getOffsetStepAngle() { return offsetStepAngle; }

    // Setter
    public static void setStepAngle(double angle)
    {
        stepAngle = angle;
        offsetStepAngle = angle + (Math.PI)/2 - angleOffset;
    }

    // Resetter
    public static void resetVector() {
        southToNorth = 0;
        westToEast = 0;
        sTNSteps = 0;
        wTESteps = 0;
        stepAngle = 0;
    }

    public static String compassDisplay() {
        if((stepAngle > south) || (stepAngle <= sEast))
            return "S";
        else if(stepAngle > sWest)
            return "SW";
        else if(stepAngle > west)
            return "W";
        else if(stepAngle > nWest)
            return "NW";
        else if(stepAngle > north)
            return "N";
        else if(stepAngle > nEast)
            return "NE";
        else if(stepAngle > east)
            return "E";
        else if(stepAngle > sEast)
            return "SE";
        else
            return "error";
    }
}