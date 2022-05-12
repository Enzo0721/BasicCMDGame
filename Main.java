package com.company;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JTextField;


public class Main{
    public static int rows = 20, cols = 20, room = 1, playerx = 9, playery = 9, deathMethod = 0, gameTable[][] = new int[rows][cols], mainRoom[][] = new int[20][20], northRoom[][] = new int[rows][cols], southRoom[][] = new int[rows][cols], eastRoom[][] = new int[rows][cols], westRoom[][] = new int[rows][cols];

    public static int itemM = 0, itemA = 0, itemC = 0, itemH = 0, itemI = 0, itemN = 0, itemE = 0;

    public static boolean playerLife = Boolean.TRUE;

    public static long startTime = System.currentTimeMillis(); // start timer
    public static long elapsedMinutes, elapsedSeconds, elapsedTime;

    public static int itemMX = generateRandom(2,17), itemMY = generateRandom(2,17);
    public static int itemAX = generateRandom(2,17), itemAY = generateRandom(2,17);
    public static int itemCX = generateRandom(2,17), itemCY = generateRandom(2,17);
    public static int itemHX = generateRandom(2,17), itemHY = generateRandom(2,17);
    public static int itemIX = generateRandom(2,17), itemIY = generateRandom(2,17);
    public static int itemNX = generateRandom(2,17), itemNY = generateRandom(2,17);
    public static int itemEX = generateRandom(2,17), itemEY = generateRandom(2,17);

    public static int itemKillerX, itemKillerY, itemKillerXR;
    public static int itemKiller = 0;

    public static int itemMR = generateRandom(0,3);
    public static int itemAR = generateRandom(0,3);
    public static int itemCR = generateRandom(0,3);
    public static int itemHR = generateRandom(0,3);
    public static int itemIR = generateRandom(0,3);
    public static int itemNR = generateRandom(0,3);
    public static int itemER = generateRandom(0,3);

    public static int[][] numRoom(int room){
        if (room == 0){
           return northRoom;
        }
        else if (room == 1){
            return eastRoom;
        }
        else if (room == 2)
        {
            return southRoom;
        }
        else if (room == 3){
            return westRoom;
        }
        return new int[0][];
    }; // turns random rooms to proper array

    static int generateRandom(int min, int max){
        int random = (int)Math.floor(Math.random()*(max-min+1)+min);
        return random;
    }

    public static boolean checkWin(){
        if (itemM+itemA+itemC+itemH+itemI+itemN+itemE == 7){
            playerLife = Boolean.FALSE;
            return true;
        }
        return false;
    }

    public static void main(String[] args){
        JTextField textField = new JTextField();
        textField.addKeyListener(new MKeyListener());
        JFrame jframe = new JFrame();
        jframe.add(textField);
        jframe.setVisible(true);

        gameTable = mainRoom;
        gameTable[playery][playerx] = 1;

        /*ROOMS 1 = MAIN, 2 = NORTH, 3 = SOUTH, 4 = EAST, 5 = WEST*/

        /*8 = floor or roof | 9 = wall | 2, 3, 4, 5, 6 are portals to other rooms*/

        for (int i = 0; i<=10; i++){
            itemKillerX = generateRandom(3,16);
            itemKillerY = generateRandom(3, 16);
            itemKillerXR = generateRandom(0, 3);
            numRoom(itemKillerXR)[itemKillerY][itemKillerX] = 16; // itemKiller
        }

        for (int i = 0; i<=18; i++)
        {
            mainRoom[i][0] = 9;// left boundary
            mainRoom[19][i] = 8;// lower boundary
            mainRoom[i][18] = 9; // right boundary
            mainRoom[0][i] = 8; // upper boundary

            northRoom[i][0] = 9;// left boundary
            northRoom[i][18] = 9;// right boundary
            northRoom[0][i] = 8;// upper boundary
            northRoom[19][i] = 8;// lower boundary

            eastRoom[i][18] = 9;// right boundary
            eastRoom[19][i] = 8;// lower boundary
            eastRoom[i][0] = 9;// left boundary
            eastRoom[0][i] = 8;// upper boundary

            southRoom[19][i] = 8;// lower boundary
            southRoom[i][0] = 9;// left boundary
            southRoom[i][18] = 9;// right boundary
            southRoom[0][i] = 8; // upper boundary

            westRoom[i][18] = 9;// right boundary
            westRoom[19][i] = 8;// lower boundary
            westRoom[i][0] = 9;// left boundary
            westRoom[0][i] = 8;// upper boundary
        }

        mainRoom[0][9] = 2; //portal north
        mainRoom[9][18] = 4;//portal east
        mainRoom[19][9] = 5;// portal south
        mainRoom[9][0] = 6;// portal west
        northRoom[19][9] = 3;//portal to main
        eastRoom[9][0] = 3;// portal to main
        southRoom[0][9] = 3;// portal to main
        westRoom[9][18] = 3;// portal to main

        /////////////////////

        if (itemM == 0)
        {
            numRoom(itemMR)[itemMY][itemMX] = 7; // itemM
        }
        else if (itemA == 0){
            numRoom(itemAR)[itemAY][itemAX] = 10; // itemA
        }
        else if (itemC == 0){
            numRoom(itemCR)[itemCY][itemCX] = 11; // itemC
        }
        else if (itemH == 0){
            numRoom(itemHR)[itemHY][itemHX] = 12; //itemH
        }
        else if (itemI == 0){
            numRoom(itemIR)[itemIY][itemIX] = 13; // itemI
        }
        else if (itemN == 0){
            numRoom(itemNR)[itemNY][itemNX] = 14; // itemN
        }
        else if (itemE == 0){
            numRoom(itemER)[itemEY][itemEX] = 15; // itemE
        }

        /*---------------------------------------------------------*/

        gameEntry();
    }

