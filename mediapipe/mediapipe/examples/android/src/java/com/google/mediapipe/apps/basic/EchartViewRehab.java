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

public class EchartViewRehab extends WebView {
    private static final String TAG = EchartViewRehab.class.getSimpleName();

    public EchartViewRehab(Context context) {
        this(context, null);
    }

    public EchartViewRehab(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EchartViewRehab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        WebSettings webSettings = getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportZoom(false);
        webSettings.setDisplayZoomControls(false);
        loadUrl("file:///android_asset/echartsRehab.html");
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

    public void refreshData(double i0, double i1, double i2, double i3, double i4/*, double i5, double i6, double i7, double i8*/) {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:refreshData(" + String.valueOf(i0)+ ", " + String.valueOf(i1)+ ", " + String.valueOf(i2)+ ", " + String.valueOf(i3)+ ", " + String.valueOf(i4)/*+ ", " + String.valueOf(i5)+ ", " + String.valueOf(i6)+ ", " + String.valueOf(i7)+ ", " + String.valueOf(i8)*/ + ")";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:EchartViewRehab refreshData", call);
        loadUrl(call);
	Log.v("MainActivity:EchartViewRehab refreshData OK!", call);
    }

    public void deleteRehabData() {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:deleteRehabData()";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:deleteRehabData", call);
        loadUrl(call);
	Log.v("MainActivity:deleteRehabData OK!", call);
    }

    public void refreshDataF3(double i0, double i1, double i2, double i3, double i4/*, double i5, double i6, double i7, double i8*/) {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:refreshDataF3(" + String.valueOf(i0)+ ", " + String.valueOf(i1)+ ", " + String.valueOf(i2)+ ", " + String.valueOf(i3)+ ", " + String.valueOf(i4)/*+ ", " + String.valueOf(i5)+ ", " + String.valueOf(i6)+ ", " + String.valueOf(i7)+ ", " + String.valueOf(i8)*/ + ")";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:EchartViewRehab refreshDataF3", call);
        loadUrl(call);
	Log.v("MainActivity:EchartViewRehab refreshDataF3 OK!", call);
    }

    public void deleteRehabDataF3() {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:deleteRehabDataF3()";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:deleteRehabDataF3", call);
        loadUrl(call);
	Log.v("MainActivity:deleteRehabDataF3 OK!", call);
    }

    public void refreshDataF5(double i0, double i1, double i2, double i3, double i4/*, double i5, double i6, double i7, double i8*/) {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:refreshDataF5(" + String.valueOf(i0)+ ", " + String.valueOf(i1)+ ", " + String.valueOf(i2)+ ", " + String.valueOf(i3)+ ", " + String.valueOf(i4)/*+ ", " + String.valueOf(i5)+ ", " + String.valueOf(i6)+ ", " + String.valueOf(i7)+ ", " + String.valueOf(i8)*/ + ")";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:EchartViewRehab refreshDataF5", call);
        loadUrl(call);
	Log.v("MainActivity:EchartViewRehab refreshDataF5 OK!", call);
    }

    public void deleteRehabDataF5() {
        //String call1 = "javascript:sayHello()";

        String call = "javascript:deleteRehabDataF5()";

	//Log.v("MainActivity:refreshData", call1);
	//loadUrl(call1);
	Log.v("MainActivity:deleteRehabDataF5", call);
        loadUrl(call);
	Log.v("MainActivity:deleteRehabDataF5 OK!", call);
    }

}
