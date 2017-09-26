package cn.guoxiang.frescodemo.utility;

/**
 * Created by guoxiang on 2017/9/26.
 */

public class ImageUtil {
    public static boolean isTooWidth(int width){
        return width > 4000;
    }
    public static boolean isTooHeight(int height){
        return height > 4000;
    }
    public static boolean isBigBitmap(int width, int height){
        return isTooHeight(height) || isTooWidth(width);
    }
}
