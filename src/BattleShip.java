package battleship; 

import java.util.Random;
import java.util.*;
import java.util.Scanner;


/**
 *  @Author: Lesly Brisson 
 *    @Date: 02/16/20 
 * @Purpose: Create Battleship Game: Player vs. Computer 
 * @Updated: 02/20/20 
 *@FileName: BattleShip.java 
 */

/**
 * Game Symbol Guide:
 *  0 -> Ocean Space               = " "
 *  1 -> Player's ships              = "@"
 *  2 -> Computer's ships          = " "
 *  3 -> Player's Missed Attack      = "-"
 *  4 -> Computer's Missed Attack  = " "
 *  5 -> Player's Sunk Ship          = "x"
 *  6 -> Computer's Sunk Ship      = "!"
*/ 
public class BattleShip {

    
    public static void main(String[] args) { 
        
        System.out.println("Welcome to BattleShip Combat!"); 
        System.out.println("******************************* \n"); 
        System.out.println("Currently, the ocean is empty.. \n"); 

    //**-----Creates Ocean Map-----*/
        int[][] ocean = new int[10][10];
        printOcean(ocean);
        Scanner input = new Scanner(System.in);
        System.out.println("Time to deploy your 5 ships: \n");
        
    //**-----Deploys User Ships-----*/
        for (int ship = 1; ship <= 5; ship++) {
            deployShips(ocean, ship);
        }
        printOcean(ocean);
    
    //**-----Deploys Computer Ships-----*/
        System.out.println("Computer is deploying 5 ships: ");
        for (int ship = 1; ship <= 5; ship++) {
            deployComputerShips(ocean, ship);
        }
        printOcean(ocean);

    //**-----Battle time, and while losing condition-----*/
        int playerShips = 0;
        int computerShips = 0;

        for (int i = 0; i < ocean.length; i++) {
            for (int j = 0; j < ocean.length; j++) {
                if (ocean[i][j] == 1) {
                 playerShips++;
                }
                if (ocean[i][j] == 2) {
                computerShips++;
                }
            }
        }
    //**-----Game Continues while both Players have ship-----*/
        while (playerShips > 0 && computerShips > 0) { 
            int score = startPlayerTurn(ocean);
            if (score == 1) {
                playerShips--;
            } else if (score == 2) {
                computerShips--;
            }
            System.out.println("COMPUTER'S TURN!");
            score = startComputerTurn(ocean);
            while (score == 3) {
                score = startComputerTurn(ocean);
            }
            if (score == 1) {
                playerShips--;
            } else if (score == 2) {
                computerShips--;
            }
            printOcean(ocean);
            System.out.println("Computer ships: " + computerShips + " | Your own ships: " + playerShips);
            System.out.println("---------------------------------------");
        }
        if (computerShips == 0) {
            System.out.println("Hooray! You win the battle!");
        } else {
            System.out.println("The computer has defeated you!");
        }
           
    }
    
    //-----Method to Create OceanMap-----*/
    public static void printOcean (int [][] ocean) {
        System.out.println("   0123456789   ");
        for (int i = 0; i < ocean.length; i++) {
            System.out.print(i + " |");
            for (int j = 0; j < ocean[0].length; j++) {
                if (ocean[i][j] == 0 || ocean[i][j] == 2 || ocean[i][j] == 4) {
                    System.out.print(" ");
                } else if (ocean[i][j] == 1){
                    System.out.print("@");
                } else if (ocean[i][j] == 3){
                    System.out.print("-");
                } else if (ocean[i][j] == 5){
                    System.out.print("x");
                } else if (ocean[i][j] == 6){
                    System.out.print("!");
                } else {
                    System.out.print("-");
                }
            }   
            System.out.println("| " + i);
        }
        System.out.println("   0123456789   \n");
    }
    
