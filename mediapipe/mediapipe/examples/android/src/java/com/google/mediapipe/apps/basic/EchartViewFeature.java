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

public class EchartViewFeature extends WebView {
    private static final String TAG = EchartViewFeature.class.getSimpleName();

    public EchartViewFeature(Context context) {
        this(context, null);
    }

    public EchartViewFeature(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EchartViewFeature(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        WebSettings webSettings = getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportZoom(false);
        webSettings.setDisplayZoomControls(false);
        loadUrl("file:///android_asset/echartsFeature.html");
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

    public void refreshDataMax(double i0, double i1, double i2, double i3, double i4, double i5, double i6, double i7, double i8, double i9, double i10, double i11, double i12, double i13, double i14) {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:refreshDataMax(" + String.valueOf(i0)+ ", " + String.valueOf(i1)+ ", " + String.valueOf(i2)+ ", " + String.valueOf(i3)+ ", " + String.valueOf(i4)+ ", " + String.valueOf(i5)+ ", " + String.valueOf(i6)+ ", " + String.valueOf(i7)+ ", " + String.valueOf(i8)+ ", " + String.valueOf(i9)+ ", " + String.valueOf(i10)+ ", " + String.valueOf(i11)+ ", " + String.valueOf(i12) + ", " + String.valueOf(i13)+ ", " + String.valueOf(i14) + ")";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:refreshDataMax", call);
        loadUrl(call);
	Log.v("MainActivity:refreshDataMax OK!", call);
    }
    public void refreshDataMin(double i0, double i1, double i2, double i3, double i4, double i5, double i6, double i7, double i8, double i9, double i10, double i11, double i12, double i13, double i14) {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:refreshDataMin(" + String.valueOf(i0)+ ", " + String.valueOf(i1)+ ", " + String.valueOf(i2)+ ", " + String.valueOf(i3)+ ", " + String.valueOf(i4)+ ", " + String.valueOf(i5)+ ", " + String.valueOf(i6)+ ", " + String.valueOf(i7)+ ", " + String.valueOf(i8)+ ", " + String.valueOf(i9)+ ", " + String.valueOf(i10)+ ", " + String.valueOf(i11)+ ", " + String.valueOf(i12) + ", " + String.valueOf(i13)+ ", " + String.valueOf(i14) + ")";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:refreshDataMin", call);
        loadUrl(call);
	Log.v("MainActivity:refreshDataMin OK!", call);
    }

