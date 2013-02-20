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

public class Utils {
    // TDB static PCVideoServer pPCVideoServer= new PCVideoServer();
public static float degreesConversion = (float)(Math.PI) / 180;                             // May be wrong or unneeded, added for GenesisDrive AutoDrive
    public void RecordData (String fmt){


    }



    static public void StartCameraDashboard(){
	//pcVideoServer.start();
        Timer.delay(2.0);
	}

    static public void StopCameraDashboard(){
        //pcVideoServer->Stop();

    }

    static public int clip(int min, int v, int max){
	if(v < min) 		v = min;
	else if(v > max) 	v = max;
	return v;
    }

    static float clip(float min, float v, float max)
    {
	if(v < min) 		v = min;
	else if(v > max) 	v = max;
	return v;
    }

    static double clip(double min, double v, double max){
	if(v < min) 		v = min;
	else if(v > max) 	v = max;
	return v;
    }
    static float fabs(float val){
	if(val<0)
            val = -val;

	return val;
    }
}


