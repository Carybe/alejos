/*
*  MAC0318 - Projeto 1
*
* 	Nomes			Nºs USP
* Carybé Gonçalves Silva	8033961
* Gabriel Baptista              8941300
*
*/
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.ColorSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.MotorPort;

import lejos.util.Delay;

import lejos.robotics.Color;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

//import lejos.util.Delay;
//import lejos.nxt.Button;

public class ColorBox {

    int target, notTarget, speed;
    double  wheelDiam, width;
    
    ColorSensor color;
    
    UltrasonicSensor head;
    UltrasonicSensor cube;
    
    NXTRegulatedMotor claw;
    DifferentialPilot pilot;


    void blockDetour(){
        /*pilot.travel(-80);
        pilot.rotate(-30);

        pilot.setRotateSpeed(60);
        pilot.travel(250);
        pilot.rotate(60);
        pilot.setRotateSpeed(120);
        
        pilot.travel(250);
        pilot.rotate(-30);
        */
    }

    void obstacleDetour(){
        /*
        pilot.travel(-80);
        pilot.rotate(-65);
        pilot.travel(450);

        pilot.rotate(65);
        pilot.travel(400);

        pilot.rotate(65);
        pilot.travel(450);

        pilot.rotate(-65);
        pilot.travel(500);
        */
    }

    public void run(){
        int supVis, actuColor;

        claw.setSpeed(150);

        pilot.setTravelSpeed(120);
        pilot.setRotateSpeed(120);

        supVis = head.getDistance(); 
        while (supVis > 23) {
            supVis = head.getDistance();
            actuColor = color.getColorID();

            if(actuColor == target){
                claw.rotateTo(45);
                claw.setSpeed(1000);
                claw.rotateTo(-105);
            }

            else if(actuColor == notTarget){
                pilot.stop();
                blockDetour();
            }
            //pilot.steer(0);
        }

        pilot.stop();
        obstacleDetour();
        pilot.stop();
        claw.rotateTo(0);
    }

    public static void main(String[] args) {
        ColorBox robot = new ColorBox();
        
        robot.head = new UltrasonicSensor(SensorPort.S2);
        robot.color = new ColorSensor(SensorPort.S3);

        robot.target = Color.RED;
        robot.notTarget = Color.BLUE;
        robot.speed = 10;

        robot.wheelDiam = 56f;
        robot.width = 112f;
        robot.pilot = new DifferentialPilot(robot.wheelDiam, robot.width,
                                            new NXTRegulatedMotor(MotorPort.B),
                                            new NXTRegulatedMotor(MotorPort.A)); 
        robot.claw = new NXTRegulatedMotor(MotorPort.C);

        robot.run();
    }
}