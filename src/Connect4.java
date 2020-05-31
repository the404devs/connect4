/*Connect 4 in the Java Console
By Owen and Kevin
Mr. Krnic
April 9, 2018

In this program, file IO is used for our highscore system. One function, readScores, gets the score values from the file scores.txt 
and stores them in the 1D array called highScore. Another function, called saveScores, does the opposite. 
It loops through each element in the highScore array, and writes each of them to the scores.txt file.
File IO is also used to create the logo on the main menu. The function logoDraw reads the file logo.txt line by line, and writes each line to the screen.

A 2D array, known as "grid," is essential to this game. It represents the grid that an actual Connect 4 game would be played on,
and the two dimensions represent the width and height of the grid. By using a 2D array alongside variables representing the height of each column 
we can easily determine where the user wants to place their piece, check for winners, and even have an AI system that attempts to beat the player.

This game contains 148 different if-else statements. That's a lot of selection.
Also contains 6 while loops and 23 for loops. That's a lot of repetition.

In this gmae, player one plays as - and player 2/computer plays as +*/

import java.util.*;//For the Scanner and Random classes
import java.io.*;//For the FileReader and BufferedReader classes

public class Connect4 {	

	/*****************************
	*******CLASS VARIABLES********
	*****************************/
	public static String grid[][] = new String[7][6];//2D array, which represents the actual grid on which the game is played.

	public static int highScore[] = new int[11];//1D array, used when bubble sorting the high scores.

	public static Scanner scan = new Scanner(System.in);//Scanner object, so we can get user input

	/*The columnHeight variables represent where the next checker in each column should be placed.
	Because of gravity, we start at the bottom.
	When a checker is placed in a column, it falls to the lowest position (5), and the columnHeight variable is reduced by one, 
	so that the next checker in that column is placed one spot above.*/
	public static int column1Height = 5;
	public static int column2Height = 5;
	public static int column3Height = 5;
	public static int column4Height = 5;
	public static int column5Height = 5;
	public static int column6Height = 5;
	public static int column7Height = 5;

	/*This boolean variable is used to tell the game whether or not the user is playing in 1- or 2-player mode.
	True = single player, false = multiplayer*/
	public static Boolean gameMode = true;

	public static int turnCount = 0;//Counts the number of turns. Used for scores.


	/**************************************************************************
	**********FUNCTIONS WITH NO PARAMETERS AND DON'T RETURN ANYTHING***********
	***************************AKA BORING FUNCTIONS****************************
	**************************************************************************/

	public static void readScores(){
		/************************
		****FILE IO USED HERE****
		************************/
		/*This function reads existing highscores from the file
		scores.txt and puts them into the array highScores[]*/

		int x = 0;//Counter variable for the loop
        String line = "";//To store a line as it is read by the file

        //Read the file line by line, putting each value into the highScore array
        try {
	        FileReader fr = new FileReader("scores.txt");
	        BufferedReader br = new BufferedReader(fr);
	        while ((line = br.readLine()) != null)
	        {
	            highScore[x] = Integer.parseInt(line);
	            x++;
	        }
	        br.close();//Close the buffered reader
        }catch (IOException e) {
        	//Can't read from the file. That's bad
        	System.out.println("ERROR: Code KEVIN_WHERES_THE_CODE");
        }    
    }

    public static void sortScores(){
    	/*This function uses good old bubble sorting to order the highscores
    	from lowest to highest. In this game a lower score is better, like golf*/
	    if(turnCount!=0)//If turn count isn't 0, this function is being called after a game.
	    {
	    	highScore[10] = turnCount;//Put the newest score into the bottom of the array
	    }
	    else//Function has been called from the main menu (view scores page.)
	    {
	    	/*Without this if statement, opening the highscores page 
	    	would put a turncount of 0 into the array, automatically becoming the lowest score,
	    	so to avoid this problem we put in a super big value instead*/
	    	highScore[10] = 2147483647;//max value for an int, so there's no way it'll make its way onto the leaderboard
	    }
	    
	    for (int x = 0; x < 10; x++)//Hooray for bubble sorting. 
	    {
	        for (int y = 0; y < 10; y++)
	        {
	            //checks if highScore is less than the highScore above it in the array
	            if (highScore[y] > highScore[y + 1])
	            {
	                //highscores switch places
	                int temp = 0;
	                temp = highScore[y];
	                highScore[y] = highScore[y + 1];
	                highScore[y + 1] = temp;
	            }
	        }
	    }
	   
	    if (highScore[0]==turnCount) {
	    	//If the top score is equal to the turnCount of the last game, somebody just got the new highscore.
	    	System.out.println("                          You got the highscore!!!");
	    }
    }

    public static void saveScores(){
    	/************************
		****FILE IO USED HERE****
		************************/
    	/*This function saves the scores to the file scores.txt
    	We don't want to append to the existing file, so we make sure we overwrite everything*/
    	try{	        
	        FileWriter fw = new FileWriter("scores.txt");//New FileWriter object to use when writing to scores.txt
	        
	        for (int x = 0; x < highScore.length; x++)//Loop through the array
	        {
	            fw.write(highScore[x]+"\r\n");//Write each score to the file followed by a line break
	        }
	        fw.close();//Close the stream.
	    }catch(IOException e)
	    {
	    	//Can't write to the scores file because it's being used by another castle.... I mean process
	    	System.out.println("ERROR: Code THE_PRINCESS_IS_IN_ANOTHER_CASTLE");
	    }
    }

	public static void delay(){
		/*This tiny function is used to create a 2 second pause in the execution of the code. 
		Super useful to make it look like the computer is thinking,
		or just to, you know, delay things for a bit.*/		
    	try{
	    	Thread.sleep(2000);
	    }
	    catch (InterruptedException e)
	    {
	    	//The thread was sleeping, but it was interrupted. Ha ha ha
	    	System.out.println("ERROR: Code WAKE_ME_UP_INSIDE");
	    }
	}

	public static void resetGrid(){		
		/*When starting a new game, this function is called.
		It sets the height of each column back to the bottom...*/
		column1Height = 5;
		column2Height = 5;
		column3Height = 5;
		column4Height = 5;
		column5Height = 5;
		column6Height = 5;
		column7Height = 5;
		/*...and then sets each element in the array back to O, which signifies an open space...*/
	    for (int x = 0; x<6; x++) {
	    	grid[0][x] = "O";
	    	grid[1][x] = "O";
	    	grid[2][x] = "O";
	    	grid[3][x] = "O";
	    	grid[4][x] = "O";
	    	grid[5][x] = "O";
	    	grid[6][x] = "O";
	    }	
	    turnCount = 0;//...also set the counter back to 0 for the new game   
	}

