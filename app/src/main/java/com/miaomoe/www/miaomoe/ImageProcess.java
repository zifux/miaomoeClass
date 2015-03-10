package com.miaomoe.www.miaomoe;

import android.graphics.Bitmap;
import android.util.Log;

public class ImageProcess {
    public ImageProcess() {
        // TODO Auto-generated constructor stub
    }
    public int[] blackwhite(int val,Bitmap myBitmap){
        // Create new array
        int width = 8;
        int height = 12;
        int[][] temp={
                {
                        0,0,0,1,1,1,0,0,
                        0,0,1,1,0,1,1,0,
                        0,0,1,0,0,0,1,0,
                        0,1,1,0,0,0,1,1,
                        0,1,1,0,0,0,1,1,
                        0,1,1,0,0,0,1,1,
                        0,1,1,0,0,0,1,1,
                        0,1,1,0,0,0,1,1,
                        0,1,1,0,0,0,1,1,
                        0,0,0,1,1,1,0,0,
                        0,0,1,1,0,1,1,0,
                        0,0,1,0,0,0,1,0

                },
                {
                        0,0,0,1,1,0,0,0,
                        0,1,1,1,1,0,0,0,
                        0,0,0,1,1,0,0,0,
                        0,0,0,1,1,0,0,0,
                        0,0,0,1,1,0,0,0,
                        0,0,0,1,1,0,0,0,
                        0,0,0,1,1,0,0,0,
                        0,0,0,1,1,0,0,0,
                        0,0,0,1,1,0,0,0,
                        0,0,0,1,1,0,0,0,
                        0,0,0,1,1,0,0,0,
                        0,1,1,1,1,1,1,0
                },
                {
                        0,0,1,1,1,1,0,0,
                        0,1,0,0,1,1,1,0,
                        1,0,0,0,0,1,1,0,
                        0,0,0,0,0,1,1,0,
                        0,0,0,0,0,1,1,0,
                        0,0,0,0,0,1,0,0,
                        0,0,0,0,1,1,0,0,
                        0,0,0,0,1,0,0,0,
                        0,0,0,1,0,0,0,0,
                        0,0,1,0,0,0,0,1,
                        0,1,1,1,1,1,1,1,
                        1,1,1,1,1,1,1,0
                },
                {
                        0,0,1,1,1,1,0,0,
                        0,1,0,0,1,1,1,0,
                        1,0,0,0,0,1,1,0,
                        0,0,0,0,0,1,1,0,
                        0,0,0,0,1,1,0,0,
                        0,0,0,1,1,1,0,0,
                        0,0,0,0,1,1,1,0,
                        0,0,0,0,0,1,1,0,
                        0,0,0,0,0,1,1,0,
                        0,0,0,0,0,1,1,0,
                        1,1,0,0,1,1,0,0,
                        1,1,1,1,1,0,0,0
                },
                {
                        0,0,0,0,0,1,1,0,
                        0,0,0,0,0,1,1,0,
                        0,0,0,0,1,1,1,0,
                        0,0,0,1,0,1,1,0,
                        0,0,1,0,0,1,1,0,
                        0,0,1,0,0,1,1,0,
                        0,1,0,0,0,1,1,0,
                        1,0,0,0,0,1,1,0,
                        1,1,1,1,1,1,1,1,
                        0,0,0,0,0,1,1,0,
                        0,0,0,0,0,1,1,0,
                        0,0,0,0,0,1,1,0,

                },
                {
                        0,0,0,1,1,1,1,0,
                        0,0,0,1,1,1,1,0,
                        0,0,1,0,0,0,0,0,
                        0,0,1,1,1,0,0,0,
                        0,1,1,1,1,1,0,0,
                        0,0,0,0,1,1,1,0,
                        0,0,0,0,0,1,1,0,
                        0,0,0,0,0,0,1,0,
                        0,0,0,0,0,0,1,0,
                        0,0,0,0,0,0,1,0,
                        0,1,0,0,0,1,0,0,
                        0,1,1,1,1,0,0,0


                },
                {
                        0,0,0,0,0,1,1,1,
                        0,0,0,1,1,1,0,0,
                        0,0,1,1,0,0,0,0,
                        0,1,1,0,0,0,0,0,
                        0,1,0,1,1,1,0,0,
                        1,1,1,0,0,1,1,0,
                        1,1,0,0,0,0,1,1,
                        1,1,0,0,0,0,1,1,
                        1,1,0,0,0,0,1,1,
                        1,1,0,0,0,0,1,1,
                        0,1,1,0,0,1,1,0,
                        0,0,1,1,1,1,0,0,


                },
                {
                        0,0,1,1,1,1,1,1,
                        0,0,1,1,1,1,1,1,
                        0,1,0,0,0,0,0,1,
                        0,0,0,0,0,0,1,0,
                        0,0,0,0,0,0,1,0,
                        0,0,0,0,0,0,1,0,
                        0,0,0,0,0,1,0,0,
                        0,0,0,0,0,1,0,0,
                        0,0,0,0,1,0,0,0,
                        0,0,0,0,1,0,0,0,
                        0,0,0,0,1,0,0,0,
                        0,0,0,1,0,0,0,0
                },
                {
                        0,0,1,1,1,1,0,0,
                        0,1,1,0,0,0,1,1,
                        1,1,0,0,0,0,1,1,
                        1,1,0,0,0,0,1,1,
                        0,1,1,1,0,1,1,0,
                        0,0,1,1,1,0,0,0,
                        0,0,1,1,1,1,0,0,
                        0,1,0,0,0,1,1,0,
                        1,1,0,0,0,0,1,1,
                        1,1,0,0,0,0,1,1,
                        0,1,1,0,0,1,1,0,
                        0,0,1,1,1,1,0,0
                },
                {
                        0,0,1,1,1,1,0,0,
                        0,1,1,0,0,1,1,0,
                        1,1,0,0,0,0,1,1,
                        1,1,0,0,0,0,1,1,
                        1,1,0,0,0,0,1,1,
                        1,1,0,0,0,0,1,1,
                        0,1,1,0,0,0,1,1,
                        0,0,1,1,1,1,1,0,
                        0,0,0,0,0,1,1,0,
                        0,0,0,0,1,1,0,0,
                        0,0,0,1,1,0,0,0,
                        1,1,1,0,0,0,0,0
                }
        };
        int[][] num = new int[4][width * height];
        int[][] result = new int[4][width * height];
        myBitmap.getPixels(num[0], 0, width, 6, 4, width, height);
        myBitmap.getPixels(num[1], 0, width, 6+13, 4, width, height);
        myBitmap.getPixels(num[2], 0, width, 6+13*2, 4, width, height);
        myBitmap.getPixels(num[3], 0, width, 6+13*3, 4, width, height);


        // Apply pixel-by-pixel change
        for(int i=0;i<4;i++){
            int index = 0;
            for (int y = 0; y < height; y++)
            {
                for (int x = 0; x < width; x++)
                {
                    int r = (num[i][index] >> 16) & 0xff;
                    int g = (num[i][index] >> 8) & 0xff;
                    int b = num[i][index] & 0xff;
                    if((float)(r+g+b)/3<=val){
                        r=b=g=0x00;
                        result[i][index]=1;
                    }else{
                        r=b=g=0xff;
                        result[i][index]=0;
                    }
                    num[i][index] = 0xff000000 | (r << 16) | (g << 8) | b;
                    index++;
                } // x
            } // y
        }

        int[] resNumber=new int[4];
        Log.i("yzm","jijiangxunhuan");
        for(int k=0;k<4;k++){
            int i=0;
            int lastll=0;
            int ll=99999;
            int is=0;
            for(int in=0;in<10;in++){
                for(int px=0;px<result[k].length;px++){
                    lastll+=Math.abs(result[k][px]-temp[in][px]);
                }
                if(lastll<ll){
                    ll=lastll;
                    is=in;
                }
                lastll=0;
            }
            ll=99999;
            resNumber[k]=is;
            is=0;
        }
        Log.i("yzm", String.valueOf(resNumber[0])+resNumber[1]+resNumber[2]+resNumber[3]);

        return resNumber;
        // Change bitmap to use new array
        /*Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        bitmap.setPixels(num[2], 0, width, 0, 0, width, height);
        return bitmap;*/
    }

