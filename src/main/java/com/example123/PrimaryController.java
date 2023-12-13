package com.example123;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PrimaryController {

    @FXML
    private Canvas canvas;
    @FXML
    private Canvas canvas2;

    @FXML
    public void initialize() {
        // Code d'initialisation, si nécessaire
        drawOnCanvas();
    }

    private void drawOnCanvas() {
        // Obtenir le contexte graphique du Canvas
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Dessiner une ligne bleue
        gc.setStroke(Color.BLACK);
        gc.strokeRect(0, 0, canvas.getWidth(), canvas.getHeight());
        double lastX = 0;
        double lastY = 0; 
        drawCoolRect(0,0,400,400);
        drawCoolRect(400, 0, 800, 400);
        drawCoolRect(0, 400, 400, 800);
        drawCoolRect(400, 400, 800, 800);
        drawCoolRect(0, 0, 800, 800);
        
        gc.strokeLine(400, 0, 400, 800);
        gc.strokeLine(0, 400, 800, 400);

    }

    private void drawCoolRect(double xMin, double yMin, double xMax, double yMax){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        //gc.strokeRect(xMin, yMin, xMax, yMax);
        ArrayList<Point> points = new ArrayList<Point>();
        //je veux trouver la position qui est a x% de distance des deux points distants, il faut donc initialiser l'array avec les 4 premieres positions
        Point dest = null;
        points.add(new Point(xMax, yMin));
        points.add(new Point(xMax, yMax));
        points.add(new Point(xMin, yMax));
        points.add(new Point(xMin, yMin));
        for (int i = 0; i < 1000; i++) {
            dest = findDest(points.get(i),points.get(i+1));
            //gc.setStroke(randColor());
            gc.strokeLine(points.get(points.size()-1).getX(), points.get(points.size()-1).getY(), dest.getX(), dest.getY());
            points.add(dest);
        }

        //dest = findDest(points.get(0),points.get(1));
        //gc.strokeLine(points.get(points.size()-1).getX(), points.get(points.size()-1).getY(), dest.getX(), dest.getY());
    }

    private Color randColor(){
        Random random = new Random();
        return Color.rgb(random.nextInt(255),random.nextInt(255),random.nextInt(255));

    }

    private Point findDest(Point point, Point point2) {
        double x = 0.98*point.getX()+0.02*point2.getX();
        double y = 0.98*point.getY()+0.02*point2.getY();
        return new Point(x, y);
    }

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
