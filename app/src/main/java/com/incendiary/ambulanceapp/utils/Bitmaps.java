package com.incendiary.ambulanceapp.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Base64;

import com.incendiary.androidcommon.etc.Logger;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class Bitmaps {

    public static Bitmap getBitmap(String picturePath, int reqSize) {
        try {
            ExifInterface exif = new ExifInterface(picturePath);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
            Bitmap bitmap = decodeSampledBitmapFromPath(picturePath, reqSize, reqSize);
            Matrix matrix = new Matrix();
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90: {
                    // bitmap = Bitmap.
                    matrix.postRotate(90);
                    break;
                }
                case ExifInterface.ORIENTATION_ROTATE_180: {
                    matrix.postRotate(180);
                    break;
                }
                case ExifInterface.ORIENTATION_ROTATE_270: {
                    matrix.postRotate(270);
                    break;
                }
                default: {
                    matrix = null;
                    break;
                }
            }
            if (bitmap == null)
                return null;
            if (matrix == null)
                return bitmap;
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeFile(picturePath);
    }

    public static Bitmap decodeSampledBitmapFromPath(String path, int reqWidth, int reqHeight) {
        try {
            final BitmapFactory.Options options = new BitmapFactory.Options();

            options.inJustDecodeBounds = true;
            options.inPurgeable = true;
            options.inInputShareable = true;
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inDither = true;

            FileInputStream fis = new FileInputStream(path);
            BitmapFactory.decodeStream(fis, null, options);
            fis.close();

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            fis = new FileInputStream(path);

            Bitmap bmp = BitmapFactory.decodeStream(fis, null, options);
            fis.close();
            return bmp;

        } catch (IOException e) {
            Logger.log(e);
        }
        return null;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }

            // This offers some additional logic in case the image has a strange
            // aspect ratio. For example, a panorama may have a much larger
            // width than height. In these cases the total pixels might still
            // end up being too large to fit comfortably in memory, so we should
            // be more aggressive with sample down the image (=larger
            // inSampleSize).

            final float totalPixels = width * height;

            // Anything more than 2x the requested pixels we'll sample down
            // further.
            final float totalReqPixelsCap = reqWidth * reqHeight * 2;

            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++;
            }
        }
        return inSampleSize;
    }

    public static Bitmap getScaledBitmap(Bitmap bmp, Bitmap.CompressFormat format, int maxSize) {
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inJustDecodeBounds = true;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(format, 100, baos);

        byte[] byteSrc = baos.toByteArray();
        BitmapFactory.decodeByteArray(byteSrc, 0, byteSrc.length, option);

        int w = option.outWidth;
        int h = option.outHeight;
        option.inSampleSize = 1;

        if (w > maxSize || h > maxSize)
            option.inSampleSize = Math.min(Math.round((float) w / maxSize), Math.round((float) h / maxSize));
        option.inJustDecodeBounds = false;

        return BitmapFactory.decodeByteArray(byteSrc, 0, byteSrc.length, option);
    }

    public static Bitmap base64StringToBitmap(String base64) {
        byte[] bytes = Base64.decode(base64, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public static String bitmapToBase64String(Bitmap bmp, int quality) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        byte[] bytes = baos.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }
}
