/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoflifefx;

import static gameoflifefx.GameOfLifeFX.CELL_NUM;
import static gameoflifefx.GameOfLifeFX.SIZE_X;
import static gameoflifefx.GameOfLifeFX.SIZE_Y;
import static gameoflifefx.GameOfLifeFX.calcNeighbor;
import static gameoflifefx.GameOfLifeFX.generateSeed;
import static gameoflifefx.GameOfLifeFX.nextGeneration;
import static gameoflifefx.GameOfLifeFX.printState;
import static gameoflifefx.GameOfLifeFX.startLife;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.util.Duration;
/**
 *
 * @author Reece
 */
public class StateDisplay extends Application  {
    
        private double gameSpeedSecs = 0.016667;
           private boolean isPaused = false;
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Game of Life");
        GridPane grid = new GridPane();        
        grid.setHgap(10);
        grid.setVgap(10);
        Label iteration = new Label("Iteration Base");
        grid.add(iteration, SIZE_X+1, SIZE_Y+1);
        
        //Dummy scene
        Group root = new Group();
        Scene theScene = new Scene(root);
        root.getChildren().add(grid);
        
        Group root2 = new Group();
        Scene finalScene = new Scene(root2, 1000, 1000);


        primaryStage.setScene(finalScene);
        
        Canvas canvas = new Canvas();
        root2.getChildren().add(canvas);       
        GraphicsContext gc = canvas.getGraphicsContext2D();
       
        //Initialize seed 
        List<Cell> cells = new ArrayList<>();
        List<Rectangle> rectangleList = new ArrayList<>();       
        cells = generateSeed(CELL_NUM, startLife, cells);
        calcNeighbor(cells);
               
        rectangleList = initCellRectangles(cells, rectangleList);     
        for(int i=0; i < CELL_NUM; i++){
            grid.add(rectangleList.get(i), i % GameOfLifeFX.SIZE_X, (int) i/GameOfLifeFX.SIZE_Y);                
        } 
        
        
        Image[] imageArray = new Image[1000];                  
        for(int i = 0; i < 1000; i++){
            
            nextGeneration(cells);
            updateRectangleList(Cell.getCellStateChange(), cells, rectangleList);
            iteration.setText("Iteration "+i);

           // imageArray[i] = theScene.snapshot(imageArray[i]);
            Cell.clearCellStateChange();                      
        }
        
        MainLoop mainLoop = new MainLoop((long)(gameSpeedSecs * 1000000000L),
            elapsedNS -> {

                Color backgroundColor = Color.WHITE;
                gc.setFill(backgroundColor);
                gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                //Loop over the environment, drawing a cell if it's alive
                for(int i = 0; i < GameOfLifeFX.CELL_NUM; i++){
                if(cells.get(i).getLife()){ 
                    gc.setFill(GameOfLifeFX.LIFE_COLOR);
                } else {
                    gc.setFill(Color.WHITE);
                }
                gc.fillRect(i % GameOfLifeFX.SIZE_X, (int) i/GameOfLifeFX.SIZE_Y, GameOfLifeFX.CELL_SIZE_W, GameOfLifeFX.CELL_SIZE_H);
                }
            });     

                env.simGeneration(0, cellsWide, 0, cellsHigh);

            }
        });

        mainLoop.start();

        primaryStage.show();
    }
    

      
    //initializes the rectangle objects for the grid so that they can be drawn/modified
    public List<Rectangle> initCellRectangles(List<Cell> cellMap, List<Rectangle> cellList) {
        for(Cell cellLife : cellMap) {
            Rectangle r = new Rectangle();
            r.setWidth(GameOfLifeFX.CELL_SIZE_W);
            r.setHeight(GameOfLifeFX.CELL_SIZE_H);
            //If cell is alive then fill with set color, else paint WHITE
            if(cellLife.getLife()){ 
                r.setFill(GameOfLifeFX.LIFE_COLOR);
            } else {
                r.setFill(Color.WHITE);
            }
            cellList.add(r);
            
        }
        return cellList;
    }
    
    public void updateRectangleList(List<Cell> changeList, List<Cell> wholeMap, List<Rectangle> rectangleList) {
        for(Cell updatedCell : changeList){ 
            int index = wholeMap.indexOf(updatedCell);
            changeFill(rectangleList.get(index), updatedCell.getLife());
        }
    }
    
    public void changeFill(Rectangle r, Boolean cellLife){
        if(cellLife){
            r.setFill(GameOfLifeFX.LIFE_COLOR);
        }else{
            r.setFill(Color.WHITE);
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