    //-----Method to create User Ships-----*/
    public static void deployShips(int[][] ocean, int ship){
        Scanner input = new Scanner(System.in);
        System.out.println("Select a single number between 0-9");
        System.out.print("Enter X coordinates for your ship " + ship + ": ");
        int xAxis = input.nextInt();
        if (xAxis < 0 || xAxis > 9) {
            System.out.println("Invalid X coordinate, please try again.");
            System.out.println("Select a single number between 0-9");
            System.out.print("Enter X coordinates for your ship " + ship + ": ");
            xAxis = input.nextInt();
            if(xAxis < 0 || xAxis > 9) {//Shuts Game Down if invalid coordinate is entered for a second time
                System.err.println("Invalid X coordinate again, Game Shutting Down..");//Displays Error Message
                System.exit(0);//Ends Program
            }
        }
        System.out.print("Enter Y coordinates for your ship " + ship + ": ");
        int yAxis = input.nextInt();
        if (yAxis < 0 || yAxis > 9) {
            System.out.println("Invalid Y coordinate, please try again.");
            System.out.println("Select a single number between 0-9");
            System.out.print("Enter Y coordinates for your ship " + ship + ": ");
            yAxis = input.nextInt();
            if(yAxis < 0 || yAxis > 9) {//Shuts Game Down if invalid coordinate is entered for a second time
                System.err.println("Invalid Y coordinate again, Game Shutting Down..");//Displays Error Message
                System.exit(0);//Ends Program
            }
        }
        if (ocean[xAxis][yAxis] == 0) {//Lets user reselect coordinates for current Ship
            ocean[xAxis][yAxis] = 1;
        } else {
            System.out.println("Invalid location, please try again.");
            deployShips(ocean, ship);
        }
    }   

    /**-----Method to Create Computer Ships-----*/
    public static void deployComputerShips(int[][] ocean, int ship) {
        Random random = new Random();
        int xAxis = random.nextInt(10);
        int yAxis = random.nextInt(10);
        if (ocean[xAxis][yAxis] == 0) {
            ocean[xAxis][yAxis] = 2;
            System.out.println( ship + ". Ship DEPLOYED");
        } else {
            deployComputerShips(ocean, ship);
        }
    }

    /**-----Battle: User Coordinates to Attack & Display Results of Attacks-----*/
    public static int startPlayerTurn(int[][] ocean) {
        Scanner input = new Scanner(System.in);
        System.out.println("YOUR TURN!");
        System.out.print("Enter X coordinates: ");
        int xAxis = input.nextInt();
        if (xAxis < 0 || xAxis > 9) {
            System.out.println("Invalid location, please try again.");
            System.out.print("Enter X coordinates: ");
            xAxis = input.nextInt();
        }
        System.out.print("Enter Y coordinates: ");
        int yAxis = input.nextInt();
        if (yAxis < 0 || yAxis > 9) {
            System.out.println("Invalid location, please try again.");
            System.out.print("Enter Y coordinates: ");
            yAxis = input.nextInt();
        }
        if (ocean[xAxis][yAxis] == 0 ) {
            System.out.println("Player missed.\n");
            ocean[xAxis][yAxis] = 3;
            return 0;
        } else if (ocean[xAxis][yAxis] == 1) {
            System.out.println("oh no, you sunk your own ship! :(\n");
            ocean[xAxis][yAxis] = 5;
            return 1;
        } else if (ocean[xAxis][yAxis] == 2) {
            System.out.println("Boom! You sunk the enemy ship!\n");
            ocean[xAxis][yAxis] = 6;
            return 2;
        } else if (ocean[xAxis][yAxis] == 4) {
            System.out.println("Player missed.\n");
            ocean[xAxis][yAxis] = 7;
            return 0;
        }
        else  {
            System.out.println("Wrong choice, you just wasted a turn! \n");
            return 0;
        }
    }

    /**-----Battle: Computer Coordinates to Attack & Display Results of Attacks-----*/
   public static int startComputerTurn(int[][] ocean) {
        Random random = new Random();
        int xAxis = random.nextInt(10);
        int yAxis = random.nextInt(10);
        if (ocean[xAxis][yAxis] == 0) {
            System.out.println("Computer missed.\n");
            ocean[xAxis][yAxis] = 4;
            return 0;
        } else if (ocean[xAxis][yAxis] == 1) {
            System.out.println("The Computer sunk 1 of your ships!\n");
            ocean[xAxis][yAxis] = 5;
            return 1;
        } else if (ocean[xAxis][yAxis] == 2) {
            System.out.println("The Computer sunk 1 of its own ships!\n");
            ocean[xAxis][yAxis] = 6;
            return 2;
        } else if (ocean[xAxis][yAxis] == 3) {
            System.out.println("Computer missed.\n");
            ocean[xAxis][yAxis] = 7;
            return 0;
        }
        else  {
            return  3;
        }
    }
}


