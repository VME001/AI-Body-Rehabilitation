// Copyright 2020 The MediaPipe Authors.
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

package com.google.mediapipe.apps.posetrackinggpu;

import android.os.Bundle;
import android.util.Log;
import com.google.mediapipe.formats.proto.LandmarkProto.NormalizedLandmark;
import com.google.mediapipe.formats.proto.LandmarkProto.NormalizedLandmarkList;
import com.google.mediapipe.framework.PacketGetter;
import com.google.protobuf.InvalidProtocolBufferException;


import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;


/** Main activity of MediaPipe pose tracking app. */
public class MainActivity extends com.google.mediapipe.apps.basic.MainActivity {
  private static final String TAG = "MainActivity";

  private static final String OUTPUT_LANDMARKS_STREAM_NAME = "pose_landmarks";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

/*
new AlertDialog.Builder(this).setTitle("pose_landmarks")//设置对话框标题  

                .setMessage("pose_landmarks")//设置显示的内容  

                .setPositiveButton("退出",new DialogInterface.OnClickListener() {//添加确定按钮  



                    @Override

                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件  

                        // TODO Auto-generated method stub  

                        Log.i("alertdialog"," 请保存数据！");  

                    }

                }).show();//在按键响应事件中显示此对话框
*/
/*
    // To show verbose logging, run:
    // adb shell setprop log.tag.MainActivity VERBOSE

    if (Log.isLoggable(TAG, Log.VERBOSE)) {
      processor.addPacketCallback(
          OUTPUT_LANDMARKS_STREAM_NAME,
          (packet) -> {
            Log.v(TAG, "Received pose landmarks packet.");
            try {
              NormalizedLandmarkList poseLandmarks =
                  PacketGetter.getProto(packet, NormalizedLandmarkList.class);
              Log.v(
                  TAG,
                  "[TS:"
                      + packet.getTimestamp()
                      + "] "
                      + getPoseLandmarksDebugString(poseLandmarks));
            } catch (InvalidProtocolBufferException exception) {
              Log.e(TAG, "Failed to get proto.", exception);
            }
          });
    }
	
*/
  }


  private static String getPoseLandmarksDebugString(NormalizedLandmarkList poseLandmarks) {
    String poseLandmarkStr = "Pose landmarks: " + poseLandmarks.getLandmarkCount() + "\n";
    // poseLandmarkStr = "Pose landmarks: " + poseLandmarks.getLandmarkCount() + "\n";
    int landmarkIndex = 0;
    for (NormalizedLandmark landmark : poseLandmarks.getLandmarkList()) {
      poseLandmarkStr +=
          "\tLandmark ["
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
    return poseLandmarkStr;
  }
}
