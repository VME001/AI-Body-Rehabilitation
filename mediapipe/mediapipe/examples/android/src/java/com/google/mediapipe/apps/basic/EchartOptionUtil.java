package com.google.mediapipe.apps.basic;

import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Line;

import com.github.abel533.echarts.code.PointerType;
import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.style.CrossStyle;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import android.util.Log;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;

 public class EchartOptionUtil {

    public static GsonOption getLineChartOptions(Object[] xAxis, Object[] yAxis) {
        GsonOption option = new GsonOption();
        option.title("人体关键点分析");
        option.legend("中心距离");
        option.tooltip().trigger(Trigger.axis);

        ValueAxis valueAxis = new ValueAxis();
        option.yAxis(valueAxis);

        CategoryAxis categorxAxis = new CategoryAxis();
        categorxAxis.axisLine().onZero(false);
        categorxAxis.boundaryGap(true);
        categorxAxis.data(xAxis);
        option.xAxis(categorxAxis);

        Line line = new Line();
        line.smooth(false).name("中心距离").data(yAxis).itemStyle().normal().lineStyle().shadowColor("rgba(0,0,0,0.4)");
        option.series(line);
	Log.v("EchartOptionUtil", "EchartOptionUtil 1");
        return option;
    }

    public static GsonOption getLineStackedAreaChartOptions(Object[] xAxis, Object[] yAxis0/*, Object[] yAxis1, Object[] yAxis2, Object[] yAxis3, Object[] yAxis4, Object[] yAxis5, Object[] yAxis6, Object[] yAxis7, Object[] yAxis8, Object[] yAxis9, Object[] yAxis10, Object[] yAxis11, Object[] yAxis12*/) {
        GsonOption option = new GsonOption();
        option.title("人体关键点运动分析");
        option.legend("头部"/*, "左肩部", "左肘部" ,"左手腕部" , "右肩部", "右肘部" ,"右手腕部", "左臀部" ,"左膝部" ,"左脚腕部", "右臀部" ,"右膝部" ,"右脚腕部"*/ );
        option.tooltip().trigger(Trigger.axis);
        option.tooltip().axisPointer().type(PointerType.cross);
        option.tooltip().backgroundColor("#6a7985");

        option.toolbox().feature(Tool.saveAsImage);

        option.grid().left("3%").right("4%").bottom("3%").containLabel(true);

        ValueAxis valueAxis = new ValueAxis();
        option.yAxis(valueAxis);

        CategoryAxis categorxAxis = new CategoryAxis();
        categorxAxis.axisLine().onZero(false);
        categorxAxis.boundaryGap(false);
        categorxAxis.data(xAxis);
        option.xAxis(categorxAxis);

        Line line0 = new Line();
        line0.smooth(false).name("头部").data(yAxis0).itemStyle().normal().lineStyle().shadowColor("rgba(0,0,0,0.4)");
        line0.areaStyle().setColor("rgba(0,0,0,0.4)");
/*
        Line line1 = new Line();
        line1.smooth(false).name("左肩部").data(yAxis1).itemStyle().normal().lineStyle().shadowColor("rgba(6,6,6,0.4)");
        line0.areaStyle().setColor("rgba(0,0,0,0.4)");

        Line line2 = new Line();
        line2.smooth(false).name("左肩部").data(yAxis1).itemStyle().normal().lineStyle().shadowColor("rgba(6,6,6,0.4)");
        line2.areaStyle().setColor("rgba(0,0,0,0.4)");

        Line line3 = new Line();
        line3.smooth(false).name("左肩部").data(yAxis1).itemStyle().normal().lineStyle().shadowColor("rgba(6,6,6,0.4)");
        line3.areaStyle().setColor("rgba(0,0,0,0.4)");

        Line line4 = new Line();
        line4.smooth(false).name("左肩部").data(yAxis1).itemStyle().normal().lineStyle().shadowColor("rgba(6,6,6,0.4)");
        line4.areaStyle().setColor("rgba(0,0,0,0.4)");

        Line line5 = new Line();
        line5.smooth(false).name("左肩部").data(yAxis1).itemStyle().normal().lineStyle().shadowColor("rgba(6,6,6,0.4)");
        line5.areaStyle().setColor("rgba(0,0,0,0.4)");

        Line line6 = new Line();
        line6.smooth(false).name("左肩部").data(yAxis1).itemStyle().normal().lineStyle().shadowColor("rgba(6,6,6,0.4)");
        line6.areaStyle().setColor("rgba(0,0,0,0.4)");

        Line line7 = new Line();
        line7.smooth(false).name("左肩部").data(yAxis1).itemStyle().normal().lineStyle().shadowColor("rgba(6,6,6,0.4)");
        line7.areaStyle().setColor("rgba(0,0,0,0.4)");

        Line line8 = new Line();
        line8.smooth(false).name("左肩部").data(yAxis1).itemStyle().normal().lineStyle().shadowColor("rgba(6,6,6,0.4)");
        line8.areaStyle().setColor("rgba(0,0,0,0.4)");

        Line line9 = new Line();
        line9.smooth(false).name("左肩部").data(yAxis1).itemStyle().normal().lineStyle().shadowColor("rgba(6,6,6,0.4)");
        line9.areaStyle().setColor("rgba(0,0,0,0.4)");

        Line line10 = new Line();
        line10.smooth(false).name("左肩部").data(yAxis1).itemStyle().normal().lineStyle().shadowColor("rgba(6,6,6,0.4)");
        line10.areaStyle().setColor("rgba(0,0,0,0.4)");

        Line line11 = new Line();
        line11.smooth(false).name("左肩部").data(yAxis1).itemStyle().normal().lineStyle().shadowColor("rgba(6,6,6,0.4)");
        line11.areaStyle().setColor("rgba(0,0,0,0.4)");

        Line line12 = new Line();
        line12.smooth(false).name("左肩部").data(yAxis1).itemStyle().normal().lineStyle().shadowColor("rgba(6,6,6,0.4)");
        line12.areaStyle().setColor("rgba(0,0,0,0.4)");

        */
        option.series(line0/*,line1, line2, line3, line4, line5, line6, line7, line8, line9, line10, line11, line12*/);
        Log.v("EchartOptionUtil", "EchartOptionUtil 1");
        return option;
    }

}
