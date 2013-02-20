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
public class GenesisDrive 
{
	
        boolean isDriverMode;
	double autoDirection;
	
	DriverStation ds;
	
	Joystick leftStick;
	Joystick rightStick;
	
	Timer mainTimer;

	
	SmartMotor leftFrontMotor;
	SmartMotor rightFrontMotor;
	SmartMotor leftBackMotor;
	SmartMotor rightBackMotor;
	SmartMotor steeringMotor;
	
	MenuControl menuControl;
	
	AnalogChannel steerSensor;
	boolean isTankMode;

	
	Gyro gyro;
	
	double maximumSpeed; 	// 1 = full speed, 0 = doesn't move
	
	GenesisSteering genesisSteering;

        public GenesisDrive(Joystick leftJoystick, Joystick rightJoystick, 
			SmartMotor leftFront, SmartMotor rightFront, SmartMotor leftBack, SmartMotor rightBack,	SmartMotor sM,
			AnalogChannel steer, MenuControl probablyUnusedMenuControl,
                        Gyro aGyro, 
			double maxSpeed)
        {
            genesisSteering = new GenesisSteering(sM, steer);
            isDriverMode = false; // save driver-station pointer
            leftStick = leftJoystick;
            rightStick = rightJoystick;

            leftFrontMotor = leftFront;
            rightFrontMotor = rightFront;
            leftBackMotor = leftBack;
            rightBackMotor = rightBack;
            steeringMotor = sM;
            isTankMode = false;
            steerSensor = steer;

	gyro = aGyro;
        menuControl = probablyUnusedMenuControl;
	maximumSpeed = (double)(maxSpeed);
        }

		
	public void SetDriverMode(boolean driverMode)
        {
            isDriverMode = driverMode;
        }
        
        public void SetAllMotors(double speed)
{
	leftFrontMotor.Set(speed);
	rightFrontMotor.Set(speed);
	leftBackMotor.Set(speed);
	rightBackMotor.Set(speed);
}

        public double GetSteeringPositionDegrees()
{
	return(1.0f);            //steerSensor.getPositionDegrees();
}

	// [autonomous functions]
	public void AutoDrive(double driveSpeed, double driveDirection)
        {
           //TODO: test for griphens [who slaughtered that spelling?]
           /*
            driveSpeed = rightStick.GetY();	 // RightJoystick: Y axis = Main speed.
            driveDirection = rightStick.GetX(); // LeftJoystick: X axis = Rack & Pinion speed
            */
            //autoSpeed = driveSpeed;
            
            autoDirection = (double)(driveDirection);

            autoDirection = autoDirection * Utils.degreesConversion;

            driveSpeed = Utils.clip(-maximumSpeed, driveSpeed, maximumSpeed);

            SetAllMotors((double)(driveSpeed));
            //steering.setPosition(autoDirection);
}


	public boolean IsTankDrive(){ 
	
		return isTankMode; 
	}
	
        // double timeOutSeconds = 10.0f, double autoSpeed = 0.3333f, double direction = 0.0f);
        
        
        public boolean DriveStraight(double desiredDriveMeters, double timeOut, double maxMotorSpeed, double direction)
        {
            // you probably don't need to pass in a speed
            //const double pFactor = 0.15; // the porportional factor
	
            boolean didTimeOut = false; 	// Assume no time out...
	
            mainTimer.reset();			// Reset & Start the elapsed timer
            mainTimer.start();
	
            double startDistance = (double)(GetTotalDistanceMeters());    // Store the distace when first called, will measure

            while (true)
            {
		double metersElapsed = (double)(GetTotalDistanceMeters()) - startDistance;
		
		//double autoSpeed = pFactor * (desiredDriveMeters - metersElapsed);
		//autoSpeed = clip(-maxMotorSpeed, maxMotorSpeed, maxMotorSpeed);
		
		SetAllMotors(maxMotorSpeed);                                            // Find variables later
		
		if(metersElapsed > desiredDriveMeters)
			break;
		
		// increments the within reason depending on the time elapsed
		if (mainTimer.get() > timeOut /* || !mainRobot.IsAutonomous()*/ )
		{
			didTimeOut = true;
			break;
		}
		Timer.delay(Constants.MotorPeriodSeconds);
            }
            // just make sure that nothing is left on
            mainTimer.stop();
            SetAllMotors(0);
            return didTimeOut;
        }
        
