/* Absolutely Cursed Connect 4 Remake
By Owen, while Kevin watches in horror.
Github Copilot assisted, sort of.
Took ~2 hours to make.
October 22, 2021 */

import java.util.*;//importing the util library
import java.io.*;//import io

public class Connect4_2 {
    public static String grid[][] = new String[7][6];//the grid
    public static int highScore[] = new int[11];//stores the high scores for each column
    public static Scanner input = new Scanner(System.in);//scanner

    public static int column1Height = 5;//height of column 1
    public static int column2Height = 5;//height of column 2
    public static int column3Height = 5;//height of column 3
    public static int column4Height = 5;//height of column 4
    public static int column5Height = 5;//height of column 5
    public static int column6Height = 5;//height of column 6
    public static int column7Height = 5;//height of column 7

    public static Boolean gameMode = true;//game mode is true by default (single player) and false if multiplayer is selected (multiplayer) 
    public static int turnCount = 0;//turn count for the game mode (for the AI) 

    public static void readScores() {//reads the high scores from the file
        int x = 0;//x is the column number (0-6) 
        String line;//line is the line from the file
        try {
            FileReader fr = new FileReader("scores2.txt");//creates a file reader
            BufferedReader br = new BufferedReader(fr);//creates a buffered reader
            while ((line = br.readLine()) != null) {//while there is a line to read
                highScore[x] = Integer.parseInt(line);//sets the high score for the column to the line
                x++;//increments x
            }
            br.close();//closes the buffered reader
        } catch (IOException e) {//catches an IOException
            System.out.println("Error reading file");//prints error message
        }
    }

    public static void sortScores() {
        if (turnCount!=0){
            highScore[10] = turnCount;//sets the high score for the column to the turn count
        }
        else{
            highScore[10] = 2147483647;//sets the high score for the column to the max int value
        }
        for (int x = 0; x < 10; x++) {//for each column
            for (int y = 0; y < 10; y++) {//for each column
                if (highScore[y] > highScore[y + 1]) {//if the high score for the column is less than the high score for the column
                    int temp = highScore[y];//temp is the high score for the column
                    highScore[y] = highScore[y + 1];//the high score for the column is the high score for the column
                    highScore[y + 1] = temp;//the high score for the column is the temp
                }
            }
        }

        if (highScore[0] == turnCount){
            System.out.println("You beat the high score!");//prints message
        }
    }

    public static void saveScores() {//saves the high scores to the file
        try {
            FileWriter fw = new FileWriter("scores2.txt");//creates a file writer 
            for (int x = 0; x < highScore.length; x++) {//for each column
                fw.write(highScore[x] + "\n");//writes the high score for the column to the file
            }
            fw.close();//closes the file writer 
        } catch (IOException e) {//catches an IOException
            System.out.println("Error writing file");//prints error message
        }
    }

    public static void delay() {
        try {
            Thread.sleep(1000);//sleeps for 1 second
        } catch (InterruptedException e) {//catches an InterruptedException
            System.out.println("Error sleeping");//prints error message
        }
    }

    public static void resetGrid(){
        column1Height = 5;//sets the height of column 1 to 5
        column2Height = 5;//sets the height of column 2 to 5
        column3Height = 5;//sets the height of column 3 to 5
        column4Height = 5;//sets the height of column 4 to 5
        column5Height = 5;//sets the height of column 5 to 5
        column6Height = 5;//sets the height of column 6 to 5
        column7Height = 5;//sets the height of column 7 to 5
        for (int x = 0; x < 7; x++) {//for each row
            for (int y = 0; y < 6; y++) {//for each column
                grid[x][y] = "O";//sets the grid to empty
            }
        }
        turnCount = 0;//sets the turn count to 0
    }//resets the grid

    public static void draw(){
        System.out.println("");//prints a blank line
        System.out.println("");//prints a blank line
        System.out.println("");//prints a blank line
        System.out.println("");//prints a blank line
        for (int x = 0; x < 6; x++) {//for each row
            System.out.println("                            "+grid[0][x]+"  "+grid[1][x]+"  "+grid[2][x]+"  "+grid[3][x]+"  "+grid[4][x]+"  "+grid[5][x]+"  "+grid[6][x]);
        }
        System.out.println("                            -------------------");
	    System.out.println("                            1  2  3  4  5  6  7");
    }

