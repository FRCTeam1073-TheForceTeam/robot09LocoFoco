/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;

/**
 *
 * @author hedenj
 */
public class SmartAccelerometer {
	float zeroValue;
	float velocity;
	float displacement;
	//float history [ARRAY_LENGTH+1];
	int historyCount;
	//Task *serviceTask;
	//SEM_ID accelerometerSemaphore;


        public SmartAccelerometer(int anaologPort){

        }

	public void PeriodicService(){

        }

	public float GetVelocity(){
            return(1f);
        }
	public float GetDisplacement(){
            return(2f);
        }

}