        //timeOutSeconds = 10.0f, double autoSpeed = 0.3333f, double direction = 0.0f);
	
        public boolean AngleDrive(double desiredDriveMeters, double timeOut, double maxMotorSpeed, double direction){
            	
	// back up DriveStraight that uses direction!
	// direction is in degrees, (for the pinion mottttooooooor).
	// you probably don't need to pass in a speed
	
	final double pFactor = 1; // the porportional factor
	boolean didTimeOut = false; 	// Assume no time out...
	
	mainTimer.reset();			// Reset & Start the elapsed timer
	mainTimer.start();
	
	double startDistance = (double)(GetTotalDistanceMeters());    // Store the distace when first called, will measure
	
	double desiredAngle = (double)(gyro.getAngle() + direction);
	
	while (true)
	{
		double metersElapsed = (double)(GetTotalDistanceMeters() - startDistance);
		
		double angleError = (double)(desiredAngle - gyro.getAngle());
		double turnDirection = angleError * pFactor; // not actually speed
		
		//double autoSpeed = pFactor * (desiredDriveMeters - metersElapsed);
		//autoSpeed = clip(-maxMotorSpeed, maxMotorSpeed, maxMotorSpeed);
		
		//steering.SetPosition(turnDirection);
		SetAllMotors(maxMotorSpeed);
		
		if(metersElapsed > desiredDriveMeters)
			break;
		
//		if (mainTimer.get() > timeOut || !mainRobot.IsAutonomous())
//		{
//			didTimeOut = true;
//			break;
//		}
		Timer.delay(Constants.MotorPeriodSeconds);
	}
	// just make sure that nothing is left on
	mainTimer.stop();
	SetAllMotors(0);
	return didTimeOut;
        }

        //double timeOut = 10.0)
	public boolean TankTurn(double degreeTurn, double timeOut){
            final double pFactor = 0.025; // the porportional factor
	final double deadbandDegrees = 15.0; // degrees of deadband, (effectively multiplied by two)
	final double maxMotorSpeed = 0.3333;
	double desiredAngle = gyro.getAngle() + degreeTurn;

	boolean didTimeOut = false; 	// Assume no time out...


	mainTimer.reset();			// Reset & Start the elapsed timer
	mainTimer.start();

	while (true)
	{
		double angleError = desiredAngle - gyro.getAngle();

		if(Math.abs(angleError) <= deadbandDegrees)
			break;

		double speed = angleError * pFactor;
		//speed = clip(-maxMotorSpeed, speed, maxMotorSpeed);
                speed = speed>maxMotorSpeed?maxMotorSpeed:(speed< -maxMotorSpeed?-maxMotorSpeed:speed);

		leftFrontMotor.Set(-speed);
		leftBackMotor.Set(-speed);
		rightFrontMotor.Set(speed);
		rightBackMotor.Set(speed);

		if (mainTimer.get() > timeOut /* || !mainRobot.IsAutonomous()*/)
		{
			didTimeOut = true;
			break;
		}
                try
                {
		wait((int)(Constants.MotorPeriodSeconds*1000));
                }
                catch(InterruptedException e)
                {
                    //Needed just for compiler.
                }
	}
	// just make sure that nothing is left on
	mainTimer.stop();
	SetAllMotors(0);

	return didTimeOut;
        }
                