    public Bitmap threshold(int val,Bitmap myBitmap){
        // Create new array
        int width = myBitmap.getWidth();
        int height = myBitmap.getHeight();
        int[] pix = new int[width * height];
        myBitmap.getPixels(pix, 0, width, 0, 0, width, height);

        // Apply pixel-by-pixel change
        int index = 0;
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                int r = (pix[index] >> 16) & 0xff;
                int g = (pix[index] >> 8) & 0xff;
                int b = pix[index] & 0xff;
                r=(r+g+b)/3;
                if(r<val){
                    r=g=b=0x00;
                }else{
                    r=g=b=0xff;
                }
                pix[index] = 0xff000000 | (r << 16) | (g << 8) | b;
                index++;
            } // x
        } // y

        /*// Change bitmap to use new array
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        bitmap.setPixels(pix, 0, width, 0, 0, width, height);
        myBitmap = null;
        pix = null;*/
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        bitmap.setPixels(pix, 0, width, 0, 0, width, height);
        return bitmap;
    }

    // filterWidth and filterHeight must be odd numbers
    public Bitmap medianFilter(int filterWidth, int filterHeight,Bitmap myBitmap)
    {
        // Create new array
        int width = myBitmap.getWidth();
        int height = myBitmap.getHeight();
        int[] pixNew = new int[width * height];
        int[] pixOld = new int[width * height];
        myBitmap.getPixels(pixNew, 0, width, 0, 0, width, height);
        myBitmap.getPixels(pixOld, 0, width, 0, 0, width, height);

        // Apply pixel-by-pixel change
        int filterHalfWidth = filterWidth/2;
        int filterHalfHeight = filterHeight/2;
        int filterArea = filterWidth * filterHeight;
        for (int y = filterHalfHeight; y < height-filterHalfHeight; y++)
        {
            for (int x = filterHalfWidth; x < width-filterHalfWidth; x++)
            {
                // Accumulate values in neighborhood
                int accumR = 0, accumG = 0, accumB = 0;
                for (int dy = -filterHalfHeight; dy <= filterHalfHeight; dy++)
                {
                    for (int dx = -filterHalfWidth; dx <= filterHalfWidth; dx++)
                    {
                        int index = (y+dy)*width + (x+dx);
                        accumR += (pixOld[index] >> 16) & 0xff;
                        accumG += (pixOld[index] >> 8) & 0xff;
                        accumB += pixOld[index] & 0xff;
                    } // dx
                } // dy

                // Normalize
                accumR /= filterArea;
                accumG /= filterArea;
                accumB /= filterArea;
                int index = y*width + x;
                pixNew[index] = 0xff000000 | (accumR << 16) | (accumG << 8) | accumB;
            } // x
        } // y

        // Change bitmap to use new array
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        bitmap.setPixels(pixNew, 0, width, 0, 0, width, height);
        myBitmap = null;
        pixOld = null;
        pixNew = null;
        return bitmap;
    }
}
