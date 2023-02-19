// Copyright 2019 The MediaPipe Authors.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.mediapipe.apps.basic;


import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.util.Size;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import com.google.mediapipe.components.CameraHelper;
import com.google.mediapipe.components.CameraXPreviewHelper;
import com.google.mediapipe.components.ExternalTextureConverter;
import com.google.mediapipe.components.FrameProcessor;
import com.google.mediapipe.components.PermissionHelper;
import com.google.mediapipe.framework.AndroidAssetUtil;
import com.google.mediapipe.glutil.EglManager;

import android.widget.Button;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Color;

import com.google.mediapipe.formats.proto.LandmarkProto.Landmark;
import com.google.mediapipe.formats.proto.LandmarkProto.LandmarkList;

import com.google.mediapipe.formats.proto.LandmarkProto.NormalizedLandmark;
import com.google.mediapipe.formats.proto.LandmarkProto.NormalizedLandmarkList;
import com.google.mediapipe.framework.PacketGetter;
import com.google.protobuf.InvalidProtocolBufferException;

import android.widget.Toast;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;

import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.Layout;


import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.mediapipe.apps.basic.EchartView;
import com.google.mediapipe.apps.basic.EchartViewRehab;
import com.google.mediapipe.apps.basic.EchartViewFeature;
import com.google.mediapipe.apps.basic.EchartOptionUtil;
import com.google.mediapipe.apps.basic.JSInterface;

import java.util.Calendar;//导包   kælɪndə(r) 日历类

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Arrays;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Looper;

import android.text.TextUtils;

import java.util.stream.Collectors;

import com.github.abel533.echarts.json.GsonOption;

/** Main activity of MediaPipe basic app. */
public class MainActivity extends AppCompatActivity {
  private static final String TAG = "MainActivity";

  // Flips the camera-preview frames vertically by default, before sending them into FrameProcessor
  // to be processed in a MediaPipe graph, and flips the processed frames back when they are
  // displayed. This maybe needed because OpenGL represents images assuming the image origin is at
  // the bottom-left corner, whereas MediaPipe in general assumes the image origin is at the
  // top-left corner.
  // NOTE: use "flipFramesVertically" in manifest metadata to override this behavior.
  private static final boolean FLIP_FRAMES_VERTICALLY = true;

  // Number of output frames allocated in ExternalTextureConverter.
  // NOTE: use "converterNumBuffers" in manifest metadata to override number of buffers. For
  // example, when there is a FlowLimiterCalculator in the graph, number of buffers should be at
  // least `max_in_flight + max_in_queue + 1` (where max_in_flight and max_in_queue are used in
  // FlowLimiterCalculator options). That's because we need buffers for all the frames that are in
  // flight/queue plus one for the next frame from the camera.
  private static final int NUM_BUFFERS = 2;

  static {
    // Load all native libraries needed by the app.
    System.loadLibrary("mediapipe_jni");
    try {
      System.loadLibrary("opencv_java3");
    } catch (java.lang.UnsatisfiedLinkError e) {
      // Some example apps (e.g. template matching) require OpenCV 4.
      System.loadLibrary("opencv_java4");
    }
  }

  // Sends camera-preview frames into a MediaPipe graph for processing, and displays the processed
  // frames onto a {@link Surface}.
  protected FrameProcessor processor;
  // Handles camera access via the {@link CameraX} Jetpack support library.
  protected CameraXPreviewHelper cameraHelper;

  // {@link SurfaceTexture} where the camera-preview frames can be accessed.
  private SurfaceTexture previewFrameTexture;
  // {@link SurfaceView} that displays the camera-preview frames processed by a MediaPipe graph.
  private SurfaceView previewDisplayView;

  //private SurfaceView button2DisplayView;

  //private SurfaceView button3DisplayView;

  //private SurfaceView button4DisplayView;

  // Creates and manages an {@link EGLContext}.
  private EglManager eglManager;
  // Converts the GL_TEXTURE_EXTERNAL_OES texture from Android camera into a regular texture to be
  // consumed by {@link FrameProcessor} and the underlying MediaPipe graph.
  private ExternalTextureConverter converter;

  // ApplicationInfo for retrieving metadata defined in the manifest.
  private ApplicationInfo applicationInfo;

  private static String poseLandmarkStrDemo;

  private ViewGroup viewGroup;

  private static double landMarksData[][] = new double[33][3];

  private static double landMarksDataCurrent[][] = new double[33][3];

  private static double landMarksDataOneSecondPre[][] = new double[33][3];

  private static double landMarksDataTwoSecondPre[][] = new double[33][3];

  private static double landMarksDataSpeed1[] = new double[33];

  private static double landMarksDataSpeed0[] = new double[33];

  private static double landMarksDataAcceleration[] = new double[33];

  private static final String OUTPUT_LANDMARKS_STREAM_NAME = "pose_landmarks";

  private static final String OUTPUT_POSE_WORLD_LANDMARKS_STREAM_NAME = "pose_world_landmarks";

  public static EchartView lineChart;

  public static EchartViewRehab lineChartRehab;

  public static EchartViewFeature lineChartFeature;

  MyHandler myHandler;

  public static volatile boolean receivedPoseFlag = false;

  public static JSInterface mInterface;

  public MyAccelerationThread myAccelerationThread;

  public Button btn1;

  public Button btn2;

  public Button btn3;

  public Button btnSave;

  public Button btnOpen;

  public Button btnLoad;

  public int runStopFlag = 0x5A;

  public int leftKneeAngle = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    // super.onCreate(savedInstanceState);
    // setContentView(getContentViewLayoutResId());
    // lineChart = findViewById(R.id.echartsView001);

    super.onCreate(savedInstanceState);
	//this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
	mInterface = new JSInterface();
        lineChart = findViewById(R.id.lineChart);
	lineChart.addJavascriptInterface(mInterface, "EventJavascriptInterface");
        lineChart.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //最好在h5页面加载完毕后再加载数据，防止html的标签还未加载完成，不能正常显示
                //refreshLineChart(); 
		
            }
        });

	lineChartRehab = findViewById(R.id.lineChartRehab);
        lineChartRehab.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //最好在h5页面加载完毕后再加载数据，防止html的标签还未加载完成，不能正常显示
                //refreshLineChart(); 
		
            }
        });

	lineChartFeature = findViewById(R.id.lineChartFeature);
        lineChartFeature.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //最好在h5页面加载完毕后再加载数据，防止html的标签还未加载完成，不能正常显示
                //refreshLineChart(); 
		
            }
        });
		
/*
    lineChart.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
		Log.v(TAG, "onPageFinished(WebView view, String url) 1");
                super.onPageFinished(view, url);
		Log.v(TAG, "onPageFinished(WebView view, String url) 2");
                //最好在h5页面加载完毕后再加载数据，防止html的标签还未加载完成，不能正常显示
                refreshLineChart();
            }
        });*/



    try {
      applicationInfo =
          getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
    } catch (NameNotFoundException e) {
      Log.e(TAG, "Cannot find application info: " + e);
    }

    previewDisplayView = new SurfaceView(this);
    //button2DisplayView = new MySurfaceView2(this);
    //button3DisplayView = new MySurfaceView3(this);
    //button4DisplayView = new MySurfaceView4(this);
    setupPreviewDisplayView();

	lineChart.setVisibility(View.INVISIBLE);
	lineChartRehab.setVisibility(View.INVISIBLE);
	lineChartFeature.setVisibility(View.INVISIBLE);

    // Initialize asset manager so that MediaPipe native libraries can access the app assets, e.g.,
    // binary graphs.
    AndroidAssetUtil.initializeNativeAssetManager(this);
    eglManager = new EglManager(null);
    processor =
        new FrameProcessor(
            this,
            eglManager.getNativeContext(),
            applicationInfo.metaData.getString("binaryGraphName"),
            applicationInfo.metaData.getString("inputVideoStreamName"),
            applicationInfo.metaData.getString("outputVideoStreamName"));
    processor
        .getVideoSurfaceOutput()
        .setFlipY(
            applicationInfo.metaData.getBoolean("flipFramesVertically", FLIP_FRAMES_VERTICALLY));

    PermissionHelper.checkAndRequestCameraPermissions(this);

	button1Action();
	button2Action();
	button3Action();
	button4Action();

        myHandler = new MyHandler();

	// new Thread(new MyAccelerationThread()).start();
	// new Thread(new UpdateSpeedThread()).start();

	processor.addPacketCallback(
          OUTPUT_POSE_WORLD_LANDMARKS_STREAM_NAME,
          (packet) -> {
             Log.v(TAG, "Received pose world landmarks packet. receivedPoseFlag: " + String.valueOf(receivedPoseFlag));

            try {

              byte[] landmarksRaw = PacketGetter.getProtoBytes(packet);
              LandmarkList poseLandmarks = LandmarkList.parseFrom(landmarksRaw);
	      getPoseLandmarksDebugString(poseLandmarks);
		if (receivedPoseFlag == false)
			{
			        myAccelerationThread = new MyAccelerationThread();
				new Thread(myAccelerationThread).start();
				receivedPoseFlag = true;
			}
             // Log.v(TAG, "[TS:" + packet.getTimestamp() + "] " + getPoseLandmarksDebugString(poseLandmarks));
		//for (int i = 0; i < landMarksData.length; i++) {
         	   
        	     //  Log.v(TAG, "landMarksData[" + String.valueOf(i) + "] : " + String.valueOf(landMarksData[i][0]) + ", " +String.valueOf(landMarksData[i][1]) + ", " + String.valueOf(landMarksData[i][2]));
       		 
		//}
		
            } catch (InvalidProtocolBufferException exception) {
              Log.e(TAG, "Failed to get proto.", exception);
            }
          });

        btn1 = this.findViewById(R.id.buttonExit);
        btn1.setOnClickListener(new ButtonClickListener());
	btn1.setStateListAnimator(null);
	/*	
	btn2 = this.findViewById(R.id.buttonStopGo);
        btn2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //System.exit(0);
                CharSequence text = btn2.getText();
                if (TextUtils.equals(text,"暂停")){
                    myAccelerationThread.pauseThread();
                    btn2.setText("开始");
                }
                else{
                    myAccelerationThread.resumeThread();
                    btn2.setText("暂停");
                }
            }
        });

	btn3 = this.findViewById(R.id.buttonDelete);
        btn3.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //System.exit(0);
                lineChart.deleteData();
            }
        });*/
/*
	btnSave = this.findViewById(R.id.buttonSave);
        btnSave.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //System.exit(0);
                //lineChart.deleteData();
            }
        });

	btnOpen = this.findViewById(R.id.buttonOpen);
        btnOpen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //System.exit(0);
                //lineChart.deleteData();
            }
        });

	btnLoad = this.findViewById(R.id.buttonLoad);
        btnLoad.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //System.exit(0);
                //lineChart.deleteData();
            }
        });
*/
	btn1.setVisibility(View.VISIBLE);
	//btn2.setVisibility(View.INVISIBLE);
	//btn3.setVisibility(View.INVISIBLE);
	//btnSave.setVisibility(View.INVISIBLE);
	//btnOpen.setVisibility(View.INVISIBLE);
	//btnLoad.setVisibility(View.INVISIBLE);