	boolean SmartAutonomous(){
            int where = 0;
            int what = 0;
            menuControl.GetMenuResults(where, what);                  //find where is declared later
            double degrees = 0;
            double distance = 0;

	switch (what)
	{
		case Constants.kChaseLeft:
			degrees = (where == Constants.kCenter)?  -90 : -45;
			TankTurn(degrees, Constants.MotorPeriodSeconds);
			//mainRobot.FollowTarget(Constants.MaxAutonomousSpeed, what);           //tbd
			break;

		case Constants.kChaseRight:
			degrees = (where == Constants.kCenter) ? 90 : 45;
			TankTurn(degrees, Constants.MotorPeriodSeconds);
			//mainRobot.FollowTarget(Constants.MaxAutonomousSpeed, what);       //tbd
			break;

		case Constants.kChaseStraight:
			//mainRobot.FollowTarget(Constants.MaxAutonomousSpeed, what);           //tbd
			break;

		case Constants.kGoToCenter:
			distance = (where == Constants.kCenter) ? 4.1 : 18.4;
			DriveStraight(distance, Constants.MotorPeriodSeconds, Constants.mainMaxSpeed, 0.0f);
			break;
			//break;
		case Constants.kSeekSafety:
			if(where == Constants.kCenter){
				distance = 18.4;
				AngleDrive(distance, 7, -0.33, 30);
				//DriveStraight(distance);
			}
			else{
				AngleDrive(distance, 7, -0.33, 90);
				//DriveStraight(30);
			}

			break;

		case Constants.kDoNothing:
			break;
	}

	return true;
            
        }
	

	public void PeriodicService(){
            if(isDriverMode)
		OperatorPeriodicService();
	else
		AutonomousPeriodicService();

	// Service Steering servo regardless of mode
	//steering.PeriodicService();
            
        }

	public void AutonomousPeriodicService(){
            
        }
	
	public double GetTotalDistanceMeters(){
            // returns the distance in meters, based off the average of the back motor's encoder values
            double left = 0, right = 0, t1 = 0, t2 = 0;

            leftBackMotor.getEncoderData(left, t1, t2);
            rightBackMotor.getEncoderData(right, t1, t2);

	return (left + right) / 2.0;
        }

    private boolean shouldIOverride = false; // so it doesn't mess with the button mode switch
    private boolean lastButton = false;

    public void OperatorPeriodicService()
    {


	if (leftStick.getRawButton(1)){
            isTankMode = true;
            shouldIOverride = true;
        }
	else if ((!(leftStick.getRawButton(1))) && shouldIOverride){
            isTankMode = false;
            shouldIOverride = false;
	}

	boolean tankButton = leftStick.getRawButton(11);

	if(tankButton != lastButton){
		if(tankButton){
			isTankMode = isTankMode ?  false : true;
			//steering.SetPosition(0); // make sure we're heading straight when we switch modes!
		}
	}
        lastButton = tankButton;

	if (!isTankMode){
                    double driveAxis = rightStick.getY();					// RightJoystick: Y axis = Main speed.
                    double pinionAxisDegrees = rightStick.getX() * Constants.degreesConversion; 	// LeftJoystick: X axis = Rack & Pinion speed

                    double mainTargetSpeed = Utils.clip(-maximumSpeed, driveAxis, maximumSpeed);

                    leftFrontMotor.Set(mainTargetSpeed);
                    rightFrontMotor.Set(mainTargetSpeed);

                    leftBackMotor.Set(mainTargetSpeed);
                    rightBackMotor.Set(mainTargetSpeed);

                    genesisSteering.SetPosition(pinionAxisDegrees);
        }
        else{
		double leftWheelsDrive = leftStick.getY(); // RightJoystick: Y axis = Main speed.
		double rightWheelsDrive = rightStick.getY(); // LeftJoystick: Y axis = Rightspeed

		double leftTargetSpeed = Utils.clip(-maximumSpeed, leftWheelsDrive, maximumSpeed);
		double rightTargetSpeed = Utils.clip(-maximumSpeed, rightWheelsDrive, maximumSpeed);

		leftFrontMotor.Set(leftTargetSpeed);
		leftBackMotor.Set(leftTargetSpeed);

		rightFrontMotor.Set(rightTargetSpeed);
		rightBackMotor.Set(rightTargetSpeed);
        }
  }
}



