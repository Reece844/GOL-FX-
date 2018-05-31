package gameoflifefx;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Reece
 */
public class GameOfLifeFX {    
    
    public static final int SIZE_X = 20;
    public static final int SIZE_Y = 20; 
    public static final int CELL_NUM = SIZE_X*SIZE_Y;
    public static final double startLife = .30; //% of grid to start game with
    public static final int CELL_SIZE_W = 20;
    public static final int CELL_SIZE_H = 20; 
    public static final Color LIFE_COLOR = Color.BLUE;

//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String[] args) {
//        List<Cell> cells = new ArrayList<>();
//        cells = generateSeed(CELL_NUM, startLife, cells);
//        calcNeighbor(cells);
//        System.out.println("Start Seed");
//        printState(cells);
//        System.out.println("");   
//        for(int i = 0; i < 1000; i++){
//            System.out.println("Iteration "+i);
//            cells = nextGeneration(cells);
//            printState(cells);
//            System.out.println(""); 
//        }
//    }
    
    public void nextGeneration(List<Cell> cellGrid) {
        for(int i = 0; i <CELL_NUM; i++) { 
            Cell cellCheck = cellGrid.get(i);
            cellCheck.setNeighborCount(cellCheck.findNeighbors());
            if(cellCheck.getLife()){
                if(cellCheck.getNeighborCount() != 2 && cellCheck.getNeighborCount() != 3) {
                    cellCheck.setcellLifeTemp(false);
                    Cell.addCellStateChange(cellCheck);
                } else {
                    cellCheck.setcellLifeTemp(true);
                }
            }else{
                if(cellCheck.getNeighborCount() == 3) {
                    Cell.addCellStateChange(cellCheck);
                    cellCheck.setcellLifeTemp(true);
                }else{
                    cellCheck.setcellLifeTemp(false);
                }
            }                   
        }
        for(int i = 0; i <CELL_NUM; i++){
             Cell cellCheck = cellGrid.get(i);
             cellCheck.setLife(cellCheck.getcellLifeTemp());
        }
    }
    
    public static List<Cell> generateSeed(int cellNum, double lifeChance, List<Cell> cellGrid) {
        Random randSeed = new Random();
        cellGrid.clear();
        for(int i = 0; i<cellNum; i++) {
            Cell newCell = new Cell(i % SIZE_X, (int) i/SIZE_Y);
            
            if(randSeed.nextDouble() <= lifeChance) {
                newCell.setLife(true);
                newCell.setcellLifeTemp(true);
            }
            cellGrid.add(newCell);
        }
        return cellGrid;
    }
    