    public void refreshDataaverage(double i0, double i1, double i2, double i3, double i4, double i5, double i6, double i7, double i8, double i9, double i10, double i11, double i12, double i13, double i14) {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:refreshDataaverage(" + String.valueOf(i0)+ ", " + String.valueOf(i1)+ ", " + String.valueOf(i2)+ ", " + String.valueOf(i3)+ ", " + String.valueOf(i4)+ ", " + String.valueOf(i5)+ ", " + String.valueOf(i6)+ ", " + String.valueOf(i7)+ ", " + String.valueOf(i8)+ ", " + String.valueOf(i9)+ ", " + String.valueOf(i10)+ ", " + String.valueOf(i11)+ ", " + String.valueOf(i12) + ", " + String.valueOf(i13)+ ", " + String.valueOf(i14) + ")";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:refreshDataaverage", call);
        loadUrl(call);
	Log.v("MainActivity:refreshDataaverage OK!", call);
    }

    public void refreshDatapeak(double i0, double i1, double i2, double i3, double i4, double i5, double i6, double i7, double i8, double i9, double i10, double i11, double i12, double i13, double i14) {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:refreshDatapeak(" + String.valueOf(i0)+ ", " + String.valueOf(i1)+ ", " + String.valueOf(i2)+ ", " + String.valueOf(i3)+ ", " + String.valueOf(i4)+ ", " + String.valueOf(i5)+ ", " + String.valueOf(i6)+ ", " + String.valueOf(i7)+ ", " + String.valueOf(i8)+ ", " + String.valueOf(i9)+ ", " + String.valueOf(i10)+ ", " + String.valueOf(i11)+ ", " + String.valueOf(i12) + ", " + String.valueOf(i13)+ ", " + String.valueOf(i14) + ")";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:refreshDatapeak", call);
        loadUrl(call);
	Log.v("MainActivity:refreshDatapeak OK!", call);
    }

    public void refreshDataRMEAN(double i0, double i1, double i2, double i3, double i4, double i5, double i6, double i7, double i8, double i9, double i10, double i11, double i12, double i13, double i14) {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:refreshDataRMEAN(" + String.valueOf(i0)+ ", " + String.valueOf(i1)+ ", " + String.valueOf(i2)+ ", " + String.valueOf(i3)+ ", " + String.valueOf(i4)+ ", " + String.valueOf(i5)+ ", " + String.valueOf(i6)+ ", " + String.valueOf(i7)+ ", " + String.valueOf(i8)+ ", " + String.valueOf(i9)+ ", " + String.valueOf(i10)+ ", " + String.valueOf(i11)+ ", " + String.valueOf(i12) + ", " + String.valueOf(i13)+ ", " + String.valueOf(i14) + ")";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:refreshDataRMEAN", call);
        loadUrl(call);
	Log.v("MainActivity:refreshDataRMEAN OK!", call);
    }

    public void refreshDatameanSquareValue(double i0, double i1, double i2, double i3, double i4, double i5, double i6, double i7, double i8, double i9, double i10, double i11, double i12, double i13, double i14) {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:refreshDatameanSquareValue(" + String.valueOf(i0)+ ", " + String.valueOf(i1)+ ", " + String.valueOf(i2)+ ", " + String.valueOf(i3)+ ", " + String.valueOf(i4)+ ", " + String.valueOf(i5)+ ", " + String.valueOf(i6)+ ", " + String.valueOf(i7)+ ", " + String.valueOf(i8)+ ", " + String.valueOf(i9)+ ", " + String.valueOf(i10)+ ", " + String.valueOf(i11)+ ", " + String.valueOf(i12) + ", " + String.valueOf(i13)+ ", " + String.valueOf(i14) + ")";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:refreshDatameanSquareValue", call);
        loadUrl(call);
	Log.v("MainActivity:refreshDatameanSquareValue OK!", call);
    }

    public void refreshDatavariance(double i0, double i1, double i2, double i3, double i4, double i5, double i6, double i7, double i8, double i9, double i10, double i11, double i12, double i13, double i14) {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:refreshDatavariance(" + String.valueOf(i0)+ ", " + String.valueOf(i1)+ ", " + String.valueOf(i2)+ ", " + String.valueOf(i3)+ ", " + String.valueOf(i4)+ ", " + String.valueOf(i5)+ ", " + String.valueOf(i6)+ ", " + String.valueOf(i7)+ ", " + String.valueOf(i8)+ ", " + String.valueOf(i9)+ ", " + String.valueOf(i10)+ ", " + String.valueOf(i11)+ ", " + String.valueOf(i12) + ", " + String.valueOf(i13)+ ", " + String.valueOf(i14) + ")";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:refreshDatavariance", call);
        loadUrl(call);
	Log.v("MainActivity:refreshDatavariance OK!", call);
    }

    public void refreshDataRMS(double i0, double i1, double i2, double i3, double i4, double i5, double i6, double i7, double i8, double i9, double i10, double i11, double i12, double i13, double i14) {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:refreshDataRMS(" + String.valueOf(i0)+ ", " + String.valueOf(i1)+ ", " + String.valueOf(i2)+ ", " + String.valueOf(i3)+ ", " + String.valueOf(i4)+ ", " + String.valueOf(i5)+ ", " + String.valueOf(i6)+ ", " + String.valueOf(i7)+ ", " + String.valueOf(i8)+ ", " + String.valueOf(i9)+ ", " + String.valueOf(i10)+ ", " + String.valueOf(i11)+ ", " + String.valueOf(i12) + ", " + String.valueOf(i13)+ ", " + String.valueOf(i14) + ")";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:refreshDataRMS", call);
        loadUrl(call);
	Log.v("MainActivity:refreshDataRMS OK!", call);
    }

    public void refreshDatastandardDeviation(double i0, double i1, double i2, double i3, double i4, double i5, double i6, double i7, double i8, double i9, double i10, double i11, double i12, double i13, double i14) {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:refreshDatastandardDeviation(" + String.valueOf(i0)+ ", " + String.valueOf(i1)+ ", " + String.valueOf(i2)+ ", " + String.valueOf(i3)+ ", " + String.valueOf(i4)+ ", " + String.valueOf(i5)+ ", " + String.valueOf(i6)+ ", " + String.valueOf(i7)+ ", " + String.valueOf(i8)+ ", " + String.valueOf(i9)+ ", " + String.valueOf(i10)+ ", " + String.valueOf(i11)+ ", " + String.valueOf(i12) + ", " + String.valueOf(i13)+ ", " + String.valueOf(i14) + ")";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:refreshDatastandardDeviation", call);
        loadUrl(call);
	Log.v("MainActivity:refreshDatastandardDeviation OK!", call);
    }

    public void refreshDataKurtosis(double i0, double i1, double i2, double i3, double i4, double i5, double i6, double i7, double i8, double i9, double i10, double i11, double i12, double i13, double i14) {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:refreshDataKurtosis(" + String.valueOf(i0)+ ", " + String.valueOf(i1)+ ", " + String.valueOf(i2)+ ", " + String.valueOf(i3)+ ", " + String.valueOf(i4)+ ", " + String.valueOf(i5)+ ", " + String.valueOf(i6)+ ", " + String.valueOf(i7)+ ", " + String.valueOf(i8)+ ", " + String.valueOf(i9)+ ", " + String.valueOf(i10)+ ", " + String.valueOf(i11)+ ", " + String.valueOf(i12) + ", " + String.valueOf(i13)+ ", " + String.valueOf(i14) + ")";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:refreshDataKurtosis", call);
        loadUrl(call);
	Log.v("MainActivity:refreshDataKurtosis OK!", call);
    }

    public void refreshDataskewness(double i0, double i1, double i2, double i3, double i4, double i5, double i6, double i7, double i8, double i9, double i10, double i11, double i12, double i13, double i14) {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:refreshDataskewness(" + String.valueOf(i0)+ ", " + String.valueOf(i1)+ ", " + String.valueOf(i2)+ ", " + String.valueOf(i3)+ ", " + String.valueOf(i4)+ ", " + String.valueOf(i5)+ ", " + String.valueOf(i6)+ ", " + String.valueOf(i7)+ ", " + String.valueOf(i8)+ ", " + String.valueOf(i9)+ ", " + String.valueOf(i10)+ ", " + String.valueOf(i11)+ ", " + String.valueOf(i12) + ", " + String.valueOf(i13)+ ", " + String.valueOf(i14) + ")";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:refreshDataskewness", call);
        loadUrl(call);
	Log.v("MainActivity:refreshDataskewness OK!", call);
    }

    public void refreshDatapeakfactor(double i0, double i1, double i2, double i3, double i4, double i5, double i6, double i7, double i8, double i9, double i10, double i11, double i12, double i13, double i14) {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:refreshDatapeakfactor(" + String.valueOf(i0)+ ", " + String.valueOf(i1)+ ", " + String.valueOf(i2)+ ", " + String.valueOf(i3)+ ", " + String.valueOf(i4)+ ", " + String.valueOf(i5)+ ", " + String.valueOf(i6)+ ", " + String.valueOf(i7)+ ", " + String.valueOf(i8)+ ", " + String.valueOf(i9)+ ", " + String.valueOf(i10)+ ", " + String.valueOf(i11)+ ", " + String.valueOf(i12) + ", " + String.valueOf(i13)+ ", " + String.valueOf(i14) + ")";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:refreshDatapeakfactor", call);
        loadUrl(call);
	Log.v("MainActivity:refreshDatapeakfactor OK!", call);
    }

    public void refreshDatapulsefactor(double i0, double i1, double i2, double i3, double i4, double i5, double i6, double i7, double i8, double i9, double i10, double i11, double i12, double i13, double i14) {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:refreshDatapulsefactor(" + String.valueOf(i0)+ ", " + String.valueOf(i1)+ ", " + String.valueOf(i2)+ ", " + String.valueOf(i3)+ ", " + String.valueOf(i4)+ ", " + String.valueOf(i5)+ ", " + String.valueOf(i6)+ ", " + String.valueOf(i7)+ ", " + String.valueOf(i8)+ ", " + String.valueOf(i9)+ ", " + String.valueOf(i10)+ ", " + String.valueOf(i11)+ ", " + String.valueOf(i12) + ", " + String.valueOf(i13)+ ", " + String.valueOf(i14) + ")";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:refreshDatapulsefactor", call);
        loadUrl(call);
	Log.v("MainActivity:refreshDatapulsefactor OK!", call);
    }

    public void refreshDatamarginfactor(double i0, double i1, double i2, double i3, double i4, double i5, double i6, double i7, double i8, double i9, double i10, double i11, double i12, double i13, double i14) {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:refreshDatamarginfactor(" + String.valueOf(i0)+ ", " + String.valueOf(i1)+ ", " + String.valueOf(i2)+ ", " + String.valueOf(i3)+ ", " + String.valueOf(i4)+ ", " + String.valueOf(i5)+ ", " + String.valueOf(i6)+ ", " + String.valueOf(i7)+ ", " + String.valueOf(i8)+ ", " + String.valueOf(i9)+ ", " + String.valueOf(i10)+ ", " + String.valueOf(i11)+ ", " + String.valueOf(i12) + ", " + String.valueOf(i13)+ ", " + String.valueOf(i14) + ")";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:refreshDatamarginfactor", call);
        loadUrl(call);
	Log.v("MainActivity:refreshDatamarginfactor OK!", call);
    }

    public void refreshDatawaveformfactor(double i0, double i1, double i2, double i3, double i4, double i5, double i6, double i7, double i8, double i9, double i10, double i11, double i12, double i13, double i14) {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:refreshDatawaveformfactor(" + String.valueOf(i0)+ ", " + String.valueOf(i1)+ ", " + String.valueOf(i2)+ ", " + String.valueOf(i3)+ ", " + String.valueOf(i4)+ ", " + String.valueOf(i5)+ ", " + String.valueOf(i6)+ ", " + String.valueOf(i7)+ ", " + String.valueOf(i8)+ ", " + String.valueOf(i9)+ ", " + String.valueOf(i10)+ ", " + String.valueOf(i11)+ ", " + String.valueOf(i12) + ", " + String.valueOf(i13)+ ", " + String.valueOf(i14) + ")";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:refreshDatawaveformfactor", call);
        loadUrl(call);
	Log.v("MainActivity:refreshDatawaveformfactor OK!", call);
    }


    public void refreshFFTData0(double i0, double i1) {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:refreshFFTData0(" + String.valueOf(i0)+ ", " + String.valueOf(i1)+ ")";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:refreshFFTData0", call);
        loadUrl(call);
	Log.v("MainActivity:refreshFFTData0 OK!", call);
    }
    public void deleteFFTData0() {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:deleteFFTData0()";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:deleteFFTData0", call);
        loadUrl(call);
	Log.v("MainActivity:deleteFFTData0 OK!", call);
    }
    public void renderFFTData0() {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:renderFFTData0()";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:renderFFTData0", call);
        loadUrl(call);
	Log.v("MainActivity:renderFFTData0 OK!", call);
    }
    public void refreshFFTData1(double i0, double i1) {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:refreshFFTData1(" + String.valueOf(i0)+ ", " + String.valueOf(i1)+ ")";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:refreshFFTData1", call);
        loadUrl(call);
	Log.v("MainActivity:refreshFFTData1 OK!", call);
    }
    public void deleteFFTData1() {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:deleteFFTData1()";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:deleteFFTData1", call);
        loadUrl(call);
	Log.v("MainActivity:deleteFFTData1 OK!", call);
    }
    public void renderFFTData1() {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:renderFFTData1()";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:renderFFTData1", call);
        loadUrl(call);
	Log.v("MainActivity:renderFFTData1 OK!", call);
    }
    public void refreshFFTData2(double i0, double i1) {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:refreshFFTData2(" + String.valueOf(i0)+ ", " + String.valueOf(i1)+ ")";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:refreshFFTData2", call);
        loadUrl(call);
	Log.v("MainActivity:refreshFFTData2 OK!", call);
    }
    public void deleteFFTData2() {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:deleteFFTData2()";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:deleteFFTData2", call);
        loadUrl(call);
	Log.v("MainActivity:deleteFFTData2 OK!", call);
    }
    public void renderFFTData2() {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:renderFFTData2()";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:renderFFTData2", call);
        loadUrl(call);
	Log.v("MainActivity:renderFFTData2 OK!", call);
    }
    public void refreshFFTData3(double i0, double i1) {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:refreshFFTData3(" + String.valueOf(i0)+ ", " + String.valueOf(i1)+ ")";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:refreshFFTData3", call);
        loadUrl(call);
	Log.v("MainActivity:refreshFFTData3 OK!", call);
    }
    public void deleteFFTData3() {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:deleteFFTData3()";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:deleteFFTData3", call);
        loadUrl(call);
	Log.v("MainActivity:deleteFFTData3 OK!", call);
    }
    public void renderFFTData3() {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:renderFFTData3()";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:renderFFTData3", call);
        loadUrl(call);
	Log.v("MainActivity:renderFFTData3 OK!", call);
    }
    public void refreshFFTData4(double i0, double i1) {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:refreshFFTData4(" + String.valueOf(i0)+ ", " + String.valueOf(i1)+ ")";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:refreshFFTData4", call);
        loadUrl(call);
	Log.v("MainActivity:refreshFFTData4 OK!", call);
    }
    public void deleteFFTData4() {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:deleteFFTData4()";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:deleteFFTData4", call);
        loadUrl(call);
	Log.v("MainActivity:deleteFFTData4 OK!", call);
    }
    public void renderFFTData4() {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:renderFFTData4()";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:renderFFTData4", call);
        loadUrl(call);
	Log.v("MainActivity:renderFFTData4 OK!", call);
    }
    public void refreshFFTData5(double i0, double i1) {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:refreshFFTData5(" + String.valueOf(i0)+ ", " + String.valueOf(i1)+ ")";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:refreshFFTData5", call);
        loadUrl(call);
	Log.v("MainActivity:refreshFFTData5 OK!", call);
    }
    public void deleteFFTData5() {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:deleteFFTData5()";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:deleteFFTData5", call);
        loadUrl(call);
	Log.v("MainActivity:deleteFFTData5 OK!", call);
    }
    public void renderFFTData5() {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:renderFFTData5()";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:renderFFTData5", call);
        loadUrl(call);
	Log.v("MainActivity:renderFFTData5 OK!", call);
    }
    public void refreshFFTData6(double i0, double i1) {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:refreshFFTData6(" + String.valueOf(i0)+ ", " + String.valueOf(i1)+ ")";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:refreshFFTData6", call);
        loadUrl(call);
	Log.v("MainActivity:refreshFFTData6 OK!", call);
    }
    public void deleteFFTData6() {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:deleteFFTData6()";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:deleteFFTData6", call);
        loadUrl(call);
	Log.v("MainActivity:deleteFFTData6 OK!", call);
    }
    public void renderFFTData6() {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:renderFFTData6()";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:renderFFTData6", call);
        loadUrl(call);
	Log.v("MainActivity:renderFFTData6 OK!", call);
    }
    public void refreshFFTData7(double i0, double i1) {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:refreshFFTData7(" + String.valueOf(i0)+ ", " + String.valueOf(i1)+ ")";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:refreshFFTData7", call);
        loadUrl(call);
	Log.v("MainActivity:refreshFFTData7 OK!", call);
    }
    public void deleteFFTData7() {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:deleteFFTData7()";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:deleteFFTData7", call);
        loadUrl(call);
	Log.v("MainActivity:deleteFFTData7 OK!", call);
    }
    public void renderFFTData7() {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:renderFFTData7()";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:renderFFTData7", call);
        loadUrl(call);
	Log.v("MainActivity:renderFFTData7 OK!", call);
    }
    public void refreshFFTData8(double i0, double i1) {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:refreshFFTData8(" + String.valueOf(i0)+ ", " + String.valueOf(i1)+ ")";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:refreshFFTData8", call);
        loadUrl(call);
	Log.v("MainActivity:refreshFFTData8 OK!", call);
    }
    public void deleteFFTData8() {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:deleteFFTData8()";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:deleteFFTData8", call);
        loadUrl(call);
	Log.v("MainActivity:deleteFFTData8 OK!", call);
    }
    public void renderFFTData8() {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:renderFFTData8()";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:renderFFTData8", call);
        loadUrl(call);
	Log.v("MainActivity:renderFFTData8 OK!", call);
    }
    public void refreshFFTData9(double i0, double i1) {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:refreshFFTData9(" + String.valueOf(i0)+ ", " + String.valueOf(i1)+ ")";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:refreshFFTData9", call);
        loadUrl(call);
	Log.v("MainActivity:refreshFFTData9 OK!", call);
    }
    public void deleteFFTData9() {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:deleteFFTData9()";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:deleteFFTData9", call);
        loadUrl(call);
	Log.v("MainActivity:deleteFFTData9 OK!", call);
    }
    public void renderFFTData9() {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:renderFFTData9()";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:renderFFTData9", call);
        loadUrl(call);
	Log.v("MainActivity:renderFFTData9 OK!", call);
    }
    public void refreshFFTData10(double i0, double i1) {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:refreshFFTData10(" + String.valueOf(i0)+ ", " + String.valueOf(i1)+ ")";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:refreshFFTData10", call);
        loadUrl(call);
	Log.v("MainActivity:refreshFFTData10 OK!", call);
    }
    public void deleteFFTData10() {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:deleteFFTData10()";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:deleteFFTData10", call);
        loadUrl(call);
	Log.v("MainActivity:deleteFFTData10 OK!", call);
    }
    public void renderFFTData10() {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:renderFFTData10()";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:renderFFTData10", call);
        loadUrl(call);
	Log.v("MainActivity:renderFFTData10 OK!", call);
    }
    public void refreshFFTData11(double i0, double i1) {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:refreshFFTData11(" + String.valueOf(i0)+ ", " + String.valueOf(i1)+ ")";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:refreshFFTData11", call);
        loadUrl(call);
	Log.v("MainActivity:refreshFFTData11 OK!", call);
    }
    public void deleteFFTData11() {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:deleteFFTData11()";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:deleteFFTData11", call);
        loadUrl(call);
	Log.v("MainActivity:deleteFFTData11 OK!", call);
    }
    public void renderFFTData11() {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:renderFFTData11()";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:renderFFTData11", call);
        loadUrl(call);
	Log.v("MainActivity:renderFFTData11 OK!", call);
    }
    public void refreshFFTData12(double i0, double i1) {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:refreshFFTData12(" + String.valueOf(i0)+ ", " + String.valueOf(i1)+ ")";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:refreshFFTData12", call);
        loadUrl(call);
	Log.v("MainActivity:refreshFFTData12 OK!", call);
    }
    public void deleteFFTData12() {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:deleteFFTData12()";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:deleteFFTData12", call);
        loadUrl(call);
	Log.v("MainActivity:deleteFFTData12 OK!", call);
    }
    public void renderFFTData12() {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:renderFFTData12()";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:renderFFTData12", call);
        loadUrl(call);
	Log.v("MainActivity:renderFFTData12 OK!", call);
    }
    public void refreshFFTData13(double i0, double i1) {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:refreshFFTData13(" + String.valueOf(i0)+ ", " + String.valueOf(i1)+ ")";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:refreshFFTData13", call);
        loadUrl(call);
	Log.v("MainActivity:refreshFFTData13 OK!", call);
    }
    public void deleteFFTData13() {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:deleteFFTData13()";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:deleteFFTData13", call);
        loadUrl(call);
	Log.v("MainActivity:deleteFFTData13 OK!", call);
    }
    public void renderFFTData13() {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:renderFFTData13()";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:renderFFTData13", call);
        loadUrl(call);
	Log.v("MainActivity:renderFFTData13 OK!", call);
    }
    public void refreshFFTData14(double i0, double i1) {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:refreshFFTData14(" + String.valueOf(i0)+ ", " + String.valueOf(i1)+ ")";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:refreshFFTData14", call);
        loadUrl(call);
	Log.v("MainActivity:refreshFFTData14 OK!", call);
    }
    public void deleteFFTData14() {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:deleteFFTData14()";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:deleteFFTData14", call);
        loadUrl(call);
	Log.v("MainActivity:deleteFFTData14 OK!", call);
    }
    public void renderFFTData14() {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:renderFFTData14()";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:renderFFTData14", call);
        loadUrl(call);
	Log.v("MainActivity:renderFFTData14 OK!", call);
    }

    public void refreshDataCenterGravityGrequency(double i0, double i1, double i2, double i3, double i4, double i5, double i6, double i7, double i8, double i9, double i10, double i11, double i12, double i13, double i14) {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:refreshDataCenterGravityGrequency(" + String.valueOf(i0)+ ", " + String.valueOf(i1)+ ", " + String.valueOf(i2)+ ", " + String.valueOf(i3)+ ", " + String.valueOf(i4)+ ", " + String.valueOf(i5)+ ", " + String.valueOf(i6)+ ", " + String.valueOf(i7)+ ", " + String.valueOf(i8)+ ", " + String.valueOf(i9)+ ", " + String.valueOf(i10)+ ", " + String.valueOf(i11)+ ", " + String.valueOf(i12) + ", " + String.valueOf(i13)+ ", " + String.valueOf(i14) + ")";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:refreshDataCenterGravityGrequency", call);
        loadUrl(call);
	Log.v("MainActivity:refreshDataCenterGravityGrequency OK!", call);
    }

    public void refreshDataMeanSquareFrequency(double i0, double i1, double i2, double i3, double i4, double i5, double i6, double i7, double i8, double i9, double i10, double i11, double i12, double i13, double i14) {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:refreshDataMeanSquareFrequency(" + String.valueOf(i0)+ ", " + String.valueOf(i1)+ ", " + String.valueOf(i2)+ ", " + String.valueOf(i3)+ ", " + String.valueOf(i4)+ ", " + String.valueOf(i5)+ ", " + String.valueOf(i6)+ ", " + String.valueOf(i7)+ ", " + String.valueOf(i8)+ ", " + String.valueOf(i9)+ ", " + String.valueOf(i10)+ ", " + String.valueOf(i11)+ ", " + String.valueOf(i12) + ", " + String.valueOf(i13)+ ", " + String.valueOf(i14) + ")";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:refreshDataMeanSquareFrequency", call);
        loadUrl(call);
	Log.v("MainActivity:refreshDataMeanSquareFrequency OK!", call);
    }
    public void refreshDataRootMeanSquareFrequency(double i0, double i1, double i2, double i3, double i4, double i5, double i6, double i7, double i8, double i9, double i10, double i11, double i12, double i13, double i14) {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:refreshDataRootMeanSquareFrequency(" + String.valueOf(i0)+ ", " + String.valueOf(i1)+ ", " + String.valueOf(i2)+ ", " + String.valueOf(i3)+ ", " + String.valueOf(i4)+ ", " + String.valueOf(i5)+ ", " + String.valueOf(i6)+ ", " + String.valueOf(i7)+ ", " + String.valueOf(i8)+ ", " + String.valueOf(i9)+ ", " + String.valueOf(i10)+ ", " + String.valueOf(i11)+ ", " + String.valueOf(i12) + ", " + String.valueOf(i13)+ ", " + String.valueOf(i14) + ")";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:refreshDataRootMeanSquareFrequency", call);
        loadUrl(call);
	Log.v("MainActivity:refreshDataRootMeanSquareFrequency OK!", call);
    }
    public void refreshDataFrequencyVariance(double i0, double i1, double i2, double i3, double i4, double i5, double i6, double i7, double i8, double i9, double i10, double i11, double i12, double i13, double i14) {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:refreshDataFrequencyVariance(" + String.valueOf(i0)+ ", " + String.valueOf(i1)+ ", " + String.valueOf(i2)+ ", " + String.valueOf(i3)+ ", " + String.valueOf(i4)+ ", " + String.valueOf(i5)+ ", " + String.valueOf(i6)+ ", " + String.valueOf(i7)+ ", " + String.valueOf(i8)+ ", " + String.valueOf(i9)+ ", " + String.valueOf(i10)+ ", " + String.valueOf(i11)+ ", " + String.valueOf(i12) + ", " + String.valueOf(i13)+ ", " + String.valueOf(i14) + ")";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:refreshDataFrequencyVariance", call);
        loadUrl(call);
	Log.v("MainActivity:refreshDataFrequencyVariance OK!", call);
    }
    public void refreshDataFrequencyStandardDeviation(double i0, double i1, double i2, double i3, double i4, double i5, double i6, double i7, double i8, double i9, double i10, double i11, double i12, double i13, double i14) {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:refreshDataFrequencyStandardDeviation(" + String.valueOf(i0)+ ", " + String.valueOf(i1)+ ", " + String.valueOf(i2)+ ", " + String.valueOf(i3)+ ", " + String.valueOf(i4)+ ", " + String.valueOf(i5)+ ", " + String.valueOf(i6)+ ", " + String.valueOf(i7)+ ", " + String.valueOf(i8)+ ", " + String.valueOf(i9)+ ", " + String.valueOf(i10)+ ", " + String.valueOf(i11)+ ", " + String.valueOf(i12) + ", " + String.valueOf(i13)+ ", " + String.valueOf(i14) + ")";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:refreshDataFrequencyStandardDeviation", call);
        loadUrl(call);
	Log.v("MainActivity:refreshDataFrequencyStandardDeviation OK!", call);
    }

    public void refreshDataPowerSpectrumEntropy(double i0, double i1, double i2, double i3, double i4, double i5, double i6, double i7, double i8, double i9, double i10, double i11, double i12, double i13, double i14) {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:refreshDataPowerSpectrumEntropy(" + String.valueOf(i0)+ ", " + String.valueOf(i1)+ ", " + String.valueOf(i2)+ ", " + String.valueOf(i3)+ ", " + String.valueOf(i4)+ ", " + String.valueOf(i5)+ ", " + String.valueOf(i6)+ ", " + String.valueOf(i7)+ ", " + String.valueOf(i8)+ ", " + String.valueOf(i9)+ ", " + String.valueOf(i10)+ ", " + String.valueOf(i11)+ ", " + String.valueOf(i12) + ", " + String.valueOf(i13)+ ", " + String.valueOf(i14) + ")";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:refreshDataPowerSpectrumEntropy", call);
        loadUrl(call);
	Log.v("MainActivity:refreshDataPowerSpectrumEntropy OK!", call);
    }
    public void refreshDataSingularSpectrumEntropy(double i0, double i1, double i2, double i3, double i4, double i5, double i6, double i7, double i8, double i9, double i10, double i11, double i12, double i13, double i14) {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:refreshDataSingularSpectrumEntropy(" + String.valueOf(i0)+ ", " + String.valueOf(i1)+ ", " + String.valueOf(i2)+ ", " + String.valueOf(i3)+ ", " + String.valueOf(i4)+ ", " + String.valueOf(i5)+ ", " + String.valueOf(i6)+ ", " + String.valueOf(i7)+ ", " + String.valueOf(i8)+ ", " + String.valueOf(i9)+ ", " + String.valueOf(i10)+ ", " + String.valueOf(i11)+ ", " + String.valueOf(i12) + ", " + String.valueOf(i13)+ ", " + String.valueOf(i14) + ")";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:refreshDataSingularSpectrumEntropy", call);
        loadUrl(call);
	Log.v("MainActivity:refreshDataSingularSpectrumEntropy OK!", call);
    }
    public void refreshDataEnergyEntropy(double i0, double i1, double i2, double i3, double i4, double i5, double i6, double i7, double i8, double i9, double i10, double i11, double i12, double i13, double i14) {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:refreshDataEnergyEntropy(" + String.valueOf(i0)+ ", " + String.valueOf(i1)+ ", " + String.valueOf(i2)+ ", " + String.valueOf(i3)+ ", " + String.valueOf(i4)+ ", " + String.valueOf(i5)+ ", " + String.valueOf(i6)+ ", " + String.valueOf(i7)+ ", " + String.valueOf(i8)+ ", " + String.valueOf(i9)+ ", " + String.valueOf(i10)+ ", " + String.valueOf(i11)+ ", " + String.valueOf(i12) + ", " + String.valueOf(i13)+ ", " + String.valueOf(i14) + ")";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:refreshDataEnergyEntropy", call);
        loadUrl(call);
	Log.v("MainActivity:refreshDataEnergyEntropy OK!", call);
    }


    public void refreshDataBalance(double i0, double i1, double i2, double i3, double i4, double i5, double i6, double i7) {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:refreshDataBalance(" + String.valueOf(i0)+ ", " + String.valueOf(i1)+ ", " + String.valueOf(i2)+ ", " + String.valueOf(i3)+ ", " + String.valueOf(i4)+ ", " + String.valueOf(i5)+ ", " + String.valueOf(i6)+ ", " + String.valueOf(i7)+ ")";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:refreshDataBalance", call);
        loadUrl(call);
	Log.v("MainActivity:refreshDataBalance OK!", call);
    }


    public void refreshDataEstimateInformation(double i0, double i1, double i2, double i3, double i4, double i5, double i6, double i7) {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:refreshDataEstimateInformation(" + String.valueOf(i0)+ ", " + String.valueOf(i1)+ ", " + String.valueOf(i2)+ ", " + String.valueOf(i3)+ ", " + String.valueOf(i4)+ ", " + String.valueOf(i5)+ ", " + String.valueOf(i6)+ ", " + String.valueOf(i7)+ ")";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:refreshDataEstimateInformation", call);
        loadUrl(call);
	Log.v("MainActivity:refreshDataEstimateInformation OK!", call);
    }

    public void refreshDatamaxAmplitudeFreuqOfFTData(double i0, double i1, double i2, double i3, double i4, double i5, double i6, double i7, double i8, double i9, double i10, double i11, double i12, double i13, double i14) {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:refreshDatamaxAmplitudeFreuqOfFTData(" + String.valueOf(i0)+ ", " + String.valueOf(i1)+ ", " + String.valueOf(i2)+ ", " + String.valueOf(i3)+ ", " + String.valueOf(i4)+ ", " + String.valueOf(i5)+ ", " + String.valueOf(i6)+ ", " + String.valueOf(i7)+ ", " + String.valueOf(i8)+ ", " + String.valueOf(i9)+ ", " + String.valueOf(i10)+ ", " + String.valueOf(i11)+ ", " + String.valueOf(i12) + ", " + String.valueOf(i13)+ ", " + String.valueOf(i14) + ")";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:refreshDatamaxAmplitudeFreuqOfFTData", call);
        loadUrl(call);
	Log.v("MainActivity:refreshDatamaxAmplitudeFreuqOfFTData OK!", call);
    }


}
