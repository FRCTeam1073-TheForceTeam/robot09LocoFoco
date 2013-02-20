/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;
import edu.wpi.first.wpilibj.*;
/**
 *
 * @author hedenj
 * @author nabioullinr
 */
public class DashboardData {
    int updateCount = 0;
    final int BUF_LEN = 1200;
    final char delim = '\t';
    //char dashboardData[] = new char[1200];
    DriverStation driverStation =  DriverStation.getInstance();

    //String dashboardString= new String();

    //StringBuffer is a mutable, thread-safe String
    StringBuffer dashboardString = new StringBuffer(BUF_LEN);

    public void reset(){
        dashboardString.setLength(0);
        add(updateCount++);
    }

    public DashboardData(){
         System.out.println("Made it to dashBoardData()");


    }

    public void add(int i) {
        dashboardString.append(i);
        dashboardString.append(delim);
    }
    
    public void add(float f){
        dashboardString.append(f);
        dashboardString.append(delim);
    }

    public void add(String s){
        dashboardString.append(s + delim);
    }

    public void add(double d){
        dashboardString.append(d);
        dashboardString.append(delim);
    }

    public void add(boolean b){
        dashboardString.append(b ? 1 : 0);
        dashboardString.append(delim);
    }

    public void addBlobData(){
    }

    public void SendData(){
      DriverStation ds = DriverStation.getInstance();
      Dashboard db = ds.getDashboardPacker();
      //assert (dashboardString.length() <= BUF_LEN);
      db.print(dashboardString.toString() + '\n');
      db.commit();
   }
}