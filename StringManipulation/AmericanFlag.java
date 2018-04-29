package StringManipulation;

import marvin.color.MarvinColorModelConverter;
import marvin.image.MarvinImage;
import marvin.io.MarvinImageIO;
import marvin.math.MarvinMath;

import java.awt.*;

import static marvin.MarvinPluginCollection.*;

public class AmericanFlag {

    public AmericanFlag(){
        process("D:\\IntelliJ\\GitDemo\\CoreJava\\StringManipulation\\", "flag_0", Color.yellow);
        //process("D:\\IntelliJ\\GitDemo\\CoreJava\\StringManipulation\\", "flag_1", Color.yellow);
        //process("D:\\IntelliJ\\GitDemo\\CoreJava\\StringManipulation\\", "flag_2", Color.yellow);
        //process("D:\\IntelliJ\\GitDemo\\CoreJava\\StringManipulation\\", "flag_3", Color.yellow);
        //process("D:\\IntelliJ\\GitDemo\\CoreJava\\StringManipulation\\", "flag_4", Color.blue);
    }

    private void process(String dir, String fileName, Color color){
        MarvinImage originalImage = MarvinImageIO.loadImage(dir+fileName+".png");
        MarvinImage image = originalImage.clone();
        colorFilter(image);
        MarvinImageIO.saveImage(image, dir+fileName+"_color.png");

        MarvinImage output = new MarvinImage(image.getWidth(), image.getHeight());
        output.clear(0xFFFFFFFF);
        findStripsH(image, output);
        findStripsV(image, output);
        MarvinImageIO.saveImage(output, dir+fileName+"_1.png");

        MarvinImage bin = MarvinColorModelConverter.rgbToBinary(output, 127);
        morphologicalErosion(bin.clone(), bin, MarvinMath.getTrueMatrix(5, 5));
        morphologicalDilation(bin.clone(), bin, MarvinMath.getTrueMatrix(15, 15));
        MarvinImageIO.saveImage(bin, dir+fileName+"_2.png");

        int[] centroid = getCentroid(bin);
        image.fillRect(centroid[0], centroid[1], 30, 30, Color.yellow);

        int area = getMass(bin);
        boolean blueNeighbors = hasBlueNeighbors(image, bin, centroid[0], centroid[1], area);

        if(blueNeighbors){
            int[] seg = getSegment(bin);
            for(int i=0; i<4; i++){
                originalImage.drawRect(seg[0]+i, seg[1]+i, seg[2]-seg[0], seg[3]-seg[1], color);
            }
            MarvinImageIO.saveImage(originalImage, dir+fileName+"_final.png");
        }
    }

    private boolean hasBlueNeighbors(MarvinImage image, MarvinImage bin, int centerX, int centerY, int area){
        int totalBlue=0;
        int r,g,b;
        int maxDistance =  (int)(Math.sqrt(area)*1.2);
        for(int y=0; y<image.getHeight(); y++){
            for(int x=0; x<image.getWidth(); x++){
                r = image.getIntComponent0(x, y);
                g = image.getIntComponent1(x, y);
                b = image.getIntComponent2(x, y);

                if(
                        (b == 255 && r == 0 && g == 0) &&
                                (MarvinMath.euclideanDistance(x, y, centerX, centerY) < maxDistance)
                        ){
                    totalBlue++;
                    bin.setBinaryColor(x, y, true);
                }
            }
        }

        if(totalBlue > area/5){
            return true;
        }
        return false;
    }

    private int[] getCentroid(MarvinImage bin){
        long totalX=0, totalY=0, totalPixels=0;
        for(int y=0; y<bin.getHeight(); y++){
            for(int x=0; x<bin.getWidth(); x++){

                if(bin.getBinaryColor(x, y)){
                    totalX += x;
                    totalY += y;
                    totalPixels++;
                }
            }
        }

        totalPixels = Math.max(1, totalPixels);
        return new int[]{(int)(totalX/totalPixels), (int)(totalY/totalPixels)};
    }

    private int getMass(MarvinImage bin){
        int totalPixels=0;
        for(int y=0; y<bin.getHeight(); y++){
            for(int x=0; x<bin.getWidth(); x++){
                if(bin.getBinaryColor(x, y)){
                    totalPixels++;
                }
            }
        }

        return totalPixels;
    }

    private int[] getSegment(MarvinImage bin){
        int x1=-1, x2=-1, y1=-1, y2=-1;
        for(int y=0; y<bin.getHeight(); y++){
            for(int x=0; x<bin.getWidth(); x++){
                if(bin.getBinaryColor(x, y)){

                    if(x1 == -1 || x < x1){ x1 = x; }
                    if(x2 == -1 || x > x2){ x2 = x; }
                    if(y1 == -1 || y < y1){ y1 = y; }
                    if(y2 == -1 || y > y2){ y2 = y; }
                }
            }
        }
        return new int[]{x1,y1,x2,y2};
    }

