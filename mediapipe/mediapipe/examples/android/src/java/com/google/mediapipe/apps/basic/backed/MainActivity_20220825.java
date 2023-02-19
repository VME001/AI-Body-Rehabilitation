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

  private SurfaceView button2DisplayView;

  private SurfaceView button3DisplayView;

  private SurfaceView button4DisplayView;

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


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(getContentViewLayoutResId());

    try {
      applicationInfo =
          getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
    } catch (NameNotFoundException e) {
      Log.e(TAG, "Cannot find application info: " + e);
    }

    previewDisplayView = new SurfaceView(this);
    button2DisplayView = new MySurfaceView2(this);
    button3DisplayView = new MySurfaceView3(this);
    button4DisplayView = new MySurfaceView4(this);
    setupPreviewDisplayView();

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
	new Thread(new MyAccelerationThread()).start();
/*
new AlertDialog.Builder(this).setTitle("processor.addPacketCallback0000")//设置对话框标题  

                .setMessage(poseLandmarkStrDemo)//设置显示的内容  

                .setPositiveButton("退出",new DialogInterface.OnClickListener() {//添加确定按钮  



                    @Override

                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件  

                        // TODO Auto-generated method stub  

                        Log.i("alertdialog"," 请保存数据！");  

                    }

                }).show();//在按键响应事件中显示此对话框
*/
	processor.addPacketCallback(
          OUTPUT_LANDMARKS_STREAM_NAME,
          (packet) -> {
            // Log.v(TAG, "Received pose landmarks packet.");
            try {

              byte[] landmarksRaw = PacketGetter.getProtoBytes(packet);
              LandmarkList poseLandmarks = LandmarkList.parseFrom(landmarksRaw);
	      getPoseLandmarksDebugString(poseLandmarks);
/*
              Log.v(TAG, "[TS:" + packet.getTimestamp() + "] " + getPoseLandmarksDebugString(poseLandmarks));
		for (int i = 0; i < landMarksData.length; i++) {
         	   
        	       Log.v(TAG, "landMarksData[" + String.valueOf(i) + "] : " + String.valueOf(landMarksData[i][0]) + ", " +String.valueOf(landMarksData[i][1]) + ", " + String.valueOf(landMarksData[i][2]));
       		 
		}
		*/
            } catch (InvalidProtocolBufferException exception) {
              Log.e(TAG, "Failed to get proto.", exception);
            }
          });

     	/*
	new AlertDialog.Builder(this).setTitle("processor.addPacketCallback!!!!!!!!!!!!")//设置对话框标题  

                .setMessage(poseLandmarkStrDemo)//设置显示的内容  

                .setPositiveButton("退出",new DialogInterface.OnClickListener() {//添加确定按钮  



                    @Override

                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件  

                        // TODO Auto-generated method stub  

                        Log.i("alertdialog"," 请保存数据！");  

                    }

                }).show();//在按键响应事件中显示此对话框
*/
  }


  private void button1Action() {
        Button startButton1 = findViewById(R.id.button1);
        startButton1.setOnClickListener(
                v -> {
		Log.v(TAG, "button1Action.");
		previewDisplayView.setVisibility(View.VISIBLE);
		button2DisplayView.setVisibility(View.INVISIBLE);
		button3DisplayView.setVisibility(View.INVISIBLE);
		button4DisplayView.setVisibility(View.INVISIBLE);
	});
  }

  private void button2Action() {
        Button startButton2 = findViewById(R.id.button2);
        startButton2.setOnClickListener(
                v -> {
		Log.v(TAG, "button1Action.");
		previewDisplayView.setVisibility(View.INVISIBLE);
		button3DisplayView.setVisibility(View.INVISIBLE);
		button4DisplayView.setVisibility(View.INVISIBLE);
		// button2DisplayView = new MySurfaceView2(this);
		// viewGroup.addView(button2DisplayView);
		// button2DisplayView.setVisibility(View.VISIBLE);
		// viewGroup.addView(eChart);
               //eChart = findViewById(R.id.eChart);


		/*new AlertDialog.Builder(this).setTitle("poseLandmarkStrDemo")//设置对话框标题  

                .setMessage(poseLandmarkStrDemo)//设置显示的内容  

                .setPositiveButton("退出",new DialogInterface.OnClickListener() {//添加确定按钮  



                    @Override

                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件  

                        // TODO Auto-generated method stub  

                        Log.i("alertdialog"," 请保存数据！");  

                    }

                }).show();//在按键响应事件中显示此对话框*/
	});
  }

  private void button3Action() {
        Button startButton3 = findViewById(R.id.button3);
        startButton3.setOnClickListener(
                v -> {
		Log.v(TAG, "button1Action.");
		previewDisplayView.setVisibility(View.INVISIBLE);
		button2DisplayView.setVisibility(View.INVISIBLE);
		button4DisplayView.setVisibility(View.INVISIBLE);
		button3DisplayView = new MySurfaceView3(this);
		viewGroup.addView(button3DisplayView);
		button3DisplayView.setVisibility(View.VISIBLE);
		
	});
  }

  private void button4Action() {
        Button startButton4 = findViewById(R.id.button4);
        startButton4.setOnClickListener(
                v -> {
		Log.v(TAG, "button1Action.");
		previewDisplayView.setVisibility(View.INVISIBLE);
		button2DisplayView.setVisibility(View.INVISIBLE);
		button3DisplayView.setVisibility(View.INVISIBLE);
		button4DisplayView = new MySurfaceView4(this);
		viewGroup.addView(button4DisplayView);
		button4DisplayView.setVisibility(View.VISIBLE);
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


  viewGroup.addView(button2DisplayView);
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
  viewGroup.addView(button3DisplayView);
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
  viewGroup.addView(button4DisplayView);
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
  button2DisplayView.setVisibility(View.GONE);
  button3DisplayView.setVisibility(View.GONE);
  button4DisplayView.setVisibility(View.GONE);
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

       class MyAccelerationThread extends Thread {

           // run in a second  
        final long timeInterval = 1000;  
        @Override

	public void run() {
                while (true) {  
                    // ------- code for task to run  
			for(int i=0 ;i<33;i++){
				for(int j=0;j<3;j++){
					landMarksDataTwoSecondPre[i][j]=landMarksDataOneSecondPre[i][j];
					landMarksDataOneSecondPre[i][j]=landMarksDataCurrent[i][j];
					landMarksDataCurrent[i][j]=landMarksData[i][j];
				}
			}
			//landMarksDataTwoSecondPre = (double[][])landMarksDataOneSecondPre.clone();;
		        //landMarksDataOneSecondPre = (double[][])landMarksDataCurrent.clone();;
                        //landMarksDataCurrent = (double[][])landMarksData.clone();;
			for (int i = 0; i < landMarksDataSpeed0.length; i++) {
				landMarksDataSpeed0[i] = Math.sqrt(Math.pow((landMarksDataOneSecondPre[i][0]- landMarksDataTwoSecondPre[i][0]),2) + Math.pow((landMarksDataOneSecondPre[i][1]- landMarksDataTwoSecondPre[i][1]),2) + Math.pow((landMarksDataOneSecondPre[i][2]- landMarksDataTwoSecondPre[i][2]),2)); 
				landMarksDataSpeed1[i] = Math.sqrt(Math.pow((landMarksDataCurrent[i][0]- landMarksDataOneSecondPre[i][0]),2) + Math.pow((landMarksDataCurrent[i][1]- landMarksDataOneSecondPre[i][1]),2) + Math.pow((landMarksDataCurrent[i][2]- landMarksDataOneSecondPre[i][2]),2)); 
				landMarksDataAcceleration[i] = landMarksDataSpeed1[i] - landMarksDataSpeed0[i];
			}
		for (int i = 0; i < landMarksDataTwoSecondPre.length; i++) {
         	   
        	       Log.v(TAG, "landMarksDataTwoSecondPre[" + String.valueOf(i) + "] : " + String.valueOf(landMarksDataTwoSecondPre[i][0]) + ", " +String.valueOf(landMarksDataTwoSecondPre[i][1]) + ", " + String.valueOf(landMarksDataTwoSecondPre[i][2]));
       		 
		}
		for (int i = 0; i < landMarksDataOneSecondPre.length; i++) {
         	   
        	       Log.v(TAG, "landMarksDataOneSecondPre[" + String.valueOf(i) + "] : " + String.valueOf(landMarksDataOneSecondPre[i][0]) + ", " +String.valueOf(landMarksDataOneSecondPre[i][1]) + ", " + String.valueOf(landMarksDataOneSecondPre[i][2]));
       		 
		}
		for (int i = 0; i < landMarksDataCurrent.length; i++) {
         	   
        	       Log.v(TAG, "landMarksDataCurrent[" + String.valueOf(i) + "] : " + String.valueOf(landMarksDataCurrent[i][0]) + ", " +String.valueOf(landMarksDataCurrent[i][1]) + ", " + String.valueOf(landMarksDataCurrent[i][2]));
       		 
		}

		for (int i = 0; i < landMarksDataSpeed0.length; i++) {
         	   
        	       Log.v(TAG, "landMarksDataSpeed0[" + String.valueOf(i) + "] : " + String.valueOf(landMarksDataSpeed0[i]));
       		 
		}
		for (int i = 0; i < landMarksDataSpeed1.length; i++) {
         	   
        	       Log.v(TAG, "landMarksDataSpeed1[" + String.valueOf(i) + "] : " + String.valueOf(landMarksDataSpeed1[i]));
       		 
		}
		for (int i = 0; i < landMarksDataAcceleration.length; i++) {
         	   
        	       Log.v(TAG, "landMarksDataAcceleration[" + String.valueOf(i) + "] : " + String.valueOf(landMarksDataAcceleration[i]));
       		 
		}
                    // ------- ends here  
                    try {  
                        Thread.sleep(timeInterval);  
                    } catch (InterruptedException e) {  
                        e.printStackTrace();  
                    }  
                }  
            }  
        }
     


}