    public static void drawGame() {

        switch (room)
        {
            case 1:
                System.out.println("Main Room");
                break;
            case 2:
                System.out.println("North Room");
                break;
            case 3:
                System.out.println("South Room");
                break;
            case 4:
                System.out.println("East Room");
                break;
            case 5:
                System.out.println("West Room");
                break;
        }

        System.out.print("Machine Progress: ");

        if (itemM == 0){
            System.out.print("_");
        }
        else {
            System.out.print("M");
        }
        if (itemA == 0){
            System.out.print("_");
        }
        else{
            System.out.print("A");
        }
        if (itemC == 0){
            System.out.print("_");
        }
        else{
            System.out.print("C");
        }
        if (itemH == 0){
            System.out.print("_");
        }
        else{
            System.out.print("H");
        }
        if (itemI == 0){
            System.out.print("_");
        }
        else{
            System.out.print("I");
        }
        if (itemN == 0){
            System.out.print("_");
        }
        else{
            System.out.print("N");
        }
        if (itemE == 0){
            System.out.print("_");
        }
        else{
            System.out.print("E");
        }

        System.out.println("");
        for (int[] row: gameTable)
        {
            for (int col : row)
            {
                switch (col)
                {
                    case 0, 2, 3, 4, 5, 6:
                        System.out.print(" ");
                        break;
                    case 9:
                        System.out.print("|");
                        break;
                    case 8:
                        System.out.print("-");
                        break;
                    case 7:
                        System.out.print("M");
                        break;
                    case 10:
                        System.out.print("A");
                        break;
                    case 11:
                        System.out.print("C");
                        break;
                    case 12:
                        System.out.print("H");
                        break;
                    case 13:
                        System.out.print("I");
                        break;
                    case 14:
                        System.out.print("N");
                        break;
                    case 15:
                        System.out.print("E");
                        break;
                    case 16:
                        System.out.print("X");
                        break;
                    default:
                        System.out.print(col);
                        break;
                }
            }
            System.out.println();
        }
    }

    public static void gameEntry() {
        System.out.println("Machine Game\n\n Rules: You have to build a machine that will let you escape the dungeon which you are trapped in.\n If you stay in the dungeon for over 5 minutes you will die due to toxic gas.\nYou are not able to see how much time you have left until falling to the toxic gas.\n Using the wrong parts while building the machine will kill you.\n Running out of time will kill you.\n Press any key to begin.");
    }

    public static void CLS() throws IOException, InterruptedException {
        new ProcessBuilder("cmd",
                "/c",
                "cls").inheritIO().start().waitFor();
    }

    public static void gameExit(){
        try {
            CLS(); // delete last frame
        } catch (IOException | InterruptedException e){}

        switch (deathMethod){
            case 0:
                System.out.println("Congratulations on surviving!\n It took you: " + elapsedMinutes + " Minutes and " + elapsedSeconds + " seconds.");
                break;
            case 1:
                System.out.println("You died to toxic gas");
                break;
            case 2:
                System.out.println("You picked up the wrong part and got electrocuted. You survived for: " + elapsedMinutes + " Minutes and " + elapsedSeconds + " seconds");
                break;
        }
        System.out.print("Press ENTER to exit.");
    }

    public static void checkTimer(){
        elapsedTime = System.currentTimeMillis() - Main.startTime;
        elapsedSeconds = elapsedTime / 1000;
        elapsedMinutes = elapsedSeconds / 60;
        if (elapsedTime >= 300000)// five minutes = 300000ms
        {
            playerLife = Boolean.FALSE;
            deathMethod = 1;
        }
    }
}

class MKeyListener extends KeyAdapter{

    public void playerMovement(int keyCode){
        switch (keyCode)
        {
            case KeyEvent.VK_D:
                if (Main.gameTable[Main.playery][Main.playerx+1] == 0) {
                    Main.gameTable[Main.playery][Main.playerx] = 0;
                    Main.playerx++;
                }
                break;
            case KeyEvent.VK_A:
                if (Main.gameTable[Main.playery][Main.playerx-1] == 0) {
                    Main.gameTable[Main.playery][Main.playerx] = 0;
                    Main.playerx--;
                }
                break;
            case KeyEvent.VK_S:
                if (Main.gameTable[Main.playery+1][Main.playerx] == 0) {
                    Main.gameTable[Main.playery][Main.playerx] = 0;
                    Main.playery++;
                }
                break;
            case KeyEvent.VK_W:
                if (Main.gameTable[Main.playery-1][Main.playerx] == 0) {
                    Main.gameTable[Main.playery][Main.playerx] = 0;
                    Main.playery--;
                }
        }
    }