    public static void logoDraw() {
        try {
            FileReader fr = new FileReader("logo.txt");//creates a file reader
            BufferedReader br = new BufferedReader(fr);//creates a buffered reader
            String line = br.readLine();//line is the line from the file
            while (line != null) {//while there is a line to read
                System.out.println(line);//prints the line
                line = br.readLine();//sets line to the next line
            }
        } catch (IOException e) {//catches an IOException
            System.out.println("Error reading file");//prints error message
        }
        delay();//delays
    }

    public static void winScreen(int win, Boolean mode){
        draw();//draws the grid
        delay();//delays
        if (mode == true){
            if (win == 1){
                System.out.println("You win!");//prints message
                System.out.println("                               Player 1 wins!");//prints message
                System.out.println("");
	    	    System.out.println("                     Took "+turnCount+" turns to beat the computer.");
	    	    System.out.println("");
                readScores();//reads the high scores
                sortScores();//sorts the high scores
                saveScores();//saves the high scores
                delay();//delays
                resetGrid();//resets the grid
            }
            else if (win == 2){
                System.out.println("                              Computer wins...");//prints message
                resetGrid();//resets the grid
            }
            else if (win == 3){
                System.out.println("                               Tie!");//prints message
                resetGrid();//resets the grid
            }
        }
        else{
            if (win == 1){
                System.out.println("                               Player 1 wins!");//prints message
                resetGrid();//resets the grid
            }
            else if (win == 2){
                System.out.println("                               Player 2 wins!");//prints message
                resetGrid();//resets the grid
            }
            else if (win == 3){
                System.out.println("                               Tie!");//prints message
                resetGrid();//resets the grid
            }
        }
    }

    public static int winCheck() {
        String allRows = "";//allRows is the string of all the rows
        String allColumns = "";//allColumns is the string of all the columns
        String fwdDiag = "";//fwdDiag is the string of the forward diagonal
        String bwdDiag = "";//bwdDiag is the string of the backward diagonal

        //Check for horizontal wins
        for(int y=0; y<6; y++) {//for each row
            for(int x=0; x<7; x++) {//for each column
                allRows += grid[x][y];//adds the grid to the string
            }
            allRows+="R";//adds a R to the end of the string
        }

        //Check for vertical wins
        for(int x=0; x<7; x++) {//for each column
            for(int y=0; y<6; y++) {//for each row
                allColumns += grid[x][y];//adds the grid to the string
            }
            allColumns+="C";//adds a C to the end of the string
        }

        //Check for backward diagonal wins
        for(int y=3; y<6; y++) {//for each row
            int num=0;//num is the number of the column
            for(int x=0; (y-num)>=0; x++) {//for each column
                bwdDiag += (grid[x][y-num]);//adds the grid to the string
                num++;//increments num
            }
            bwdDiag+="D";//adds a D to the end of the string
        }
        for(int y=2; y>=0; y--) {//for each row
            int num = 0;//num is the number of the column
            for(int x=6; (y+num)<6; x--) {//for each column
                bwdDiag += (grid[x][y+num]);//adds the grid to the string
                num++;//increments num
            }
            bwdDiag+="D";//adds a D to the end of the string
        }

        //Check for forward diagonal wins
        for(int y=2; y>=0; y--) {//for each row
            int num = 0;//num is the number of the column
            for (int x=0; (y+num)<6; x++) {//for each column
                fwdDiag += (grid[x][y+num]);//adds the grid to the string
                num++;//increments num
            }
            fwdDiag+="D";//adds a D to the end of the string
        }
        for(int y=3; y<6; y++) {//for each row
            int num = 0;//num is the number of the column
            for (int x=6; (y-num)>=0; x--) {//for each column
                fwdDiag += (grid[x][y-num]);//adds the grid to the string
                num++;//increments num
            }
            fwdDiag+="D";//adds a D to the end of the string
        }

        // System.out.println(fwdDiag);
        // System.out.println(bwdDiag);
        // delay();
        // delay();
        // delay();
        if(allRows.contains("----") || allColumns.contains("----") || fwdDiag.contains("----") || bwdDiag.contains("----")) {//if there is a win in any of the strings
            return 1;//returns 1 for a win
        } 
        else if (allRows.contains("++++") || allColumns.contains("++++") || fwdDiag.contains("++++") || bwdDiag.contains("++++")) {//if there is a win in any of the strings
            return 2;// returns 2 for a win
        } 
        else if (!allRows.contains("O")) {
            return 3;//returns 3 for a tie
        }
        else {
            return 0;//returns 0 for no win
        }
    }