	public static void clearScreen(){
		/*This function will clear the console window.
		Only works for legit consoles, like CMD. Doesn't work when running on Sublime or Eclipse.*/
	    try {
	        if (System.getProperty("os.name").contains("Windows"))//Simple way to check if it's running on a Windows machine, which would mean CMD.
	            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();//This sends a "cls" command to CMD, which clears the screen.
	        else
	            Runtime.getRuntime().exec("clear");//If it's not on Windows, try clearing it a different way.
	    } catch (IOException | InterruptedException ex) {
	    	//Can't clear the screen. Error code windex, cause windex is used to clean windows. Get it?
	    	System.out.println("ERROR: Code WINDEX");
	    }
	}

	public static void draw(){
		/*This function is what draws the grid on the screen each turn.*/
		clearScreen();//Clear the screen, for obvious reasons
		System.out.println("");//Blank lines to centre it vertically
		System.out.println("");
		System.out.println("");
		System.out.println("");
		//Loop through each row, printing out each element in that row separated by 2 spaces
	    for (int x = 0; x<6; x++) {
	    	System.out.println("                            "+grid[0][x]+"  "+grid[1][x]+"  "+grid[2][x]+"  "+grid[3][x]+"  "+grid[4][x]+"  "+grid[5][x]+"  "+grid[6][x]);
	    }
	    //Dashes and column numbers below the grid so the game looks good.
	    //You may notice a whole bunch of spaces. They centre the stuff horizontally
	    System.out.println("                            -------------------");
	    System.out.println("                            1  2  3  4  5  6  7");
	}

	public static void logoDraw() {
		/************************
		****FILE IO USED HERE****
		************************/
		/*This function is what draws the logo on the main menu*/
		clearScreen();//Blank screen to start
	    try{
	    	FileReader fr = new FileReader("logo.txt");//Set up file reader and buffered reader, pointing to logo.txt
	    	BufferedReader br = new BufferedReader(fr);
	    	String line = br.readLine();//Read the first line
	    	while(line!=null)//Keep going until theres nothing left in the file
	    	{
	    		System.out.println(line);//write the line
	    		line = br.readLine();//Get the next line
	    	}
	    }catch(IOException e)
		{
			//Can't find the logo file, show an error
			System.out.println("ERROR: Code RIP_THE_LOGO");
		}
		delay();
	}

	/*************************************************
	**********FUNCTIONS THAT HAVE PARAMETERS**********
	*************************************************/
	public static void winScreen(int win, Boolean mode) {
		//This function is what shows the message after someone wins. Called after the winCheck function returns 1 or 2
		draw();//Draw the grid one last time, so we can actually see where the winning piece went
		delay();//Pause for dramatic effect
	    if (win==1 && gameMode==true){
	    	//If player 1 wins in singleplayer mode...
	    	clearScreen();//clean slate
	    	System.out.println("                               Player 1 wins!");//happy message, yay good job
	    	System.out.println("");
	    	System.out.println("                     Took "+turnCount+" turns to beat the computer.");
	    	System.out.println("");
	    	readScores();
	    	sortScores();
	    	saveScores();
	    	delay();
	    	resetGrid();//Get ready for a new game    	
	    }
	    else if (win==2 && gameMode==true){
	    	//if player 2 wins in singleplayer mode
	    	//in single player the computer is player 2
	    	clearScreen();
	    	System.out.println("                              Computer wins...");
	    	resetGrid();	    	
	    }
	    else if (win==1 && gameMode==false){
	    	//if player 1 wins in multiplayer mode
	    	clearScreen();
	    	System.out.println("                               Player 1 wins!");
	    	resetGrid();	    	
	    }
	    else if (win==2 && gameMode==false){
	    	//if player 2 wins in multiplayer mode
	    	clearScreen();
	    	System.out.println("                               Player 2 wins!");
	    	resetGrid();
	    }
	    else if (win==3){
	    	//if it's a draw (same for both modes)
	    	clearScreen();
	    	System.out.println("                                It's a draw!");
	    	resetGrid();
	    }
	}

