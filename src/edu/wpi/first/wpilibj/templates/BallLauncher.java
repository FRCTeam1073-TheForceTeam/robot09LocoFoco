/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;
import edu.wpi.first.wpilibj.*;

/**
 *
 * @author hedenj
 */
public class BallLauncher {
        SmartMotor ballHandleMotor;
	SmartMotor doorMotor;
	Joystick mainJoystick;
	float doorMotorSpeed;
	float ballHandleSpeed;
        boolean lastDoorButton;

    public BallLauncher(SmartMotor ballHandler, SmartMotor door, Joystick right){
        ballHandleMotor = ballHandler;
        doorMotor = door;
        mainJoystick = right;
       	ballHandleSpeed = 0;
	doorMotorSpeed = 0;


    }
    public void OperateBallCollector(){
        //static boolean lastDoorButton = false;

        boolean doorButton = mainJoystick.getRawButton(7);
	    double handelerStick = mainJoystick.getRawAxis(3);

	    if (handelerStick >.9)
	    {
	    	ballHandleSpeed =-1.0f;
	    }
	    else if (handelerStick <-.9){
	 	ballHandleSpeed = 1.0f;
	    }
	    else
	    {
	    	ballHandleSpeed = 0.0f;
	    }
	    if(doorButton)
	    {
	    	doorMotorSpeed= 1.0f; //set door motor speed
	   	}
	    else
	    {
			doorMotorSpeed= 0.0f; //set door motor speed

	    }
	    lastDoorButton = doorButton;

	    ballHandleMotor.Set(ballHandleSpeed);
	    doorMotor.Set(doorMotorSpeed);
        }


        public void GetSpeeds(double door, double handeler){
            door =	doorMotorSpeed;
            handeler =  ballHandleSpeed;

        }

        public void SetSpeeds(double doorMotorSpeed, double ballHandleSpeed){

            ballHandleMotor.Set(ballHandleSpeed);
            doorMotor.Set(doorMotorSpeed);
        }

}