    public static Boolean singleUpdate() {
        int inputColumn = 0;//inputColumn is the column the user inputs
        int winner = 0;//winner is the winner of the game
        String inputCheck = "";//inputCheck is the string of the inputted column
        draw();//draws the grid
        System.out.println("                            -------------------");//prints the grid
	    System.out.println("                              Player 1's turn.");// 
	    System.out.println("                     What column will you choose? (1-7)");
	    System.out.print("                                     ");
        inputCheck = input.nextLine();//inputCheck is the inputted column
        try {
            inputColumn = Integer.parseInt(inputCheck);//inputColumn is the inputted column
        } catch (NumberFormatException e) {//catches a NumberFormatException
            if (inputCheck.equals("new")){
                resetGrid();//resets the grid
                singleUpdate();//updates the grid
            }
            else if (inputCheck.equals("menu")){
                return true;//returns true for the menu
            }
            else{
                System.out.println("Invalid input.");//prints error message
            }
        }

        if (inputColumn==1){
            if (column1Height < 0){
                System.out.println("                    Column is full! You miss your turn!");//prints error message
                delay();//delays
            }
            else{
                inputColumn--;//decrements inputColumn
                grid[inputColumn][column1Height] = "-";//sets the grid to -
                column1Height--;//decrements column1Height
            }
        }
        else if (inputColumn==2){
            if (column2Height < 0){
                System.out.println("                    Column is full! You miss your turn!");//prints error message
                delay();//delays
            }
            else{
                inputColumn--;//decrements inputColumn
                grid[inputColumn][column2Height] = "-";//sets the grid to -
                column2Height--;//decrements column2Height
            }
        }
        else if (inputColumn==3){
            if (column3Height < 0){
                System.out.println("                    Column is full! You miss your turn!");//prints error message
                delay();//delays
            }
            else{
                inputColumn--;//decrements inputColumn
                grid[inputColumn][column3Height] = "-";//sets the grid to -
                column3Height--;//decrements column3Height
            }
        }
        else if (inputColumn==4){
            if (column4Height < 0){
                System.out.println("                    Column is full! You miss your turn!");//prints error message
                delay();//delays
            }
            else{
                inputColumn--;//decrements inputColumn
                grid[inputColumn][column4Height] = "-";//sets the grid to -
                column4Height--;//decrements column4Height
            }
        }
        else if (inputColumn==5){
            if (column5Height < 0){
                System.out.println("                    Column is full! You miss your turn!");//prints error message
                delay();//delays
            }
            else{
                inputColumn--;//decrements inputColumn
                grid[inputColumn][column5Height] = "-";//sets the grid to -
                column5Height--;//decrements column5Height
            }
        }
        else if (inputColumn==6){
            if (column6Height < 0){
                System.out.println("                    Column is full! You miss your turn!");//prints error message
                delay();//delays
            }
            else{
                inputColumn--;//decrements inputColumn
                grid[inputColumn][column6Height] = "-";//sets the grid to -
                column6Height--;//decrements column6Height
            }
        }
        else if (inputColumn==7){
            if (column7Height < 0){
                System.out.println("                    Column is full! You miss your turn!");//prints error message
                delay();//delays
            }
            else{
                inputColumn--;//decrements inputColumn
                grid[inputColumn][column7Height] = "-";//sets the grid to -
                column7Height--;//decrements column7Height
            }
        }
        else {
            System.out.println("                    Invalid input! You miss your turn!");//prints error message
            delay();//delays
        }
        turnCount++;//increments turnCount
        winner = winCheck();//checks for a win

        if(winner > 0) {//if there is a win
            winScreen(winner, gameMode);//prints the win screen
            return true;//returns true for the menu
        }
        draw();//draws the grid
        System.out.println("                            -------------------");
	    System.out.println("                              Computer's turn.");
	    System.out.println("                      Be patient... It's thinking.....");

        String cpuRows = "";//cpuRows is the string of the cpu's rows
        String cpuCols = "";//cpuCols is the string of the cpu's columns
        int cpuColumn = 0;//cpuColumn is the cpu's column

        for(int y=0; y<6; y++) {//for each row
            for (int x=0; x<7; x++) {//for each column
                cpuRows += grid[x][y];//adds the grid to the string
            }
            cpuRows+="R";//adds a R to the end of the string
        }

        for(int x=0; x<7; x++) {//for each column
            for (int y=0; y<6; y++) {//for each row
                cpuCols += grid[x][y];//adds the grid to the string
            }
            cpuCols+="C";//adds a C to the end of the string
        }

        if(cpuRows.contains("O+++")){
            int pos = cpuRows.indexOf("O+++");//pos is the index of the O+++
            int nextR = cpuRows.indexOf("R", pos);//nextR is the index of the next R
            String tempRow = cpuRows.substring(nextR-8,nextR);//tempRow is the substring of the next 8 characters
            tempRow = tempRow.replace("R", "");//removes the R
            cpuColumn = tempRow.indexOf("O+++");//cpuColumn is the index of the O+++
            if (cpuColumn+1==1){
                grid[cpuColumn][column1Height] = "+";//sets the grid to +
                column1Height--;//decrements column1Height
            }
            else if (cpuColumn+1==2){
                grid[cpuColumn][column2Height] = "+";//sets the grid to +
                column2Height--;//decrements column2Height
            }
            else if (cpuColumn+1==3){
                grid[cpuColumn][column3Height] = "+";//sets the grid to +
                column3Height--;//decrements column3Height
            }
            else if (cpuColumn+1==4){
                grid[cpuColumn][column4Height] = "+";//sets the grid to +
                column4Height--;//decrements column4Height
            }
            else if (cpuColumn+1==5){
                grid[cpuColumn][column5Height] = "+";//sets the grid to +
                column5Height--;//decrements column5Height
            }
            else if (cpuColumn+1==6){
                grid[cpuColumn][column6Height] = "+";//sets the grid to +
                column6Height--;//decrements column6Height
            }
            else if (cpuColumn+1==7){
                grid[cpuColumn][column7Height] = "+";//sets the grid to +
                column7Height--;//decrements column7Height
            }
        }
        else if(cpuRows.contains("+++O")){
            int pos = cpuRows.indexOf("+++O");//pos is the index of the +++O
            int nextR = cpuRows.indexOf("R", pos);//nextR is the index of the next R
            String tempRow = cpuRows.substring(nextR-8,nextR);//tempRow is the substring of the next 8 characters
            tempRow = tempRow.replace("R", "");//removes the R
            cpuColumn = tempRow.indexOf("+++O")+3;//cpuColumn is the index of the +++O plus 3
            if (cpuColumn+1==1){
                grid[cpuColumn][column1Height] = "+";//sets the grid to +
                column1Height--;//decrements column1Height
            }
            else if (cpuColumn+1==2){
                grid[cpuColumn][column2Height] = "+";//sets the grid to +
                column2Height--;//decrements column2Height
            }
            else if (cpuColumn+1==3){
                grid[cpuColumn][column3Height] = "+";//sets the grid to +
                column3Height--;//decrements column3Height
            }
            else if (cpuColumn+1==4){
                grid[cpuColumn][column4Height] = "+";//sets the grid to +
                column4Height--;//decrements column4Height
            }
            else if (cpuColumn+1==5){
                grid[cpuColumn][column5Height] = "+";//sets the grid to +
                column5Height--;//decrements column5Height
            }
            else if (cpuColumn+1==6){
                grid[cpuColumn][column6Height] = "+";//sets the grid to +
                column6Height--;//decrements column6Height
            }
            else if (cpuColumn+1==7){
                grid[cpuColumn][column7Height] = "+";//sets the grid to +
                column7Height--;//decrements column7Height
            }
        }
        else if(cpuCols.contains("O+++")){
            int pos = cpuCols.indexOf("O+++");//pos is the index of the O+++
            int rCount = cpuCols.length() - cpuCols.replace("C", "").length();//rCount is the number of R's
            cpuColumn = pos/rCount;//cpuColumn is the index of the O+++ divided by the number of R's
            if (cpuColumn+1==1){
                grid[cpuColumn][column1Height] = "+";//sets the grid to +
                column1Height--;//decrements column1Height
            }
            else if (cpuColumn+1==2){
                grid[cpuColumn][column2Height] = "+";//sets the grid to +
                column2Height--;//decrements column2Height
            }
            else if (cpuColumn+1==3){
                grid[cpuColumn][column3Height] = "+";//sets the grid to +
                column3Height--;//decrements column3Height
            }
            else if (cpuColumn+1==4){
                grid[cpuColumn][column4Height] = "+";//sets the grid to +
                column4Height--;//decrements column4Height
            }
            else if (cpuColumn+1==5){
                grid[cpuColumn][column5Height] = "+";//sets the grid to +
                column5Height--;//decrements column5Height
            }
            else if (cpuColumn+1==6){
                grid[cpuColumn][column6Height] = "+";//sets the grid to +
                column6Height--;//decrements column6Height
            }
            else if (cpuColumn+1==7){
                grid[cpuColumn][column7Height] = "+";//sets the grid to +
                column7Height--;//decrements column7Height
            }
        }
        else if(cpuRows.contains("O---")){
            int pos = cpuRows.indexOf("O---");//pos is the index of the O---
            int nextR = cpuRows.indexOf("R", pos);//nextR is the index of the next R
            String tempRow = cpuRows.substring(nextR-8,nextR);//tempRow is the substring of the next 8 characters
            tempRow = tempRow.replace("R", "");//removes the R
            cpuColumn = tempRow.indexOf("O---");//cpuColumn is the index of the O---
            if (cpuColumn+1==1){
                grid[cpuColumn][column1Height] = "+";//sets the grid to +
                column1Height--;//decrements column1Height
            }
            else if (cpuColumn+1==2){
                grid[cpuColumn][column2Height] = "+";//sets the grid to +
                column2Height--;//decrements column2Height
            }
            else if (cpuColumn+1==3){
                grid[cpuColumn][column3Height] = "+";//sets the grid to +
                column3Height--;//decrements column3Height
            }
            else if (cpuColumn+1==4){
                grid[cpuColumn][column4Height] = "+";//sets the grid to +
                column4Height--;//decrements column4Height
            }
            else if (cpuColumn+1==5){
                grid[cpuColumn][column5Height] = "+";//sets the grid to +
                column5Height--;//decrements column5Height
            }
            else if (cpuColumn+1==6){
                grid[cpuColumn][column6Height] = "+";//sets the grid to +
                column6Height--;//decrements column6Height
            }
            else if (cpuColumn+1==7){
                grid[cpuColumn][column7Height] = "+";//sets the grid to +
                column7Height--;//decrements column7Height
            }
        }
        else if(cpuRows.contains("---O")){
            int pos = cpuRows.indexOf("---O");//pos is the index of the ---O
            int nextR = cpuRows.indexOf("R", pos);//nextR is the index of the next R
            String tempRow = cpuRows.substring(nextR-8,nextR);//tempRow is the substring of the next 8 characters
            tempRow = tempRow.replace("R", "");//removes the R
            cpuColumn = tempRow.indexOf("---O")+3;//cpuColumn is the index of the ---O plus 3
            if (cpuColumn+1==1){
                grid[cpuColumn][column1Height] = "+";//sets the grid to +
                column1Height--;//decrements column1Height
            }
            else if (cpuColumn+1==2){
                grid[cpuColumn][column2Height] = "+";//sets the grid to +
                column2Height--;//decrements column2Height
            }
            else if (cpuColumn+1==3){
                grid[cpuColumn][column3Height] = "+";//sets the grid to +
                column3Height--;//decrements column3Height
            }
            else if (cpuColumn+1==4){
                grid[cpuColumn][column4Height] = "+";//sets the grid to +
                column4Height--;//decrements column4Height
            }
            else if (cpuColumn+1==5){
                grid[cpuColumn][column5Height] = "+";//sets the grid to +
                column5Height--;//decrements column5Height
            }
            else if (cpuColumn+1==6){
                grid[cpuColumn][column6Height] = "+";//sets the grid to +
                column6Height--;//decrements column6Height
            }
            else if (cpuColumn+1==7){
                grid[cpuColumn][column7Height] = "+";//sets the grid to +
                column7Height--;//decrements column7Height
            }
        }
        else if(cpuCols.contains("O---")){
            int pos = cpuCols.indexOf("O---");//pos is the index of the O---
            int rCount = cpuCols.length() - cpuCols.replace("C", "").length();//rCount is the number of R's
            cpuColumn = pos/rCount;//cpuColumn is the index of the O--- divided by the number of R's
            if (cpuColumn+1==1){
                grid[cpuColumn][column1Height] = "+";//sets the grid to +
                column1Height--;//decrements column1Height
            }
            else if (cpuColumn+1==2){
                grid[cpuColumn][column2Height] = "+";//sets the grid to +
                column2Height--;//decrements column2Height
            }
            else if (cpuColumn+1==3){
                grid[cpuColumn][column3Height] = "+";//sets the grid to +
                column3Height--;//decrements column3Height
            }
            else if (cpuColumn+1==4){
                grid[cpuColumn][column4Height] = "+";//sets the grid to +
                column4Height--;//decrements column4Height
            }
            else if (cpuColumn+1==5){
                grid[cpuColumn][column5Height] = "+";//sets the grid to +
                column5Height--;//decrements column5Height
            }
            else if (cpuColumn+1==6){
                grid[cpuColumn][column6Height] = "+";//sets the grid to +
                column6Height--;//decrements column6Height
            }
            else if (cpuColumn+1==7){
                grid[cpuColumn][column7Height] = "+";//sets the grid to +
                column7Height--;//decrements column7Height
            }
        }
        else{
            Random r = new Random();//creates a new random object
            int randNum = r.nextInt(7)+1;//randNum is a random number between 1 and 7
            while(0!=1){
                if (randNum==1){
                    if (column1Height!=0){
                        grid[randNum-1][column1Height] = "+";//sets the grid to +
                        column1Height--;//decrements column1Height
                        break;
                    }
                    else{
                        randNum = r.nextInt(7)+1;//randNum is a random number between 1 and 7
                    }
                }
                else if (randNum==2){
                    if (column2Height!=0){
                        grid[randNum-1][column2Height] = "+";//sets the grid to +
                        column2Height--;//decrements column2Height
                        break;
                    }
                    else{
                        randNum = r.nextInt(7)+1;//randNum is a random number between 1 and 7
                    }
                }
                else if (randNum==3){
                    if (column3Height!=0){
                        grid[randNum-1][column3Height] = "+";//sets the grid to +
                        column3Height--;//decrements column3Height
                        break;
                    }
                    else{
                        randNum = r.nextInt(7)+1;//randNum is a random number between 1 and 7
                    }
                }
                else if (randNum==4){
                    if (column4Height!=0){
                        grid[randNum-1][column4Height] = "+";//sets the grid to +
                        column4Height--;//decrements column4Height
                        break;
                    }
                    else{
                        randNum = r.nextInt(7)+1;//randNum is a random number between 1 and 7
                    }
                }
                else if (randNum==5){
                    if (column5Height!=0){
                        grid[randNum-1][column5Height] = "+";//sets the grid to +
                        column5Height--;//decrements column5Height
                        break;
                    }
                    else{
                        randNum = r.nextInt(7)+1;//randNum is a random number between 1 and 7
                    }
                }
                else if (randNum==6){
                    if (column6Height!=0){
                        grid[randNum-1][column6Height] = "+";//sets the grid to +
                        column6Height--;//decrements column6Height
                        break;
                    }
                    else{
                        randNum = r.nextInt(7)+1;//randNum is a random number between 1 and 7
                    }
                }
                else if (randNum==7){
                    if (column7Height!=0){
                        grid[randNum-1][column7Height] = "+";//sets the grid to +
                        column7Height--;//decrements column7Height
                        break;
                    }
                    else{
                        randNum = r.nextInt(7)+1;//randNum is a random number between 1 and 7
                    }
                }
            }
        }

        delay();//calls the delay method
        winner = winCheck();//sets winner to the value of winCheck
        if (winner>0){
            winScreen(winner, gameMode);//calls the winScreen method
            return true;//returns true
        }
        return false;//returns false
    }