	/***********************************************
	**********FUNCTIONS THAT RETURN VALUES**********
	***********************************************/
	public static int winCheck() {
		/*This bad boy is what does all the work.
		It checks the grid in its current state for any possible winning combination of 4-in-a-row.*/

		/*4 string variables in a row... guess I win
		These are used when checking for (4?) wins in each possible way,
		which are horizontal, vertical, diagonal forward(/) and diagonal backward(\)*/
		String allRows = "";
		String allCols = "";
		String fwdDiags = "";
		String bwdDiags = "";

		/********************************
		****Check for Horizontal Wins****
		********************************/
		for(int y=0; y<6; y++)//Loop through each row (y-value)
		{
			for(int x=0; x<7; x++)//In each row, loop through each column (x-value)
			{
				allRows+=(grid[x][y]);//Add the element stored at the current x-y position to the allRows string
			}
			allRows+="R";
			/*Add an R to the end of this rows, this means that a setup like follows:
			O  O  O  O  O  O  O
			O  O  O  O  O  O  O
			O  O  O  O  O  O  O
			O  O  O  O  O  O  O
			O  O  O  O  O  -  -
			-  -  O  O  O  +  +
			-------------------
			1  2  3  4  5  6  7
			will not show a win for Player 1, because the bottom 2 rows in the grid will be formatted in allRows as:
			OOOOO--R--OOO++R
			The R makes sure that the two sets of - do not end up beside each other in the string, causing a 4-in-a-row*/
		}

		

		/******************************
		****Check for Vertical Wins****
		******************************/
		//Similar to the row checking except it's reversed.
		for(int x=0; x<7; x++)//Loop through each columm (y-value)
		{
			for(int y=0; y<6; y++)//Loop through each row in that column (x-value)
			{
				allCols+=(grid[x][y]);//Add the current x-y position to the allCols string

			}
			allCols+="R";//Add an R to separate the individual columns in the big string, see above for reasoning.
		}

		

		/******************************
		****Check for Diagonal Wins****
		******************************/
		
		//Diagonal checks are split up into 2 sections of 2 for loops.
		//The first section is to check the backwards diagonals. ie \
		//the first for loop goes through the left side of the grid
		/*
			the numbers on the grid shows what iteration takes which space
						5 | 1O  O   O   O   O   O   O
						4 | 5   11  O   O   O   O   O
						3 | 1   6   12  O   O   O   O
			grid[][y]	2 | O   2   7   13  O   O   O
						1 | O   O   3   8   14  O   O
						0 | O   O   O   4   9   15  O
						  | -------------------------
						    0   1   2   3   4   5   6
									grid[x][]
		*/
		//the second for loop goes through the right side of the grid
		/*
			the numbers on the grid shows what iteration takes which space
						5 | O   15  9   4   O   O   O
						4 | O   O   14  8   3   O   O
						3 | O   O   O   13  7   2   O
			grid[][y]	2 | O   O   O   O   12  6   1
						1 | O   O   O   O   O   11  5
						0 | O   O   O   O   O   O   10
						  | -------------------------
						    0   1   2   3   4   5   6
									grid[x][]
		*/
		//The second section is to check the forward diagonals. ie /
		//the first for loop goes through the left side of the grid
		/*
			the numbers on the grid shows what iteration takes which space
						5 | O   O   O   4   9   15  O
						4 | O   O   3   8   14  O   O
						3 | O   2   7   13  O   O   O
			grid[][y]	2 | 1   6   12  O   O   O   O
						1 | 5   11  O   O   O   O   O
						0 | 10  O   O   O   O   O   O
						  | -------------------------
						    0   1   2   3   4   5   6
									grid[x][]
		*/
		//the second for loop goes through the right side of the grid
		/*
			the numbers on the grid shows what iteration takes which space
						5 | O   O   O   O   O   O  10
						4 | O   O   O   O   O   11  5
						3 | O   O   O   O   12  6   1
			grid[][y]	2 | O   O   O   13  7   2   O
						1 | O   O   14  8   3   O   O
						0 | O   15  9   4   O   O   O
						  | -------------------------
						    0   1   2   3   4   5   6
									grid[x][]
		*/


		/*These are backwards diagonals
		\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\*/

		//for the left side of grid
		for(int y=3; y<6; y++)//for loop that excecutes the inside for loop one y value above where it was each iteration. this happens until it reaches 6, which is off the grid.
		{//the y value of the for loop is set to 3 because it is only at that point and above where you can get 4 in a row (bwd diag)
			int num=0;//a number that keeps track of the iterations-1 of the next for loop
			for (int x=0; (y-num)>=0; x++)//a for loop to through the x values of the grid.
			{//y-num>=0 ensures that the y value of the grid does not go below the grid
				bwdDiags+=(grid[x][y-num]);//adds the string of the of the grid[][] array to bwdDiags to later be checked if there is a 4 in a row
				num++;
			}
			bwdDiags+="R";//adds an R to bwdDiags. this seperates each diagonal from the other
		}

		//for the right side of grid
		for(int y=2; y>=0; y--)//for loop that excecutes the inside for loop one y value below where it was each iteration. this happens until it reaches -1, which is off the grid.
		{//the y value of the for loop is set to 2 because it is only at that point and below where you can get 4 in a row (bwd diag)
			int num=0;
			for (int x=6; (y+num)<6; x--)
			{//y+num<6 ensures that the y value of the grid does not go above the grid
				bwdDiags+=(grid[x][y+num]);
				num++;
			}
			bwdDiags+="R";
		}

		/*These are forwards diagonals
		/////////////////////////////*/

		//for the left side of grid
		for(int y=2; y>=0; y--)//for loop that excecutes the inside for loop one y value below where it was each iteration. this happens until it reaches -1, which is off the grid.
		{//the y value of the for loop is set to 2 because it is only at that point and below where you can get 4 in a row (fwd diag)
			int num=0;
			for (int x=0; (y+num)<6; x++)
			{//y+num<6 ensures that the y value of the grid does not go above the grid
				fwdDiags+=(grid[x][y+num]);
				num++;
			}
			fwdDiags+="R";
		}

		//for the right side of grid
		for(int y=3; y<6; y++)//for loop that excecutes the inside for loop one y value above where it was each iteration. this happens until it reaches 6, which is off the grid.
		{//the y value of the for loop is set to 3 because it is only at that point and above where you can get 4 in a row (fwd diag)
			int num=0;
			for (int x=6; (y-num)>=0; x--)
			{//y-num>=0 ensures that the y value of the grid does not go below the grid
				fwdDiags+=(grid[x][y-num]);
				num++;
			}
			fwdDiags+="R";
		}

		/*The following block of if statements will check
		each of the 4 strings created above for any 4-in-a-rows like "----" or "++++"
		The function will return one of 3 values:
		0 if nobody has won.
		1 if player 1 wins
		2 if player 2 wins*/
		if(allRows.contains("----"))
		{
			return 1;
		}
		else if (allRows.contains("++++"))
		{
			return 2;
		}
		else if(allCols.contains("----"))
		{
			return 1;			
		}
		else if (allCols.contains("++++"))
		{
			return 2;
		}
		else if (fwdDiags.contains("----"))
		{
			return 1;
		}
		else if (fwdDiags.contains("++++"))
		{
			return 2;
		}
		else if (bwdDiags.contains("----"))
		{
			return 1;
		}
		else if (bwdDiags.contains("++++"))
		{
			return 2;
		}
		/*This is to check if the grid is full
		Since "O" represents an empty spot, 
		e know the grid is full and its a draw if 
		there are no Os in the string containing the grid*/
		else if (!allRows.contains("O")) {
			return 3;
		}
		else{
			return 0;
		}
	}

