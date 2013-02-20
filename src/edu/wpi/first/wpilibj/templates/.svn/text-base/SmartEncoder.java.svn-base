/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;
import edu.wpi.first.wpilibj.Encoder;


/**
 *
 * @author hedenj
 */
public class SmartEncoder extends Encoder{
        final int MaxEncoderHistory = 20;
        final float MaxEncoderHistorySeconds = 0.10f;		// Keep Track of last 0.1 seconds
        final int HalfMaxEncHist = MaxEncoderHistory / 2;

        int readCount;
	double acceleration;
	boolean isReversed;
	int countHistory[] = new int[MaxEncoderHistory+1];
	double periodHistory[] = new double [MaxEncoderHistory+1];

	int  semaphore;


    public SmartEncoder(final int aChannel, final int bChannel, final boolean reverseDirection)
    {
       super(aChannel,bChannel, reverseDirection);

       System.out.println("SmartEncoder() construcor");
       	isReversed = reverseDirection;
	// TBD  semaphore = semMCreate(SEM_DELETE_SAFE | SEM_INVERSION_SAFE); // synchronize access to multi-value registers

        // Initialize History to 0
        for(int i = 0; i < MaxEncoderHistory; i++){
            countHistory[i] = 0;
            periodHistory[i] = 0.0f;
        }
    }

    public void PeriodicService()
    {
        // TBD Synchronized syncronized(semaphore);
	
	readCount++;					
	// Lets track the last 
	countHistory[readCount % MaxEncoderHistory] = get();
	
	double secs = getPeriod();
	periodHistory[readCount & MaxEncoderHistory]= secs; 


    }
    public void GetData(double displacementMeters, double speedMetersPerSecond, double accelMetersPerSecPerSec)
    {
        //TBD Synchronized syncronized(semaphore);

	// Get index which is most recent value array index
	int index =  readCount % MaxEncoderHistory;

	// Total displacement = last count = ticks / countsperrev * wheeldiamMM * pi / 1000
	displacementMeters = countHistory[index] * Constants.tickstodist;

	// Calculate speed based on current encoder count and encoder position from MaxEncoderHistorySeconds ago..
	speedMetersPerSecond = (countHistory[index] - countHistory[(index+1)% MaxEncoderHistory])*Constants.tickstodist/(MaxEncoderHistorySeconds-Constants.MotorPeriodSeconds);

	// Look at speed( from last period & from MaxEncoderHistorySeconds ago..

	float NEWper1s_raw = countHistory[index]-countHistory[(index+HalfMaxEncHist+1)%MaxEncoderHistory];
	float OLDper2s_raw = countHistory[(index+HalfMaxEncHist)%MaxEncoderHistory]-countHistory[(index+1)%MaxEncoderHistory];
	accelMetersPerSecPerSec = (NEWper1s_raw-OLDper2s_raw)*Constants.tickstodist/((HalfMaxEncHist - 1)*HalfMaxEncHist);

    }

    private void updateAcceleration()
    {

    }
}