    public static Boolean multiUpdate(){
        int inputColumn = 0;//inputColumn is set to 0
        int winner = 0;//winner is set to 0
        String inputCheck = "";//inputCheck is set to ""
        draw();//calls the draw method
        System.out.println("                            -------------------");
	    System.out.println("                              Player 1's turn.");
	    System.out.println("                     What column will you choose? (1-7)");
	    System.out.print("                                     ");
        inputCheck = input.nextLine();//inputCheck is set to the value of input.nextLine()
        try{
            inputColumn = Integer.parseInt(inputCheck);//inputColumn is set to the value of Integer.parseInt(inputCheck)
        }
        catch(NumberFormatException e){
            if(inputCheck.equals("new")){
                resetGrid();//calls the resetGrid method
                multiUpdate();//calls the multiUpdate method
            }
            else if (inputCheck.equals("menu")){
                return true;//returns true
            }
            else{
                System.out.println("invalid input.");//prints invalid input
            }
        }

        if (inputColumn==1){
            if (column1Height < 0){
                System.out.println("That column is full.");//prints that column is full
            }
            else{
                inputColumn--;//decrements inputColumn
                grid[inputColumn][column1Height] = "-";//sets the grid to -
                column1Height--;//decrements column1Height
            }
        }
        else if (inputColumn==2){
            if (column2Height < 0){
                System.out.println("That column is full.");//prints that column is full
            }
            else{
                inputColumn--;//decrements inputColumn
                grid[inputColumn][column2Height] = "-";//sets the grid to -
                column2Height--;//decrements column2Height
            }
        }
        else if (inputColumn==3){
            if (column3Height < 0){
                System.out.println("That column is full.");//prints that column is full
            }
            else{
                inputColumn--;//decrements inputColumn
                grid[inputColumn][column3Height] = "-";//sets the grid to -
                column3Height--;//decrements column3Height
            }
        }
        else if (inputColumn==4){
            if (column4Height < 0){
                System.out.println("That column is full.");//prints that column is full
            }
            else{
                inputColumn--;//decrements inputColumn
                grid[inputColumn][column4Height] = "-";//sets the grid to -
                column4Height--;//decrements column4Height
            }
        }
        else if (inputColumn==5){
            if (column5Height < 0){
                System.out.println("That column is full.");//prints that column is full
            }
            else{
                inputColumn--;//decrements inputColumn
                grid[inputColumn][column5Height] = "-";//sets the grid to -
                column5Height--;//decrements column5Height
            }
        }
        else if (inputColumn==6){
            if (column6Height < 0){
                System.out.println("That column is full.");//prints that column is full
            }
            else{
                inputColumn--;//decrements inputColumn
                grid[inputColumn][column6Height] = "-";//sets the grid to -
                column6Height--;//decrements column6Height
            }
        }
        else if (inputColumn==7){
            if (column7Height < 0){
                System.out.println("That column is full.");//prints that column is full
            }
            else{
                inputColumn--;//decrements inputColumn
                grid[inputColumn][column7Height] = "-";//sets the grid to -
                column7Height--;//decrements column7Height
            }
        }
        else{
            System.out.println("That is not a valid column.");//prints that is not a valid column
            delay();//calls the delay method
        }

        winner = winCheck();//sets winner to the value of winCheck
        if (winner > 0){
            winScreen(winner, gameMode);//calls the winScreen method
            return true;//returns true
        }

        inputColumn = 0;//inputColumn is set to 0
        draw();//calls the draw method
        System.out.println("                            -------------------");
	    System.out.println("                              Player 2's turn.");
	    System.out.println("                     What column will you choose? (1-7)");
	    System.out.print("                                     ");
        inputCheck = input.nextLine();//inputCheck is set to the value of input.nextLine()

        try {
            inputColumn = Integer.parseInt(inputCheck);//inputColumn is set to the value of Integer.parseInt(inputCheck)
        }
        catch(NumberFormatException e){
            if(inputCheck.equals("new")){
                resetGrid();//calls the resetGrid method
                multiUpdate();//calls the multiUpdate method
            }
            else if (inputCheck.equals("menu")){
                return true;//returns true
            }
            else{
                System.out.println("invalid input.");//prints invalid input
            }
        }

        if (inputColumn==1){
            if (column1Height < 0){
                System.out.println("That column is full.");//prints that column is full
            }
            else{
                inputColumn--;//decrements inputColumn
                grid[inputColumn][column1Height] = "+";//sets the grid to +
                column1Height--;//decrements column1Height
            }
        }
        else if (inputColumn==2){
            if (column2Height < 0){
                System.out.println("That column is full.");//prints that column is full
            }
            else{
                inputColumn--;//decrements inputColumn
                grid[inputColumn][column2Height] = "+";//sets the grid to +
                column2Height--;//decrements column2Height
            }
        }
        else if (inputColumn==3){
            if (column3Height < 0){
                System.out.println("That column is full.");//prints that column is full
            }
            else{
                inputColumn--;//decrements inputColumn
                grid[inputColumn][column3Height] = "+";//sets the grid to +
                column3Height--;//decrements column3Height
            }
        }
        else if (inputColumn==4){
            if (column4Height < 0){
                System.out.println("That column is full.");//prints that column is full
            }
            else{
                inputColumn--;//decrements inputColumn
                grid[inputColumn][column4Height] = "+";//sets the grid to +
                column4Height--;//decrements column4Height
            }
        }
        else if (inputColumn==5){
            if (column5Height < 0){
                System.out.println("That column is full.");//prints that column is full
            }
            else{
                inputColumn--;//decrements inputColumn
                grid[inputColumn][column5Height] = "+";//sets the grid to +
                column5Height--;//decrements column5Height
            }
        }
        else if (inputColumn==6){
            if (column6Height < 0){
                System.out.println("That column is full.");//prints that column is full
            }
            else{
                inputColumn--;//decrements inputColumn
                grid[inputColumn][column6Height] = "+";//sets the grid to +
                column6Height--;//decrements column6Height
            }
        }
        else if (inputColumn==7){
            if (column7Height < 0){
                System.out.println("That column is full.");//prints that column is full
            }
            else{
                inputColumn--;//decrements inputColumn
                grid[inputColumn][column7Height] = "+";//sets the grid to +
                column7Height--;//decrements column7Height
            }
        }
        else{
            System.out.println("That is not a valid column.");//prints that is not a valid column
            delay();//calls the delay method
        }

        winner = winCheck();//sets winner to the value of winCheck
        if (winner > 0){
            winScreen(winner, gameMode);//calls the winScreen method
            return true;//returns true
        }
        return false;//returns false
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        System.out.println("Welcome to Connect 4!");//prints welcome to connect 4
        while(0!=1){
            logoDraw();//calls the logoDraw method
            String selection = "";//sets selection to ""
            System.out.println("===========================================================================");	
	    	System.out.println("                 Welcome to ABSOLUTELY CURSED Connect 4!                 ");    
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
            selection = input.nextLine();//sets selection to the value of input.nextLine()
            try{
                if (Integer.parseInt(selection)==1){
                    gameMode = true;//sets gameMode to true
                    System.out.println("                       Type 'new' to start a new game!");
	    			System.out.println("                   Type 'menu' to return to the main menu!");	    		
			    	System.out.println("===========================================================================");
                    //Tell player 1 to press enter so we know they're paying attention
			    	System.out.println("                            Player 1 press enter.");
                    input.nextLine();//calls the nextLine method
                    resetGrid();//calls the resetGrid method
                    while(0!=1){
                        if (singleUpdate()){//calls the singleUpdate method
                            break;//breaks
                        }
                    }
                }
                else if (Integer.parseInt(selection)==2){
                    gameMode = false;//sets gameMode to false
                    System.out.println("                       Type 'new' to start a new game!");
                    System.out.println("                   Type 'menu' to return to the main menu!");    		
			    	System.out.println("===========================================================================");
                    //Tell player one to press enter, AND THEN do the same with player 2, that way we know they're both ready.
			    	System.out.println("                            Player 1 press enter.");
                    input.nextLine();//calls the nextLine method
                    System.out.println("===========================================================================");
			    	System.out.println("                            Player 2 press enter.");
                    input.nextLine();//calls the nextLine method
                    resetGrid();//calls the resetGrid method
                    while(0!=1){
                        if (multiUpdate()){//calls the multiUpdate method
                            break;//breaks
                        }
                    }
                }
                else if (Integer.parseInt(selection)==3){
                    String scoreSelection = "";//sets scoreSelection to ""
                    readScores();
                    sortScores();
                    System.out.println("Connect 4: High Scores");
		    		System.out.println("===========================================================================");
                    System.out.println("When playing against the computer, ");
		    		System.out.println("try to beat it in the fewest amount of turns possible!");
		    		System.out.println("===========================================================================");
                    for (int x = 1; x<highScore.length; x++){
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
                    scoreSelection = input.nextLine();//sets scoreSelection to the value of input.nextLine()
                }
                else if (Integer.parseInt(selection)==4){
                    String aboutSelection = "";//sets aboutSelection to ""
                    System.out.println("ABSOLUTELY CURSED Connect 4");
		    		System.out.println("===========================================================================");
                    System.out.println("For torturing Github Copilot");
                    System.out.println("By Owen");
                    System.out.println("Version 2.0 (October 22, 2021)");
                    System.out.println("===========================================================================");
                    //Oh boy, a website?
		    		System.out.println("                            ===================");
			    	System.out.println("                            = 1. Back         =");
			    	System.out.println("                            ===================");
			    	System.out.println("                            = 2. Visit Website=");
			    	System.out.println("                            ===================");
                    aboutSelection = input.nextLine();//sets aboutSelection to the value of input.nextLine()
                    try{
                        if (Integer.parseInt(aboutSelection)==1){
                            continue;//continues
                        }
                        else if (Integer.parseInt(aboutSelection)==2){
                            try{
                                java.awt.Desktop.getDesktop().browse(new java.net.URI("https://connect4.the404.nl/"));
                            }
                            catch(Exception e){
                                System.out.println("Could not open the website.");
                            }
                        }
                    }
                    catch(NumberFormatException e){
                        System.out.println("That is not a valid selection.");
                    }
                }
                else{
                    continue;//continues
                }
                delay();//calls the delay method
            }
            catch(NumberFormatException e){
                if (selection.contains("exit")){
                    System.exit(0);//exits
                }
                System.out.println("That is not a valid selection.");
            }
        }
    }
}
