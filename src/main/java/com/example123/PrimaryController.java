package com.example123;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import packageIO.SVGFileHandler;

import java.lang.Math;
import java.text.SimpleDateFormat;

public class PrimaryController {

    @FXML
    private Canvas canvas;

    SVGFileHandler fileHandler;

    @FXML
    public void initialize() {
        // Code d'initialisation, si nécessaire
        drawOnCanvas();
    }

    private void drawOnCanvas() {
        // Obtenir le contexte graphique du Canvas
        GraphicsContext gc = canvas.getGraphicsContext2D();
        // Choix de la couleur
        gc.setStroke(Color.BLACK);
        //creation du fichier svg
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
        System.out.println("choisir le nom du fichier svg a créer");
        Scanner input = new Scanner(System.in);
        String filePath = "src/Data/" + input.nextLine()+formatter.format(calendar.getTime())+".svg";
        fileHandler = new SVGFileHandler(filePath);
        input.close();

        



        
        //gc.strokeRect(0, 0, canvas.getWidth(), canvas.getHeight());
        /*drawFractale(400,0,800,800,0,800);
        drawFractale(400,0,800,800,800,0);
        drawFractale(400,0,0,800,0,0);*/
        /*drawFractale(0,0,0,400,400,0);
        drawFractale(400,0,400,400,0,400);
        drawFractale(0,400,400,400,0,800);
        drawFractale(400,400,0,800,400,800);
        drawFractale(400,0,400,400,800,0);
        drawFractale(800,0,800,400,400,400);
        drawFractale(400,400,800,400,400,800);
        drawFractale(800,400,400,800,800,800);*/
        /*drawCoolCarre(0, 0, 400, 400, 0, false);
        drawCoolCarre(400, 0, 800, 400, 0, true);
        drawCoolCarre(0, 400, 400, 800, 0, false);
        drawCoolCarre(400, 400, 800, 800, 0, true);*/
        //drawFractale(800,400, 600,746, 200,746, 0,400, 200,54, 600,54);

        //drawFractale(400,400,800,800,800,0);



                
        //drawFractale(0,0,500,0,500,500,600,500,600,800,800,800,800,0);
        drawRegPolygon(500, 6, 0, false);
        try {
            fileHandler.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void drawCoolCarre(int xMin, int yMin, int xMax, int yMax, int orient, boolean oppose){
        int width = xMax - xMin;
        int height = yMax - yMin;
        drawFractale(xMin,yMin,xMin+width,yMin,xMin,yMin+height);
        drawFractale(xMin+width,yMin,xMin,yMin+height,xMin+width,yMin+height);
    }

    private void drawRegPolygon(int radius, int nbSummits, int offset, boolean oppose){
        double[] coordinates = new double[2*nbSummits];
        double radOffset = Math.toRadians(offset);
        double summitOffset = 360/nbSummits;
        for (int i = 0; i < nbSummits; i++) {
            coordinates[i*2]=Math.cos(radOffset+Math.toRadians(i*summitOffset))*radius+radius;
            coordinates[i*2+1]=Math.sin(radOffset+Math.toRadians(i*summitOffset))*radius+radius;
        }
        drawFractale(coordinates);
    }

    private void drawCoolRect(double xMin, double yMin, double xMax, double yMax){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        //gc.strokeRect(xMin, yMin, xMax, yMax);
        ArrayList<Point> points = new ArrayList<>();
        //je veux trouver la position qui est a x% de distance des deux points distants, il faut donc initialiser l'array avec les 4 premieres positions
        Point dest = null;
        points.add(new Point(xMax, yMin));
        points.add(new Point(xMax, yMax));
        points.add(new Point(xMin, yMax));
        points.add(new Point(xMin, yMin));
        for (int i = 0; i < 10000; i++) {
            dest = findDest(points.get(i),points.get(i+1));
            //gc.setStroke(randColor());
            gc.strokeLine(points.get(points.size()-1).getX(), points.get(points.size()-1).getY(), dest.getX(), dest.getY());
            points.add(dest);
        }

        //dest = findDest(points.get(0),points.get(1));
        //gc.strokeLine(points.get(points.size()-1).getX(), points.get(points.size()-1).getY(), dest.getX(), dest.getY());
    }

    private void drawFractale(double... elements){
        ArrayList<Point> points = new ArrayList<>();
        //on range les données dans un arrayList de points 2 par 2
        for (int i = 0; i < elements.length; i+=2) {
            points.add(new Point(elements[i],elements[i+1]));
        }

        GraphicsContext gc = canvas.getGraphicsContext2D();
        //gc.strokeRect(xMin, yMin, xMax, yMax);
        Point dest = null;
        for (int i = 0; i < points.size()-1; i++) {
            gc.strokeLine(points.get(i).getX(), points.get(i).getY(), points.get(i+1).getX(), points.get(i+1).getY());
        }
        gc.strokeLine(points.get(points.size()-1).getX(), points.get(points.size()-1).getY(), points.get(0).getX(), points.get(0).getY());

        for (int i = 0; i < 10000; i++) {
            dest = findDest(points.get(i),points.get(i+1));
            //gc.setStroke(randColor());
            gc.strokeLine(points.get(points.size()-1).getX(), points.get(points.size()-1).getY(), dest.getX(), dest.getY());
            fileHandler.addLine(points.get(points.size()-1).getX(), points.get(points.size()-1).getY(), dest.getX(), dest.getY());
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
