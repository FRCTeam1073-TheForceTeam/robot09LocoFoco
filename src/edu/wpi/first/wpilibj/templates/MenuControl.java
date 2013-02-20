/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;
import java.io.*;
//import com.sun.squawk.microedition.io.*;
import com.sun.squawk.microedition.io.*;
import javax.microedition.io.Connector;

// C++

//#include "MenuControl.h"



/**
 *
 * @author hedenj
 */
public class MenuControl {


	public void GetMenuResults(int where, int what)
		{
			where = whereIsRobot;
			what = whatToDo;
		}

	private Joystick mainJoystick;
	private int menuIndex;
	private int choiceIndex;


	private final String MENU_FILE_NAME = "/team1073/menu.txt";

	private int whereIsRobot;
	private int whatToDo;
        char[] choiceCount = { 3, 6 };

    MenuControl(Joystick rightStick)
    {
        try
        {

            FileConnection file = (FileConnection)Connector.open("file://test.txt");
            if (!file.exists()) {
                file.create();
            }
            DataOutputStream dos = file.openDataOutputStream();
            PrintStream p = new PrintStream(dos);
            p.println("This is a test");
            dos.close();
        }
        catch(IOException ie){}


	mainJoystick = rightStick;
	// GetFromFile();
	menuIndex = 0;
	choiceIndex = 0;
    }

    private boolean lastMenuButton = false;
    private boolean lastChoiceButton = false;

    void PeriodicService()
    {



	boolean menuButton = mainJoystick.getRawButton(6);
        boolean choiceButton = mainJoystick.getRawButton(7);                        // figure out later

        int maxMenuCount = choiceCount.length;

	if(menuButton != lastMenuButton)
	{
		if (menuButton)
		{
			if(menuIndex == 0){
				choiceIndex = whereIsRobot;
			}


			if(menuIndex == 1){
				whereIsRobot = choiceIndex;
				PutToFile();
				choiceIndex = whatToDo;

			}
			if(menuIndex == 2){
				whatToDo = choiceIndex;
				PutToFile();
			}


			if(++menuIndex>maxMenuCount)
			{
				menuIndex=0;
				choiceIndex = 0;
			}
		}
	}


	lastMenuButton=menuButton;

        if(menuIndex != 0 && choiceButton != lastChoiceButton)
	{
		if (choiceButton)
		{
			if(++choiceIndex >= choiceCount[menuIndex-1])
			{
				choiceIndex = 0;
			}

		}
	}
	lastChoiceButton=choiceButton;
}

    public int GetMenuValue()
    {
        return menuIndex;
    }

    public int GetChoiceValue()
    {
        return choiceIndex;
    }

    public void PutToFile()
    {
        try
        {

            FileConnection file = (FileConnection)Connector.open("file://test.txt");
            if (!file.exists())
            {
                file.create();
            }
            DataOutputStream dos = file.openDataOutputStream();
            PrintStream p = new PrintStream(dos);
            p.println(""  +  whereIsRobot + ", " + whatToDo);
            dos.close();
        }
        catch(IOException ie)
        {

        }
    }
    /*
    public void MenuControl::PutToFile()
{
	FILE *fp = fopen(MENU_FILE_NAME, "w+");

	int i1, i2;

	i1 = whereIsRobot;
	i2 = whatToDo;


        //char temp[81];
	//sprintf(temp, "%d %d\n", i1, i2);
        sprintf(temp, Integer.toString(i1)+" "+Integer.toString(i2)+"\n")


	if (fp) {
		fprintf(fp, temp);
		fclose(fp);
	}
}
public void MenuControl::GetFromFile()
{
	FILE *fp = fopen(MENU_FILE_NAME, "r");

	int i1 = 0;
	int i2 = 0;

	if (fp) {

		//fscanf(fp, "%d %d\n", &i1, &i2);
            fscanf(fp,Integer.toString(i1)+" "+Integer.toString(i2+"\n"))
		fclose(fp);
	}
	whereIsRobot = i1;
	whatToDo = i2;
}
*/ // Figure out later

}



