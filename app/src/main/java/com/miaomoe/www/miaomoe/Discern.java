package com.miaomoe.www.miaomoe;

import android.graphics.Bitmap;

/**
 * Created by aszer_000 on 2015/3/4 0004.
 */
public class Discern {
    Discern(){

    }
    public Bitmap yz(Bitmap myBitmap){
        // Create new array
        int width = 8;
        int height =12;
        int[][] num = new int[4][width*height];
        int[][] result = new int[4][8*12];
        myBitmap.getPixels(num[0], 0, width, 7, 5, width, height);
        myBitmap.getPixels(num[1], 0, width, 7+13, 5, width, height);
        myBitmap.getPixels(num[2], 0, width, 7+13*2, 5, width, height);
        myBitmap.getPixels(num[3], 0, width, 7+13*3, 5, width, height);


        // Apply pixel-by-pixel change
        for (int i=0;i<4;i++) {
            int index = 0;
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int r = (num[i][index] >> 16) & 0xff;
/*                    int g = (num[i][index] >> 8) & 0xff;
                    int b = num[i][index] & 0xff;*/
                    if (r == 0xff) {
                        result[i][index]=1;
                    } else if (r == 0x00) {
                        result[i][index]=0;
                    }
                    index++;
                } // x
            } // y
        }

        // Change bitmap to use new array
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        bitmap.setPixels(num[1], 0, width, 0, 0, width, height);
        myBitmap = null;
        num = null;
        return bitmap;
    }
}