    public static void calcNeighbor(List<Cell> grid){
        Cell boundryCell = new Cell(); //Generates the dead cell to represent out of bounds dead cells for reference
        for(int i = 0; i < CELL_NUM; i++) {
            Cell tempCell = grid.get(i);
            if(!isBoundryCell(grid, i)){            
                tempCell.setNeighborCellIndex(grid.get(i-21), 0);
                tempCell.setNeighborCellIndex(grid.get(i-20), 1); 
                tempCell.setNeighborCellIndex(grid.get(i-19), 2);
                tempCell.setNeighborCellIndex(grid.get(i-1), 3);
                tempCell.setNeighborCellIndex(grid.get(i+1), 4);
                tempCell.setNeighborCellIndex(grid.get(i+19), 5);
                tempCell.setNeighborCellIndex(grid.get(i+20), 6);
                tempCell.setNeighborCellIndex(grid.get(i+21), 7);
                continue;
            }
            
            int edgeCase = 0;
            /*
             * Top Left = 1 Top Middle = 2 Top Right = 3
             * Bottom Left = -3 Bottom Middle = -2 Bottom Right = -1
            */
//             Top Left = 2 Top Middle = 3 Top Right = 4
//             * Bottom Left = -4 Bottom Middle = -3 Bottom Right = -2
//             * Left = -1 Right = 1
            
            if(tempCell.getY_Pos() == 0) {
                tempCell.setNeighborCellIndex(boundryCell, 0);
                tempCell.setNeighborCellIndex(boundryCell, 1);
                tempCell.setNeighborCellIndex(boundryCell, 2);
                edgeCase = 3;
            }else if(tempCell.getY_Pos() == SIZE_Y-1) {
                tempCell.setNeighborCellIndex(boundryCell, 5);
                tempCell.setNeighborCellIndex(boundryCell, 6);
                tempCell.setNeighborCellIndex(boundryCell, 7); 
                edgeCase = -3;                
            }
            if(tempCell.getX_Pos() == 0 ) {
                tempCell.setNeighborCellIndex(boundryCell, 0);
                tempCell.setNeighborCellIndex(boundryCell, 3);
                tempCell.setNeighborCellIndex(boundryCell, 5);
                edgeCase--;
            }else if(tempCell.getX_Pos() == SIZE_X-1) {
                tempCell.setNeighborCellIndex(boundryCell, 2);
                tempCell.setNeighborCellIndex(boundryCell, 4);
                tempCell.setNeighborCellIndex(boundryCell, 7);
                edgeCase++;
            }
            switch(edgeCase){
                case 2:
                    tempCell.setNeighborCellIndex(grid.get(i+1), 4);
                    tempCell.setNeighborCellIndex(grid.get(i+20), 6);
                    tempCell.setNeighborCellIndex(grid.get(i+21), 7);
                    break;
                case 3:
                    tempCell.setNeighborCellIndex(grid.get(i-1), 3);
                    tempCell.setNeighborCellIndex(grid.get(i+1), 4);
                    tempCell.setNeighborCellIndex(grid.get(i+19), 5);
                    tempCell.setNeighborCellIndex(grid.get(i+20), 6);
                    tempCell.setNeighborCellIndex(grid.get(i+21), 7);
                    break;
                case 4:
                    tempCell.setNeighborCellIndex(grid.get(i-1), 3);
                    tempCell.setNeighborCellIndex(grid.get(i+19), 5);
                    tempCell.setNeighborCellIndex(grid.get(i+20), 6);                    
                    break;
                case -2:
                    tempCell.setNeighborCellIndex(grid.get(i-21), 0);
                    tempCell.setNeighborCellIndex(grid.get(i-20), 1);
                    tempCell.setNeighborCellIndex(grid.get(i-1), 3);
                    break;
                case -3:
                    tempCell.setNeighborCellIndex(grid.get(i-21), 0);
                    tempCell.setNeighborCellIndex(grid.get(i-20), 1); 
                    tempCell.setNeighborCellIndex(grid.get(i-19), 2);
                    tempCell.setNeighborCellIndex(grid.get(i-1), 3);
                    tempCell.setNeighborCellIndex(grid.get(i+1), 4);
                    break;
                case -4:
                    tempCell.setNeighborCellIndex(grid.get(i-20), 1); 
                    tempCell.setNeighborCellIndex(grid.get(i-19), 2);
                    tempCell.setNeighborCellIndex(grid.get(i+1), 4);                  
                    break;
                case -1:
                    tempCell.setNeighborCellIndex(grid.get(i-20), 1); 
                    tempCell.setNeighborCellIndex(grid.get(i-19), 2);
                    tempCell.setNeighborCellIndex(grid.get(i+1), 4);
                    tempCell.setNeighborCellIndex(grid.get(i+20), 6);
                    tempCell.setNeighborCellIndex(grid.get(i+21), 7);                  
                    break;
                case 1:
                    tempCell.setNeighborCellIndex(grid.get(i-21), 0);
                    tempCell.setNeighborCellIndex(grid.get(i-20), 1); 
                    tempCell.setNeighborCellIndex(grid.get(i-1), 3);
                    tempCell.setNeighborCellIndex(grid.get(i+19), 5);
                    tempCell.setNeighborCellIndex(grid.get(i+20), 6);               
                    break;                    
            }
        }       
    }
    
    public static boolean isBoundryCell(List<Cell> grid, int index) {
        if(grid.get(index).getY_Pos() == 0 || 
           grid.get(index).getY_Pos() == SIZE_Y-1 ||
           grid.get(index).getX_Pos() == 0 ||
           grid.get(index).getX_Pos() == SIZE_X-1 ) {
            return true;
        }
        return false;
    }
    
    public static void printState(List<Cell> cells) {
        for(Cell gridIndex : cells){
            System.out.print(gridIndex);
          
        }
    }    
    
}
