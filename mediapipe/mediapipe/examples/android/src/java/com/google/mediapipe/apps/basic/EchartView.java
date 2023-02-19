package com.google.mediapipe.apps.basic;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
    
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Line;

import android.util.Log;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;

import java.util.Arrays;
import java.util.*;

public class EchartView extends WebView {


    private static final String TAG = EchartView.class.getSimpleName();

    public EchartView(Context context) {
        this(context, null);
    }

    public EchartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EchartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        WebSettings webSettings = getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportZoom(false);
        webSettings.setDisplayZoomControls(false);
        loadUrl("file:///android_asset/echarts.html");
    }

    /**刷新图表
     * java调用js的loadEcharts方法刷新echart
     * 不能在第一时间就用此方法来显示图表，因为第一时间html的标签还未加载完成，不能获取到标签值
     * @param option
     */
    public void refreshEchartsWithOption(GsonOption option) {
        if (option == null) {
            return;
        }
        String optionString = option.toString();
        String call = "javascript:loadEcharts('" + optionString + "')";
        loadUrl(call);
    }

    public void refreshData(String date, int i0, int i1, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, int i13, int i14, int leftKneeAngle, int rightKneeAngle, int leftElbowAngle, int rightElbowAngle, int leftShoulderAngle, int rightShoulderAngle) {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:refreshData(\"" + date + "\", " + String.valueOf(i0)+ ", " + String.valueOf(i1)+ ", " + String.valueOf(i2)+ ", " + String.valueOf(i3)+ ", " + String.valueOf(i4)+ ", " + String.valueOf(i5)+ ", " + String.valueOf(i6)+ ", " + String.valueOf(i7)+ ", " + String.valueOf(i8)+ ", " + String.valueOf(i9)+ ", " + String.valueOf(i10)+ ", " + String.valueOf(i11)+ ", " + String.valueOf(i12) + ", " + String.valueOf(i13)+ ", " + String.valueOf(i14)+ ", " + String.valueOf(leftKneeAngle)+ ", " + String.valueOf(rightKneeAngle)+ ", " + String.valueOf(leftElbowAngle)+ ", " + String.valueOf(rightElbowAngle)+ ", " + String.valueOf(leftShoulderAngle)+ ", " + String.valueOf(rightShoulderAngle)+ ")";


	//String call = "javascript:refreshData(\"" + (date[0]) + "\",\"" +  (date[1]) + "\",\"" +  (date[2]) + "\",\"" +  (date[3]) + "\",\"" +  (date[4]) + "\",\"" +  (date[5]) + "\",\"" +  (date[6]) + "\",\"" +  (date[7]) + "\",\"" +  (date[8]) + "\",\"" +  (date[9]) + "\", "+ String.valueOf(i0[0])+ ", " + String.valueOf(i0[1])+ ", " + String.valueOf(i0[2])+ ", " + String.valueOf(i0[3])+ ", " + String.valueOf(i0[4])+ ", " + String.valueOf(i0[5])+ ", " + String.valueOf(i0[6])+ ", " + String.valueOf(i0[7])+ ", " + String.valueOf(i0[8])+ ", " + String.valueOf(i0[9])+ ", " + String.valueOf(i1[0])+ ", " + String.valueOf(i1[1])+ ", " + String.valueOf(i1[2])+ ", " + String.valueOf(i1[3])+ ", " + String.valueOf(i1[4])+ ", " + String.valueOf(i1[5])+ ", " + String.valueOf(i1[6])+ ", " + String.valueOf(i1[7])+ ", " + String.valueOf(i1[8])+ ", " + String.valueOf(i1[9])+ ", " + String.valueOf(i2[0])+ ", " + String.valueOf(i2[1])+ ", " + String.valueOf(i2[2])+ ", " + String.valueOf(i2[3])+ ", " + String.valueOf(i2[4])+ ", " + String.valueOf(i2[5])+ ", " + String.valueOf(i2[6])+ ", " + String.valueOf(i2[7])+ ", " + String.valueOf(i2[8])+ ", " + String.valueOf(i2[9])+ ", " + String.valueOf(i3[0])+ ", " + String.valueOf(i3[1])+ ", " + String.valueOf(i3[2])+ ", " + String.valueOf(i3[3])+ ", " + String.valueOf(i3[4])+ ", " + String.valueOf(i3[5])+ ", " + String.valueOf(i3[6])+ ", " + String.valueOf(i3[7])+ ", " + String.valueOf(i3[8])+ ", " + String.valueOf(i3[9])+ ", " +  String.valueOf(i4[0])+ ", " + String.valueOf(i4[1])+ ", " + String.valueOf(i4[2])+ ", " + String.valueOf(i4[3])+ ", " + String.valueOf(i4[4])+ ", " + String.valueOf(i4[5])+ ", " + String.valueOf(i4[6])+ ", " + String.valueOf(i4[7])+ ", " + String.valueOf(i4[8])+ ", " + String.valueOf(i4[9])+ ", " +  String.valueOf(i5[0])+ ", " + String.valueOf(i5[1])+ ", " + String.valueOf(i5[2])+ ", " + String.valueOf(i5[3])+ ", " + String.valueOf(i5[4])+ ", " + String.valueOf(i5[5])+ ", " + String.valueOf(i5[6])+ ", " + String.valueOf(i5[7])+ ", " + String.valueOf(i5[8])+ ", " + String.valueOf(i5[9])+ ", " + String.valueOf(i6[0])+ ", " + String.valueOf(i6[1])+ ", " + String.valueOf(i6[2])+ ", " + String.valueOf(i6[3])+ ", " + String.valueOf(i6[4])+ ", " + String.valueOf(i6[5])+ ", " + String.valueOf(i6[6])+ ", " + String.valueOf(i6[7])+ ", " + String.valueOf(i6[8])+ ", " + String.valueOf(i6[9])+ ", " + String.valueOf(i7[0])+ ", " + String.valueOf(i7[1])+ ", " + String.valueOf(i7[2])+ ", " + String.valueOf(i7[3])+ ", " + String.valueOf(i7[4])+ ", " + String.valueOf(i7[5])+ ", " + String.valueOf(i7[6])+ ", " + String.valueOf(i7[7])+ ", " + String.valueOf(i7[8])+ ", " + String.valueOf(i7[9])+ ", " + String.valueOf(i8[0])+ ", " + String.valueOf(i8[1])+ ", " + String.valueOf(i8[2])+ ", " + String.valueOf(i8[3])+ ", " + String.valueOf(i8[4])+ ", " + String.valueOf(i8[5])+ ", " + String.valueOf(i8[6])+ ", " + String.valueOf(i8[7])+ ", " + String.valueOf(i8[8])+ ", " + String.valueOf(i8[9])+ ", " + String.valueOf(i9[0])+ ", " + String.valueOf(i9[1])+ ", " + String.valueOf(i9[2])+ ", " + String.valueOf(i9[3])+ ", " + String.valueOf(i9[4])+ ", " + String.valueOf(i9[5])+ ", " + String.valueOf(i9[6])+ ", " + String.valueOf(i9[7])+ ", " + String.valueOf(i9[8])+ ", " + String.valueOf(i9[9])+ ", " + String.valueOf(i10[0])+ ", " + String.valueOf(i10[1])+ ", " + String.valueOf(i10[2])+ ", " + String.valueOf(i10[3])+ ", " + String.valueOf(i10[4])+ ", " + String.valueOf(i10[5])+ ", " + String.valueOf(i10[6])+ ", " + String.valueOf(i10[7])+ ", " + String.valueOf(i10[8])+ ", " + String.valueOf(i10[9])+ ", " + String.valueOf(i11[0])+ ", " + String.valueOf(i11[1])+ ", " + String.valueOf(i11[2])+ ", " + String.valueOf(i11[3])+ ", " + String.valueOf(i11[4])+ ", " + String.valueOf(i11[5])+ ", " + String.valueOf(i11[6])+ ", " + String.valueOf(i11[7])+ ", " + String.valueOf(i11[8])+ ", " + String.valueOf(i11[9])+ ", " + String.valueOf(i12[0])+ ", " + String.valueOf(i12[1])+ ", " + String.valueOf(i12[2])+ ", " + String.valueOf(i12[3])+ ", " + String.valueOf(i12[4])+ ", " + String.valueOf(i12[5])+ ", " + String.valueOf(i12[6])+ ", " + String.valueOf(i12[7])+ ", " + String.valueOf(i12[8])+ ", " + String.valueOf(i12[9])+ ", " + String.valueOf(i13[0])+ ", " + String.valueOf(i13[1])+ ", " + String.valueOf(i13[2])+ ", " + String.valueOf(i13[3])+ ", " + String.valueOf(i13[4])+ ", " + String.valueOf(i13[5])+ ", " + String.valueOf(i13[6])+ ", " + String.valueOf(i13[7])+ ", " + String.valueOf(i13[8])+ ", " + String.valueOf(i13[9])+ ", " + String.valueOf(i14[0])+ ", " + String.valueOf(i14[1])+ ", " + String.valueOf(i14[2])+ ", " + String.valueOf(i14[3])+ ", " + String.valueOf(i14[4])+ ", " + String.valueOf(i14[5])+ ", " + String.valueOf(i14[6])+ ", " + String.valueOf(i14[7])+ ", " + String.valueOf(i14[8])+ ", " + String.valueOf(i14[9]) +  ")";
	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	//Log.v("MainActivity:refreshData", call);
        loadUrl(call);
	Log.v("MainActivity:refreshData OK!", call);
    }
	public void deleteData() {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:deleteData()";

	//Log.v("MainActivity:deleteData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:deleteData", call);
        loadUrl(call);
	Log.v("MainActivity:deleteData OK!", call);
    }
}