	/************SINGLE PLAYER MODE******************/
	public static Boolean singleUpdate() {	
		/*This function is broken into two halves:
		User's turn
		Computer's turn*/

		/******************************
		*******Player 1's turn*********
		******************************/
		int inputColumn = 0;//To hold the column number the user gives us
		int winner = 0;//Used when checking for a winner
		String inputCheck = "";//Used when attempting to parse the user's input to an integer
		draw();//Draw the grid
		//Message to prompt the player
		System.out.println("                            -------------------");
	    System.out.println("                              Player 1's turn.");
	    System.out.println("                     What column will you choose? (1-7)");
	    System.out.print("                                     ");
		inputCheck = scan.nextLine();//Get the user's column input, as a string

	    try{	    	
	    	inputColumn = Integer.parseInt(inputCheck);//Try to parse it to an int..
	    }
	    catch(NumberFormatException e)//...but if we can't...
	    {
	    	if (inputCheck.equals("new"))//...check if they entered "new" and start a new game
	    	{
	    		resetGrid();//reset the game
	    		singleUpdate();//start singleplayer mode again
	    	}
	    	else if(inputCheck.equals("menu"))//...and check if they entered "menu" and send them back to the menu
	    	{
	    		return true;
	    		/*Return true, so we break out of the loop repeatedly calling the update function
	    		 in the main method and go back to the menu*/
	    	}
	    	else{
	    		//They didn't enter a number. Come on guys
	    		System.out.println("ERROR: Code WHAT_ARE_NUMBERS");
	    	}
	    } 

	    /*The next bit of code is all pretty much the same, so only the first part is documented*/	
			
	    
	    if (inputColumn==1) {//check which column the user told us to place their piece in
	    	if (column1Height < 0)//make sure that column isn't already full
	    	{
	    		System.out.println("                    Column is full! You miss your turn!");//Message
	    		delay();//Pause so the user has time to read the message
	    	}
	    	else//Column isn't full, so we're good to go
	    	{
	    		inputColumn--;//Subtract 1 from the user column, since the array elements go from 0-6, but we tell the user to pick from 1-7
	    		grid[inputColumn][column1Height] = "-";//Add a piece for the player in the right spot
	    		column1Height--;//Reduce the height of the column by one, for more info see the declaration of these variables at the top.
	    	}	    	
	    }
	    else if (inputColumn==2) {//Repeat, but with the next column. Repeated for all 7 columns.
	    	if (column2Height < 0)
	    	{
	    		System.out.println("                    Column is full! You miss your turn!");
	    		delay();
	    	}
	    	else
	    	{
	    		inputColumn--;
	    		grid[inputColumn][column2Height] = "-";
	    		column2Height--;
	    	}
	    }
	    else if (inputColumn==3) {
	    	if (column3Height < 0)
	    	{
	    		System.out.println("                    Column is full! You miss your turn!");
	    		delay();
	    	}
	    	else
	    	{
	    		inputColumn--;
	    		grid[inputColumn][column3Height] = "-";
	    		column3Height--;
	    	}
	    }
	    else if (inputColumn==4) {
	    	if (column4Height < 0)
	    	{
	    		System.out.println("                    Column is full! You miss your turn!");
	    		delay();
	    	}
	    	else
	    	{
	    		inputColumn--;
	    		grid[inputColumn][column4Height] = "-";
	    		column4Height--;
	    	}
	    }
	    else if (inputColumn==5) {
	    	if (column5Height < 0)
	    	{
	    		System.out.println("                    Column is full! You miss your turn!");
	    		delay();
	    	}
	    	else
	    	{
	    		inputColumn--;
	    		grid[inputColumn][column5Height] = "-";
	    		column5Height--;
	    	}
	    }
	    else if (inputColumn==6) {
	    	if (column6Height < 0)
	    	{
	    		System.out.println("                    Column is full! You miss your turn!");
	    		delay();
	    	}
	    	else
	    	{
	    		inputColumn--;
	    		grid[inputColumn][column6Height] = "-";
	    		column6Height--;
	    	}
	    }
	    else if (inputColumn==7) {
	    	if (column7Height < 0)
	    	{
	    		System.out.println("                    Column is full! You miss your turn!");
	    		delay();
	    	}
	    	else
	    	{
	    		inputColumn--;
	    		grid[inputColumn][column7Height] = "-";
	    		column7Height--;
	    	}
	    } 
	    else{//The user didn't give us a proper column number
	    	System.out.println("                         Whoops! That wasn't right.");//Message to tell them off for being a horrible player
	    	delay();//Give them time to read the message and reflect on what they've done
	    } 

	    turnCount++;//Add one to the turn count

	    //Check for a winner after this turn.
	    winner = winCheck();//winCheck returns an int depending on who won. See the actual function for more info
	    if (winner==1)//If player 1 won...
	    {	    	
	    	winScreen(winner, gameMode);//show the winScreen, give the function who won and the current game mode
	    	return true;//Return true to break out of the infinite loop in the main method and return to the menu
	    }
	    else if (winner==2)//If player 2 won...
	    {	    	
	    	winScreen(winner, gameMode);//show the winScreen, give the function who won and the current game mode
	    	return true;//Return true to break out of the infinite loop in the main method and return to the menu
	    }
	    else if (winner == 3)//If it's a draw..
	    {
	    	winScreen(winner, gameMode);//show the winScreen with a message about the tie
	    	return true;//Break out of the loop
	    }
	    
	    
	    /******************************
		*******Computer's turn*********
		******************************/
	    draw();//Draw the grid

	    //Tell the player that it's the computer's turn now and they need to hold their horses
		System.out.println("                            -------------------");
	    System.out.println("                              Computer's turn.");
	    System.out.println("                      Be patient... It's thinking.....");
	    
	    /****************************************************************************************/
	    /******************************************AI********************************************/
	    /****************************************************************************************/
	    /*Order stuff happens:
	    Check for cpu 3 in a rows, then win
		check for player 3 in a rows, block them
		choose random column, make sure it's not full*/
		String cpuRows = "";
		String cpuCols = "";
		int cpuColumn = 0;

		/*Get all the rows in one big string to use*/
		for(int y=0; y<6; y++)//Loop through each row (y-value)
		{
			for(int x=0; x<7; x++)//In each row, loop through each column (x-value)
			{
				cpuRows+=(grid[x][y]);//Add the element stored at the current x-y position to the allRows string
			}
			cpuRows+="R";
			/*Add an R to the end of this row*/
		}
		/*Get all the columns*/
		for(int x=0; x<7; x++)//Loop through each columm (y-value)
		{
			for(int y=0; y<6; y++)//Loop through each row in that column (x-value)
			{
				cpuCols+=(grid[x][y]);//Add the current x-y position to the allCols string

			}
			cpuCols+="R";//Add an R to separate the individual columns in the big string, see above for reasoning.
		}

		/*You may notice our AI doesn't ever check for diagonals. 
		We felt it would be best if there is a nice way to beat him*/


		/*Start checking for places to go*/
		if(cpuRows.contains("O+++"))//Check rows if he can win
		{
			int pos = cpuRows.indexOf("O+++");//Get the start position of the 3 in a row in the big string
			int nextR = cpuRows.indexOf("R", pos);//Get the index of the next row after the 3 in a row
			String tempRow = cpuRows.substring(nextR-8,nextR);//Cut out the row with the 3 in a row
			tempRow = tempRow.replace("R","");//Get rid of the R's on the ends
			cpuColumn = tempRow.indexOf("O+++");//Computer knows what column to go in now.
			/*Place his piece depending on what column he has calculated*/
			if (cpuColumn+1==1) {
				grid[cpuColumn][column1Height] = "+";
				column1Height--;
			}
			else if (cpuColumn+1==2) {
				grid[cpuColumn][column2Height] = "+";
				column2Height--;
			}
			else if (cpuColumn+1==3) {
				grid[cpuColumn][column3Height] = "+";
				column3Height--;
			}
			else if (cpuColumn+1==4) {
				grid[cpuColumn][column4Height] = "+";
				column4Height--;
			}
			else if (cpuColumn+1==5) {
				grid[cpuColumn][column5Height] = "+";
				column5Height--;
			}
			else if (cpuColumn+1==6) {
				grid[cpuColumn][column6Height] = "+";
				column6Height--;
			}
			else if (cpuColumn+1==7) {
				grid[cpuColumn][column7Height] = "+";
				column7Height--;
			}
		}
		else if(cpuRows.contains("+++O"))//Same as last case, 
		{
			int pos = cpuRows.indexOf("+++O");//Position in cpuRows of the thing
			int nextR = cpuRows.indexOf("R", pos);
			String tempRow = cpuRows.substring(nextR-8,nextR);
			tempRow = tempRow.replace("R","");
			cpuColumn = tempRow.indexOf("+++O")+3;//+3 cause the empty space is on the other side of the 3 in a row
			if (cpuColumn+1==1) {
				grid[cpuColumn][column1Height] = "+";
				column1Height--;
			}
			else if (cpuColumn+1==2) {
				grid[cpuColumn][column2Height] = "+";
				column2Height--;
			}
			else if (cpuColumn+1==3) {
				grid[cpuColumn][column3Height] = "+";
				column3Height--;
			}
			else if (cpuColumn+1==4) {
				grid[cpuColumn][column4Height] = "+";
				column4Height--;
			}
			else if (cpuColumn+1==5) {
				grid[cpuColumn][column5Height] = "+";
				column5Height--;
			}
			else if (cpuColumn+1==6) {
				grid[cpuColumn][column6Height] = "+";
				column6Height--;
			}
			else if (cpuColumn+1==7) {
				grid[cpuColumn][column7Height] = "+";
				column7Height--;
			}
		}
		else if(cpuCols.contains("O+++"))
		{
			int pos = cpuCols.indexOf("O+++");//Position of the vertical 3 in a row
			int rCount = cpuCols.length() - cpuCols.replace("R", "").length();//Count the number of Rs in the string
			cpuColumn = pos/rCount;//Current position divided by the number of Rs gives us the column to use
			/*Place our piece*/
			if (cpuColumn+1==1) {
				grid[cpuColumn][column1Height] = "+";
				column1Height--;
			}
			else if (cpuColumn+1==2) {
				grid[cpuColumn][column2Height] = "+";
				column2Height--;
			}
			else if (cpuColumn+1==3) {
				grid[cpuColumn][column3Height] = "+";
				column3Height--;
			}
			else if (cpuColumn+1==4) {
				grid[cpuColumn][column4Height] = "+";
				column4Height--;
			}
			else if (cpuColumn+1==5) {
				grid[cpuColumn][column5Height] = "+";
				column5Height--;
			}
			else if (cpuColumn+1==6) {
				grid[cpuColumn][column6Height] = "+";
				column6Height--;
			}
			else if (cpuColumn+1==7) {
				grid[cpuColumn][column7Height] = "+";
				column7Height--;
			}
		}
		/**********************************THE NEXT 3 CASES ARE IDENTICAL TO THE LAST 3**********************************
		*only difference is that we are searching for the other player's pieces so we can try to stop them from winning*/
		else if(cpuRows.contains("---O"))
		{
			int pos = cpuRows.indexOf("---O");
			int nextR = cpuRows.indexOf("R", pos);
			String tempRow = cpuRows.substring(nextR-8,nextR);
			tempRow = tempRow.replace("R","");
			cpuColumn = tempRow.indexOf("---O")+3;
			if (cpuColumn+1==1) {
				grid[cpuColumn][column1Height] = "+";
				column1Height--;
			}
			else if (cpuColumn+1==2) {
				grid[cpuColumn][column2Height] = "+";
				column2Height--;
			}
			else if (cpuColumn+1==3) {
				grid[cpuColumn][column3Height] = "+";
				column3Height--;
			}
			else if (cpuColumn+1==4) {
				grid[cpuColumn][column4Height] = "+";
				column4Height--;
			}
			else if (cpuColumn+1==5) {
				grid[cpuColumn][column5Height] = "+";
				column5Height--;
			}
			else if (cpuColumn+1==6) {
				grid[cpuColumn][column6Height] = "+";
				column6Height--;
			}
			else if (cpuColumn+1==7) {
				grid[cpuColumn][column7Height] = "+";
				column7Height--;
			}
		}
		else if(cpuRows.contains("O---"))
		{
			int pos = cpuRows.indexOf("O---");
			int nextR = cpuRows.indexOf("R", pos);
			String tempRow = cpuRows.substring(nextR-8,nextR);
			tempRow = tempRow.replace("R","");
			cpuColumn = tempRow.indexOf("O---");
			if (cpuColumn+1==1) {
				grid[cpuColumn][column1Height] = "+";
				column1Height--;
			}
			else if (cpuColumn+1==2) {
				grid[cpuColumn][column2Height] = "+";
				column2Height--;
			}
			else if (cpuColumn+1==3) {
				grid[cpuColumn][column3Height] = "+";
				column3Height--;
			}
			else if (cpuColumn+1==4) {
				grid[cpuColumn][column4Height] = "+";
				column4Height--;
			}
			else if (cpuColumn+1==5) {
				grid[cpuColumn][column5Height] = "+";
				column5Height--;
			}
			else if (cpuColumn+1==6) {
				grid[cpuColumn][column6Height] = "+";
				column6Height--;
			}
			else if (cpuColumn+1==7) {
				grid[cpuColumn][column7Height] = "+";
				column7Height--;
			}
		}
		else if(cpuCols.contains("O---"))
		{
			int pos = cpuCols.indexOf("O---");
			int rCount = cpuCols.length() - cpuCols.replace("R", "").length();//Count the number of Rs
			cpuColumn = pos/rCount;
			if (cpuColumn+1==1) {
				grid[cpuColumn][column1Height] = "+";
				column1Height--;
			}
			else if (cpuColumn+1==2) {
				grid[cpuColumn][column2Height] = "+";
				column2Height--;
			}
			else if (cpuColumn+1==3) {
				grid[cpuColumn][column3Height] = "+";
				column3Height--;
			}
			else if (cpuColumn+1==4) {
				grid[cpuColumn][column4Height] = "+";
				column4Height--;
			}
			else if (cpuColumn+1==5) {
				grid[cpuColumn][column5Height] = "+";
				column5Height--;
			}
			else if (cpuColumn+1==6) {
				grid[cpuColumn][column6Height] = "+";
				column6Height--;
			}
			else if (cpuColumn+1==7) {
				grid[cpuColumn][column7Height] = "+";
				column7Height--;
			}
		}
		/*If there are no 3-in-a-rows, we just choose a random column*/
		else{
			Random r = new Random();//Create a new random object
		    int randNum = r.nextInt(7)+1;//Get a random number from 1-7 (that's what the +1 is for)

		    while (1==1)//Inifinitely generate random numbers so that the cpu doesn't try and use a full column
		    {
			    /*Code gets repetitive again, only the first is documented because they're all the same*/
			    if(randNum==1)//randNum is like the inputColumn when its the player's turn
			    {		    	
			    	if (column1Height < 0)//Check if column is full. Choose another random number.
			    	{
			    		randNum = r.nextInt(7)+1;
			    	}
			    	else//Column isn't full, we're good.
			    	{
			    		grid[randNum-1][column1Height] = "+";//Place their piece in the lowest posble spot in that column
			    		column1Height--;//One less space avaliable
			    		break;//We have a non-full column, so get out of the loop.
			    	}   	
			    }
			    else if(randNum==2)//Repeat with next coln and so on...
			    {
			    	if (column2Height < 0)
			    	{
						randNum = r.nextInt(7)+1;	
			    	}
			    	else
			    	{
			    		grid[randNum-1][column2Height] = "+";
			    		column2Height--;
			    		break;
			    	}
			    }
			    else if(randNum==3)
			    {
			    	if (column3Height < 0)
			    	{
			    		randNum = r.nextInt(7)+1;
			    	}
			    	else
			    	{
			    		grid[randNum-1][column3Height] = "+";
			    		column3Height--;
			    		break;
			    	}
			    }
			    else if(randNum==4)
			    {
			    	if (column4Height < 0)
			    	{
			    		randNum = r.nextInt(7)+1;
			    	}
			    	else
			    	{
			    		grid[randNum-1][column4Height] = "+";
			    		column4Height--;
			    		break;
			    	}
			    }
			    else if(randNum==5)
			    {
			    	if (column5Height < 0)
			    	{
			    		randNum = r.nextInt(7)+1;
			    	}
			    	else
			    	{
			    		grid[randNum-1][column5Height] = "+";
			    		column5Height--;
			    		break;
			    	}
			    }
			    else if(randNum==6)
			    {
			    	if (column6Height < 0)
			    	{
			    		randNum = r.nextInt(7)+1;
			    	}
			    	else
			    	{
			    		grid[randNum-1][column6Height] = "+";
			    		column6Height--;
			    		break;
			    	}
			    }
			    else if(randNum==7)
			    {
			    	if (column7Height < 0)
			    	{
			    		randNum = r.nextInt(7)+1;
			    	}
			    	else
			    	{
			    		grid[randNum-1][column7Height] = "+";
			    		column7Height--;
			    		break;
			    	}
			    }
			}
		}	    

	    delay();//Delay, makes it look like it takes the computer longer than a milisecond to come up with an answer

	    winner = winCheck();//Check if anyone has won yet
	    if (winner==1)
	    {	   	
	    	winScreen(winner, gameMode);//If player 1 wins, show a little message
	    	return true;//and then get out of the update loop
	    }
	    else if (winner==2)
	    {	    	
	    	winScreen(winner, gameMode);//If computer wins show a little message
	    	return true;//and then get out of the update loop
	    }
	    else if (winner == 3)//If it's a draw..
	    {
	    	winScreen(winner, gameMode);//show the winScreen with a message about the tie
	    	return true;//Break out of the loop
	    }
	    return false;//or else nobody won, so keep going.   
	}