    public void gamePortals(){
        if (Main.gameTable[Main.playery-1][Main.playerx] == 2) // main to north
        {
            Main.gameTable = Main.northRoom;
            Main.playery = 17;
            Main.playerx = 9;
            Main.room = 2;
        }
        else if (Main.gameTable[Main.playery][Main.playerx+1] == 4) // main to east
        {
            Main.gameTable = Main.eastRoom;
            Main.playery = 9;
            Main.playerx = 2;
            Main.room = 4;
        }
        else if (Main.gameTable[Main.playery][Main.playerx-1] == 6)// main to west
        {
            Main.gameTable = Main.westRoom;
            Main.playery = 9;
            Main.playerx = 17;
            Main.room = 5;
        }
        else if (Main.gameTable[Main.playery+1][Main.playerx] == 5) // main to south
        {
            Main.gameTable = Main.southRoom;
            Main.playery = 2;
            Main.playerx = 9;
            Main.room = 3;
        }
        else if (Main.gameTable[Main.playery+1][Main.playerx] == 3 && Main.room == 2) // north to main
        {
            Main.gameTable = Main.mainRoom;
            Main.playery = 1;
            Main.playerx = 9;
            Main.room = 1;
        }
        else if (Main.gameTable[Main.playery][Main.playerx-1] == 3 && Main.room == 4) // east to main
        {
            Main.gameTable = Main.mainRoom;
            Main.playery = 9;
            Main.playerx = 17;
            Main.room = 1;
        }
        else if (Main.gameTable[Main.playery][Main.playerx+1] == 3 && Main.room == 5) // west to main
        {
            Main.gameTable = Main.mainRoom;
            Main.playery = 9;
            Main.playerx = 1;
            Main.room = 1;
        }
        else if (Main.gameTable[Main.playery-1][Main.playerx] == 3 && Main.room == 3) // south to main
        {
            Main.gameTable = Main.mainRoom;
            Main.playery = 18;
            Main.playerx = 9;
            Main.room = 1;
        }
    }

    boolean surrounding(int x){
        if  (Main.gameTable[Main.playery +1][Main.playerx] == x | Main.gameTable[Main.playery -1 ][Main.playerx] == x | Main.gameTable[Main.playery][Main.playerx + 1] == x | Main.gameTable[Main.playery][Main.playerx - 1] == x)
            return true;
        return false;
    }

    public void machinePickup(){
        if (surrounding(7))
        {
            Main.itemM = 1;
        }
        else if (surrounding(10)){
            Main.itemA = 1;
        }
        else if (surrounding(11)){
            Main.itemC = 1;
        }
        else if (surrounding(12)){
            Main.itemH = 1;
        }
        else if (surrounding(13)){
            Main.itemI = 1;
        }
        else if (surrounding(14)){
            Main.itemN = 1;
        }
        else if (surrounding(15)){
            Main.itemE = 1;
        }
        else if (surrounding(16)){
            Main.itemKiller = 1;
        }
    }

    void machinePartShell(int item, int itemR, int itemY, int itemX, int itemNum){
        if (item == 0){
            Main.numRoom(itemR)[itemY][itemX] = itemNum;
        }
        else Main.numRoom(itemR)[itemY][itemX] = 0;
    }

    public void machinePart(){
        machinePartShell(Main.itemM, Main.itemMR, Main.itemMY, Main.itemMX, 7);
        machinePartShell(Main.itemA, Main.itemAR, Main.itemAY, Main.itemAX, 10);
        machinePartShell(Main.itemC, Main.itemCR, Main.itemCY, Main.itemCX, 11);
        machinePartShell(Main.itemH, Main.itemHR, Main.itemHY, Main.itemHX, 12);
        machinePartShell(Main.itemI, Main.itemIR, Main.itemIY, Main.itemIX, 13);
        machinePartShell(Main.itemN, Main.itemNR, Main.itemNY, Main.itemNX, 14);
        machinePartShell(Main.itemE, Main.itemER, Main.itemEY, Main.itemEX, 15);
    }

    public void keyPressed(KeyEvent event) {

        try {
            Main.CLS(); // delete last frame on key press
        } catch (IOException | InterruptedException e){}

        playerMovement(event.getKeyCode());
        gamePortals();
        Main.gameTable[Main.playery][Main.playerx] = 1; // make the given cords the actual player
        Main.checkTimer(); // check time
        machinePickup();
        machinePart();
        Main.checkWin();

        if (Main.itemKiller == 1){
            Main.deathMethod = 2;
            Main.playerLife = Boolean.FALSE;
        }

        if (Main.playerLife == Boolean.TRUE)
        {
            Main.drawGame(); // draw frame on key press
        }
        else
        {
            Main.gameExit();
            if (event.getKeyCode() == KeyEvent.VK_ENTER){
                System.exit(0);
            }
        }
    }
}
