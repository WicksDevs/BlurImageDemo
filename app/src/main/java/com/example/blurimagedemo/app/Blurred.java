package com.example.blurimagedemo.app;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

/**
 * Created by lhernandez on 23/04/14.
 */
public class Blurred {

    private Context context;
    private RenderScript rs;
    private ScriptIntrinsicBlur blur;
    private Allocation allocationInputBitmap;
    private  Allocation allocationOutputBitmap;


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    Blurred(Context context) {
        this.context = context;
        this.rs = RenderScript.create(context);
        this.blur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public Bitmap blurBitmap(Bitmap inputBitmap,float radius) {
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);
        if (radius == 0)
            return outputBitmap;
        allocationInputBitmap = Allocation.createFromBitmap(rs,inputBitmap);
        allocationOutputBitmap = Allocation.createFromBitmap(rs,outputBitmap);
        blur.setRadius(radius);
        blur.setInput(allocationInputBitmap);
        blur.forEach(allocationOutputBitmap);
        allocationOutputBitmap.copyTo(outputBitmap);
        allocationInputBitmap.destroy();
        allocationOutputBitmap.destroy();
        rs.destroy();
        return outputBitmap;

    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
