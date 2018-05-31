
package gameoflifefx;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Reece
 */
public class Cell {
    public Cell(int x, int y) {
        x_Pos = x;
        y_Pos = y; 
        cellLife = false;
        cellLifeTemp = false;
        neighborCells = new Cell[8];   
    }
    
    public Cell() {
        x_Pos = -1;
        y_Pos = -1; 
        cellLife = false; 
        neighborCells = new Cell[8];
    }
    
    private int x_Pos;    
    private int y_Pos;
    private int boundStatus;
    private boolean cellLife; 
    private boolean cellLifeTemp;
    private int neighborCount;   
    public Cell[] neighborCells;
    
    private static List<Cell> cellStateChange = new ArrayList<>();
    
    public int findNeighbors(){
        int counter = 0;    
        for(int i = 0; i< 8; i++){
            if(this.getNeighborCells()[i].getLife() == true){
                counter++;
            }
        }
        return counter;
    }

    @Override
    public String toString() {
        String output = "-";
        if(this.cellLife){
            output = "@";
        }
        if(x_Pos == 19){
            output += "\n";
        }
        return output;
    }
    
    public void testString(int ind) {
        for(int i = 0; i < 8; i++) {
            if(this.neighborCells[i].cellLife) {
                System.out.print("@");
            }else{
                System.out.print("-");
            }
            if(i == 3){
                System.out.print(ind);
            } 
            if(i == 2 || i == 4 || i == 7) {
                System.out.println("");
            }
        }
    }
    
    /**
     * @return the cellStateChange
     */
    public static List<Cell> getCellStateChange() {
        return cellStateChange;
    }

    /**
     * @param cellStateChange Adds a Cell to the list to be changed
     */
    public static void addCellStateChange(Cell cellStateChange) {
        Cell.cellStateChange.add(cellStateChange);
    }
    /**
     * @param cellStateChange Clears the list of cells to be changed
     */
    public static void clearCellStateChange() {
        Cell.cellStateChange.clear();
    }
    
    public void setLife(boolean life) {
        this.cellLife = life;
    }
    
    public boolean getLife() {
         return this.cellLife;
    }

    public Cell[] getNeighborCells() {
        return this.neighborCells;
    }

    public void setNeighborCellIndex(Cell neighborCell, int neighborIndex) {
        this.neighborCells[neighborIndex] = neighborCell;
    }

    /**
     * Get the value of neighborCount
     */
    public int getNeighborCount() {
        return neighborCount;
    }

    /**
     * Set the value of neighborCount
     */
    public void setcellLifeTemp(boolean cellLifeTemp) {
        this.cellLifeTemp = cellLifeTemp;
    }
    
    public boolean getcellLifeTemp() {
        return cellLifeTemp;
    }

    /**
     * Set the value of neighborCount
     */
    public void setNeighborCount(int neighborCount) {
        this.neighborCount = neighborCount;
    }

    /**
     * Get the value of y_Pos
     */
    public int getY_Pos() {
        return y_Pos;
    }

    /**
     * Set the value of y_Pos
     */
    public void setY_Pos(int y_Pos) {
        this.y_Pos = y_Pos;
    }


    /**
     * Get the value of x_Pos
     */
    public int getX_Pos() {
        return x_Pos;
    }

    /**
     * Set the value of x_Pos
     */
    public void setX_Pos(int x_Pos) {
        this.x_Pos = x_Pos;
    }

    
}
