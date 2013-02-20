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
public class SmartMotor{
        final static int MaxSmartMotors = 8;
        Jaguar jaguar;
        static SmartMotor motors[] = new SmartMotor[MaxSmartMotors];
        static boolean isMotor[] = new boolean[MaxSmartMotors];
        SmartEncoder smartEncoder;

	boolean isReversed;
	double accelTimeSeconds;
	
	static int motorCount;
	double targetSpeed;
	double lastSpeed;

        private final static Object syncRoot = new Object();
	static boolean doCollectData;


        public static void MotorTask(){

		

		for(int i = 0; i < MaxSmartMotors; i++){
                        // Service each possible motor
                        if(isMotor[i])
                            motors[i].DriveIt();									// Go service this motor!!!
		}
		//mainRobot->ServiceAccelerometers();
		//mainRobot->DrivePeriodicService();

		//if(SmartMotor::DoCollectData() && mainRobot)
			// mainRobot->PrintData();

        }


        public SmartMotor(final int channel, final SmartEncoder smartEnc, final boolean reverse, final double accelTime)
        {
             System.out.println("SmartMotor()");
             jaguar = new Jaguar(channel);
             smartEncoder = smartEnc;
             isReversed = reverse;
             accelTimeSeconds = accelTime;
             targetSpeed = 0.0f;
             lastSpeed = 0.0f;

             isMotor[motorCount] = true;
             motors[motorCount++] = this;
        }

        public double Get()
        {
           return jaguar.get();
        }

        public void Set(double value){
            jaguar.set(value);

        }

        public void getEncoderData(double displacementMeters, double speedMetersPerSecond, double accelMetersPerSecPerSec)
        {
             smartEncoder.GetData(displacementMeters, speedMetersPerSecond, accelMetersPerSecPerSec);
        }

/*        public void Set(double value) {

            synchronized (syncRoot) {
                if (isReversed){
                        value*=-1;
                        targetSpeed = value;
                }

            }
       }
*/
       public void SetAccelerationTime(double timeSeconds)
       {
            accelTimeSeconds = timeSeconds;
       }

       public void SetCollectData(boolean doCollect)
       {
            doCollectData = doCollect;

       }

       public void DriveIt()
       {
	  //if(smartEncoder)
          //        smartEncoder->PeriodicService();		// Drive Encoder Update with each Motor update


	  synchronized (syncRoot) {

                double maxDelta;
                if (accelTimeSeconds != 0)
                {
                        maxDelta = (1 / accelTimeSeconds) * Constants.MotorPeriodSeconds;		// Increment size;
                }
                else
                {
                        maxDelta = 1;
                }
                double speedDif = targetSpeed - lastSpeed;							// Total speed change remaining
                double newSpeed;														// Need to calculate new speed

                if(speedDif > 0){
                        newSpeed = lastSpeed + maxDelta;
                        if(newSpeed > targetSpeed){
                                newSpeed = targetSpeed;
                        }
                }
                else if(speedDif < 0){
                        newSpeed = lastSpeed - maxDelta;
                        if(newSpeed < targetSpeed){
                                newSpeed = targetSpeed;
                        }
                }
                else{							// No difference in target vs. actual
                        newSpeed = lastSpeed;
                }
                jaguar.set(newSpeed);
                lastSpeed = newSpeed;			// Preserve last speed for next loop
            }
       }




       protected void finalize()
       {
           // motors[--motorCount] = (SmartMotor)0;
           // if(motorCount <= 0){
	//	if(motorTask){
	//		delete motorTask;
	//		motorTask = NULL;
	//


       }

}