/*
processor.addPacketCallback(
          OUTPUT_LANDMARKS_STREAM_NAME,
          (packet) -> {
             Log.v(TAG, "Received pose landmarks packet.");
            try {

              byte[] landmarksRaw = PacketGetter.getProtoBytes(packet);
              LandmarkList poseLandmarks = LandmarkList.parseFrom(landmarksRaw);
	      getPoseLandmarksDebugString(poseLandmarks);

              Log.v(TAG, "[TS:" + packet.getTimestamp() + "] " + getPoseLandmarksDebugString(poseLandmarks));
		//for (int i = 0; i < landMarksData.length; i++) {
         	   
        	     //  Log.v(TAG, "landMarksData[" + String.valueOf(i) + "] : " + String.valueOf(landMarksData[i][0]) + ", " +String.valueOf(landMarksData[i][1]) + ", " + String.valueOf(landMarksData[i][2]));
       		 
		//}
		
            } catch (InvalidProtocolBufferException exception) {
              Log.e(TAG, "Failed to get proto.", exception);
            }
          });
*/
		

   	 new Thread(new Runnable() {
	     @Override
	     public void run() {
		while(true){
		 try{
		if (mInterface.latestFFTData == true){
			mInterface.latestFFTData = false;
			Message msg = new Message();
			Bundle b = new Bundle();// 存放数据
			b.putInt("case", 200);
			msg.setData(b);
			MainActivity.this.myHandler.sendMessage(msg);// 向Handler发送消息,更新UI
		}
		     Thread.sleep(10);
		 } catch (InterruptedException e){
		     e.printStackTrace();
		 }
	     }
		}
	 }).start();


  }


 private class ButtonClickListener implements View.OnClickListener {

        public void onClick(View v) {
            //System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

  private void button1Action() {
        Button startButton1 = findViewById(R.id.button1);
        startButton1.setOnClickListener(
                v -> {
		Log.v(TAG, "button1Action.");
		previewDisplayView.setVisibility(View.VISIBLE);
		lineChart.setVisibility(View.INVISIBLE);
		lineChartRehab.setVisibility(View.INVISIBLE);
		lineChartFeature.setVisibility(View.INVISIBLE);
		//button3DisplayView.setVisibility(View.INVISIBLE);
		//button4DisplayView.setVisibility(View.INVISIBLE);
		btn1.setVisibility(View.VISIBLE);
		//btn2.setVisibility(View.INVISIBLE);
		//btn3.setVisibility(View.INVISIBLE);
		//btnSave.setVisibility(View.INVISIBLE);
		//btnOpen.setVisibility(View.INVISIBLE);
		//btnLoad.setVisibility(View.INVISIBLE);
	});
  }

  private void button2Action() {
        Button startButton2 = findViewById(R.id.button2);
        startButton2.setOnClickListener(
                v -> {
		Log.v(TAG, "button2Action.");
		previewDisplayView.setVisibility(View.INVISIBLE);
		//button3DisplayView.setVisibility(View.INVISIBLE);
		//button4DisplayView.setVisibility(View.INVISIBLE);
		lineChart.setVisibility(View.VISIBLE);
		lineChartRehab.setVisibility(View.INVISIBLE);
		lineChartFeature.setVisibility(View.INVISIBLE);
		btn1.setVisibility(View.VISIBLE);
		//btn2.setVisibility(View.VISIBLE);
		//btn3.setVisibility(View.VISIBLE);
		//btnSave.setVisibility(View.INVISIBLE);
		//btnOpen.setVisibility(View.INVISIBLE);
		//btnLoad.setVisibility(View.INVISIBLE);
		// refreshLineChartTest();

		// refreshLineChart1();
		// button2DisplayView = new MySurfaceView2(this);
		// viewGroup.addView(button2DisplayView);
		// button2DisplayView.setVisibility(View.VISIBLE);
		// viewGroup.addView(eChart);
               //eChart = findViewById(R.id.eChart);
/*
        WebView webView=findViewById(R.id.webview);
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        webView.setBackgroundColor(0);
        //开启本地文件读取（默认为true，不设置也可以）
        webView.getSettings().setAllowFileAccess(true);
        //开启脚本支持
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/echarts.html");

	 new Thread(new Runnable() {
	     @Override
	     public void run() {
		 try{
		     Thread.sleep(2000);
		 } catch (InterruptedException e){
		     e.printStackTrace();
		 }
	     }
	 }).start();*/
	// webView.loadUrl("content://Download/assets/echarts.html");
	/*
		new AlertDialog.Builder(this).setTitle("findViewById")//设置对话框标题  

                .setMessage("findViewById")//设置显示的内容  

                .setPositiveButton("退出",new DialogInterface.OnClickListener() {//添加确定按钮  



                    @Override

                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件  

                        // TODO Auto-generated method stub  

                        Log.i("alertdialog"," 请保存数据！");  

                    }

                }).show();//在按键响应事件中显示此对话框

        */



	});
  }

  private void refreshLineChart(){
		Log.v(TAG, "refreshLineChart()");
        Object[] x = new Object[]{
                "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"
        };
        Object[] y = new Object[]{
                820, 932, 901, 934, 1290, 1330, 1320
        };
			Log.v(TAG, Arrays.toString(x));Log.v(TAG, Arrays.toString(y));
        lineChart.refreshEchartsWithOption(EchartOptionUtil.getLineChartOptions(x, y));
    }
  private void refreshLineChart1(){
		Log.v(TAG, "refreshLineChart() 1");
        Object[] x = new Object[]{
                "00:00:01", "00:00:02", "00:00:03", "00:00:04", "00:00:05", "00:00:06", "00:00:07","00:00:07", "00:00:08", "00:00:09", "00:00:10", "00:00:11", "00:00:12", "00:00:13"
        };
        Object[] y = new Object[]{
                820, 932, 901, 934, 1290, 1330, 1320, 820, 932, 901, 934, 1290, 1330, 1320
        };
		Log.v(TAG, Arrays.toString(x));Log.v(TAG, Arrays.toString(y));
        lineChart.refreshEchartsWithOption(EchartOptionUtil.getLineChartOptions(x, y));
    }

  private void button3Action() {
        Button startButton3 = findViewById(R.id.button3);
        startButton3.setOnClickListener(
                v -> {
		Log.v(TAG, "button3Action.");
		previewDisplayView.setVisibility(View.INVISIBLE);
		lineChart.setVisibility(View.INVISIBLE);
		lineChartRehab.setVisibility(View.INVISIBLE);
		btn1.setVisibility(View.VISIBLE);
		//btn2.setVisibility(View.INVISIBLE);
		//btn3.setVisibility(View.INVISIBLE);
		/*
if (mInterface.latestFFTData == true){
		mInterface.latestFFTData = false;
		this.lineChartFeature.deleteFFTData0();Log.v(TAG, "button3Action.deleteDataFFT");
		this.lineChartFeature.deleteFFTData1();
		this.lineChartFeature.deleteFFTData2();
		this.lineChartFeature.deleteFFTData3();
		this.lineChartFeature.deleteFFTData4();
		this.lineChartFeature.deleteFFTData5();
		this.lineChartFeature.deleteFFTData6();
		this.lineChartFeature.deleteFFTData7();
		this.lineChartFeature.deleteFFTData8();
		this.lineChartFeature.deleteFFTData9();
		this.lineChartFeature.deleteFFTData10();
		this.lineChartFeature.deleteFFTData11();
		this.lineChartFeature.deleteFFTData12();
		this.lineChartFeature.deleteFFTData13();
		this.lineChartFeature.deleteFFTData14();
		this.lineChartFeature.refreshDataMax(mInterface.maxdataJava[0],
			mInterface.maxdataJava[1],
			mInterface.maxdataJava[2],
			mInterface.maxdataJava[3],
			mInterface.maxdataJava[4],
			mInterface.maxdataJava[5],
			mInterface.maxdataJava[6],
			mInterface.maxdataJava[7],
			mInterface.maxdataJava[8],
			mInterface.maxdataJava[9],
			mInterface.maxdataJava[10],
			mInterface.maxdataJava[11],
			mInterface.maxdataJava[12],
			mInterface.maxdataJava[13],
			mInterface.maxdataJava[14]);
		this.lineChartFeature.refreshDataMin(mInterface.mindataJava[0],
			mInterface.mindataJava[1],
			mInterface.mindataJava[2],
			mInterface.mindataJava[3],
			mInterface.mindataJava[4],
			mInterface.mindataJava[5],
			mInterface.mindataJava[6],
			mInterface.mindataJava[7],
			mInterface.mindataJava[8],
			mInterface.mindataJava[9],
			mInterface.mindataJava[10],
			mInterface.mindataJava[11],
			mInterface.mindataJava[12],
			mInterface.mindataJava[13],
			mInterface.mindataJava[14]);
		this.lineChartFeature.refreshDataaverage(mInterface.averagedataJava[0],
			mInterface.averagedataJava[1],
			mInterface.averagedataJava[2],
			mInterface.averagedataJava[3],
			mInterface.averagedataJava[4],
			mInterface.averagedataJava[5],
			mInterface.averagedataJava[6],
			mInterface.averagedataJava[7],
			mInterface.averagedataJava[8],
			mInterface.averagedataJava[9],
			mInterface.averagedataJava[10],
			mInterface.averagedataJava[11],
			mInterface.averagedataJava[12],
			mInterface.averagedataJava[13],
			mInterface.averagedataJava[14]);
		this.lineChartFeature.refreshDatapeak(mInterface.peakdataJava[0],
			mInterface.peakdataJava[1],
			mInterface.peakdataJava[2],
			mInterface.peakdataJava[3],
			mInterface.peakdataJava[4],
			mInterface.peakdataJava[5],
			mInterface.peakdataJava[6],
			mInterface.peakdataJava[7],
			mInterface.peakdataJava[8],
			mInterface.peakdataJava[9],
			mInterface.peakdataJava[10],
			mInterface.peakdataJava[11],
			mInterface.peakdataJava[12],
			mInterface.peakdataJava[13],
			mInterface.peakdataJava[14]);
		this.lineChartFeature.refreshDataRMEAN(mInterface.RMEANdataJava[0],
			mInterface.RMEANdataJava[1],
			mInterface.RMEANdataJava[2],
			mInterface.RMEANdataJava[3],
			mInterface.RMEANdataJava[4],
			mInterface.RMEANdataJava[5],
			mInterface.RMEANdataJava[6],
			mInterface.RMEANdataJava[7],
			mInterface.RMEANdataJava[8],
			mInterface.RMEANdataJava[9],
			mInterface.RMEANdataJava[10],
			mInterface.RMEANdataJava[11],
			mInterface.RMEANdataJava[12],
			mInterface.RMEANdataJava[13],
			mInterface.RMEANdataJava[14]);
		this.lineChartFeature.refreshDatameanSquareValue(mInterface.meanSquareValueJava[0],
			mInterface.meanSquareValueJava[1],
			mInterface.meanSquareValueJava[2],
			mInterface.meanSquareValueJava[3],
			mInterface.meanSquareValueJava[4],
			mInterface.meanSquareValueJava[5],
			mInterface.meanSquareValueJava[6],
			mInterface.meanSquareValueJava[7],
			mInterface.meanSquareValueJava[8],
			mInterface.meanSquareValueJava[9],
			mInterface.meanSquareValueJava[10],
			mInterface.meanSquareValueJava[11],
			mInterface.meanSquareValueJava[12],
			mInterface.meanSquareValueJava[13],
			mInterface.meanSquareValueJava[14]);
		this.lineChartFeature.refreshDatavariance(mInterface.varianceJava[0],
			mInterface.varianceJava[1],
			mInterface.varianceJava[2],
			mInterface.varianceJava[3],
			mInterface.varianceJava[4],
			mInterface.varianceJava[5],
			mInterface.varianceJava[6],
			mInterface.varianceJava[7],
			mInterface.varianceJava[8],
			mInterface.varianceJava[9],
			mInterface.varianceJava[10],
			mInterface.varianceJava[11],
			mInterface.varianceJava[12],
			mInterface.varianceJava[13],
			mInterface.varianceJava[14]);
		this.lineChartFeature.refreshDataRMS(mInterface.RMSJava[0],
			mInterface.RMSJava[1],
			mInterface.RMSJava[2],
			mInterface.RMSJava[3],
			mInterface.RMSJava[4],
			mInterface.RMSJava[5],
			mInterface.RMSJava[6],
			mInterface.RMSJava[7],
			mInterface.RMSJava[8],
			mInterface.RMSJava[9],
			mInterface.RMSJava[10],
			mInterface.RMSJava[11],
			mInterface.RMSJava[12],
			mInterface.RMSJava[13],
			mInterface.RMSJava[14]);
		this.lineChartFeature.refreshDatastandardDeviation(mInterface.standardDeviationJava[0],
			mInterface.standardDeviationJava[1],
			mInterface.standardDeviationJava[2],
			mInterface.standardDeviationJava[3],
			mInterface.standardDeviationJava[4],
			mInterface.standardDeviationJava[5],
			mInterface.standardDeviationJava[6],
			mInterface.standardDeviationJava[7],
			mInterface.standardDeviationJava[8],
			mInterface.standardDeviationJava[9],
			mInterface.standardDeviationJava[10],
			mInterface.standardDeviationJava[11],
			mInterface.standardDeviationJava[12],
			mInterface.standardDeviationJava[13],
			mInterface.standardDeviationJava[14]);
		this.lineChartFeature.refreshDataKurtosis(mInterface.KurtosisJava[0],
			mInterface.KurtosisJava[1],
			mInterface.KurtosisJava[2],
			mInterface.KurtosisJava[3],
			mInterface.KurtosisJava[4],
			mInterface.KurtosisJava[5],
			mInterface.KurtosisJava[6],
			mInterface.KurtosisJava[7],
			mInterface.KurtosisJava[8],
			mInterface.KurtosisJava[9],
			mInterface.KurtosisJava[10],
			mInterface.KurtosisJava[11],
			mInterface.KurtosisJava[12],
			mInterface.KurtosisJava[13],
			mInterface.KurtosisJava[14]);
		this.lineChartFeature.refreshDataskewness(mInterface.skewnessJava[0],
			mInterface.skewnessJava[1],
			mInterface.skewnessJava[2],
			mInterface.skewnessJava[3],
			mInterface.skewnessJava[4],
			mInterface.skewnessJava[5],
			mInterface.skewnessJava[6],
			mInterface.skewnessJava[7],
			mInterface.skewnessJava[8],
			mInterface.skewnessJava[9],
			mInterface.skewnessJava[10],
			mInterface.skewnessJava[11],
			mInterface.skewnessJava[12],
			mInterface.skewnessJava[13],
			mInterface.skewnessJava[14]);
		this.lineChartFeature.refreshDatapeakfactor(mInterface.peakfactorJava[0],
			mInterface.peakfactorJava[1],
			mInterface.peakfactorJava[2],
			mInterface.peakfactorJava[3],
			mInterface.peakfactorJava[4],
			mInterface.peakfactorJava[5],
			mInterface.peakfactorJava[6],
			mInterface.peakfactorJava[7],
			mInterface.peakfactorJava[8],
			mInterface.peakfactorJava[9],
			mInterface.peakfactorJava[10],
			mInterface.peakfactorJava[11],
			mInterface.peakfactorJava[12],
			mInterface.peakfactorJava[13],
			mInterface.peakfactorJava[14]);
		this.lineChartFeature.refreshDatapulsefactor(mInterface.pulsefactorJava[0],
			mInterface.pulsefactorJava[1],
			mInterface.pulsefactorJava[2],
			mInterface.pulsefactorJava[3],
			mInterface.pulsefactorJava[4],
			mInterface.pulsefactorJava[5],
			mInterface.pulsefactorJava[6],
			mInterface.pulsefactorJava[7],
			mInterface.pulsefactorJava[8],
			mInterface.pulsefactorJava[9],
			mInterface.pulsefactorJava[10],
			mInterface.pulsefactorJava[11],
			mInterface.pulsefactorJava[12],
			mInterface.pulsefactorJava[13],
			mInterface.pulsefactorJava[14]);
		this.lineChartFeature.refreshDatamarginfactor(mInterface.marginfactorJava[0],
			mInterface.marginfactorJava[1],
			mInterface.marginfactorJava[2],
			mInterface.marginfactorJava[3],
			mInterface.marginfactorJava[4],
			mInterface.marginfactorJava[5],
			mInterface.marginfactorJava[6],
			mInterface.marginfactorJava[7],
			mInterface.marginfactorJava[8],
			mInterface.marginfactorJava[9],
			mInterface.marginfactorJava[10],
			mInterface.marginfactorJava[11],
			mInterface.marginfactorJava[12],
			mInterface.marginfactorJava[13],
			mInterface.marginfactorJava[14]);
		this.lineChartFeature.refreshDatawaveformfactor(mInterface.waveformfactorJava[0],
			mInterface.waveformfactorJava[1],
			mInterface.waveformfactorJava[2],
			mInterface.waveformfactorJava[3],
			mInterface.waveformfactorJava[4],
			mInterface.waveformfactorJava[5],
			mInterface.waveformfactorJava[6],
			mInterface.waveformfactorJava[7],
			mInterface.waveformfactorJava[8],
			mInterface.waveformfactorJava[9],
			mInterface.waveformfactorJava[10],
			mInterface.waveformfactorJava[11],
			mInterface.waveformfactorJava[12],
			mInterface.waveformfactorJava[13],
			mInterface.waveformfactorJava[14]);

		double sampleFreq = 10.0;
		for(int i = 0; i<=(this.mInterface.dataFFT0Length/2); i++){
		if(i==0){
			this.lineChartFeature.refreshFFTData0((double) (sampleFreq*(double)i/(double)this.mInterface.dataFFT0Length),
			(double)(0));
			i++;
		}
		this.lineChartFeature.refreshFFTData0((double) (sampleFreq*(double)i/(double)this.mInterface.dataFFT0Length),
			(double)mInterface.dataFFT0.get(i));
		}
		for(int i = 0; i<=(this.mInterface.dataFFT0Length/2); i++){
		if(i==0){
			this.lineChartFeature.refreshFFTData1((double) (sampleFreq*(double)i/(double)this.mInterface.dataFFT0Length),
			(double)(0));
			i++;
		}
		this.lineChartFeature.refreshFFTData1((double) (sampleFreq*(double)i/(double)this.mInterface.dataFFT0Length),
			(double)mInterface.dataFFT1.get(i));
		}
		for(int i = 0; i<=(this.mInterface.dataFFT0Length/2); i++){
		if(i==0){
			this.lineChartFeature.refreshFFTData2((double) (sampleFreq*(double)i/(double)this.mInterface.dataFFT0Length),
			(double)(0));
			i++;
		}
		this.lineChartFeature.refreshFFTData2((double) (sampleFreq*(double)i/(double)this.mInterface.dataFFT0Length),
			(double)mInterface.dataFFT2.get(i));
		}
		for(int i = 0; i<=(this.mInterface.dataFFT0Length/2); i++){
		if(i==0){
			this.lineChartFeature.refreshFFTData3((double) (sampleFreq*(double)i/(double)this.mInterface.dataFFT0Length),
			(double)(0));
			i++;
		}
		this.lineChartFeature.refreshFFTData3((double) (sampleFreq*(double)i/(double)this.mInterface.dataFFT0Length),
			(double)mInterface.dataFFT3.get(i));
		}
		for(int i = 0; i<=(this.mInterface.dataFFT0Length/2); i++){
		if(i==0){
			this.lineChartFeature.refreshFFTData4((double) (sampleFreq*(double)i/(double)this.mInterface.dataFFT0Length),
			(double)(0));
			i++;
		}
		this.lineChartFeature.refreshFFTData4((double) (sampleFreq*(double)i/(double)this.mInterface.dataFFT0Length),
			(double)mInterface.dataFFT4.get(i));
		}
		for(int i = 0; i<=(this.mInterface.dataFFT0Length/2); i++){
		if(i==0){
			this.lineChartFeature.refreshFFTData5((double) (sampleFreq*(double)i/(double)this.mInterface.dataFFT0Length),
			(double)(0));
			i++;
		}
		this.lineChartFeature.refreshFFTData5((double) (sampleFreq*(double)i/(double)this.mInterface.dataFFT0Length),
			(double)mInterface.dataFFT5.get(i));
		}
		for(int i = 0; i<=(this.mInterface.dataFFT0Length/2); i++){
		if(i==0){
			this.lineChartFeature.refreshFFTData6((double) (sampleFreq*(double)i/(double)this.mInterface.dataFFT0Length),
			(double)(0));
			i++;
		}
		this.lineChartFeature.refreshFFTData6((double) (sampleFreq*(double)i/(double)this.mInterface.dataFFT0Length),
			(double)mInterface.dataFFT6.get(i));
		}
		for(int i = 0; i<=(this.mInterface.dataFFT0Length/2); i++){
		if(i==0){
			this.lineChartFeature.refreshFFTData7((double) (sampleFreq*(double)i/(double)this.mInterface.dataFFT0Length),
			(double)(0));
			i++;
		}
		this.lineChartFeature.refreshFFTData7((double) (sampleFreq*(double)i/(double)this.mInterface.dataFFT0Length),
			(double)mInterface.dataFFT7.get(i));
		}
		for(int i = 0; i<=(this.mInterface.dataFFT0Length/2); i++){
		if(i==0){
			this.lineChartFeature.refreshFFTData8((double) (sampleFreq*(double)i/(double)this.mInterface.dataFFT0Length),
			(double)(0));
			i++;
		}
		this.lineChartFeature.refreshFFTData8((double) (sampleFreq*(double)i/(double)this.mInterface.dataFFT0Length),
			(double)mInterface.dataFFT8.get(i));
		}
		for(int i = 0; i<=(this.mInterface.dataFFT0Length/2); i++){
		if(i==0){
			this.lineChartFeature.refreshFFTData9((double) (sampleFreq*(double)i/(double)this.mInterface.dataFFT0Length),
			(double)(0));
			i++;
		}
		this.lineChartFeature.refreshFFTData9((double) (sampleFreq*(double)i/(double)this.mInterface.dataFFT0Length),
			(double)mInterface.dataFFT9.get(i));
		}
		for(int i = 0; i<=(this.mInterface.dataFFT0Length/2); i++){
		if(i==0){
			this.lineChartFeature.refreshFFTData10((double) (sampleFreq*(double)i/(double)this.mInterface.dataFFT0Length),
			(double)(0));
			i++;
		}
		this.lineChartFeature.refreshFFTData10((double) (sampleFreq*(double)i/(double)this.mInterface.dataFFT0Length),
			(double)mInterface.dataFFT10.get(i));
		}
		for(int i = 0; i<=(this.mInterface.dataFFT0Length/2); i++){
		if(i==0){
			this.lineChartFeature.refreshFFTData11((double) (sampleFreq*(double)i/(double)this.mInterface.dataFFT0Length),
			(double)(0));
			i++;
		}
		this.lineChartFeature.refreshFFTData11((double) (sampleFreq*(double)i/(double)this.mInterface.dataFFT0Length),
			(double)mInterface.dataFFT11.get(i));
		}
		for(int i = 0; i<=(this.mInterface.dataFFT0Length/2); i++){
		if(i==0){
			this.lineChartFeature.refreshFFTData12((double) (sampleFreq*(double)i/(double)this.mInterface.dataFFT0Length),
			(double)(0));
			i++;
		}
		this.lineChartFeature.refreshFFTData12((double) (sampleFreq*(double)i/(double)this.mInterface.dataFFT0Length),
			(double)mInterface.dataFFT12.get(i));
		}
		for(int i = 0; i<=(this.mInterface.dataFFT0Length/2); i++){
		if(i==0){
			this.lineChartFeature.refreshFFTData13((double) (sampleFreq*(double)i/(double)this.mInterface.dataFFT0Length),
			(double)(0));
			i++;
		}
		this.lineChartFeature.refreshFFTData13((double) (sampleFreq*(double)i/(double)this.mInterface.dataFFT0Length),
			(double)mInterface.dataFFT13.get(i));
		}
		for(int i = 0; i<=(this.mInterface.dataFFT0Length/2); i++){
		if(i==0){
			this.lineChartFeature.refreshFFTData14((double) (sampleFreq*(double)i/(double)this.mInterface.dataFFT0Length),
			(double)(0));
			i++;
		}
		this.lineChartFeature.refreshFFTData14((double) (sampleFreq*(double)i/(double)this.mInterface.dataFFT0Length),
			(double)mInterface.dataFFT14.get(i));
		}
		}
		*/
		lineChartFeature.setVisibility(View.VISIBLE);

		//btnSave.setVisibility(View.VISIBLE);
		//btnOpen.setVisibility(View.VISIBLE);
		//btnLoad.setVisibility(View.VISIBLE);
		//button4DisplayView.setVisibility(View.INVISIBLE);
		//button3DisplayView = new MySurfaceView3(this);
		//viewGroup.addView(button3DisplayView);
		//button3DisplayView.setVisibility(View.VISIBLE);
		
	});
  }

  private void button4Action() {
        Button startButton4 = findViewById(R.id.button4);
        startButton4.setOnClickListener(
                v -> {
		Log.v(TAG, "button4Action.");
		previewDisplayView.setVisibility(View.INVISIBLE);
		lineChart.setVisibility(View.INVISIBLE);
		lineChartFeature.setVisibility(View.INVISIBLE);
		btn1.setVisibility(View.VISIBLE);
		//btn2.setVisibility(View.INVISIBLE);
		//btn3.setVisibility(View.INVISIBLE);
		//btnSave.setVisibility(View.INVISIBLE);
		//btnOpen.setVisibility(View.INVISIBLE);
		//btnLoad.setVisibility(View.INVISIBLE);

		lineChartRehab.setVisibility(View.VISIBLE);
		//button3DisplayView.setVisibility(View.INVISIBLE);
		//button4DisplayView = new MySurfaceView4(this);
		//viewGroup.addView(button4DisplayView);
		//button4DisplayView.setVisibility(View.VISIBLE);
	});
  }

  // Used to obtain the content view for this application. If you are extending this class, and
  // have a custom layout, override this method and return the custom layout.
  protected int getContentViewLayoutResId() {
    return R.layout.activity_main;
  }

  @Override
  protected void onResume() {
    super.onResume();
    converter =
        new ExternalTextureConverter(
            eglManager.getContext(),
            applicationInfo.metaData.getInt("converterNumBuffers", NUM_BUFFERS));
    converter.setFlipY(
        applicationInfo.metaData.getBoolean("flipFramesVertically", FLIP_FRAMES_VERTICALLY));
    converter.setConsumer(processor);
    if (PermissionHelper.cameraPermissionsGranted(this)) {
      startCamera();
    }

  }



  @Override
  protected void onPause() {
    super.onPause();
    converter.close();

    // Hide preview display until we re-open the camera again.
    previewDisplayView.setVisibility(View.GONE);
  }

  @Override
  public void onRequestPermissionsResult(
      int requestCode, String[] permissions, int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    PermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
  }

  protected void onCameraStarted(SurfaceTexture surfaceTexture) {
    previewFrameTexture = surfaceTexture;
    // Make the display view visible to start showing the preview. This triggers the
    // SurfaceHolder.Callback added to (the holder of) previewDisplayView.
    previewDisplayView.setVisibility(View.VISIBLE);
  }

  protected Size cameraTargetResolution() {
    return null; // No preference and let the camera (helper) decide.
  }

  public void startCamera() {
    cameraHelper = new CameraXPreviewHelper();
    previewFrameTexture = converter.getSurfaceTexture();
    cameraHelper.setOnCameraStartedListener(
        surfaceTexture -> {
          onCameraStarted(surfaceTexture);
        });
    CameraHelper.CameraFacing cameraFacing =
        applicationInfo.metaData.getBoolean("cameraFacingFront", false)
            ? CameraHelper.CameraFacing.FRONT
            : CameraHelper.CameraFacing.BACK;
    cameraHelper.startCamera(
        this, cameraFacing, previewFrameTexture, cameraTargetResolution());
  }

  protected Size computeViewSize(int width, int height) {
    return new Size(width, height);
  }

  protected void onPreviewDisplaySurfaceChanged(
      SurfaceHolder holder, int format, int width, int height) {
    // (Re-)Compute the ideal size of the camera-preview display (the area that the
    // camera-preview frames get rendered onto, potentially with scaling and rotation)
    // based on the size of the SurfaceView that contains the display.
    Size viewSize = computeViewSize(width, height);
    Size displaySize = cameraHelper.computeDisplaySizeFromViewSize(viewSize);
    boolean isCameraRotated = cameraHelper.isCameraRotated();

    // Configure the output width and height as the computed display size.
    converter.setDestinationSize(
        isCameraRotated ? displaySize.getHeight() : displaySize.getWidth(),
        isCameraRotated ? displaySize.getWidth() : displaySize.getHeight());
  }

  private void setupPreviewDisplayView() {
    previewDisplayView.setVisibility(View.GONE);
    viewGroup = findViewById(R.id.preview_display_layout);
    viewGroup.addView(previewDisplayView);

    previewDisplayView
        .getHolder()
        .addCallback(
            new SurfaceHolder.Callback() {
              @Override
              public void surfaceCreated(SurfaceHolder holder) {
                processor.getVideoSurfaceOutput().setSurface(holder.getSurface());
              }

              @Override
              public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                onPreviewDisplaySurfaceChanged(holder, format, width, height);
              }

              @Override
              public void surfaceDestroyed(SurfaceHolder holder) {
                processor.getVideoSurfaceOutput().setSurface(null);
              }
            });


  //viewGroup.addView(button2DisplayView);
/*
  button2DisplayView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
 
                if (holder == null) {
                    return;
                }
 

		Canvas c=holder.lockCanvas(); 
           	Paint p=new Paint(); 
          	p.setColor(Color.RED); 
   	        c.drawColor(Color.WHITE); 
       	  	// c.drawText("Button2", 500, 500, p); 
		c.drawText(poseLandmarkStrDemo, 100, 100, p); 
          	holder.unlockCanvasAndPost(c); 
		
 
            }
 
            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
 
            }
 
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
 
            }
        });*/
  //viewGroup.addView(button3DisplayView);
/*
  button3DisplayView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
 
                if (holder == null) {
                    return;
                }
		Canvas c=holder.lockCanvas(); 
           	Paint p=new Paint(); 
          	p.setColor(Color.RED); 
   	        c.drawColor(Color.WHITE); 
       	  	c.drawText("Button3", 500, 500, p); 
          	holder.unlockCanvasAndPost(c);  

 
            }
 
            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
 
            }
 
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
 
            }
        });*/
  //viewGroup.addView(button4DisplayView);
/*
  button4DisplayView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
 
                if (holder == null) {
                    return;
                }
 
		Canvas c=holder.lockCanvas(); 
           	Paint p=new Paint(); 
          	p.setColor(Color.RED); 
   	        c.drawColor(Color.WHITE); 
       	  	c.drawText("Button4", 500, 500, p); 
          	holder.unlockCanvasAndPost(c); 
 
            }
 
            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
 
            }
 
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
 
            }
	
        });
*/
  //button2DisplayView.setVisibility(View.GONE);
  //button3DisplayView.setVisibility(View.GONE);
  //button4DisplayView.setVisibility(View.GONE);
  }

  private static String getPoseLandmarksDebugString(LandmarkList poseLandmarks) {
    String poseLandmarkStr = "Pose landmarks: " + poseLandmarks.getLandmarkCount() + "\n";
    int landmarkIndex = 0;
    for (Landmark landmark : poseLandmarks.getLandmarkList()) {
	landMarksData[landmarkIndex][0]= Double.valueOf(landmark.getX()).doubleValue();
	landMarksData[landmarkIndex][1]= Double.valueOf(landmark.getY()).doubleValue();
	landMarksData[landmarkIndex][2]= Double.valueOf(landmark.getZ()).doubleValue();
      poseLandmarkStr += "\tLandmark ["
          + landmarkIndex
          + "]: ("
          + landmark.getX()
          + ", "
          + landmark.getY()
          + ", "
          + landmark.getZ()
          + ")\n";
      ++landmarkIndex;
    }
    poseLandmarkStrDemo = poseLandmarkStr;
    return poseLandmarkStr;
  }


// 自定义的SurfaceView子类

    class MySurfaceView2 extends SurfaceView implements SurfaceHolder.Callback {

 


 
	private volatile boolean flag; // 线程运行的标识，用于控制线程  

       SurfaceHolder Holder;

 

       public MySurfaceView2(Context context) {

           super(context);

           Holder = this.getHolder();// 获取holder

           Holder.addCallback(this);

       }

 

       @Override

       public void surfaceChanged(SurfaceHolder holder, int format, int width,

              int height) {

           // TODO Auto-generated method stub

 

       }

 

       @Override

       public void surfaceCreated(SurfaceHolder holder) {

           // 启动自定义线程
		flag = true;
           new Thread(new MyThread()).start();

       }

 

       @Override

       public void surfaceDestroyed(SurfaceHolder holder) {

           // TODO Auto-generated method stub
	flag = false; // 把线程运行的标识设置成false  
        Holder.removeCallback(this);  

 

       }

 

       // 自定义线程类

       class MyThread implements Runnable {

 

           @Override

           public void run() {

              Canvas canvas = null;

              int rotate = 100;// 旋转角度变量

              while (flag) {

                  try {
			/*
         	        canvas =Holder.lockCanvas(); 
           		Paint p=new Paint(); 
          		p.setColor(Color.BLACK); 
   	        	canvas.drawColor(Color.WHITE); 
			canvas.drawText(poseLandmarkStrDemo, (rotate += 10), (rotate += 10), p); 
       	  		// canvas.drawText(poseLandmarkStrDemo, (rotate += 10), (rotate += 10), p); 
			// c.drawText(poseLandmarkStrDemo, 100, 100, p); 
          		// Holder.unlockCanvasAndPost(canvas); */

		canvas =Holder.lockCanvas(); 
		canvas.drawColor(Color.BLACK); 
 		TextPaint textPaint = new TextPaint();
                textPaint.setColor(Color.parseColor("#ffffff"));
                textPaint.setTextSize(50.0F);
                textPaint.setAntiAlias(true);
                StaticLayout layout = new StaticLayout("Button2: "+poseLandmarkStrDemo, textPaint, 300, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
               // 这里的参数300，表示字符串的长度，当满300时，就会换行，也可以使用“\r\n”来实现换行
                canvas.save();
                canvas.translate(100,100);//从100，100开始画
                layout.draw(canvas);
                //canvas.restore();
                     // 休眠以控制最大帧频为每秒约30帧
			Holder.unlockCanvasAndPost(canvas);// 解锁画布，提交画好的图像
                     Thread.sleep(500);


                  } catch (Exception e) {

                  } finally {

                     //Holder.unlockCanvasAndPost(canvas);// 解锁画布，提交画好的图像

                  }

              }

           }

 

       }

 

    }



// 自定义的SurfaceView子类

    class MySurfaceView3 extends SurfaceView implements SurfaceHolder.Callback {

 


 
	private volatile boolean flag; // 线程运行的标识，用于控制线程  

       SurfaceHolder Holder;

 

       public MySurfaceView3(Context context) {

           super(context);

           Holder = this.getHolder();// 获取holder

           Holder.addCallback(this);

       }

 

       @Override

       public void surfaceChanged(SurfaceHolder holder, int format, int width,

              int height) {

           // TODO Auto-generated method stub

 

       }

 

       @Override

       public void surfaceCreated(SurfaceHolder holder) {

           // 启动自定义线程
		flag = true;
           new Thread(new MyThread()).start();

       }

 

       @Override

       public void surfaceDestroyed(SurfaceHolder holder) {

           // TODO Auto-generated method stub
	flag = false; // 把线程运行的标识设置成false  
        Holder.removeCallback(this);  

 

       }

 

       // 自定义线程类

       class MyThread implements Runnable {

 

           @Override

           public void run() {

              Canvas canvas = null;

              int rotate = 100;// 旋转角度变量

              while (flag) {

                  try {
			/*
         	        canvas =Holder.lockCanvas(); 
           		Paint p=new Paint(); 
          		p.setColor(Color.BLACK); 
   	        	canvas.drawColor(Color.WHITE); 
			canvas.drawText(poseLandmarkStrDemo, (rotate += 10), (rotate += 10), p); 
       	  		// canvas.drawText(poseLandmarkStrDemo, (rotate += 10), (rotate += 10), p); 
			// c.drawText(poseLandmarkStrDemo, 100, 100, p); 
          		// Holder.unlockCanvasAndPost(canvas); */

		canvas =Holder.lockCanvas(); 
		canvas.drawColor(Color.BLACK); 
 		TextPaint textPaint = new TextPaint();
                textPaint.setColor(Color.parseColor("#ffffff"));
                textPaint.setTextSize(50.0F);
                textPaint.setAntiAlias(true);
                StaticLayout layout = new StaticLayout("Button3: "+poseLandmarkStrDemo, textPaint, 300, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
               // 这里的参数300，表示字符串的长度，当满300时，就会换行，也可以使用“\r\n”来实现换行
                canvas.save();
                canvas.translate(100,100);//从100，100开始画
                layout.draw(canvas);
                //canvas.restore();
                     // 休眠以控制最大帧频为每秒约30帧
			Holder.unlockCanvasAndPost(canvas);
                     Thread.sleep(500);


                  } catch (Exception e) {

                  } finally {

                     //Holder.unlockCanvasAndPost(canvas);// 解锁画布，提交画好的图像

                  }

              }

           }

 

       }

 

    }


// 自定义的SurfaceView子类

    class MySurfaceView4 extends SurfaceView implements SurfaceHolder.Callback {

 


 
	private volatile boolean flag; // 线程运行的标识，用于控制线程  

       SurfaceHolder Holder;

 

       public MySurfaceView4(Context context) {

           super(context);

           Holder = this.getHolder();// 获取holder

           Holder.addCallback(this);

       }

 

       @Override

       public void surfaceChanged(SurfaceHolder holder, int format, int width,

              int height) {

           // TODO Auto-generated method stub

 

       }

 

       @Override

       public void surfaceCreated(SurfaceHolder holder) {

           // 启动自定义线程
		flag = true;
           new Thread(new MyThread()).start();

       }

 

       @Override

       public void surfaceDestroyed(SurfaceHolder holder) {

           // TODO Auto-generated method stub
	flag = false; // 把线程运行的标识设置成false  
        Holder.removeCallback(this);  

 

       }

 

       // 自定义线程类

       class MyThread implements Runnable {

 

           @Override

           public void run() {

              Canvas canvas = null;

              int rotate = 100;// 旋转角度变量

              while (flag) {

                  try {
			/*
         	        canvas =Holder.lockCanvas(); 
           		Paint p=new Paint(); 
          		p.setColor(Color.BLACK); 
   	        	canvas.drawColor(Color.WHITE); 
			canvas.drawText(poseLandmarkStrDemo, (rotate += 10), (rotate += 10), p); 
       	  		// canvas.drawText(poseLandmarkStrDemo, (rotate += 10), (rotate += 10), p); 
			// c.drawText(poseLandmarkStrDemo, 100, 100, p); 
          		// Holder.unlockCanvasAndPost(canvas); */

		canvas =Holder.lockCanvas(); 
		canvas.drawColor(Color.BLACK); 
 		TextPaint textPaint = new TextPaint();
                textPaint.setColor(Color.parseColor("#ffffff"));
                textPaint.setTextSize(50.0F);
                textPaint.setAntiAlias(true);
                StaticLayout layout = new StaticLayout("Button4: "+poseLandmarkStrDemo, textPaint, 300, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
               // 这里的参数300，表示字符串的长度，当满300时，就会换行，也可以使用“\r\n”来实现换行
                canvas.save();
                canvas.translate(100,100);//从100，100开始画
                layout.draw(canvas);
                //canvas.restore();
                     // 休眠以控制最大帧频为每秒约30帧
			Holder.unlockCanvasAndPost(canvas);
                     Thread.sleep(500);
			

                  } catch (Exception e) {

                  } finally {

                     // Holder.unlockCanvasAndPost(canvas);// 解锁画布，提交画好的图像

                  }

              }

           }

 

       }

 

	}


	  public double cal_angle(double Xa, double Ya, double Za, double Xb, double Yb, double Zb, double Xc, double Yc, double Zc) {

	  double x1,y1,z1,x2,y2,z2,cos_b,angle;
	  x1 = Xa - Xb;y1 = Ya - Yb;z1 = Za - Zb;x2 = Xc - Xb;y2 = Yc - Yb;z2 = Zc - Zb;
	  cos_b = (x1*x2 + y1*y2 + z1*z2) / (Math.sqrt(x1*x1 + y1*y1 + z1*z1) *(Math.sqrt(x2*x2 + y2*y2 + z2*z2))); // 角点b的夹角余弦值
	  angle = Math.toDegrees(Math.acos(cos_b)); // 角点b的夹角值
	  return angle;
	  }


       class MyAccelerationThread extends Thread {

           // run in a second  
        final long timeInterval = 100;  

	Object[] x = new Object[]{};
	ArrayList<Object> newX = new ArrayList<Object>(Arrays.asList(x));
	Object[] y = new Object[]{};
	ArrayList<Object> newY = new ArrayList<Object>(Arrays.asList(y));
	int wait = 0;

	int SampleFramePS = 2;
	String[] date = new String[SampleFramePS];
	int[] data0 = new int[SampleFramePS];
	int[] data1 = new int[SampleFramePS];
	int[] data2 = new int[SampleFramePS];
	int[] data3 = new int[SampleFramePS];
	int[] data4 = new int[SampleFramePS];
	int[] data5 = new int[SampleFramePS];
	int[] data6 = new int[SampleFramePS];
	int[] data7 = new int[SampleFramePS];
	int[] data8 = new int[SampleFramePS];
	int[] data9 = new int[SampleFramePS];
	int[] data10 = new int[SampleFramePS];
	int[] data11 = new int[SampleFramePS];
	int[] data12 = new int[SampleFramePS];
	int[] data13 = new int[SampleFramePS];
	int[] data14 = new int[SampleFramePS];

	int FrameCntPS = 0;

	private final Object lock = new Object();
		private boolean pause = false;

		 /**
		 * 调用这个方法实现暂停线程
		 */
		void pauseThread() {
		    pause = true;
		}

		/**
		 * 调用这个方法实现恢复线程的运行
		 */
		void resumeThread() {
		    pause = false;
		    synchronized (lock) {
		        lock.notifyAll();
		    }
		}

		/**
		 * 注意：这个方法只能在run方法里调用，不然会阻塞主线程，导致页面无响应
		 */
		void onPause() {
		    synchronized (lock) {
		        try {
		            lock.wait();
		        } catch (InterruptedException e) {
		            e.printStackTrace();
		        }
		    }
		}
        @Override

	public void run() {
		Log.v(TAG, "MyAccelerationThread " +  String.valueOf(mInterface.runOrStop));
                while (true) {  
				 while (mInterface.runOrStop == false) {
                        		//onPause();
					//Log.v(TAG, "MyAccelerationThread " +  String.valueOf(mInterface.runOrStop));
						 try {  
						Thread.sleep(timeInterval);  //if(wait<5){wait++;Log.v(TAG, "Thread.sleep(timeInterval)");}
					    } catch (InterruptedException e) {  
						e.printStackTrace();  
					    }  
                   		 }
                    // ------- code for task to run  
			//for(int i=0 ;i<33;i++){
				//for(int j=0;j<3;j++){
					//landMarksDataTwoSecondPre[i][j]=landMarksDataOneSecondPre[i][j];
					//landMarksDataOneSecondPre[i][j]=landMarksDataCurrent[i][j];
					//landMarksDataCurrent[i][j]=landMarksData[i][j];
				//}
			//}
			//landMarksDataTwoSecondPre = (double[][])landMarksDataOneSecondPre.clone();;
		        //landMarksDataOneSecondPre = (double[][])landMarksDataCurrent.clone();;
                        //landMarksDataCurrent = (double[][])landMarksData.clone();;
			for (int i = 0; i < landMarksDataSpeed0.length; i++) {
				//landMarksDataSpeed0[i] = Math.sqrt(Math.pow((landMarksDataOneSecondPre[i][0]- landMarksDataTwoSecondPre[i][0]),2) + Math.pow((landMarksDataOneSecondPre[i][1]- landMarksDataTwoSecondPre[i][1]),2) + Math.pow((landMarksDataOneSecondPre[i][2]- landMarksDataTwoSecondPre[i][2]),2)); 
				//landMarksDataSpeed1[i] = Math.sqrt(Math.pow((landMarksDataCurrent[i][0]- landMarksDataOneSecondPre[i][0]),2) + Math.pow((landMarksDataCurrent[i][1]- landMarksDataOneSecondPre[i][1]),2) + Math.pow((landMarksDataCurrent[i][2]- landMarksDataOneSecondPre[i][2]),2)); 
				//landMarksDataAcceleration[i] = landMarksDataSpeed1[i] - landMarksDataSpeed0[i];
				landMarksDataSpeed1[i] = Math.sqrt(Math.pow((landMarksData[i][0]),2) + Math.pow((landMarksData[i][1]),2) + Math.pow((landMarksData[i][2]),2)); 
			}
		//for (int i = 0; i < landMarksDataTwoSecondPre.length; i++) {
         	   
        	       // Log.v(TAG, "landMarksDataTwoSecondPre[" + String.valueOf(i) + "] : " + String.valueOf(landMarksDataTwoSecondPre[i][0]) + ", " +String.valueOf(landMarksDataTwoSecondPre[i][1]) + ", " + String.valueOf(landMarksDataTwoSecondPre[i][2]));
       		 
		//}
		//for (int i = 0; i < landMarksDataOneSecondPre.length; i++) {
         	   
        	       //Log.v(TAG, "landMarksDataOneSecondPre[" + String.valueOf(i) + "] : " + String.valueOf(landMarksDataOneSecondPre[i][0]) + ", " +String.valueOf(landMarksDataOneSecondPre[i][1]) + ", " + String.valueOf(landMarksDataOneSecondPre[i][2]));
       		 
		//}
		//for (int i = 0; i < landMarksDataCurrent.length; i++) {
         	   
        	       //Log.v(TAG, "landMarksDataCurrent[" + String.valueOf(i) + "] : " + String.valueOf(landMarksDataCurrent[i][0]) + ", " +String.valueOf(landMarksDataCurrent[i][1]) + ", " + String.valueOf(landMarksDataCurrent[i][2]));
       		 
		//}

		//for (int i = 0; i < landMarksDataSpeed0.length; i++) {
         	   
        	      // Log.v(TAG, "landMarksDataSpeed0[" + String.valueOf(i) + "] : " + String.valueOf(landMarksDataSpeed0[i]));
       		 
		//}
		//for (int i = 0; i < landMarksDataSpeed1.length; i++) {
         	   
        	     // Log.v(TAG, "landMarksDataSpeed1[" + String.valueOf(i) + "] : " + String.valueOf(landMarksDataSpeed1[i]));
       		 
		//}
		//for (int i = 0; i < landMarksDataAcceleration.length; i++) {
         	   
        	      // Log.v(TAG, "landMarksDataAcceleration[" + String.valueOf(i) + "] : " + String.valueOf(landMarksDataAcceleration[i]));
       		 
		//}
                    // ------- ends here  
                    // ------- code for task to run  


			double a0 = 180.0 - cal_angle(landMarksData[23][0],landMarksData[23][1], landMarksData[23][2], landMarksData[25][0],landMarksData[25][1], landMarksData[25][2],landMarksData[29][0],landMarksData[29][1], landMarksData[29][2]);// 
			if(a0<0.0){a0 = 0.0;} //左膝关节活动度

			double a1 = 180.0 - cal_angle(landMarksData[24][0],landMarksData[24][1], landMarksData[24][2], landMarksData[26][0],landMarksData[26][1], landMarksData[26][2],landMarksData[30][0],landMarksData[30][1], landMarksData[30][2]);// 
			if(a1<0.0){a1 = 0.0;} //右膝关节活动度

			double a2 = 180.0 - cal_angle(landMarksData[11][0],landMarksData[11][1], landMarksData[11][2], landMarksData[13][0],landMarksData[13][1], landMarksData[13][2],landMarksData[15][0],landMarksData[15][1], landMarksData[15][2]);// 
			if(a2<0.0){a2 = 0.0;} //左肘关节活动度

			double a3 = 180.0 - cal_angle(landMarksData[12][0],landMarksData[12][1], landMarksData[12][2], landMarksData[14][0],landMarksData[14][1], landMarksData[14][2],landMarksData[16][0],landMarksData[16][1], landMarksData[16][2]);// 
			if(a3<0.0){a3 = 0.0;} //右肘关节活动度

			//double a4backward = 0.0; double a4forward =0.0;
			double a4 = cal_angle(landMarksData[13][0],landMarksData[13][1], landMarksData[13][2], landMarksData[11][0],landMarksData[11][1], landMarksData[11][2],landMarksData[23][0],landMarksData[23][1], landMarksData[23][2]);// 左肩部关节活动度
			if(a4<0.0){a4 = 0.0;} /*
			if(landMarksData[13][2]<landMarksData[11][2])
			{
				a4backward = a4;
			}
			else
			{
				a4forward  = a4;
			}*/

			//double a5backward = 0.0; double a5forward =0.0;
			double a5 = cal_angle(landMarksData[14][0],landMarksData[14][1], landMarksData[14][2], landMarksData[12][0],landMarksData[12][1], landMarksData[12][2],landMarksData[24][0],landMarksData[24][1], landMarksData[24][2]);// 右肩部关节活动度
			if(a5<0.0){a5 = 0.0;} /*
			if(landMarksData[14][2]<landMarksData[12][2])
			{
				a5backward = a5;
			}
			else
			{
				a5forward  = a5;
			}*/



			//Log.v(TAG, "landMarksData[13] 左肘部: " + String.valueOf(landMarksData[13][0]) + ", " +String.valueOf(landMarksData[13][1]) + ", " + String.valueOf(landMarksData[13][2]));
			//Log.v(TAG, "landMarksData[11] 左肩部: " + String.valueOf(landMarksData[11][0]) + ", " +String.valueOf(landMarksData[11][1]) + ", " + String.valueOf(landMarksData[11][2]));
			//Log.v(TAG, "landMarksData[14] 右肘部: " + String.valueOf(landMarksData[14][0]) + ", " +String.valueOf(landMarksData[14][1]) + ", " + String.valueOf(landMarksData[14][2]));
			//Log.v(TAG, "landMarksData[12] 右肩部: " + String.valueOf(landMarksData[12][0]) + ", " +String.valueOf(landMarksData[12][1]) + ", " + String.valueOf(landMarksData[12][2]));
			//Log.v(TAG, "MAX leftKneeAngle: "+ (Double.toString(leftKneeAngle)));
			//double a2 = cal_angle(1,1, 0, 0,0, 0, 1,0, 0);  // 结果为 45°
			//Log.v(TAG, "double a2 = cal_angle(1,1, 0, 0,0, 0, 1,0, 0) 结果为 45°: "+ Double.toString(a2));
			//double a3 = cal_angle(-1,1, 0, 0,0, 0, 1,0, 0); // 结果为 135°
			//Log.v(TAG, "double a3 = cal_angle(-1,1, 0, 0,0, 0, 1,0, 0) 结果为 135°: "+ Double.toString(a3));

			//if(wait>=5){
			Log.v(TAG, "receivedPoseFlag is true and UpdateSpeedThread 1");
			Message msg = new Message();
			Bundle b = new Bundle();// 存放数据
			b.putInt("case", 100);
			b.putString("date", getCurrentHourMinute());
			b.putInt("data0", (int)Math.round(landMarksDataSpeed1[0]*1000));//头部
			b.putInt("data1", (int)Math.round(landMarksDataSpeed1[12]*1000));//右肩部
			b.putInt("data2", (int)Math.round(landMarksDataSpeed1[14]*1000));//右肘部
			b.putInt("data3", (int)Math.round(landMarksDataSpeed1[16]*1000));//右手腕部
			b.putInt("data4", (int)Math.round(landMarksDataSpeed1[11]*1000));//左肩部
			b.putInt("data5", (int)Math.round(landMarksDataSpeed1[13]*1000));//左肘部
			b.putInt("data6", (int)Math.round(landMarksDataSpeed1[15]*1000));//左手腕部
			b.putInt("data7", (int)Math.round(landMarksDataSpeed1[24]*1000));//右臀部
			b.putInt("data8", (int)Math.round(landMarksDataSpeed1[26]*1000));//右膝部
			b.putInt("data9", (int)Math.round(landMarksDataSpeed1[28]*1000));//右脚腕部
			b.putInt("data10", (int)Math.round(landMarksDataSpeed1[23]*1000));//左臀部
			b.putInt("data11", (int)Math.round(landMarksDataSpeed1[25]*1000));//左膝部
			b.putInt("data12", (int)Math.round(landMarksDataSpeed1[27]*1000));//左脚腕部
			b.putInt("data13", (int)Math.round(landMarksDataSpeed1[30]*1000));//右脚跟
			b.putInt("data14", (int)Math.round(landMarksDataSpeed1[29]*1000));//左脚跟

			//b.putInt("data13", (int)Math.round(a1));//右脚跟
			//b.putInt("data14", (int)Math.round(a0));//左脚跟

			b.putInt("leftKneeAngle", (int)Math.round(a0));//左膝关节活动度
			b.putInt("rightKneeAngle", (int)Math.round(a1));//右膝关节活动度
			b.putInt("leftElbowAngle", (int)Math.round(a2));//左肘关节活动度
			b.putInt("rightElbowAngle", (int)Math.round(a3));//右肘关节活动度

			b.putInt("leftShoulderAngle", (int)Math.round(a4));//左肩部关节活动度
			b.putInt("rightShoulderAngle", (int)Math.round(a5));//右肩部关节活动度

			msg.setData(b);
			MainActivity.this.myHandler.sendMessage(msg);// 向Handler发送消息,更新UI
			/*
			newX.add(getCurrentHourMinute());
			newY.add(Math.round(landMarksDataSpeed1[0]*1000));
			Log.v(TAG, Arrays.toString(newX.toArray()));Log.v(TAG, Arrays.toString(newY.toArray()));
			Message msg = new Message();
			Bundle b = new Bundle();// 存放数据
			ArrayList listX = new ArrayList();
			listX.add((ArrayList<Object>)newX);
			b.putParcelableArrayList("dataX",listX);
			ArrayList listY = new ArrayList();
			listY.add((ArrayList<Object>)newY);
			b.putParcelableArrayList("dataY",listY);*/
/*
			date[FrameCntPS] = getCurrentHourMinute();
			data0[FrameCntPS] = (int)Math.round(landMarksDataSpeed1[0]*1000);
			data1[FrameCntPS] = (int)Math.round(landMarksDataSpeed1[11]*1000);
			data2[FrameCntPS] = (int)Math.round(landMarksDataSpeed1[13]*1000);
			data3[FrameCntPS] = (int)Math.round(landMarksDataSpeed1[15]*1000);
			data4[FrameCntPS] = (int)Math.round(landMarksDataSpeed1[12]*1000);
			data5[FrameCntPS] = (int)Math.round(landMarksDataSpeed1[14]*1000);
			data6[FrameCntPS] = (int)Math.round(landMarksDataSpeed1[16]*1000);
			data7[FrameCntPS] = (int)Math.round(landMarksDataSpeed1[23]*1000);
			data8[FrameCntPS] = (int)Math.round(landMarksDataSpeed1[25]*1000);
			data9[FrameCntPS] = (int)Math.round(landMarksDataSpeed1[27]*1000);
			data10[FrameCntPS] = (int)Math.round(landMarksDataSpeed1[24]*1000);
			data11[FrameCntPS] = (int)Math.round(landMarksDataSpeed1[26]*1000);
			data12[FrameCntPS] = (int)Math.round(landMarksDataSpeed1[28]*1000);
			data13[FrameCntPS] = (int)Math.round(landMarksDataSpeed1[31]*1000);
			data14[FrameCntPS] = (int)Math.round(landMarksDataSpeed1[32]*1000);
			FrameCntPS++;
			if(FrameCntPS == (SampleFramePS))
			{
			Message msg = new Message();
			Bundle b = new Bundle();// 存放数据
			b.putStringArray("date", date);
			b.putIntArray("data0", data0);
			b.putIntArray("data1", data1);
			b.putIntArray("data2", data2);
			b.putIntArray("data3", data3);
			b.putIntArray("data4", data4);
			b.putIntArray("data5", data5);
			b.putIntArray("data6", data6);
			b.putIntArray("data7", data7);
			b.putIntArray("data8", data8);
			b.putIntArray("data9", data9);
			b.putIntArray("data10", data10);
			b.putIntArray("data11", data11);
			b.putIntArray("data12", data12);
			b.putIntArray("data13", data13);
			b.putIntArray("data14", data14);
			//b.putInt("data0", (int)Math.round(landMarksDataSpeed1[0]*1000));
			//b.putInt("data1", (int)Math.round(landMarksDataSpeed1[11]*1000));
			//b.putInt("data2", (int)Math.round(landMarksDataSpeed1[13]*1000));
			//b.putInt("data3", (int)Math.round(landMarksDataSpeed1[15]*1000));
			//b.putInt("data4", (int)Math.round(landMarksDataSpeed1[12]*1000));
			//b.putInt("data5", (int)Math.round(landMarksDataSpeed1[14]*1000));
			//b.putInt("data6", (int)Math.round(landMarksDataSpeed1[16]*1000));
			//b.putInt("data7", (int)Math.round(landMarksDataSpeed1[23]*1000));
			//b.putInt("data8", (int)Math.round(landMarksDataSpeed1[25]*1000));
			//b.putInt("data9", (int)Math.round(landMarksDataSpeed1[27]*1000));
			//b.putInt("data10", (int)Math.round(landMarksDataSpeed1[24]*1000));
			//b.putInt("data11", (int)Math.round(landMarksDataSpeed1[26]*1000));
			//b.putInt("data12", (int)Math.round(landMarksDataSpeed1[28]*1000));
			//b.putInt("data13", (int)Math.round(landMarksDataSpeed1[31]*1000));
			//b.putInt("data14", (int)Math.round(landMarksDataSpeed1[32]*1000));
			msg.setData(b);
			MainActivity.this.myHandler.sendMessage(msg);// 向Handler发送消息,更新UI
			FrameCntPS = 0;
			}
*/
			// refreshLineChart1();
			/*
			Log.v(TAG, "UpdateSpeedThread 1");
			newX.add(getCurrentHourMinute());
			newY.add((int)(landMarksDataSpeed1[0]*1000));
			
			// refreshLineChart1();
			// Object[] xOut= newX.toArray();Object[] yOut= newY.toArray();
			Log.v(TAG, Arrays.toString(newX.toArray()));Log.v(TAG, Arrays.toString(newY.toArray()));

			GsonOption echartOption = EchartOptionUtil.getLineChartOptions((Object[])newX.toArray(), (Object[])newY.toArray());
			
			Log.v(TAG, "UpdateSpeedThread 2 : "+ echartOption.toString());

			lineChart.refreshEchartsWithOption(echartOption);

			Log.v(TAG, "UpdateSpeedThread 3");*/
			// lineChart.refreshEchartsWithOption(EchartOptionUtil.getLineChartOptions(newX.toArray(), newY.toArray()));
		        //}
                    // ------- ends here  

                    try {  
                        Thread.sleep(timeInterval);  //if(wait<5){wait++;Log.v(TAG, "Thread.sleep(timeInterval)");}
                    } catch (InterruptedException e) {  
                        e.printStackTrace();  
                    }  
                }  
            }  
        }
     
	  private void refreshLineChartTest(){
			Log.v(TAG, "refreshLineChartTest() 1");

		Object[] x = new Object[]{getCurrentHourMinute()};
		ArrayList<Object> newX = new ArrayList<Object>(Arrays.asList(x));
		Object[] y = new Object[]{0};
		ArrayList<Object> newY = new ArrayList<Object>(Arrays.asList(y));
		newX.add(getCurrentHourMinute());
		newY.add(Math.round(landMarksDataSpeed1[0]*1000));
			
			//Object[] xOut= newX.toArray();Object[] yOut= newY.toArray();
			Log.v(TAG, Arrays.toString(newX.toArray()));Log.v(TAG, Arrays.toString(newY.toArray()));
		Object[] x1 = (Object[])newX.toArray();
		Object[] y1 = (Object[])newY.toArray();
			Log.v(TAG, Arrays.toString(x1));Log.v(TAG, Arrays.toString(y1));
		lineChart.refreshEchartsWithOption(EchartOptionUtil.getLineChartOptions(x1, y1));/*
		new AlertDialog.Builder(this).setTitle("findViewById")//设置对话框标题  

                .setMessage("findViewById")//设置显示的内容  

                .setPositiveButton("退出",new DialogInterface.OnClickListener() {//添加确定按钮  



                    @Override

                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件  

                        // TODO Auto-generated method stub  

                        Log.i("alertdialog"," 请保存数据！");  

                    }

                }).show();//在按键响应事件中显示此对话框*/

	    }

	 private Object[] appendValue(Object[] obj, Object newObj) {
	 
		ArrayList<Object> temp = new ArrayList<Object>(Arrays.asList(obj));
		temp.add(newObj);
		return temp.toArray();
	 
	  }

	private String getCurrentHourMinute() {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
	int secound = cal.get(Calendar.SECOND);
        return hour + ":" + minute + ":" + secound;
        }

	//接受消息,处理消息 ,此Handler会与当前主线程一块运行
	class MyHandler extends Handler{
		public MyHandler (){
	}
	public MyHandler (Looper L) {
		super(L);
	}
	// 子类必须重写此方法,接受数据
	@Override
	public void handleMessage (Message msg){
		//Log.v("MainActivity", "handleMessage 1############################################");
		super.handleMessage(msg);
		//Log.v("MainActivity", "handleMessage 2############################################");
		// 此处能够更新UI
		Bundle b = new Bundle();
		b = msg.getData();

		Log.v("MainActivity", "handleMessage 3############################################");

		if((int)b.getInt("case")==200){
		MainActivity.this.lineChartFeature.deleteFFTData0();Log.v(TAG, "button3Action.deleteDataFFT");
		MainActivity.this.lineChartFeature.deleteFFTData1();
		MainActivity.this.lineChartFeature.deleteFFTData2();
		MainActivity.this.lineChartFeature.deleteFFTData3();
		MainActivity.this.lineChartFeature.deleteFFTData4();
		MainActivity.this.lineChartFeature.deleteFFTData5();
		MainActivity.this.lineChartFeature.deleteFFTData6();
		MainActivity.this.lineChartFeature.deleteFFTData7();
		MainActivity.this.lineChartFeature.deleteFFTData8();
		MainActivity.this.lineChartFeature.deleteFFTData9();
		MainActivity.this.lineChartFeature.deleteFFTData10();
		MainActivity.this.lineChartFeature.deleteFFTData11();
		MainActivity.this.lineChartFeature.deleteFFTData12();
		MainActivity.this.lineChartFeature.deleteFFTData13();
		MainActivity.this.lineChartFeature.deleteFFTData14();
		MainActivity.this.lineChartRehab.deleteRehabData();MainActivity.this.lineChartRehab.deleteRehabDataF3();MainActivity.this.lineChartRehab.deleteRehabDataF5();
		MainActivity.this.lineChartFeature.refreshDataMax(mInterface.maxdataJava[0],
			mInterface.maxdataJava[1],
			mInterface.maxdataJava[2],
			mInterface.maxdataJava[3],
			mInterface.maxdataJava[4],
			mInterface.maxdataJava[5],
			mInterface.maxdataJava[6],
			mInterface.maxdataJava[7],
			mInterface.maxdataJava[8],
			mInterface.maxdataJava[9],
			mInterface.maxdataJava[10],
			mInterface.maxdataJava[11],
			mInterface.maxdataJava[12],
			mInterface.maxdataJava[13],
			mInterface.maxdataJava[14]);
		MainActivity.this.lineChartFeature.refreshDataMin(mInterface.mindataJava[0],
			mInterface.mindataJava[1],
			mInterface.mindataJava[2],
			mInterface.mindataJava[3],
			mInterface.mindataJava[4],
			mInterface.mindataJava[5],
			mInterface.mindataJava[6],
			mInterface.mindataJava[7],
			mInterface.mindataJava[8],
			mInterface.mindataJava[9],
			mInterface.mindataJava[10],
			mInterface.mindataJava[11],
			mInterface.mindataJava[12],
			mInterface.mindataJava[13],
			mInterface.mindataJava[14]);
		MainActivity.this.lineChartFeature.refreshDataaverage(mInterface.averagedataJava[0],
			mInterface.averagedataJava[1],
			mInterface.averagedataJava[2],
			mInterface.averagedataJava[3],
			mInterface.averagedataJava[4],
			mInterface.averagedataJava[5],
			mInterface.averagedataJava[6],
			mInterface.averagedataJava[7],
			mInterface.averagedataJava[8],
			mInterface.averagedataJava[9],
			mInterface.averagedataJava[10],
			mInterface.averagedataJava[11],
			mInterface.averagedataJava[12],
			mInterface.averagedataJava[13],
			mInterface.averagedataJava[14]);
		MainActivity.this.lineChartFeature.refreshDatapeak(mInterface.peakdataJava[0],
			mInterface.peakdataJava[1],
			mInterface.peakdataJava[2],
			mInterface.peakdataJava[3],
			mInterface.peakdataJava[4],
			mInterface.peakdataJava[5],
			mInterface.peakdataJava[6],
			mInterface.peakdataJava[7],
			mInterface.peakdataJava[8],
			mInterface.peakdataJava[9],
			mInterface.peakdataJava[10],
			mInterface.peakdataJava[11],
			mInterface.peakdataJava[12],
			mInterface.peakdataJava[13],
			mInterface.peakdataJava[14]);
		MainActivity.this.lineChartFeature.refreshDataRMEAN(mInterface.RMEANdataJava[0],
			mInterface.RMEANdataJava[1],
			mInterface.RMEANdataJava[2],
			mInterface.RMEANdataJava[3],
			mInterface.RMEANdataJava[4],
			mInterface.RMEANdataJava[5],
			mInterface.RMEANdataJava[6],
			mInterface.RMEANdataJava[7],
			mInterface.RMEANdataJava[8],
			mInterface.RMEANdataJava[9],
			mInterface.RMEANdataJava[10],
			mInterface.RMEANdataJava[11],
			mInterface.RMEANdataJava[12],
			mInterface.RMEANdataJava[13],
			mInterface.RMEANdataJava[14]);
		MainActivity.this.lineChartFeature.refreshDatameanSquareValue(mInterface.meanSquareValueJava[0],
			mInterface.meanSquareValueJava[1],
			mInterface.meanSquareValueJava[2],
			mInterface.meanSquareValueJava[3],
			mInterface.meanSquareValueJava[4],
			mInterface.meanSquareValueJava[5],
			mInterface.meanSquareValueJava[6],
			mInterface.meanSquareValueJava[7],
			mInterface.meanSquareValueJava[8],
			mInterface.meanSquareValueJava[9],
			mInterface.meanSquareValueJava[10],
			mInterface.meanSquareValueJava[11],
			mInterface.meanSquareValueJava[12],
			mInterface.meanSquareValueJava[13],
			mInterface.meanSquareValueJava[14]);
		MainActivity.this.lineChartFeature.refreshDatavariance(mInterface.varianceJava[0],
			mInterface.varianceJava[1],
			mInterface.varianceJava[2],
			mInterface.varianceJava[3],
			mInterface.varianceJava[4],
			mInterface.varianceJava[5],
			mInterface.varianceJava[6],
			mInterface.varianceJava[7],
			mInterface.varianceJava[8],
			mInterface.varianceJava[9],
			mInterface.varianceJava[10],
			mInterface.varianceJava[11],
			mInterface.varianceJava[12],
			mInterface.varianceJava[13],
			mInterface.varianceJava[14]);
		MainActivity.this.lineChartFeature.refreshDataRMS(mInterface.RMSJava[0],
			mInterface.RMSJava[1],
			mInterface.RMSJava[2],
			mInterface.RMSJava[3],
			mInterface.RMSJava[4],
			mInterface.RMSJava[5],
			mInterface.RMSJava[6],
			mInterface.RMSJava[7],
			mInterface.RMSJava[8],
			mInterface.RMSJava[9],
			mInterface.RMSJava[10],
			mInterface.RMSJava[11],
			mInterface.RMSJava[12],
			mInterface.RMSJava[13],
			mInterface.RMSJava[14]);
		MainActivity.this.lineChartFeature.refreshDatastandardDeviation(mInterface.standardDeviationJava[0],
			mInterface.standardDeviationJava[1],
			mInterface.standardDeviationJava[2],
			mInterface.standardDeviationJava[3],
			mInterface.standardDeviationJava[4],
			mInterface.standardDeviationJava[5],
			mInterface.standardDeviationJava[6],
			mInterface.standardDeviationJava[7],
			mInterface.standardDeviationJava[8],
			mInterface.standardDeviationJava[9],
			mInterface.standardDeviationJava[10],
			mInterface.standardDeviationJava[11],
			mInterface.standardDeviationJava[12],
			mInterface.standardDeviationJava[13],
			mInterface.standardDeviationJava[14]);
		MainActivity.this.lineChartFeature.refreshDataKurtosis(mInterface.KurtosisJava[0],
			mInterface.KurtosisJava[1],
			mInterface.KurtosisJava[2],
			mInterface.KurtosisJava[3],
			mInterface.KurtosisJava[4],
			mInterface.KurtosisJava[5],
			mInterface.KurtosisJava[6],
			mInterface.KurtosisJava[7],
			mInterface.KurtosisJava[8],
			mInterface.KurtosisJava[9],
			mInterface.KurtosisJava[10],
			mInterface.KurtosisJava[11],
			mInterface.KurtosisJava[12],
			mInterface.KurtosisJava[13],
			mInterface.KurtosisJava[14]);
		MainActivity.this.lineChartFeature.refreshDataskewness(mInterface.skewnessJava[0],
			mInterface.skewnessJava[1],
			mInterface.skewnessJava[2],
			mInterface.skewnessJava[3],
			mInterface.skewnessJava[4],
			mInterface.skewnessJava[5],
			mInterface.skewnessJava[6],
			mInterface.skewnessJava[7],
			mInterface.skewnessJava[8],
			mInterface.skewnessJava[9],
			mInterface.skewnessJava[10],
			mInterface.skewnessJava[11],
			mInterface.skewnessJava[12],
			mInterface.skewnessJava[13],
			mInterface.skewnessJava[14]);
		MainActivity.this.lineChartFeature.refreshDatapeakfactor(mInterface.peakfactorJava[0],
			mInterface.peakfactorJava[1],
			mInterface.peakfactorJava[2],
			mInterface.peakfactorJava[3],
			mInterface.peakfactorJava[4],
			mInterface.peakfactorJava[5],
			mInterface.peakfactorJava[6],
			mInterface.peakfactorJava[7],
			mInterface.peakfactorJava[8],
			mInterface.peakfactorJava[9],
			mInterface.peakfactorJava[10],
			mInterface.peakfactorJava[11],
			mInterface.peakfactorJava[12],
			mInterface.peakfactorJava[13],
			mInterface.peakfactorJava[14]);
		MainActivity.this.lineChartFeature.refreshDatapulsefactor(mInterface.pulsefactorJava[0],
			mInterface.pulsefactorJava[1],
			mInterface.pulsefactorJava[2],
			mInterface.pulsefactorJava[3],
			mInterface.pulsefactorJava[4],
			mInterface.pulsefactorJava[5],
			mInterface.pulsefactorJava[6],
			mInterface.pulsefactorJava[7],
			mInterface.pulsefactorJava[8],
			mInterface.pulsefactorJava[9],
			mInterface.pulsefactorJava[10],
			mInterface.pulsefactorJava[11],
			mInterface.pulsefactorJava[12],
			mInterface.pulsefactorJava[13],
			mInterface.pulsefactorJava[14]);
		MainActivity.this.lineChartFeature.refreshDatamarginfactor(mInterface.marginfactorJava[0],
			mInterface.marginfactorJava[1],
			mInterface.marginfactorJava[2],
			mInterface.marginfactorJava[3],
			mInterface.marginfactorJava[4],
			mInterface.marginfactorJava[5],
			mInterface.marginfactorJava[6],
			mInterface.marginfactorJava[7],
			mInterface.marginfactorJava[8],
			mInterface.marginfactorJava[9],
			mInterface.marginfactorJava[10],
			mInterface.marginfactorJava[11],
			mInterface.marginfactorJava[12],
			mInterface.marginfactorJava[13],
			mInterface.marginfactorJava[14]);
		MainActivity.this.lineChartFeature.refreshDatawaveformfactor(mInterface.waveformfactorJava[0],
			mInterface.waveformfactorJava[1],
			mInterface.waveformfactorJava[2],
			mInterface.waveformfactorJava[3],
			mInterface.waveformfactorJava[4],
			mInterface.waveformfactorJava[5],
			mInterface.waveformfactorJava[6],
			mInterface.waveformfactorJava[7],
			mInterface.waveformfactorJava[8],
			mInterface.waveformfactorJava[9],
			mInterface.waveformfactorJava[10],
			mInterface.waveformfactorJava[11],
			mInterface.waveformfactorJava[12],
			mInterface.waveformfactorJava[13],
			mInterface.waveformfactorJava[14]);

		MainActivity.this.lineChartFeature.refreshDataCenterGravityGrequency(mInterface.CenterGravityGrequencyJava[0],
			mInterface.CenterGravityGrequencyJava[1],
			mInterface.CenterGravityGrequencyJava[2],
			mInterface.CenterGravityGrequencyJava[3],
			mInterface.CenterGravityGrequencyJava[4],
			mInterface.CenterGravityGrequencyJava[5],
			mInterface.CenterGravityGrequencyJava[6],
			mInterface.CenterGravityGrequencyJava[7],
			mInterface.CenterGravityGrequencyJava[8],
			mInterface.CenterGravityGrequencyJava[9],
			mInterface.CenterGravityGrequencyJava[10],
			mInterface.CenterGravityGrequencyJava[11],
			mInterface.CenterGravityGrequencyJava[12],
			mInterface.CenterGravityGrequencyJava[13],
			mInterface.CenterGravityGrequencyJava[14]);

		MainActivity.this.lineChartFeature.refreshDataMeanSquareFrequency(mInterface.MeanSquareFrequencyJava[0],
			mInterface.MeanSquareFrequencyJava[1],
			mInterface.MeanSquareFrequencyJava[2],
			mInterface.MeanSquareFrequencyJava[3],
			mInterface.MeanSquareFrequencyJava[4],
			mInterface.MeanSquareFrequencyJava[5],
			mInterface.MeanSquareFrequencyJava[6],
			mInterface.MeanSquareFrequencyJava[7],
			mInterface.MeanSquareFrequencyJava[8],
			mInterface.MeanSquareFrequencyJava[9],
			mInterface.MeanSquareFrequencyJava[10],
			mInterface.MeanSquareFrequencyJava[11],
			mInterface.MeanSquareFrequencyJava[12],
			mInterface.MeanSquareFrequencyJava[13],
			mInterface.MeanSquareFrequencyJava[14]);

		MainActivity.this.lineChartFeature.refreshDataRootMeanSquareFrequency(mInterface.RootMeanSquareFrequencyJava[0],
			mInterface.RootMeanSquareFrequencyJava[1],
			mInterface.RootMeanSquareFrequencyJava[2],
			mInterface.RootMeanSquareFrequencyJava[3],
			mInterface.RootMeanSquareFrequencyJava[4],
			mInterface.RootMeanSquareFrequencyJava[5],
			mInterface.RootMeanSquareFrequencyJava[6],
			mInterface.RootMeanSquareFrequencyJava[7],
			mInterface.RootMeanSquareFrequencyJava[8],
			mInterface.RootMeanSquareFrequencyJava[9],
			mInterface.RootMeanSquareFrequencyJava[10],
			mInterface.RootMeanSquareFrequencyJava[11],
			mInterface.RootMeanSquareFrequencyJava[12],
			mInterface.RootMeanSquareFrequencyJava[13],
			mInterface.RootMeanSquareFrequencyJava[14]);

		MainActivity.this.lineChartFeature.refreshDataFrequencyVariance(mInterface.FrequencyVarianceJava[0],
			mInterface.FrequencyVarianceJava[1],
			mInterface.FrequencyVarianceJava[2],
			mInterface.FrequencyVarianceJava[3],
			mInterface.FrequencyVarianceJava[4],
			mInterface.FrequencyVarianceJava[5],
			mInterface.FrequencyVarianceJava[6],
			mInterface.FrequencyVarianceJava[7],
			mInterface.FrequencyVarianceJava[8],
			mInterface.FrequencyVarianceJava[9],
			mInterface.FrequencyVarianceJava[10],
			mInterface.FrequencyVarianceJava[11],
			mInterface.FrequencyVarianceJava[12],
			mInterface.FrequencyVarianceJava[13],
			mInterface.FrequencyVarianceJava[14]);


		MainActivity.this.lineChartFeature.refreshDataFrequencyStandardDeviation(mInterface.FrequencyStandardDeviationJava[0],
			mInterface.FrequencyStandardDeviationJava[1],
			mInterface.FrequencyStandardDeviationJava[2],
			mInterface.FrequencyStandardDeviationJava[3],
			mInterface.FrequencyStandardDeviationJava[4],
			mInterface.FrequencyStandardDeviationJava[5],
			mInterface.FrequencyStandardDeviationJava[6],
			mInterface.FrequencyStandardDeviationJava[7],
			mInterface.FrequencyStandardDeviationJava[8],
			mInterface.FrequencyStandardDeviationJava[9],
			mInterface.FrequencyStandardDeviationJava[10],
			mInterface.FrequencyStandardDeviationJava[11],
			mInterface.FrequencyStandardDeviationJava[12],
			mInterface.FrequencyStandardDeviationJava[13],
			mInterface.FrequencyStandardDeviationJava[14]);

		MainActivity.this.lineChartFeature.refreshDataPowerSpectrumEntropy(mInterface.PowerSpectrumEntropyJava[0],
			mInterface.PowerSpectrumEntropyJava[1],
			mInterface.PowerSpectrumEntropyJava[2],
			mInterface.PowerSpectrumEntropyJava[3],
			mInterface.PowerSpectrumEntropyJava[4],
			mInterface.PowerSpectrumEntropyJava[5],
			mInterface.PowerSpectrumEntropyJava[6],
			mInterface.PowerSpectrumEntropyJava[7],
			mInterface.PowerSpectrumEntropyJava[8],
			mInterface.PowerSpectrumEntropyJava[9],
			mInterface.PowerSpectrumEntropyJava[10],
			mInterface.PowerSpectrumEntropyJava[11],
			mInterface.PowerSpectrumEntropyJava[12],
			mInterface.PowerSpectrumEntropyJava[13],
			mInterface.PowerSpectrumEntropyJava[14]);

		MainActivity.this.lineChartFeature.refreshDataEnergyEntropy(mInterface.EnergyEntropyJava[0],
			mInterface.EnergyEntropyJava[1],
			mInterface.EnergyEntropyJava[2],
			mInterface.EnergyEntropyJava[3],
			mInterface.EnergyEntropyJava[4],
			mInterface.EnergyEntropyJava[5],
			mInterface.EnergyEntropyJava[6],
			mInterface.EnergyEntropyJava[7],
			mInterface.EnergyEntropyJava[8],
			mInterface.EnergyEntropyJava[9],
			mInterface.EnergyEntropyJava[10],
			mInterface.EnergyEntropyJava[11],
			mInterface.EnergyEntropyJava[12],
			mInterface.EnergyEntropyJava[13],
			mInterface.EnergyEntropyJava[14]);

		MainActivity.this.lineChartFeature.refreshDataBalance(mInterface.balanceFactor[0],
			mInterface.balanceFactor[1],
			mInterface.balanceFactor[2],
			mInterface.balanceFactor[3],
			mInterface.balanceFactor[4],
			mInterface.balanceFactor[5],
			mInterface.balanceFactor[6],
			mInterface.balanceFactor[7]);

		MainActivity.this.lineChartFeature.refreshDataEstimateInformation(mInterface.estimateBodyHeight,
			mInterface.maxdataAngleJava[0],
			mInterface.maxdataAngleJava[1],
			mInterface.maxdataAngleJava[2],
			mInterface.maxdataAngleJava[3],
			mInterface.maxdataAngleJava[4],
			mInterface.maxdataAngleJava[5],
			mInterface.estimateBodyHeight);

		MainActivity.this.lineChartFeature.refreshDatamaxAmplitudeFreuqOfFTData(mInterface.maxAmplitudeFreuqOfFTData0,
			mInterface.maxAmplitudeFreuqOfFTData1,mInterface.maxAmplitudeFreuqOfFTData2,
			mInterface.maxAmplitudeFreuqOfFTData3,mInterface.maxAmplitudeFreuqOfFTData4,
			mInterface.maxAmplitudeFreuqOfFTData5,mInterface.maxAmplitudeFreuqOfFTData6,
			mInterface.maxAmplitudeFreuqOfFTData7,mInterface.maxAmplitudeFreuqOfFTData8,
			mInterface.maxAmplitudeFreuqOfFTData9,mInterface.maxAmplitudeFreuqOfFTData10,
			mInterface.maxAmplitudeFreuqOfFTData11,mInterface.maxAmplitudeFreuqOfFTData12,
			mInterface.maxAmplitudeFreuqOfFTData13,mInterface.maxAmplitudeFreuqOfFTData14);

		double sampleFreq = 10.0;
		for(int i = 0; i<(mInterface.dataFFT0Length/2); i++){/*

		if(i==0){
			MainActivity.this.lineChartFeature.refreshFFTData0((double) (sampleFreq*(double)i/(double)mInterface.dataFFT0Length),
			(double)(0));
			i++;
		}*/
		MainActivity.this.lineChartFeature.refreshFFTData0((double) (sampleFreq*(double)i/(double)mInterface.dataFFT0Length),
			(double)mInterface.dataFFT0.get(mInterface.dataFFT0Length-1-i));
		}
		MainActivity.this.lineChartFeature.renderFFTData0();

		for(int i = 0; i<(mInterface.dataFFT0Length/2); i++){/*

		if(i==0){
			MainActivity.this.lineChartFeature.refreshFFTData1((double) (sampleFreq*(double)i/(double)mInterface.dataFFT0Length),
			(double)(0));
			i++;
		}*/
		MainActivity.this.lineChartFeature.refreshFFTData1((double) (sampleFreq*(double)i/(double)mInterface.dataFFT0Length),
			(double)mInterface.dataFFT1.get(mInterface.dataFFT0Length-1-i));
		}
		MainActivity.this.lineChartFeature.renderFFTData1();

		for(int i = 0; i<(mInterface.dataFFT0Length/2); i++){/*
		if(i==0){
			MainActivity.this.lineChartFeature.refreshFFTData2((double) (sampleFreq*(double)i/(double)mInterface.dataFFT0Length),
			(double)(0));
			i++;
		}*/
		MainActivity.this.lineChartFeature.refreshFFTData2((double) (sampleFreq*(double)i/(double)mInterface.dataFFT0Length),
			(double)mInterface.dataFFT2.get(mInterface.dataFFT0Length-1-i));
		}
		MainActivity.this.lineChartFeature.renderFFTData2();


		for(int i = 0; i<(mInterface.dataFFT0Length/2); i++){/*
		if(i==0){
			MainActivity.this.lineChartFeature.refreshFFTData3((double) (sampleFreq*(double)i/(double)mInterface.dataFFT0Length),
			(double)(0));
			i++;
		}*/
		MainActivity.this.lineChartFeature.refreshFFTData3((double) (sampleFreq*(double)i/(double)mInterface.dataFFT0Length),
			(double)mInterface.dataFFT3.get(mInterface.dataFFT0Length-1-i));
		}
		MainActivity.this.lineChartFeature.renderFFTData3();


		for(int i = 0; i<(mInterface.dataFFT0Length/2); i++){/*
		if(i==0){
			MainActivity.this.lineChartFeature.refreshFFTData4((double) (sampleFreq*(double)i/(double)mInterface.dataFFT0Length),
			(double)(0));
			i++;
		}*/
		MainActivity.this.lineChartFeature.refreshFFTData4((double) (sampleFreq*(double)i/(double)mInterface.dataFFT0Length),
			(double)mInterface.dataFFT4.get(mInterface.dataFFT0Length-1-i));
		}
		MainActivity.this.lineChartFeature.renderFFTData4();


		for(int i = 0; i<(mInterface.dataFFT0Length/2); i++){/*
		if(i==0){
			MainActivity.this.lineChartFeature.refreshFFTData5((double) (sampleFreq*(double)i/(double)mInterface.dataFFT0Length),
			(double)(0));
			i++;
		}*/
		MainActivity.this.lineChartFeature.refreshFFTData5((double) (sampleFreq*(double)i/(double)mInterface.dataFFT0Length),
			(double)mInterface.dataFFT5.get(mInterface.dataFFT0Length-1-i));
		}
		MainActivity.this.lineChartFeature.renderFFTData5();


		for(int i = 0; i<(mInterface.dataFFT0Length/2); i++){/*
		if(i==0){
			MainActivity.this.lineChartFeature.refreshFFTData6((double) (sampleFreq*(double)i/(double)mInterface.dataFFT0Length),
			(double)(0));
			i++;
		}*/
		MainActivity.this.lineChartFeature.refreshFFTData6((double) (sampleFreq*(double)i/(double)mInterface.dataFFT0Length),
			(double)mInterface.dataFFT6.get(mInterface.dataFFT0Length-1-i));
		}
		MainActivity.this.lineChartFeature.renderFFTData6();


		for(int i = 0; i<(mInterface.dataFFT0Length/2); i++){/*
		if(i==0){
			MainActivity.this.lineChartFeature.refreshFFTData7((double) (sampleFreq*(double)i/(double)mInterface.dataFFT0Length),
			(double)(0));
			i++;
		}*/
		MainActivity.this.lineChartFeature.refreshFFTData7((double) (sampleFreq*(double)i/(double)mInterface.dataFFT0Length),
			(double)mInterface.dataFFT7.get(mInterface.dataFFT0Length-1-i));
		}
		MainActivity.this.lineChartFeature.renderFFTData7();


		for(int i = 0; i<(mInterface.dataFFT0Length/2); i++){/*
		if(i==0){
			MainActivity.this.lineChartFeature.refreshFFTData8((double) (sampleFreq*(double)i/(double)mInterface.dataFFT0Length),
			(double)(0));
			i++;
		}*/
		MainActivity.this.lineChartFeature.refreshFFTData8((double) (sampleFreq*(double)i/(double)mInterface.dataFFT0Length),
			(double)mInterface.dataFFT8.get(mInterface.dataFFT0Length-1-i));
		}
		MainActivity.this.lineChartFeature.renderFFTData8();


		for(int i = 0; i<(mInterface.dataFFT0Length/2); i++){/*
		if(i==0){
			MainActivity.this.lineChartFeature.refreshFFTData9((double) (sampleFreq*(double)i/(double)mInterface.dataFFT0Length),
			(double)(0));
			i++;
		}*/
		MainActivity.this.lineChartFeature.refreshFFTData9((double) (sampleFreq*(double)i/(double)mInterface.dataFFT0Length),
			(double)mInterface.dataFFT9.get(mInterface.dataFFT0Length-1-i));
		}
		MainActivity.this.lineChartFeature.renderFFTData9();


		for(int i = 0; i<(mInterface.dataFFT0Length/2); i++){/*
		if(i==0){
			MainActivity.this.lineChartFeature.refreshFFTData10((double) (sampleFreq*(double)i/(double)mInterface.dataFFT0Length),
			(double)(0));
			i++;
		}*/
		MainActivity.this.lineChartFeature.refreshFFTData10((double) (sampleFreq*(double)i/(double)mInterface.dataFFT0Length),
			(double)mInterface.dataFFT10.get(mInterface.dataFFT0Length-1-i));
		}
		MainActivity.this.lineChartFeature.renderFFTData10();


		for(int i = 0; i<(mInterface.dataFFT0Length/2); i++){/*
		if(i==0){
			MainActivity.this.lineChartFeature.refreshFFTData11((double) (sampleFreq*(double)i/(double)mInterface.dataFFT0Length),
			(double)(0));
			i++;
		}*/
		MainActivity.this.lineChartFeature.refreshFFTData11((double) (sampleFreq*(double)i/(double)mInterface.dataFFT0Length),
			(double)mInterface.dataFFT11.get(mInterface.dataFFT0Length-1-i));
		}
		MainActivity.this.lineChartFeature.renderFFTData11();


		for(int i = 0; i<(mInterface.dataFFT0Length/2); i++){/*
		if(i==0){
			MainActivity.this.lineChartFeature.refreshFFTData12((double) (sampleFreq*(double)i/(double)mInterface.dataFFT0Length),
			(double)(0));
			i++;
		}*/
		MainActivity.this.lineChartFeature.refreshFFTData12((double) (sampleFreq*(double)i/(double)mInterface.dataFFT0Length),
			(double)mInterface.dataFFT12.get(mInterface.dataFFT0Length-1-i));
		}
		MainActivity.this.lineChartFeature.renderFFTData12();


		for(int i = 0; i<(mInterface.dataFFT0Length/2); i++){/*
		if(i==0){
			MainActivity.this.lineChartFeature.refreshFFTData13((double) (sampleFreq*(double)i/(double)mInterface.dataFFT0Length),
			(double)(0));
			i++;
		}*/
		MainActivity.this.lineChartFeature.refreshFFTData13((double) (sampleFreq*(double)i/(double)mInterface.dataFFT0Length),
			(double)mInterface.dataFFT13.get(mInterface.dataFFT0Length-1-i));
		}
		MainActivity.this.lineChartFeature.renderFFTData13();


		for(int i = 0; i<(mInterface.dataFFT0Length/2); i++){/*
		if(i==0){
			MainActivity.this.lineChartFeature.refreshFFTData14((double) (sampleFreq*(double)i/(double)mInterface.dataFFT0Length),
			(double)(0));
			i++;
		}*/
		MainActivity.this.lineChartFeature.refreshFFTData14((double) (sampleFreq*(double)i/(double)mInterface.dataFFT0Length),
			(double)mInterface.dataFFT14.get(mInterface.dataFFT0Length-1-i));//mInterface.dataFFT0Length-1-i
		}
		MainActivity.this.lineChartFeature.renderFFTData14();

		MainActivity.this.lineChartRehab.refreshData(mInterface.balanceAwareness,/*
			mInterface.agility,
			mInterface.continuity,
			mInterface.powerControl,
			mInterface.Explosivepower,*/
			mInterface.coordinationDegree,
			mInterface.StepLengthValue,
			mInterface.stepFrequencyValue,
			mInterface.FrequencyOfSwingArm);
		MainActivity.this.lineChartRehab.refreshDataF3(mInterface.LeftUpperLimbStrength,
			mInterface.LeftLowerLimbStrength,
			mInterface.maxdataAngleJava[4],
			mInterface.maxdataAngleJava[2],
			mInterface.maxdataAngleJava[0]);
		MainActivity.this.lineChartRehab.refreshDataF5(mInterface.RightUpperLimbStrength,
			mInterface.RightLowerLimbStrength,
			mInterface.maxdataAngleJava[5],
			mInterface.maxdataAngleJava[3],
			mInterface.maxdataAngleJava[1]);

		leftKneeAngle = 0;
		Log.v(TAG, "button3Action.DataFFT dataFFT0Length = " + String.valueOf(mInterface.dataFFT0Length));


		}
		
		
		// MainActivity.this.lineChart.refreshEchartsWithOption(EchartOptionUtil.getLineChartOptions((Object[])((ArrayList<Object>)b.getParcelableArrayList("dataX").get(0)).toArray(), (Object[])((ArrayList<Object>)b.getParcelableArrayList("dataY").get(0)).toArray()));
		// MainActivity.this.lineChart.refreshEchartsWithOption(EchartOptionUtil.getLineStackedAreaChartOptions((Object[])((ArrayList<Object>)b.getParcelableArrayList("dataX").get(0)).toArray(), (Object[])((ArrayList<Object>)b.getParcelableArrayList("dataY").get(0)).toArray()));
/*
		MainActivity.this.lineChart.refreshData(b.getStringArray("date"),
			b.getIntArray("data0"),
			b.getIntArray("data1"),
			b.getIntArray("data2"),
			b.getIntArray("data3"),
			b.getIntArray("data4"),
			b.getIntArray("data5"),
			b.getIntArray("data6"),
			b.getIntArray("data7"),
			b.getIntArray("data8"),
			b.getIntArray("data9"),
			b.getIntArray("data10"),
			b.getIntArray("data11"),
			b.getIntArray("data12"),
			b.getIntArray("data13"),
			b.getIntArray("data14"));*/
		if((int)b.getInt("case")==100){

		MainActivity.this.lineChart.refreshData(b.getString("date"),
			b.getInt("data0"),
			b.getInt("data1"),
			b.getInt("data2"),
			b.getInt("data3"),
			b.getInt("data4"),
			b.getInt("data5"),
			b.getInt("data6"),
			b.getInt("data7"),
			b.getInt("data8"),
			b.getInt("data9"),
			b.getInt("data10"),
			b.getInt("data11"),
			b.getInt("data12"),
			b.getInt("data13"),
			b.getInt("data14"),
			b.getInt("leftKneeAngle"),
			b.getInt("rightKneeAngle"),
			b.getInt("leftElbowAngle"),
			b.getInt("rightElbowAngle"),
			b.getInt("leftShoulderAngle"),
			b.getInt("rightShoulderAngle"));}

	}
	}

/*
       class UpdateSpeedThread implements Runnable {

           // run in a second  
        final long timeInterval = 10000;  
	Object[] x = new Object[]{};
	ArrayList<Object> newX = new ArrayList<Object>(Arrays.asList(x));
	Object[] y = new Object[]{};
	ArrayList<Object> newY = new ArrayList<Object>(Arrays.asList(y));
	int wait = 0;

	public void run() {
                while (true) {  
                    // ------- code for task to run  
			//if (wait>=5) 
			//{
			Log.v(TAG, "UpdateSpeedThread 1");
			newX.add(getCurrentHourMinute());
			newY.add(Math.round(landMarksDataSpeed1[0]*1000));
			
			//Object[] xOut= newX.toArray();Object[] yOut= newY.toArray();
			Log.v(TAG, Arrays.toString(newX.toArray()));Log.v(TAG, Arrays.toString(newY.toArray()));
			Message msg = new Message();
			Bundle b = new Bundle();// 存放数据
			b.putString("datatX",Arrays.toString(newX.toArray()));
			b.putString("datatY",Arrays.toString(newY.toArray()));
			msg.setData(b);
			MainActivity.this.myHandler.sendMessage(msg);// 向Handler发送消息,更新UI
			//lineChart.refreshEchartsWithOption(EchartOptionUtil.getLineChartOptions(xOut, yOut));
			//}
		
                    // ------- ends here  
                    try {  
                       Thread.sleep(timeInterval);  //if(wait<5){wait++;Log.v(TAG, "Thread.sleep(timeInterval)");}
                    } catch (InterruptedException e) {  
                        e.printStackTrace();  
                    }  
                }  
            }  
        }
*/
}
