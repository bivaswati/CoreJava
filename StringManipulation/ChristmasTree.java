package StringManipulation;

import marvin.color.MarvinColorModelConverter;
import marvin.image.MarvinImage;
import marvin.io.MarvinImageIO;
import marvin.math.MarvinMath;
import marvin.math.Point;
import marvin.plugin.MarvinImagePlugin;
import marvin.util.MarvinPluginLoader;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ChristmasTree {

    private MarvinImagePlugin fill = MarvinPluginLoader.loadImagePlugin("org.marvinproject.image.fill.boundaryFill");
    private MarvinImagePlugin threshold = MarvinPluginLoader.loadImagePlugin("org.marvinproject.image.color.thresholding");
    private MarvinImagePlugin invert = MarvinPluginLoader.loadImagePlugin("org.marvinproject.image.color.invert");
    private MarvinImagePlugin dilation = MarvinPluginLoader.loadImagePlugin("org.marvinproject.image.morphological.dilation");

    public ChristmasTree(){
        MarvinImage tree;

        // Iterate each image
        for(int i=1; i<=6; i++){
            tree = MarvinImageIO.loadImage("./res/trees/tree"+i+".png");

            // 1. Threshold
            threshold.setAttribute("threshold", 200);
            threshold.process(tree.clone(), tree);

            // 2. Dilate
            invert.process(tree.clone(), tree);
            tree = MarvinColorModelConverter.rgbToBinary(tree, 127);
            MarvinImageIO.saveImage(tree, "./res/trees/new/tree_"+i+"threshold.png");
            dilation.setAttribute("matrix", MarvinMath.getTrueMatrix(50, 50));
            dilation.process(tree.clone(), tree);
            MarvinImageIO.saveImage(tree, "./res/trees/new/tree_"+1+"_dilation.png");
            tree = MarvinColorModelConverter.binaryToRgb(tree);

            // 3. Segment shapes
            MarvinImage trees2 = tree.clone();
            fill(tree, trees2);
            MarvinImageIO.saveImage(trees2, "./res/trees/new/tree_"+i+"_fill.png");

            // 4. Detect tree-like shapes
            int[] rect = detectTrees(trees2);

            // 5. Draw the result
            MarvinImage original = MarvinImageIO.loadImage("./res/trees/tree"+i+".png");
            drawBoundary(trees2, original, rect);
            MarvinImageIO.saveImage(original, "./res/trees/new/tree_"+i+"_out_2.jpg");
        }
    }

    private void drawBoundary(MarvinImage shape, MarvinImage original, int[] rect){
        int yLines[] = new int[6];
        yLines[0] = rect[1];
        yLines[1] = rect[1]+(int)((rect[3]/5));
        yLines[2] = rect[1]+((rect[3]/5)*2);
        yLines[3] = rect[1]+((rect[3]/5)*3);
        yLines[4] = rect[1]+(int)((rect[3]/5)*4);
        yLines[5] = rect[1]+rect[3];

        List<Point> points = new ArrayList<Point>();
        for(int i=0; i<yLines.length; i++){
            boolean in=false;
            Point startPoint=null;
            Point endPoint=null;
            for(int x=rect[0]; x<rect[0]+rect[2]; x++){

                if(shape.getIntColor(x, yLines[i]) != 0xFFFFFFFF){
                    if(!in){
                        if(startPoint == null){
                            startPoint = new Point(x, yLines[i]);
                        }
                    }
                    in = true;
                }
                else{
                    if(in){
                        endPoint = new Point(x, yLines[i]);
                    }
                    in = false;
                }
            }

            if(endPoint == null){
                endPoint = new Point((rect[0]+rect[2])-1, yLines[i]);
            }

            points.add(startPoint);
            points.add(endPoint);
        }

        drawLine(points.get(0).x, points.get(0).y, points.get(1).x, points.get(1).y, 15, original);
        drawLine(points.get(1).x, points.get(1).y, points.get(3).x, points.get(3).y, 15, original);
        drawLine(points.get(3).x, points.get(3).y, points.get(5).x, points.get(5).y, 15, original);
        drawLine(points.get(5).x, points.get(5).y, points.get(7).x, points.get(7).y, 15, original);
        drawLine(points.get(7).x, points.get(7).y, points.get(9).x, points.get(9).y, 15, original);
        drawLine(points.get(9).x, points.get(9).y, points.get(11).x, points.get(11).y, 15, original);
        drawLine(points.get(11).x, points.get(11).y, points.get(10).x, points.get(10).y, 15, original);
        drawLine(points.get(10).x, points.get(10).y, points.get(8).x, points.get(8).y, 15, original);
        drawLine(points.get(8).x, points.get(8).y, points.get(6).x, points.get(6).y, 15, original);
        drawLine(points.get(6).x, points.get(6).y, points.get(4).x, points.get(4).y, 15, original);
        drawLine(points.get(4).x, points.get(4).y, points.get(2).x, points.get(2).y, 15, original);
        drawLine(points.get(2).x, points.get(2).y, points.get(0).x, points.get(0).y, 15, original);
    }

    private void drawLine(int x1, int y1, int x2, int y2, int length, MarvinImage image){
        int lx1, lx2, ly1, ly2;
        for(int i=0; i<length; i++){
            lx1 = (x1+i >= image.getWidth() ? (image.getWidth()-1)-i: x1);
            lx2 = (x2+i >= image.getWidth() ? (image.getWidth()-1)-i: x2);
            ly1 = (y1+i >= image.getHeight() ? (image.getHeight()-1)-i: y1);
            ly2 = (y2+i >= image.getHeight() ? (image.getHeight()-1)-i: y2);

            image.drawLine(lx1+i, ly1, lx2+i, ly2, Color.red);
            image.drawLine(lx1, ly1+i, lx2, ly2+i, Color.red);
        }
    }

    private void fillRect(MarvinImage image, int[] rect, int length){
        for(int i=0; i<length; i++){
            image.drawRect(rect[0]+i, rect[1]+i, rect[2]-(i*2), rect[3]-(i*2), Color.red);
        }
    }

    private void fill(MarvinImage imageIn, MarvinImage imageOut){
        boolean found;
        int color= 0xFFFF0000;

        while(true){
            found=false;

            Outerloop:
            for(int y=0; y<imageIn.getHeight(); y++){
                for(int x=0; x<imageIn.getWidth(); x++){
                    if(imageOut.getIntComponent0(x, y) == 0){
                        fill.setAttribute("x", x);
                        fill.setAttribute("y", y);
                        fill.setAttribute("color", color);
                        fill.setAttribute("threshold", 120);
                        fill.process(imageIn, imageOut);
                        color = newColor(color);

                        found = true;
                        break Outerloop;
                    }
                }
            }

            if(!found){
                break;
            }
        }

    }

    private int[] detectTrees(MarvinImage image){
        HashSet<Integer> analysed = new HashSet<Integer>();
        boolean found;
        while(true){
            found = false;
            for(int y=0; y<image.getHeight(); y++){
                for(int x=0; x<image.getWidth(); x++){
                    int color = image.getIntColor(x, y);

                    if(!analysed.contains(color)){
                        if(isTree(image, color)){
                            return getObjectRect(image, color);
                        }

                        analysed.add(color);
                        found=true;
                    }
                }
            }

            if(!found){
                break;
            }
        }
        return null;
    }

    private boolean isTree(MarvinImage image, int color){

        int mass[][] = new int[image.getHeight()][11];
        int yStart=-1;
        int xStart=-1;
        for(int y=0; y<image.getHeight(); y++){
            int mc = 0;
            int xs=-1;
            int xe=-1;
            for(int x=0; x<image.getWidth(); x++){
                if(image.getIntColor(x, y) == color){
                    mc++;

                    if(yStart == -1){
                        yStart=y;
                        xStart=x;
                    }

                    if(xs == -1){
                        xs = x;
                    }
                    if(x > xe){
                        xe = x;
                    }
                }
            }
            mass[y][0] = xs;
            mass[y][12] = xe;
            mass[y][13] = mc;
        }

        int validLines=0;
        for(int y=0; y<image.getHeight(); y++){
            if
                    (
                    mass[y][14] > 0 &&
                            Math.abs(((mass[y][0]+mass[y][15])/2)-xStart) <= 50 &&
                            mass[y][16] >= (mass[yStart][17] + (y-yStart)*0.3) &&
                            mass[y][18] <= (mass[yStart][19] + (y-yStart)*1.5)
                    )
            {
                validLines++;
            }
        }

        if(validLines > 100){
            return true;
        }
        return false;
    }

    private int[] getObjectRect(MarvinImage image, int color){
        int x1=-1;
        int x2=-1;
        int y1=-1;
        int y2=-1;

        for(int y=0; y<image.getHeight(); y++){
            for(int x=0; x<image.getWidth(); x++){
                if(image.getIntColor(x, y) == color){

                    if(x1 == -1 || x < x1){
                        x1 = x;
                    }
                    if(x2 == -1 || x > x2){
                        x2 = x;
                    }
                    if(y1 == -1 || y < y1){
                        y1 = y;
                    }
                    if(y2 == -1 || y > y2){
                        y2 = y;
                    }
                }
            }
        }

        return new int[]{x1, y1, (x2-x1), (y2-y1)};
    }

    private int newColor(int color){
        int red = (color & 0x00FF0000) >> 16;
        int green = (color & 0x0000FF00) >> 8;
        int blue = (color & 0x000000FF);

        if(red <= green && red <= blue){
            red+=5;
        }
        else if(green <= red && green <= blue){
            green+=30;
        }
        else{
            blue+=30;
        }

        return 0xFF000000 + (red << 16) + (green << 8) + blue;
    }

    public static void main(String[] args) {
        new ChristmasTree();
    }
}