    private void findStripsH(MarvinImage imageIn, MarvinImage imageOut){

        int strips=0;
        int totalPixels=0;
        int r,g,b;
        int patternStart;
        boolean cR=true;
        int patternLength = -1;
        for(int y=0; y<imageIn.getHeight(); y++){
            patternStart = -1;
            strips = 0;
            patternLength=-1;
            for(int x=0; x<imageIn.getWidth(); x++){
                r = imageIn.getIntComponent0(x, y);
                g = imageIn.getIntComponent1(x, y);
                b = imageIn.getIntComponent2(x, y);

                if(cR){
                    if(r == 255 && g == 0 && b == 0){
                        if(patternStart == -1){ patternStart = x;}
                        totalPixels++;
                    } else{
                        if(patternLength == -1){
                            if(totalPixels >=3 && totalPixels <= 100){
                                patternLength = (int)(totalPixels);
                            } else{
                                totalPixels=0; patternStart=-1; strips=0; patternLength=-1;
                            }
                        } else{
                            if(totalPixels >= Math.max(patternLength*0.5,3) && totalPixels <= patternLength * 2){
                                strips++;
                                totalPixels=1;
                                cR = false;
                            } else{
                                totalPixels=0; patternStart=-1; strips=0; patternLength=-1;
                            }
                        }
                    }
                }
                else{
                    if(r == 255 && g == 255 && b == 255){
                        totalPixels++;
                    } else{
                        if(totalPixels >= Math.max(patternLength*0.5,3) && totalPixels <= patternLength * 2){
                            strips++;
                            totalPixels=1;
                            cR = true;
                        } else{
                            totalPixels=0; patternStart=-1; strips=0; patternLength=-1; cR=true;
                        }
                    }
                }


                if(strips >= 4){
                    imageOut.fillRect(patternStart, y, x-patternStart, 2, Color.black);
                    totalPixels=0; patternStart=-1; strips=0; patternLength=-1; cR=true;
                }
            }
        }
    }

    private void findStripsV(MarvinImage imageIn, MarvinImage imageOut){

        int strips=0;
        int totalPixels=0;
        int r,g,b;
        int patternStart;
        boolean cR=true;
        int patternLength = -1;
        for(int x=0; x<imageIn.getWidth(); x++){
            patternStart = -1;
            strips = 0;
            patternLength=-1;
            for(int y=0; y<imageIn.getHeight(); y++){
                r = imageIn.getIntComponent0(x, y);
                g = imageIn.getIntComponent1(x, y);
                b = imageIn.getIntComponent2(x, y);

                if(cR){
                    if(r == 255 && g == 0 && b == 0){
                        if(patternStart == -1){ patternStart = y;}
                        totalPixels++;
                    } else{
                        if(patternLength == -1){
                            if(totalPixels >=3 && totalPixels <= 100){
                                patternLength = (int)(totalPixels);
                            } else{
                                totalPixels=0; patternStart=-1; strips=0; patternLength=-1;
                            }
                        } else{
                            if(totalPixels >= Math.max(patternLength*0.5,3) && totalPixels <= patternLength * 2){
                                strips++;
                                totalPixels=1;
                                cR = false;
                            } else{
                                totalPixels=0; patternStart=-1; strips=0; patternLength=-1;
                            }
                        }
                    }

//                  if(maxL != -1 && totalPixels > maxL){
//                      totalPixels=0; patternStart=-1; strips=0; maxL=-1;
//                  }
                }
                else{
                    if(r == 255 && g == 255 && b == 255){
                        totalPixels++;
                    } else{
                        if(totalPixels >= Math.max(patternLength*0.5,3) && totalPixels <= patternLength * 2){
                            strips++;
                            totalPixels=1;
                            cR = true;
                        } else{
                            totalPixels=0; patternStart=-1; strips=0; patternLength=-1; cR=true;
                        }
                    }

//                  if(maxL != -1 &&  totalPixels > maxL){
//                      totalPixels=0; patternStart=-1; strips=0; maxL=-1;
//                      cR=true;
//                  }
                }


                if(strips >= 4){
                    imageOut.fillRect(x, patternStart, 2, y-patternStart, Color.black);
                    totalPixels=0; patternStart=-1; strips=0; patternLength=-1; cR=true;
                }
            }
        }
    }

    private void colorFilter(MarvinImage image){

        int r,g,b;
        boolean isR, isB;
        for(int y=0; y<image.getHeight(); y++){
            for(int x=0; x<image.getWidth(); x++){
                r = image.getIntComponent0(x, y);
                g = image.getIntComponent1(x, y);
                b = image.getIntComponent2(x, y);

                isR = (r > 120 && r > g * 1.3 && r > b * 1.3);
                isB = (b > 30 && b < 150 && b > r * 1.3 && b > g * 1.3);

                if(isR){
                    image.setIntColor(x, y, 255,0,0);
                } else if(isB){
                    image.setIntColor(x, y, 0,0,255);
                } else{
                    image.setIntColor(x, y, 255,255,255);
                }
            }
        }
    }

    public static void main(String[] args) {
        new AmericanFlag();
    }
}