	/**************MULTI PLAYER MODE*****************/

	public static Boolean multiUpdate() {	
		/*This function is broken into 2 halves:
		Player 1's turn
		Player 2's turn*/
		/******************************
		*******Player 1's turn*********
		******************************/
		int inputColumn = 0;//To hold the column the player's are prompted to input. Same variable is used for players 1 and 2
		int winner = 0;//Used when checking for a winner
		String inputCheck = "";//Used when trying to par the user input to a string
		draw();//Update the grid on the screen
		//Message to prompt the player
		System.out.println("                            -------------------");
	    System.out.println("                              Player 1's turn.");
	    System.out.println("                     What column will you choose? (1-7)");
	    System.out.print("                                     ");
		inputCheck = scan.nextLine();//Get the input from the player

	    try{	    	
	    	inputColumn = Integer.parseInt(inputCheck);//Try and turn it into an int
	    }
	    catch(NumberFormatException e){//and if we can't then we check if they entered "new" or "menu"
	    	if (inputCheck.equals("new"))//if they entered "new"
	    	{
	    		resetGrid();//reset the game
	    		multiUpdate();//and start a new one
	    	}
	    	else if(inputCheck.equals("menu"))//if they entered "menu"
	    	{
	    		return true;
	    		//Return true, so we break out of the loop repeatedly calling the update function in the main method and go back to the menu
	    	}
	    	else{
	    		//They didn't enter a number. Come on guys
	    		System.out.println("ERROR: Code WHAT_ARE_NUMBERS");
	    	}
	    }	

	    /*The next bit of code is all pretty much the same, so only the first part is documented*/
	    
	    if (inputColumn==1) {//Code for column 1. Repeated for each column
	    	if (column1Height < 0)//check if column is full
	    	{
	    		System.out.println("                    Column is full! You miss your turn!");//whoopsies
	    		delay();//delay so the user can read the message
	    	}
	    	else
	    	{
	    		inputColumn--;//Remember how the array columns go from 0-6 but we tell the user 1-7? This makes sure their piece goes in the right spot.
	    		grid[inputColumn][column1Height] = "-";//Place the piece for player 1
	    		column1Height--;//column has 1 less place to put a piece
	    	}
	    	
	    }
	    else if (inputColumn==2) {//Repeat for the rest of the columns
	    	if (column2Height < 0)
	    	{
	    		System.out.println("                    Column is full! You miss your turn!");
	    		delay();
	    	}
	    	else
	    	{
	    		inputColumn--;
	    		grid[inputColumn][column2Height] = "-";
	    		column2Height--;
	    	}
	    }
	    else if (inputColumn==3) {
	    	if (column3Height < 0)
	    	{
	    		System.out.println("                    Column is full! You miss your turn!");
	    		delay();
	    	}
	    	else
	    	{
	    		inputColumn--;
	    		grid[inputColumn][column3Height] = "-";
	    		column3Height--;
	    	}
	    }
	    else if (inputColumn==4) {
	    	if (column4Height < 0)
	    	{
	    		System.out.println("                    Column is full! You miss your turn!");
	    		delay();
	    	}
	    	else
	    	{
	    		inputColumn--;
	    		grid[inputColumn][column4Height] = "-";
	    		column4Height--;
	    	}
	    }
	    else if (inputColumn==5) {
	    	if (column5Height < 0)
	    	{
	    		System.out.println("                    Column is full! You miss your turn!");
	    		delay();
	    	}
	    	else
	    	{
	    		inputColumn--;
	    		grid[inputColumn][column5Height] = "-";
	    		column5Height--;
	    	}
	    }
	    else if (inputColumn==6) {
	    	if (column6Height < 0)
	    	{
	    		System.out.println("                    Column is full! You miss your turn!");
	    		delay();
	    	}
	    	else
	    	{
	    		inputColumn--;
	    		grid[inputColumn][column6Height] = "-";
	    		column6Height--;
	    	}
	    }
	    else if (inputColumn==7) {
	    	if (column7Height < 0)
	    	{
	    		System.out.println("                    Column is full! You miss your turn!");
	    		delay();
	    	}
	    	else
	    	{
	    		inputColumn--;
	    		grid[inputColumn][column7Height] = "-";
	    		column7Height--;
	    	}
	    } 
	    else{//They didn't choose between 1 and 7
	    	System.out.println("                         Whoops! That wasn't right.");
	    	delay();
	    } 
	    
	    winner = winCheck();//check for any wins
	    if (winner==1)//if player 1 wins
	    {	    	
	    	winScreen(winner, gameMode);//show message
	    	return true;//get out of the loop
	    }
	    else if (winner==2)//if player 2 wins
	    {	    	
	    	winScreen(winner, gameMode);//show message
	    	return true;//get out of the loop
	    }
	    else if (winner == 3)//If it's a draw..
	    {
	    	winScreen(winner, gameMode);//show the winScreen with a message about the tie
	    	return true;//Break out of the loop
	    }
	    

	    
	   	/******************************
		*******Player 2's turn*********
		******************************/
	    inputColumn = 0;//To hold the number they give us
	    draw();//update the grid on the screen
		//Message to prompt the player
		System.out.println("                            -------------------");
	    System.out.println("                              Player 2's turn.");
	    System.out.println("                     What column will you choose? (1-7)");
	    System.out.print("                                     ");
	    inputCheck = scan.nextLine();//get the input 

	    try{	    	
	    	inputColumn = Integer.parseInt(inputCheck);//try to turn the input into an int
	    }
	    catch(NumberFormatException e){//input couldn't be turned into an int
	    	if (inputCheck.equals("new"))//check if they put in "new"
	    	{
	    		resetGrid();//reset the game
	    		multiUpdate();//and take it from the top
	    	}
	    	else if(inputCheck.equals("menu"))//check if they put in "menu"
	    	{
	    		return true;
	    		//Return true, so we break out of the loop repeatedly calling the update function in the main method and go back to the menu
	    	}
	    	else{
	    		//They didn't enter a number. Come on guys
	    		System.out.println("ERROR: Code WHAT_ARE_NUMBERS");
	    	}
	    }

	    /*By now you know that it gets real repetitive here. 1st bit is commented*/

	    if (inputColumn==1) {//If they chose column 1
	    	if (column1Height < 0)//Make sure its not full
	    	{
	    		System.out.println("                    Column is full! You miss your turn!");//Oh no, it's full. oops.
	    		delay();//time to read the message
	    	}
	    	else
	    	{
	    		inputColumn--;//Array columns are 0-6, we tell the player 1-7 for simplicity. The column they want is always 1 lower than what they give us. 
	    		grid[inputColumn][column1Height] = "+";//Place the piece
	    		column1Height--;//One less spot in that column
	    	}
	    }
	    else if (inputColumn==2) {//Repeat for column 2
	    	if (column2Height < 0)
	    	{
	    		System.out.println("                    Column is full! You miss your turn!");
	    		delay();
	    	}
	    	else
	    	{
	    		inputColumn--;
	    		grid[inputColumn][column2Height] = "+";
	    		column2Height--;
	    	}
	    }
	    else if (inputColumn==3) {
	    	if (column3Height < 0)
	    	{
	    		System.out.println("                    Column is full! You miss your turn!");
	    		delay();
	    	}
	    	else
	    	{
	    		inputColumn--;
	    		grid[inputColumn][column3Height] = "+";
	    		column3Height--;
	    	}
	    }
	    else if (inputColumn==4) {
	    	if (column4Height < 0)
	    	{
	    		System.out.println("                    Column is full! You miss your turn!");
	    		delay();
	    	}
	    	else
	    	{
	    		inputColumn--;
	    		grid[inputColumn][column4Height] = "+";
	    		column4Height--;
	    	}
	    }
	    else if (inputColumn==5) {
	    	if (column5Height < 0)
	    	{
	    		System.out.println("                    Column is full! You miss your turn!");
	    		delay();
	    	}
	    	else
	    	{
	    		inputColumn--;
	    		grid[inputColumn][column5Height] = "+";
	    		column5Height--;
	    	}
	    }
	    else if (inputColumn==6) {
	    	if (column6Height < 0)
	    	{
	    		System.out.println("                    Column is full! You miss your turn!");
	    		delay();
	    	}
	    	else
	    	{
	    		inputColumn--;
	    		grid[inputColumn][column6Height] = "+";
	    		column6Height--;
	    	}
	    }
	    else if (inputColumn==7) {
	    	if (column7Height < 0)
	    	{
	    		System.out.println("                    Column is full! You miss your turn!");
	    		delay();
	    	}
	    	else
	    	{
	    		inputColumn--;
	    		grid[inputColumn][column7Height] = "+";
	    		column7Height--;
	    	}
	    }  
	    else{
	    	System.out.println("                         Whoops! That wasn't right.");
	    	delay();
	    } 


	    winner = winCheck();//check if anybody won
	    if (winner==1)//if player 1 wins
	    {	    	
	    	winScreen(winner, gameMode);//show a message
	    	return true;//break out of this loop
	    }
	    else if (winner==2)//if player 2 won
	    {	    	
	    	winScreen(winner, gameMode);//show a message
	    	return true;//break out of the loop
	   	}
	   	else if (winner == 3)//If it's a draw..
	    {
	    	winScreen(winner, gameMode);//show the winScreen with a message about the tie
	    	return true;//Break out of the loop
	    }
	    return false;//nobody won, keep on going  
	}

	


