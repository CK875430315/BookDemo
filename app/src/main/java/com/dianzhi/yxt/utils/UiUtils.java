package com.dianzhi.yxt.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by CK on 2017/4/12.
 */

public class UiUtils {
    /**
     * 获取屏幕尺寸
     *
     * @param activity
     *            Activity
     * @return 屏幕尺寸像素值，下标为0的值为宽，下标为1的值为高
     */
    public static int[] getScreenSize(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return new int[] { metrics.widthPixels, metrics.heightPixels};
    }

    /**
     * 加载大图
     * @param context
     * @param img
     * @param url
     */
    public static void loadDetailImage(Context context, final ImageView img, String url) {
        Picasso.with(context)
                .load(url)
//                .error(R.drawable.zwt_normal)
//                .placeholder(R.drawable.zwt_normal)
                .transform(new Transformation() {
                    @Override
                    public Bitmap transform(Bitmap source) {

                        int targetWidth = img.getWidth();
                        int targetHeightMy = img.getHeight();
                        //Log.e("sssss","source.getHeight()="+source.getHeight()+",source.getWidth()="+source.getWidth()+",targetWidth="+targetWidth+",targetHeight="+targetHeightMy);

                        if(source.getWidth()==0){
                            return source;
                        }

                        //如果图片小于设置的宽度，则返回原图
                        if(source.getWidth()<targetWidth){
                            return source;
                        }else{
                            //如果图片大小大于等于设置的宽度，则按照设置的宽度比例来缩放
                            double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
                            int targetHeight = (int) (targetWidth * aspectRatio);
                            if (targetHeight != 0 && targetWidth != 0) {
//								if (targetWidth > 1080) {
//									targetWidth=targetWidth*2/3;
//								}
//								if (targetHeight > 1080) {
//									targetHeight=targetHeight*2/3;
//								}
                                Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
                                if (result != source) {
                                    // Same bitmap is returned if sizes are the same
                                    source.recycle();
                                }
                                return result;
                            } else {
                                return source;
                            }
                        }
                    }

                    @Override
                    public String key() {
                        return "transformation" + " desiredWidth";
                    }
                })
                .config(Bitmap.Config.RGB_565)
                .into(img);
    }
    private static long lastClickTime;
    private final static int SPACE_TIME = 300;

    /**
     * 双击
     * @return
     */
    public synchronized static boolean isDoubleClick() {
        long currentTime = System.currentTimeMillis();
        boolean isClick2;
        if (currentTime - lastClickTime >
                SPACE_TIME) {
            isClick2 = false;
        } else {
            isClick2 = true;
        }
        lastClickTime = currentTime;
        return isClick2;
    }

    /**
     * 检测是否是emoji
     * @param string
     * @return
     */
    public static boolean isEmoji(String string) {
        Pattern p = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(string);
        return m.find();
    }

    /**
     * 手机号正则
     * @param str
     * @return
     */
    public static boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机�?
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    /**
     * 根据uri找文件路径
     * @param context
     * @param uri
     * @return
     */
    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * 根据路径获得图片并压缩，返回bitmap用于显示
     * @param filePath
     * @return
     */
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 720, 960);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 根据资源文件获得图片并压缩，返回bitmap用于显示
     * @param res
     * @param resId
     * @return
     */
    public static Bitmap getSmallBitmap(Resources res,int resId) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res,resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 720, 560);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeResource(res,resId, options);
    }
    //计算图片的缩放值
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /***
     * 根据秒返回格式化的时间
     * @param l
     * @return
     */
    public static String formatSeconds(long l) {
        SimpleDateFormat df = new SimpleDateFormat("dd HH:mm:ss");
        long days = TimeUnit.SECONDS.toDays(l);
        long hours = TimeUnit.SECONDS.toHours(l-days*24*3600);
        long minutes = TimeUnit.SECONDS.toMinutes(l-days*24*3600-hours*3600);
        long seconds = TimeUnit.SECONDS.toSeconds(l-days*24*3600-hours*3600-minutes*60);
        return  days+"天"+hours + "小时" + minutes + "分"+seconds+"秒" ;
    }
    private static DecimalFormat fileIntegerFormat = new DecimalFormat("#0");
    private static DecimalFormat fileDecimalFormat = new DecimalFormat("#0.#");
    /**
     * 单位换算
     *
     * @param size      单位为B
     * @param isInteger 是否返回取整的单位
     * @return 转换后的单位
     */
    public static String formatFileSize(long size, boolean isInteger) {
        DecimalFormat df = isInteger ? fileIntegerFormat : fileDecimalFormat;
        String fileSizeString = "0M";
        if (size < 1024 && size > 0) {
            fileSizeString = df.format((double) size) + "B";
        } else if (size < 1024 * 1024) {
            fileSizeString = df.format((double) size / 1024) + "K";
        } else if (size < 1024 * 1024 * 1024) {
            fileSizeString = df.format((double) size / (1024 * 1024)) + "M";
        } else {
            fileSizeString = df.format((double) size / (1024 * 1024 * 1024)) + "G";
        }
        return fileSizeString;
    }
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
