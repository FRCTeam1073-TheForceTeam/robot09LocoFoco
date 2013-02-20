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
public class MenuChanger {
    Joystick joystick;

    public void GetMenuItems(int menuIndex,int choiceIndex){
        menuIndex = 0;
        choiceIndex = 0;
    }

    MenuChanger(Joystick stick){
        joystick = stick;
    }

    public void PeriodicService(){

    }

}