	/***********************************
	**********CODE STARTS HERE**********
	***********************************/

    public static void main(String[] args) {

    	/****************************************************************************************************
    	*Are you looking for repetition? Well you're in luck, cause this entire game is in an infinite loop!*
    	****************************************************************************************************/	   	
    	while(1==1)//Infinite loop repeating the entire game over and over again
    	{   	
    		logoDraw();//Draw the logo
    		String selection = "";//To hold user input

    		//Messages for the user to read, formatted all nice and pretty
    		System.out.println("===========================================================================");	
	    	System.out.println("                 Welcome to Connect 4 in the Java Console!                 ");
	    	System.out.println("===========================================================================");
	    	System.out.println("                            ===================");
	    	System.out.println("                            = 1. Singleplayer =");
	    	System.out.println("                            ===================");
	    	System.out.println("                            = 2. Multiplayer  =");
	    	System.out.println("                            ===================");
	    	System.out.println("                            = 3. View Scores  =");
	    	System.out.println("                            ===================");
	    	System.out.println("                            = 4. About        =");
	    	System.out.println("                            ==================="); 	
	    	System.out.print("                                     ");
	    	selection = scan.nextLine();//Get the user's input
	    	
	    	clearScreen();//blank screen, now it's fun time
	    	
	    	try{ //try/catch in case something goes horribly wrong 

		    	if(Integer.parseInt(selection)==1){
			    	/*******SINGLE PLAYER OPTION*******/
		    		gameMode = true;//So the game knows we're on 1 player mode.
		    		//Message showing some tips and tricks
		    		System.out.println("                       Type 'new' to start a new game!");
	    			System.out.println("                   Type 'menu' to return to the main menu!");	    		
			    	System.out.println("===========================================================================");

			    	//Tell player 1 to press enter so we know they're paying attention
			    	System.out.println("                            Player 1 press enter.");
			    	scan.nextLine();//wait for an input	    	
			    	resetGrid();//function to reset the grid
			    	while(1==1)//infinite loop
			    	{
			    		if (singleUpdate()==true)//update function for 1 player mode
			    		{
			    			/*singleUpdate returns a boolean after every 2 turns (player and computer) 
			    			indicating whether or not the game is over. "True" means the game is over*/
			    			break;
			    		}
			    	}
		    	}
		    	else if (Integer.parseInt(selection)==2) {
		    		/*******MULTI PLAYER MODE*******/
		    		gameMode = false;//So the game knows we're on 2 player mode
		    		//Show some handy-dandy tips and tricks
		    		System.out.println("                       Type 'new' to start a new game!");
	    			System.out.println("                   Type 'menu' to return to the main menu!");    		
			    	System.out.println("===========================================================================");

			    	//Tell player one to press enter, AND THEN do the same with player 2, that way we know they're both ready.
			    	System.out.println("                            Player 1 press enter.");
			    	scan.nextLine();//wait for an input
			    	System.out.println("===========================================================================");
			    	System.out.println("                            Player 2 press enter.");
			    	scan.nextLine();//wait again

			    	resetGrid();//function to reset the grid
			    	while(1==1)//infinite loop
			    	{
			    		if (multiUpdate()==true)//update function for 2 player mode
			    		{
			    			/*singleUpdate returns a boolean after every 2 turns (player 1 and 2) 
			    			indicating whether or not the game is over. "True" means the game is over*/
			    			break;
			    		}
			    	}
		    	}  
		    	else if (Integer.parseInt(selection)==3) {
		    		/*******HIGH SCORES SCREEN*******/
		    		String scoreSelection = "";//To store user input
		    		clearScreen();//blank screen

		    		readScores();//Get scores from the file
		    		sortScores();//sort 'em
		    		//Message so the user knows what's going on
		    		System.out.println("Connect 4: High Scores");
		    		System.out.println("===========================================================================");
		    		//Nice tip
		    		System.out.println("When playing against the computer, ");
		    		System.out.println("try to beat it in the fewest amount of turns possible!");
		    		System.out.println("===========================================================================");

		    		//Loop through each of the highscores that have been freshly sorted and print them out on the screen
		    		for (int x = 1; x<highScore.length; x++) {
		    			//whole point of this if statement is to keep the score values in a neat line
		    			if (x==10)
		    			{
		    				System.out.println(x+". "+highScore[x-1]);//Only 1 space between 10. and the score
		    			}
		    			else
		    			{
		    				System.out.println(x+".  "+highScore[x-1]);//2 spaces like normal
		    			}		    			
		    		}
		    		//Keeping things formatted nice
		    		System.out.println("===========================================================================");
		    		System.out.println("                            ===================");
			    	System.out.println("                            = 1. Back         =");
			    	System.out.println("                            ===================");

		    		scoreSelection = scan.nextLine();//Get the input
		    		try{
		    			Integer.parseInt(scoreSelection);		    			
		    			/*Technically we don't need this, but if the user doesn't enter in a number 
		    			we can still tell them off for it before we go back to the menu*/		    			
		    		}catch(NumberFormatException e)
		    		{
		    			//They didn't enter a number. Come on guys
		    			System.out.println("ERROR: Code WHAT_ARE_NUMBERS");
		    		}
		    	}
		    	else if (Integer.parseInt(selection)==4) {
		    		String aboutSelection = "";//To hold user input
		    		clearScreen();//blank screen
		    		//Display some useful info about the game
		    		System.out.println("Connect 4 in the Java Console");
		    		System.out.println("===========================================================================");
		    		System.out.println("For Grade 12 Computer Science");
		    		System.out.println("By Owen and Kevin");
		    		System.out.println("Version 1.0 (April 6, 2018)");
		    		System.out.println("===========================================================================");
		    		//Oh boy, a website?
		    		System.out.println("                            ===================");
			    	System.out.println("                            = 1. Back         =");
			    	System.out.println("                            ===================");
			    	System.out.println("                            = 2. Visit Website=");
			    	System.out.println("                            ===================");

			    	aboutSelection = scan.nextLine();//Get their input
		    		try{
		    			if(Integer.parseInt(aboutSelection)==2)//If they chose option 2
		    			{
		    				try{
		    					//Try and send them to our website
					    		java.awt.Desktop.getDesktop().browse(new java.net.URI("http://www.sdsscomputers.com/OwenGoodwin/ASSIGNMENTS/COMPLETED/Unit3Assignment/index.html"));
					    	}catch(Exception e)
					    	{
					    		//If we can't send them to the website show them an error message telling the user exactly what went wrong..... sort of
					    		System.out.println("ERROR: Code OOF");
					    	}
		    			}
		    			//If they type anything other than 2 they get sent back to the menu
		    		}catch(NumberFormatException e)
		    		{
		    			//They didn't enter a number. Come on guys
		    			System.out.println("ERROR: Code WHAT_ARE_NUMBERS");
		    		}
		    	}
		    	else
		    	{
		    		clearScreen();//do nothing, it'll break without this		    		
		    	}
		    	delay();//delay before the next thing happens happens
			}
		    catch(NumberFormatException e){	
		   		//They didn't enter a number. Come on guys	    	
		    	System.out.println("ERROR: Code WHAT_ARE_NUMBERS");
		  	}	    		    	 
    	} 	
    }
}