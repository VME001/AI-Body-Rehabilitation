package com.google.mediapipe.apps.basic;

import android.util.Log;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;

import android.webkit.JavascriptInterface;

import java.util.Arrays;
import java.util.*;

import com.google.mediapipe.apps.basic.Complex;
import java.util.stream.Collectors;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class JSInterface {

    //@JavascriptInterface
    //public void testJSCall(){
       // Log.e("MainActivity: JSInterfaceTest","testJSCall");
    //}
 	public static int keyPointCnt = 15;
	public static double[] maxdataJava = new double[keyPointCnt];
	public static double[] mindataJava = new double[keyPointCnt];
	public static double[] averagedataJava = new double[keyPointCnt];
	public static double[] peakdataJava = new double[keyPointCnt];
	public static double[] RMEANdataJava = new double[keyPointCnt];
	public static double[] meanSquareValueJava = new double[keyPointCnt];
	public static double[] varianceJava = new double[keyPointCnt];
	public static double[] RMSJava = new double[keyPointCnt];
	public static double[] standardDeviationJava = new double[keyPointCnt];
	public static double[] KurtosisJava = new double[keyPointCnt];
	public static double[] skewnessJava = new double[keyPointCnt];
	public static double[] waveformfactorJava = new double[keyPointCnt];
	public static double[] peakfactorJava = new double[keyPointCnt];
	public static double[] pulsefactorJava = new double[keyPointCnt];
	public static double[] marginfactorJava = new double[keyPointCnt];

	public static double[] maxdataAngleJava = new double[6];

	public static int LeftUpperLimbStrength = 0;
	public static int RightUpperLimbStrength = 0;
	public static int LeftLowerLimbStrength = 0;
	public static int RightLowerLimbStrength = 0;

	public static double[] CenterGravityGrequencyJava = new double[keyPointCnt];
	public static double[] MeanSquareFrequencyJava = new double[keyPointCnt];
	public static double[] RootMeanSquareFrequencyJava = new double[keyPointCnt];
	public static double[] FrequencyVarianceJava = new double[keyPointCnt];
	public static double[] FrequencyStandardDeviationJava = new double[keyPointCnt];

	public static double[] PowerSpectrumEntropyJava = new double[keyPointCnt];
	public static double[] SingularSpectrumEntropyJava = new double[keyPointCnt];
	public static double[] EnergyEntropyJava = new double[keyPointCnt];

	public static ArrayList dataFFT0; public static int dataFFT0Length = 0;
	public static ArrayList dataFFT1; public static int dataFFT1Length = 0;
	public static ArrayList dataFFT2; public static int dataFFT2Length = 0;
	public static ArrayList dataFFT3; public static int dataFFT3Length = 0;
	public static ArrayList dataFFT4; public static int dataFFT4Length = 0;
	public static ArrayList dataFFT5; public static int dataFFT5Length = 0;
	public static ArrayList dataFFT6; public static int dataFFT6Length = 0;
	public static ArrayList dataFFT7; public static int dataFFT7Length = 0;
	public static ArrayList dataFFT8; public static int dataFFT8Length = 0;
	public static ArrayList dataFFT9; public static int dataFFT9Length = 0;
	public static ArrayList dataFFT10; public static int dataFFT10Length = 0;
	public static ArrayList dataFFT11; public static int dataFFT11Length = 0;
	public static ArrayList dataFFT12; public static int dataFFT12Length = 0;
	public static ArrayList dataFFT13; public static int dataFFT13Length = 0;
	public static ArrayList dataFFT14; public static int dataFFT14Length = 0;

        public static FFT newFFT;

	public static boolean runOrStop = false;

	public static boolean latestFFTData = false;

	public static double balanceAwareness = 50;
	public static double agility = 50;
	public static double continuity = 50;
	public static double powerControl = 50;
	public static double Explosivepower  = 50;
	public static double coordinationDegree = 50;
	public static double StepLengthValue = 50;
	public static double stepFrequencyValue = 50;
	public static double FrequencyOfSwingArm = 50;

	public static double  estimateBodyHeight = 50;

	public static double[] balanceFactor = new double[8];

	public static double maxAmplitudeFreuqOfFTData0;
	public static double maxAmplitudeFreuqOfFTData1;
	public static double maxAmplitudeFreuqOfFTData2;
	public static double maxAmplitudeFreuqOfFTData3;
	public static double maxAmplitudeFreuqOfFTData4;
	public static double maxAmplitudeFreuqOfFTData5;
	public static double maxAmplitudeFreuqOfFTData6;
	public static double maxAmplitudeFreuqOfFTData7;
	public static double maxAmplitudeFreuqOfFTData8;
	public static double maxAmplitudeFreuqOfFTData9;
	public static double maxAmplitudeFreuqOfFTData10;
	public static double maxAmplitudeFreuqOfFTData11;
	public static double maxAmplitudeFreuqOfFTData12;
	public static double maxAmplitudeFreuqOfFTData13;
	public static double maxAmplitudeFreuqOfFTData14;

	public static double[] maxFFTdataJavaProChannel = new double[keyPointCnt];

    @JavascriptInterface
    public void testJSCallRun(){
	runOrStop = true;
	Log.e("MainActivity: JSInterfaceTest","testJSCallRun runOrStop = "+  String.valueOf(runOrStop));
    }

    @JavascriptInterface
    public void testJSCallStop(){
	runOrStop = false;
	Log.e("MainActivity: JSInterfaceTest","testJSCallStop runOrStop = "+  String.valueOf(runOrStop));
    }

 @JavascriptInterface
    public void testJSCallOption(double startCalc, String startCalcDate, double stopCalc, String stopCalcDate, double sampleLength){
        Log.e("MainActivity: JSInterfaceTest","testJSCall startCalc = "+  String.valueOf(startCalc));
        Log.e("MainActivity: JSInterfaceTest","testJSCall xAxisData[startCalc] = "+  startCalcDate);
        Log.e("MainActivity: JSInterfaceTest","testJSCall stopCalc = "+  String.valueOf(stopCalc));
        Log.e("MainActivity: JSInterfaceTest","testJSCall xAxisData[stopCalc] = "+  stopCalcDate);
	Log.e("MainActivity: JSInterfaceTest","testJSCall sampleLength = "+  sampleLength);
}
    @JavascriptInterface
    public void testJSCall(double maxdata0[], double mindata0[], double averagedata0[], double peakdata0[], double RMEANdata0[], double meanSquareValue0[], double variance0[], double RMS0[], double standardDeviation0[], double Kurtosis0[], double skewness0[], double waveformfactor0[], double peakfactor0[], double pulsefactor0[], double marginfactor0[], double startCalc, String startCalcDate, double stopCalc, String stopCalcDate, double sampleLength, double maxdataAngle0[]){
    //public void testJSCall(String maxdata0[], String mindata0[], String averagedata0[], String peakdata0[], String RMEANdata0[], String meanSquareValue0[], String variance0[], String RMS0[], String standardDeviation0[], String Kurtosis0[], String skewness0[], String waveformfactor0[], String peakfactor0[], String pulsefactor0[], String marginfactor0[], double startCalc, String startCalcDate, double stopCalc, String stopCalcDate, double sampleLength){
/*
        Log.e("MainActivity: JSInterfaceTest","testJSCall startCalc = "+  String.valueOf(startCalc));
        Log.e("MainActivity: JSInterfaceTest","testJSCall xAxisData[startCalc] = "+  startCalcDate);
        Log.e("MainActivity: JSInterfaceTest","testJSCall maxdataArrays = "+  Arrays.toString(maxdataJava));
        Log.e("MainActivity: JSInterfaceTest","testJSCall mindataArrays = "+  Arrays.toString(mindataJava));
        Log.e("MainActivity: JSInterfaceTest","testJSCall averagedataArrays = "+  Arrays.toString(averagedataJava));
        Log.e("MainActivity: JSInterfaceTest","testJSCall peakdataArrays = "+  Arrays.toString(peakdataJava));
        Log.e("MainActivity: JSInterfaceTest","testJSCall RMEANdataArrays = "+  Arrays.toString(RMEANdataJava));
        Log.e("MainActivity: JSInterfaceTest","testJSCall meanSquareValueArrays = "+  Arrays.toString(meanSquareValueJava));
        Log.e("MainActivity: JSInterfaceTest","testJSCall varianceArrays = "+  Arrays.toString(varianceJava));
        Log.e("MainActivity: JSInterfaceTest","testJSCall RMSArrays = "+  Arrays.toString(RMSJava));
        Log.e("MainActivity: JSInterfaceTest","testJSCall standardDeviationArrays = "+  Arrays.toString(standardDeviationJava));
        Log.e("MainActivity: JSInterfaceTest","testJSCall KurtosisArrays = "+  Arrays.toString(KurtosisJava));
        Log.e("MainActivity: JSInterfaceTest","testJSCall skewnessArrays = "+  Arrays.toString(skewnessJava));
        Log.e("MainActivity: JSInterfaceTest","testJSCall waveformfactorArrays = "+  Arrays.toString(waveformfactorJava));
        Log.e("MainActivity: JSInterfaceTest","testJSCall peakfactorArrays = "+  Arrays.toString(peakfactorJava));
        Log.e("MainActivity: JSInterfaceTest","testJSCall pulsefactorArrays = "+  Arrays.toString(pulsefactorJava));
        Log.e("MainActivity: JSInterfaceTest","testJSCall marginfactorArrays = "+  Arrays.toString(marginfactorJava));
        Log.e("MainActivity: JSInterfaceTest","testJSCall stopCalc = "+  String.valueOf(stopCalc));
        Log.e("MainActivity: JSInterfaceTest","testJSCall xAxisData[stopCalc] = "+  stopCalcDate);
	Log.e("MainActivity: JSInterfaceTest","testJSCall sampleLength = "+  sampleLength);*/

	maxdataJava = (double[]) maxdata0.clone();
 	mindataJava = (double[])mindata0.clone();
	averagedataJava = (double[])averagedata0.clone();
	peakdataJava = (double[])peakdata0.clone();
	RMEANdataJava = (double[])RMEANdata0.clone();
	meanSquareValueJava = (double[])meanSquareValue0.clone();
	varianceJava = (double[])variance0.clone();
	RMSJava = (double[])RMS0.clone();
	standardDeviationJava = (double[])standardDeviation0.clone();
	KurtosisJava = (double[])Kurtosis0.clone();
	skewnessJava = (double[])skewness0.clone();
	waveformfactorJava = (double[])waveformfactor0.clone();
	peakfactorJava = (double[])peakfactor0.clone();
	pulsefactorJava = (double[])pulsefactor0.clone();
	marginfactorJava = (double[])marginfactor0.clone();

	maxdataAngleJava = (double[])maxdataAngle0.clone();

        Log.e("MainActivity: JSInterfaceTest","testJSCall startCalc = "+  String.valueOf(startCalc));
        Log.e("MainActivity: JSInterfaceTest","testJSCall xAxisData[startCalc] = "+  startCalcDate);
        Log.e("MainActivity: JSInterfaceTest","testJSCall maxdataArrays = "+  Arrays.toString(maxdataJava));
        Log.e("MainActivity: JSInterfaceTest","testJSCall mindataArrays = "+  Arrays.toString(mindataJava));
        Log.e("MainActivity: JSInterfaceTest","testJSCall averagedataArrays = "+  Arrays.toString(averagedataJava));
        Log.e("MainActivity: JSInterfaceTest","testJSCall peakdataArrays = "+  Arrays.toString(peakdataJava));
        Log.e("MainActivity: JSInterfaceTest","testJSCall RMEANdataArrays = "+  Arrays.toString(RMEANdataJava));
        Log.e("MainActivity: JSInterfaceTest","testJSCall meanSquareValueArrays = "+  Arrays.toString(meanSquareValueJava));
        Log.e("MainActivity: JSInterfaceTest","testJSCall varianceArrays = "+  Arrays.toString(varianceJava));
        Log.e("MainActivity: JSInterfaceTest","testJSCall RMSArrays = "+  Arrays.toString(RMSJava));
        Log.e("MainActivity: JSInterfaceTest","testJSCall standardDeviationArrays = "+  Arrays.toString(standardDeviationJava));
        Log.e("MainActivity: JSInterfaceTest","testJSCall KurtosisArrays = "+  Arrays.toString(KurtosisJava));
        Log.e("MainActivity: JSInterfaceTest","testJSCall skewnessArrays = "+  Arrays.toString(skewnessJava));
        Log.e("MainActivity: JSInterfaceTest","testJSCall waveformfactorArrays = "+  Arrays.toString(waveformfactorJava));
        Log.e("MainActivity: JSInterfaceTest","testJSCall peakfactorArrays = "+  Arrays.toString(peakfactorJava));
        Log.e("MainActivity: JSInterfaceTest","testJSCall pulsefactorArrays = "+  Arrays.toString(pulsefactorJava));
        Log.e("MainActivity: JSInterfaceTest","testJSCall marginfactorArrays = "+  Arrays.toString(marginfactorJava));
        Log.e("MainActivity: JSInterfaceTest","testJSCall maxdataAngleJavaArrays = "+  Arrays.toString(maxdataAngleJava));
        Log.e("MainActivity: JSInterfaceTest","testJSCall stopCalc = "+  String.valueOf(stopCalc));
        Log.e("MainActivity: JSInterfaceTest","testJSCall xAxisData[stopCalc] = "+  stopCalcDate);
	Log.e("MainActivity: JSInterfaceTest","testJSCall sampleLength = "+  sampleLength);


	if(maxdataAngleJava[4]>=10){
		LeftUpperLimbStrength = 10;
	}
	if(maxdataAngleJava[4]>=20){
		LeftUpperLimbStrength = 20;
	}
	if(maxdataAngleJava[4]>=30){
		LeftUpperLimbStrength = 30;
	}
	if(maxdataAngleJava[4]>=40){
		LeftUpperLimbStrength = 40;
	}
	if(maxdataAngleJava[4]>=50){
		LeftUpperLimbStrength = 50;
	}


	if(maxdataAngleJava[5]>=10){
		RightUpperLimbStrength = 10;
	}
	if(maxdataAngleJava[5]>=20){
		RightUpperLimbStrength = 20;
	}
	if(maxdataAngleJava[5]>=30){
		RightUpperLimbStrength = 30;
	}
	if(maxdataAngleJava[5]>=40){
		RightUpperLimbStrength = 40;
	}
	if(maxdataAngleJava[5]>=50){
		RightUpperLimbStrength = 50;
	}


	if(maxdataAngleJava[0]>=10){
		LeftLowerLimbStrength = 10;
	}
	if(maxdataAngleJava[0]>=20){
		LeftLowerLimbStrength = 20;
	}
	if(maxdataAngleJava[0]>=30){
		LeftLowerLimbStrength = 30;
	}
	if(maxdataAngleJava[0]>=40){
		LeftLowerLimbStrength = 40;
	}
	if(maxdataAngleJava[0]>=50){
		LeftLowerLimbStrength = 50;
	}


	if(maxdataAngleJava[1]>=10){
		RightLowerLimbStrength = 10;
	}
	if(maxdataAngleJava[1]>=20){
		RightLowerLimbStrength = 20;
	}
	if(maxdataAngleJava[1]>=30){
		RightLowerLimbStrength = 30;
	}
	if(maxdataAngleJava[1]>=40){
		RightLowerLimbStrength = 40;
	}
	if(maxdataAngleJava[1]>=50){
		RightLowerLimbStrength = 50;
	}


	RehabilitationAssessmentFunction();
	latestFFTData = true;
	Log.e("MainActivity: JSInterfaceTest","testJSCall latestFFTData = "+  String.valueOf(latestFFTData));

	
    }

	public void RehabilitationAssessmentFunction() {
	estimateBodyHeight = averagedataJava[0] + (maxdataJava[13]+maxdataJava[14])/2 + (averagedataJava[0]- (averagedataJava[1]+averagedataJava[4])/2);
	DecimalFormat df = new DecimalFormat("#.##");
	StepLengthValue = Double.parseDouble(df.format((double)estimateBodyHeight*0.37/10.0)); //步幅=身高×0.37
	balanceFactor[0] = CenterGravityGrequencyJava[0];
	balanceFactor[1] = Math.abs(maxAmplitudeFreuqOfFTData1 - maxAmplitudeFreuqOfFTData4);
	balanceFactor[2] = Math.abs(maxAmplitudeFreuqOfFTData2 - maxAmplitudeFreuqOfFTData5);
	balanceFactor[3] = Math.abs(maxAmplitudeFreuqOfFTData3 - maxAmplitudeFreuqOfFTData6);
	balanceFactor[4] = Math.abs(maxAmplitudeFreuqOfFTData7 - maxAmplitudeFreuqOfFTData10);
	balanceFactor[5] = Math.abs(maxAmplitudeFreuqOfFTData8 - maxAmplitudeFreuqOfFTData11);
	balanceFactor[6] = Math.abs(maxAmplitudeFreuqOfFTData9 - maxAmplitudeFreuqOfFTData12);
	balanceFactor[7] = Math.abs(maxAmplitudeFreuqOfFTData13 - maxAmplitudeFreuqOfFTData14);

	balanceAwareness = 10;

	if(/*(balanceFactor[1]<=0.45)&&*/(balanceFactor[2]<=0.45)&&(balanceFactor[3]<=0.45)&&(balanceFactor[4]<=0.45)&&(balanceFactor[5]<=0.45)/*&&(balanceFactor[6]<=0.27)&&(balanceFactor[7]<=0.27)*/){
		balanceAwareness = 20;
	}

	if(/*(balanceFactor[1]<=0.40)&&*/(balanceFactor[2]<=0.40)&&(balanceFactor[3]<=0.40)&&(balanceFactor[4]<=0.40)&&(balanceFactor[5]<=0.40)/*&&(balanceFactor[6]<=0.24)&&(balanceFactor[7]<=0.24)*/){
		balanceAwareness = 30;
	}

	if(/*(balanceFactor[1]<=0.35)&&*/(balanceFactor[2]<=0.35)&&(balanceFactor[3]<=0.35)&&(balanceFactor[4]<=0.35)&&(balanceFactor[5]<=0.35)/*&&(balanceFactor[6]<=0.21)&&(balanceFactor[7]<=0.21)*/){
		balanceAwareness = 40;
	}

	if(/*(balanceFactor[1]<=0.30)&&*/(balanceFactor[2]<=0.30)&&(balanceFactor[3]<=0.30)&&(balanceFactor[4]<=0.30)&&(balanceFactor[5]<=0.30)/*&&(balanceFactor[6]<=0.18)&&(balanceFactor[7]<=0.18)*/){
		balanceAwareness = 50;
	}

	if(/*(balanceFactor[1]<=0.25)&&*/(balanceFactor[2]<=0.25)&&(balanceFactor[3]<=0.25)&&(balanceFactor[4]<=0.25)&&(balanceFactor[5]<=0.25)/*&&(balanceFactor[6]<=0.15)&&(balanceFactor[7]<=0.15)*/){
		balanceAwareness = 60;
	}

	if(/*(balanceFactor[1]<=0.20)&&*/(balanceFactor[2]<=0.20)&&(balanceFactor[3]<=0.20)&&(balanceFactor[4]<=0.20)&&(balanceFactor[5]<=0.20)/*&&(balanceFactor[6]<=0.12)&&(balanceFactor[7]<=0.12)*/){
		balanceAwareness = 70;
	}

	if(/*(balanceFactor[1]<=0.15)&&*/(balanceFactor[2]<=0.15)&&(balanceFactor[3]<=0.15)&&(balanceFactor[4]<=0.15)&&(balanceFactor[5]<=0.15)/*&&(balanceFactor[6]<=0.09)&&(balanceFactor[7]<=0.09)*/){
		balanceAwareness = 80;
	}

	if(/*(balanceFactor[1]<=0.1)&&*/(balanceFactor[2]<=0.1)&&(balanceFactor[3]<=0.1)&&(balanceFactor[4]<=0.1)&&(balanceFactor[5]<=0.1)/*&&(balanceFactor[6]<=0.06)&&(balanceFactor[7]<=0.06)*/){
		balanceAwareness = 90;
	}

	if(/*(balanceFactor[1]<=0.05)&&*/(balanceFactor[2]<=0.05)&&(balanceFactor[3]<=0.05)&&(balanceFactor[4]<=0.05)&&(balanceFactor[5]<=0.05)/*&&(balanceFactor[6]<=0.03)&&(balanceFactor[7]<=0.03)*/){
		balanceAwareness = 100;
	}

	if((maxAmplitudeFreuqOfFTData0==0.0)||(maxAmplitudeFreuqOfFTData1==0.0)||(maxAmplitudeFreuqOfFTData2==0.0)||(maxAmplitudeFreuqOfFTData3==0.0)||(maxAmplitudeFreuqOfFTData4==0.0)||(maxAmplitudeFreuqOfFTData5==0.0)||(maxAmplitudeFreuqOfFTData6==0.0)||(maxAmplitudeFreuqOfFTData7==0.0)||(maxAmplitudeFreuqOfFTData8==0.0)||(maxAmplitudeFreuqOfFTData9==0.0)||(maxAmplitudeFreuqOfFTData10==0.0)||(maxAmplitudeFreuqOfFTData11==0.0)||(maxAmplitudeFreuqOfFTData12==0.0)||(maxAmplitudeFreuqOfFTData13==0.0)||(maxAmplitudeFreuqOfFTData14==0.0)){
		balanceAwareness = 0;
	}

	agility = (int)(100* Math.random());
	continuity = (int)(100* Math.random());
	powerControl = (int)(100* Math.random());
	Explosivepower  = (int)(100* Math.random());
	coordinationDegree = balanceAwareness;
	
	stepFrequencyValue = Double.parseDouble(df.format( 60*(maxAmplitudeFreuqOfFTData8 + maxAmplitudeFreuqOfFTData11)));
	if((maxFFTdataJavaProChannel[8]<1.8)||(maxFFTdataJavaProChannel[11]<1.8)){stepFrequencyValue = 0.0;}



	FrequencyOfSwingArm = Double.parseDouble(df.format( 60*(maxAmplitudeFreuqOfFTData2+maxAmplitudeFreuqOfFTData5)));
	if((maxFFTdataJavaProChannel[2]<8.0)||(maxFFTdataJavaProChannel[5]<8.0)){FrequencyOfSwingArm = 0.0;}
	
	if(balanceAwareness<60){stepFrequencyValue = 0.0; FrequencyOfSwingArm = 0.0;}

	StepLengthValue = Double.parseDouble(df.format( StepLengthValue*stepFrequencyValue/90.0));

	}

    private static double findMaxByFor(double[] arr, double length, int channelNum) {
        double MaxItem = 0.0; // 最大值
	int cnt = 0;
        for (int item = ((int)length-1); item>= ((int)length/2); item--) {
            if (arr[item] > MaxItem) { // 当前值大于最大值，赋值为最大值
                MaxItem = arr[item];
		cnt = Math.abs(item - ((int)length-1));

            }
        }
	maxFFTdataJavaProChannel[channelNum] = MaxItem;
        return (double)cnt*10/length;
    }

    @JavascriptInterface
    public String testJSCall1(){
        Log.e("MainActivity: JSInterfaceTest","testJSCall1!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        return "This is a test";
    }

    @JavascriptInterface
    public void testJSCallFFT(double data[], int ChannelNum, double sampleLength){
    
        //double[] dataTest= {0,1,2,3,4,5,4,3,2,1,0,1,2,3,4,5,4,3,2,1,0,1,2,3,4,5,4,3,2,1,0,1,2,3,4,5,4,3,2,1,0,1,2,3,4,5,4,3,2,1,0,1,2,3,4,5,4,3,2,1,0,1,2,3};
		int N= (int)sampleLength;
		double[] x1 = new double[N];; double[] x = new double[N];

		//傅里叶变换计算
		Complex[] input = new Complex[N];//声明复数数组
		for (int i = 0; i <= N-1; i++) {
			input[i] = new Complex(data[i], 0);}//将实数数据转换为复数数据
		//Log.e("MainActivity: JSInterfaceTest","testJSCall dataTest FFT Test = testJAVAFFT11111111111111111111111");
		newFFT = new FFT();
		//Log.e("MainActivity: JSInterfaceTest","testJSCall dataTest FFT Test = testJAVAFFT222222222222222222222222");
		input = newFFT.getFFT(input, N);//傅里叶变换
		//Log.e("MainActivity: JSInterfaceTest","testJSCall dataTest FFT Test = testJAVAFFT33333333333333333333333333");
		x=Complex.toModArray(input);//计算傅里叶变换得到的复数数组的模值
		Log.e("MainActivity: JSInterfaceTest","testJSCall dataTest FFT Test Data = "+  Arrays.toString(data));
		for(int i=0;i<=(N-1);i++) {
		//的模值数组除以N再乘以2
		x1[i]=x[i]/N*2;
		}

	Log.e("MainActivity: JSInterfaceTest","testJSCall dataTest FFT Test Result= "+  Arrays.toString(x1));
        Log.e("MainActivity: JSInterfaceTest","testJSCall ChannelNum = "+  String.valueOf(ChannelNum));
        Log.e("MainActivity: JSInterfaceTest","testJSCall sampleLength = "+  String.valueOf(sampleLength));
        Log.e("MainActivity: JSInterfaceTest","testJSCall data[] = "+  Arrays.toString(data));

	if(ChannelNum == 0)
	{
		dataFFT0Length = (int)sampleLength;for(int i =1;i<=5;i++){x1[(int)sampleLength-i] = 0.0;}
		dataFFT0 = (ArrayList) Arrays.stream(x1).boxed().collect(Collectors.toList()); //x1[0] = 0.0;
		CenterGravityGrequencyJava[(int)ChannelNum] = CenterGravityGrequencyCalcFunction(x1, sampleLength);
		FrequencyVarianceJava[(int)ChannelNum] = FrequencyVarianceCalcFunction(x1, CenterGravityGrequencyJava[(int)ChannelNum], sampleLength);
		FrequencyStandardDeviationJava[(int)ChannelNum] = Math.sqrt(FrequencyVarianceJava[(int)ChannelNum]);
		MeanSquareFrequencyJava[(int)ChannelNum] = MeanSquareFrequencyCalcFunction(x1, sampleLength);
		RootMeanSquareFrequencyJava[(int)ChannelNum] =  Math.sqrt(MeanSquareFrequencyJava[(int)ChannelNum]);
		PowerSpectrumEntropyJava[ChannelNum] = PowerSpectrumCalcFunction(x1, sampleLength); 
		EnergyEntropyJava[ChannelNum] = EnergyEntropyCalcFunction(data, sampleLength);
		maxAmplitudeFreuqOfFTData0 = findMaxByFor(x1, sampleLength, ChannelNum);
	}
	if(ChannelNum == 1)
	{
		dataFFT1Length = (int)sampleLength;for(int i =1;i<=5;i++){x1[(int)sampleLength-i] = 0.0;}
		dataFFT1 = (ArrayList) Arrays.stream(x1).boxed().collect(Collectors.toList()); //x1[0] = 0.0;
		CenterGravityGrequencyJava[(int)ChannelNum] = CenterGravityGrequencyCalcFunction(x1, sampleLength);
		FrequencyVarianceJava[(int)ChannelNum] = FrequencyVarianceCalcFunction(x1, CenterGravityGrequencyJava[(int)ChannelNum], sampleLength);
		FrequencyStandardDeviationJava[(int)ChannelNum] = Math.sqrt(FrequencyVarianceJava[(int)ChannelNum]);
		MeanSquareFrequencyJava[(int)ChannelNum] = MeanSquareFrequencyCalcFunction(x1, sampleLength);
		RootMeanSquareFrequencyJava[(int)ChannelNum] =  Math.sqrt(MeanSquareFrequencyJava[(int)ChannelNum]);
		PowerSpectrumEntropyJava[ChannelNum] = PowerSpectrumCalcFunction(x1, sampleLength);
		EnergyEntropyJava[ChannelNum] = EnergyEntropyCalcFunction(data, sampleLength);
		maxAmplitudeFreuqOfFTData1 = findMaxByFor(x1, sampleLength, ChannelNum);
	}	if(ChannelNum == 2)
	{
		dataFFT2Length = (int)sampleLength;for(int i =1;i<=5;i++){x1[(int)sampleLength-i] = 0.0;}
		dataFFT2 = (ArrayList) Arrays.stream(x1).boxed().collect(Collectors.toList()); //x1[0] = 0.0;
		CenterGravityGrequencyJava[(int)ChannelNum] = CenterGravityGrequencyCalcFunction(x1, sampleLength);
		FrequencyVarianceJava[(int)ChannelNum] = FrequencyVarianceCalcFunction(x1, CenterGravityGrequencyJava[(int)ChannelNum], sampleLength);
		FrequencyStandardDeviationJava[(int)ChannelNum] = Math.sqrt(FrequencyVarianceJava[(int)ChannelNum]);
		MeanSquareFrequencyJava[(int)ChannelNum] = MeanSquareFrequencyCalcFunction(x1, sampleLength);
		RootMeanSquareFrequencyJava[(int)ChannelNum] =  Math.sqrt(MeanSquareFrequencyJava[(int)ChannelNum]);
		PowerSpectrumEntropyJava[ChannelNum] = PowerSpectrumCalcFunction(x1, sampleLength);
		EnergyEntropyJava[ChannelNum] = EnergyEntropyCalcFunction(data, sampleLength);
		maxAmplitudeFreuqOfFTData2 = findMaxByFor(x1, sampleLength, ChannelNum);
	}	if(ChannelNum == 3)
	{
		dataFFT3Length = (int)sampleLength;for(int i =1;i<=5;i++){x1[(int)sampleLength-i] = 0.0;}
		dataFFT3 = (ArrayList) Arrays.stream(x1).boxed().collect(Collectors.toList()); //x1[0] = 0.0;
		CenterGravityGrequencyJava[(int)ChannelNum] = CenterGravityGrequencyCalcFunction(x1, sampleLength);
		FrequencyVarianceJava[(int)ChannelNum] = FrequencyVarianceCalcFunction(x1, CenterGravityGrequencyJava[(int)ChannelNum], sampleLength);
		FrequencyStandardDeviationJava[(int)ChannelNum] = Math.sqrt(FrequencyVarianceJava[(int)ChannelNum]);
		MeanSquareFrequencyJava[(int)ChannelNum] = MeanSquareFrequencyCalcFunction(x1, sampleLength);
		RootMeanSquareFrequencyJava[(int)ChannelNum] =  Math.sqrt(MeanSquareFrequencyJava[(int)ChannelNum]);
		PowerSpectrumEntropyJava[ChannelNum] = PowerSpectrumCalcFunction(x1, sampleLength);
		EnergyEntropyJava[ChannelNum] = EnergyEntropyCalcFunction(data, sampleLength);
		maxAmplitudeFreuqOfFTData3 = findMaxByFor(x1, sampleLength, ChannelNum);
	}	if(ChannelNum == 4)
	{
		dataFFT4Length = (int)sampleLength;for(int i =1;i<=5;i++){x1[(int)sampleLength-i] = 0.0;}
		dataFFT4 = (ArrayList) Arrays.stream(x1).boxed().collect(Collectors.toList()); //x1[0] = 0.0;
		CenterGravityGrequencyJava[ChannelNum] = CenterGravityGrequencyCalcFunction(x1, sampleLength);
		FrequencyVarianceJava[ChannelNum] = FrequencyVarianceCalcFunction(x1, CenterGravityGrequencyJava[ChannelNum], sampleLength);
		FrequencyStandardDeviationJava[ChannelNum] = Math.sqrt(FrequencyVarianceJava[ChannelNum]);
		MeanSquareFrequencyJava[ChannelNum] = MeanSquareFrequencyCalcFunction(x1, sampleLength);
		RootMeanSquareFrequencyJava[ChannelNum] =  Math.sqrt(MeanSquareFrequencyJava[ChannelNum]);
		PowerSpectrumEntropyJava[ChannelNum] = PowerSpectrumCalcFunction(x1, sampleLength);
		EnergyEntropyJava[ChannelNum] = EnergyEntropyCalcFunction(data, sampleLength);
		maxAmplitudeFreuqOfFTData4 = findMaxByFor(x1, sampleLength, ChannelNum);
	}	if(ChannelNum == 5)
	{
		dataFFT5Length = (int)sampleLength;for(int i =1;i<=5;i++){x1[(int)sampleLength-i] = 0.0;}
		dataFFT5 = (ArrayList) Arrays.stream(x1).boxed().collect(Collectors.toList()); //x1[0] = 0.0;
		CenterGravityGrequencyJava[ChannelNum] = CenterGravityGrequencyCalcFunction(x1, sampleLength);
		FrequencyVarianceJava[ChannelNum] = FrequencyVarianceCalcFunction(x1, CenterGravityGrequencyJava[ChannelNum], sampleLength);
		FrequencyStandardDeviationJava[ChannelNum] = Math.sqrt(FrequencyVarianceJava[ChannelNum]);
		MeanSquareFrequencyJava[ChannelNum] = MeanSquareFrequencyCalcFunction(x1, sampleLength);
		RootMeanSquareFrequencyJava[ChannelNum] =  Math.sqrt(MeanSquareFrequencyJava[ChannelNum]);
		PowerSpectrumEntropyJava[ChannelNum] = PowerSpectrumCalcFunction(x1, sampleLength);
		EnergyEntropyJava[ChannelNum] = EnergyEntropyCalcFunction(data, sampleLength);
		maxAmplitudeFreuqOfFTData5 = findMaxByFor(x1, sampleLength, ChannelNum);
	}	if(ChannelNum == 6)
	{
		dataFFT6Length = (int)sampleLength;for(int i =1;i<=5;i++){x1[(int)sampleLength-i] = 0.0;}
		dataFFT6 = (ArrayList) Arrays.stream(x1).boxed().collect(Collectors.toList()); //x1[0] = 0.0;
		CenterGravityGrequencyJava[ChannelNum] = CenterGravityGrequencyCalcFunction(x1, sampleLength);
		FrequencyVarianceJava[ChannelNum] = FrequencyVarianceCalcFunction(x1, CenterGravityGrequencyJava[ChannelNum], sampleLength);
		FrequencyStandardDeviationJava[ChannelNum] = Math.sqrt(FrequencyVarianceJava[ChannelNum]);
		MeanSquareFrequencyJava[ChannelNum] = MeanSquareFrequencyCalcFunction(x1, sampleLength);
		RootMeanSquareFrequencyJava[ChannelNum] =  Math.sqrt(MeanSquareFrequencyJava[ChannelNum]);
		PowerSpectrumEntropyJava[ChannelNum] = PowerSpectrumCalcFunction(x1, sampleLength);
		EnergyEntropyJava[ChannelNum] = EnergyEntropyCalcFunction(data, sampleLength);
		maxAmplitudeFreuqOfFTData6 = findMaxByFor(x1, sampleLength, ChannelNum);
	}	if(ChannelNum == 7)
	{
		dataFFT7Length = (int)sampleLength;for(int i =1;i<=5;i++){x1[(int)sampleLength-i] = 0.0;}
		dataFFT7 = (ArrayList) Arrays.stream(x1).boxed().collect(Collectors.toList()); //x1[0] = 0.0;
		CenterGravityGrequencyJava[ChannelNum] = CenterGravityGrequencyCalcFunction(x1, sampleLength);
		FrequencyVarianceJava[ChannelNum] = FrequencyVarianceCalcFunction(x1, CenterGravityGrequencyJava[ChannelNum], sampleLength);
		FrequencyStandardDeviationJava[ChannelNum] = Math.sqrt(FrequencyVarianceJava[ChannelNum]);
		MeanSquareFrequencyJava[ChannelNum] = MeanSquareFrequencyCalcFunction(x1, sampleLength);
		RootMeanSquareFrequencyJava[ChannelNum] =  Math.sqrt(MeanSquareFrequencyJava[ChannelNum]);
		PowerSpectrumEntropyJava[ChannelNum] = PowerSpectrumCalcFunction(x1, sampleLength);
		EnergyEntropyJava[ChannelNum] = EnergyEntropyCalcFunction(data, sampleLength);
		maxAmplitudeFreuqOfFTData7 = findMaxByFor(x1, sampleLength, ChannelNum);
	}	if(ChannelNum == 8)
	{
		dataFFT8Length = (int)sampleLength;for(int i =1;i<=5;i++){x1[(int)sampleLength-i] = 0.0;}
		dataFFT8 = (ArrayList) Arrays.stream(x1).boxed().collect(Collectors.toList()); //x1[0] = 0.0;
		CenterGravityGrequencyJava[ChannelNum] = CenterGravityGrequencyCalcFunction(x1, sampleLength);
		FrequencyVarianceJava[ChannelNum] = FrequencyVarianceCalcFunction(x1, CenterGravityGrequencyJava[ChannelNum], sampleLength);
		FrequencyStandardDeviationJava[ChannelNum] = Math.sqrt(FrequencyVarianceJava[ChannelNum]);
		MeanSquareFrequencyJava[ChannelNum] = MeanSquareFrequencyCalcFunction(x1, sampleLength);
		RootMeanSquareFrequencyJava[ChannelNum] =  Math.sqrt(MeanSquareFrequencyJava[ChannelNum]);
		PowerSpectrumEntropyJava[ChannelNum] = PowerSpectrumCalcFunction(x1, sampleLength);
		EnergyEntropyJava[ChannelNum] = EnergyEntropyCalcFunction(data, sampleLength);
		maxAmplitudeFreuqOfFTData8 = findMaxByFor(x1, sampleLength, ChannelNum);
	}	if(ChannelNum == 9)
	{
		dataFFT9Length = (int)sampleLength;for(int i =1;i<=5;i++){x1[(int)sampleLength-i] = 0.0;}
		dataFFT9 = (ArrayList) Arrays.stream(x1).boxed().collect(Collectors.toList()); //x1[0] = 0.0;
		CenterGravityGrequencyJava[ChannelNum] = CenterGravityGrequencyCalcFunction(x1, sampleLength);
		FrequencyVarianceJava[ChannelNum] = FrequencyVarianceCalcFunction(x1, CenterGravityGrequencyJava[ChannelNum], sampleLength);
		FrequencyStandardDeviationJava[ChannelNum] = Math.sqrt(FrequencyVarianceJava[ChannelNum]);
		MeanSquareFrequencyJava[ChannelNum] = MeanSquareFrequencyCalcFunction(x1, sampleLength);
		RootMeanSquareFrequencyJava[ChannelNum] =  Math.sqrt(MeanSquareFrequencyJava[ChannelNum]);
		PowerSpectrumEntropyJava[ChannelNum] = PowerSpectrumCalcFunction(x1, sampleLength);
		EnergyEntropyJava[ChannelNum] = EnergyEntropyCalcFunction(data, sampleLength);
		maxAmplitudeFreuqOfFTData9 = findMaxByFor(x1, sampleLength, ChannelNum);
	}	if(ChannelNum == 10)
	{
		dataFFT10Length = (int)sampleLength;for(int i =1;i<=5;i++){x1[(int)sampleLength-i] = 0.0;}
		dataFFT10 = (ArrayList) Arrays.stream(x1).boxed().collect(Collectors.toList()); //x1[0] = 0.0;
		CenterGravityGrequencyJava[ChannelNum] = CenterGravityGrequencyCalcFunction(x1, sampleLength);
		FrequencyVarianceJava[ChannelNum] = FrequencyVarianceCalcFunction(x1, CenterGravityGrequencyJava[ChannelNum], sampleLength);
		FrequencyStandardDeviationJava[ChannelNum] = Math.sqrt(FrequencyVarianceJava[ChannelNum]);
		MeanSquareFrequencyJava[ChannelNum] = MeanSquareFrequencyCalcFunction(x1, sampleLength);
		RootMeanSquareFrequencyJava[ChannelNum] =  Math.sqrt(MeanSquareFrequencyJava[ChannelNum]);
		PowerSpectrumEntropyJava[ChannelNum] = PowerSpectrumCalcFunction(x1, sampleLength);
		EnergyEntropyJava[ChannelNum] = EnergyEntropyCalcFunction(data, sampleLength);
		maxAmplitudeFreuqOfFTData10 = findMaxByFor(x1, sampleLength, ChannelNum);
	}	if(ChannelNum == 11)
	{
		dataFFT11Length = (int)sampleLength;for(int i =1;i<=5;i++){x1[(int)sampleLength-i] = 0.0;}
		dataFFT11 = (ArrayList) Arrays.stream(x1).boxed().collect(Collectors.toList()); //x1[0] = 0.0;
		CenterGravityGrequencyJava[ChannelNum] = CenterGravityGrequencyCalcFunction(x1, sampleLength);
		FrequencyVarianceJava[ChannelNum] = FrequencyVarianceCalcFunction(x1, CenterGravityGrequencyJava[ChannelNum], sampleLength);
		FrequencyStandardDeviationJava[ChannelNum] = Math.sqrt(FrequencyVarianceJava[ChannelNum]);
		MeanSquareFrequencyJava[ChannelNum] = MeanSquareFrequencyCalcFunction(x1, sampleLength);
		RootMeanSquareFrequencyJava[ChannelNum] =  Math.sqrt(MeanSquareFrequencyJava[ChannelNum]);
		PowerSpectrumEntropyJava[ChannelNum] = PowerSpectrumCalcFunction(x1, sampleLength);
		EnergyEntropyJava[ChannelNum] = EnergyEntropyCalcFunction(data, sampleLength);
		maxAmplitudeFreuqOfFTData11 = findMaxByFor(x1, sampleLength, ChannelNum);
	}	if(ChannelNum == 12)
	{
		dataFFT12Length = (int)sampleLength;for(int i =1;i<=5;i++){x1[(int)sampleLength-i] = 0.0;}
		dataFFT12 = (ArrayList) Arrays.stream(x1).boxed().collect(Collectors.toList()); //x1[0] = 0.0;
		CenterGravityGrequencyJava[ChannelNum] = CenterGravityGrequencyCalcFunction(x1, sampleLength);
		FrequencyVarianceJava[ChannelNum] = FrequencyVarianceCalcFunction(x1, CenterGravityGrequencyJava[ChannelNum], sampleLength);
		FrequencyStandardDeviationJava[ChannelNum] = Math.sqrt(FrequencyVarianceJava[ChannelNum]);
		MeanSquareFrequencyJava[ChannelNum] = MeanSquareFrequencyCalcFunction(x1, sampleLength);
		RootMeanSquareFrequencyJava[ChannelNum] =  Math.sqrt(MeanSquareFrequencyJava[ChannelNum]);
		PowerSpectrumEntropyJava[ChannelNum] = PowerSpectrumCalcFunction(x1, sampleLength);
		EnergyEntropyJava[ChannelNum] = EnergyEntropyCalcFunction(data, sampleLength);
		maxAmplitudeFreuqOfFTData12 = findMaxByFor(x1, sampleLength, ChannelNum);
	}	if(ChannelNum == 13)
	{
		dataFFT13Length = (int)sampleLength;for(int i =1;i<=5;i++){x1[(int)sampleLength-i] = 0.0;}
		dataFFT13 = (ArrayList) Arrays.stream(x1).boxed().collect(Collectors.toList()); //x1[0] = 0.0;
		CenterGravityGrequencyJava[ChannelNum] = CenterGravityGrequencyCalcFunction(x1, sampleLength);
		FrequencyVarianceJava[ChannelNum] = FrequencyVarianceCalcFunction(x1, CenterGravityGrequencyJava[ChannelNum], sampleLength);
		FrequencyStandardDeviationJava[ChannelNum] = Math.sqrt(FrequencyVarianceJava[ChannelNum]);
		MeanSquareFrequencyJava[ChannelNum] = MeanSquareFrequencyCalcFunction(x1, sampleLength);
		RootMeanSquareFrequencyJava[ChannelNum] =  Math.sqrt(MeanSquareFrequencyJava[ChannelNum]);
		PowerSpectrumEntropyJava[ChannelNum] = PowerSpectrumCalcFunction(x1, sampleLength);
		EnergyEntropyJava[ChannelNum] = EnergyEntropyCalcFunction(data, sampleLength);
		maxAmplitudeFreuqOfFTData13 = findMaxByFor(x1, sampleLength, ChannelNum);
	}	if(ChannelNum == 14)
	{
		dataFFT14Length = (int)sampleLength;for(int i =1;i<=5;i++){x1[(int)sampleLength-i] = 0.0;}
		dataFFT14 = (ArrayList) Arrays.stream(x1).boxed().collect(Collectors.toList()); //x1[0] = 0.0;
		CenterGravityGrequencyJava[(int)ChannelNum] = CenterGravityGrequencyCalcFunction(x1, sampleLength);
		FrequencyVarianceJava[(int)ChannelNum] = FrequencyVarianceCalcFunction(x1, CenterGravityGrequencyJava[(int)ChannelNum], sampleLength);
		FrequencyStandardDeviationJava[(int)ChannelNum] = Math.sqrt(FrequencyVarianceJava[(int)ChannelNum]);
		MeanSquareFrequencyJava[(int)ChannelNum] = MeanSquareFrequencyCalcFunction(x1, sampleLength);
		RootMeanSquareFrequencyJava[(int)ChannelNum] =  Math.sqrt(MeanSquareFrequencyJava[(int)ChannelNum]);
		PowerSpectrumEntropyJava[ChannelNum] = PowerSpectrumCalcFunction(x1, sampleLength);
		EnergyEntropyJava[ChannelNum] = EnergyEntropyCalcFunction(data, sampleLength);
		maxAmplitudeFreuqOfFTData14 = findMaxByFor(x1, sampleLength, ChannelNum);
	}
	
    }


		public double CenterGravityGrequencyCalcFunction(double data[], double sampleLength) {
			double[] f = new double[(int)sampleLength];
			double CenterGravityGrequencyCalcResult ;
			double sum1 = 0.0, sum2 = 0.0;
			for(int i = 0; i< (int)sampleLength; i++) {
				f[i] = 10*(i+1)/((int)sampleLength);
			}
			for(int i = 0; i< (int)sampleLength/2; i++) {
				sum1 = sum1 + data[i];//(int)sampleLength/2 + 
			}
			for(int i = 0; i< (int)sampleLength/2; i++) {
				sum2 = sum2 + data[i]*f[i];//(int)sampleLength/2 + 
			}
			CenterGravityGrequencyCalcResult = sum2/sum1;
			return CenterGravityGrequencyCalcResult;
	
		}

		public double FrequencyVarianceCalcFunction(double data[], double CenterGravityGrequency, double sampleLength) {
			double[] f = new double[(int)sampleLength];			
			//double[] CenterGravityGrequencyCalc = new double[(int)sampleLength/2];
			double FrequencyVariance ;
			double sum1 = 0.0, sum2 = 0.0;
			for(int i = 0; i< (int)sampleLength; i++) {
				f[i] = 10*(i+1)/((int)sampleLength);
			}
			for(int i = 0; i< (int)sampleLength; i++) {
				f[i] = (f[i]-CenterGravityGrequency)*(f[i]-CenterGravityGrequency);
			}
			for(int i = 0; i< (int)sampleLength/2; i++) {
				sum1 = sum1 + data[i];//(int)sampleLength/2 + 
			}
			for(int i = 0; i< (int)sampleLength/2; i++) {
				sum2 = sum2 + data[i]*f[i];//(int)sampleLength/2 + 
			}
			FrequencyVariance = sum2/sum1;
			return FrequencyVariance;
	
		}
		public double MeanSquareFrequencyCalcFunction(double data[], double sampleLength) {
			double[] f = new double[(int)sampleLength];			
			//double[] CenterGravityGrequencyCalc = new double[(int)sampleLength/2];
			double FrequencyVariance ;
			double sum1 = 0.0, sum2 = 0.0;
			for(int i = 0; i< (int)sampleLength; i++) {
				f[i] = 10*(i+1)/((int)sampleLength);
			}
			for(int i = 0; i< (int)sampleLength; i++) {
				f[i] = f[i]*f[i];
			}
			for(int i = 0; i< (int)sampleLength/2; i++) {
				sum1 = sum1 + data[i];//(int)sampleLength/2 + 
			}
			for(int i = 0; i< (int)sampleLength/2; i++) {
				sum2 = sum2 + data[i]*f[i];//(int)sampleLength/2 + 
			}
			FrequencyVariance = sum2/sum1;
			return FrequencyVariance;
	
		}


		public double PowerSpectrumCalcFunction(double data[], double sampleLength) {
			double[] PowerSpectrum = new double[(int)sampleLength];	
			double[] PowerSpectrumPercent = new double[(int)sampleLength];	
			double[] PowerSpectrumPercentLog10 = new double[(int)sampleLength];			
			//double[] CenterGravityGrequencyCalc = new double[(int)sampleLength/2];
			double PowerSpectrumCalc = 0.0; 
			double sum1 = 0.0, sum2 = 0.0;
			for(int i = 0; i< (int)sampleLength; i++) {
				PowerSpectrum[i] = data[i]*data[i]/((int)sampleLength);
			}
			Log.e("MainActivity: JSInterfaceTest","testJSCall PowerSpectrumCalcFunction data[] = "+  Arrays.toString(PowerSpectrum));

			for(int i = 0; i< (int)sampleLength/2; i++) {
				sum1 = sum1 + PowerSpectrum[(int)sampleLength/2 + i];
			}

			for(int i = 0; i< (int)sampleLength/2; i++) {
				PowerSpectrumPercent[(int)sampleLength/2 + i] = PowerSpectrum[(int)sampleLength/2 + i]/sum1;
			}
			for(int i = 0; i< (int)sampleLength/2; i++) {
				PowerSpectrumPercentLog10[(int)sampleLength/2 + i] = (-1)*Math.log(PowerSpectrumPercent[(int)sampleLength/2 + i]);
			}
			for(int i = 0; i< (int)sampleLength/2; i++) {
				sum2 = sum2 + PowerSpectrumPercent[(int)sampleLength/2 + i]*PowerSpectrumPercentLog10[(int)sampleLength/2 + i];
			}
			Log.e("MainActivity: JSInterfaceTest","testJSCall PowerSpectrumCalcFunction sum2  = "+  String.valueOf(sum2));
			//if(sum2 == NaN)
			return sum2;
	
		}

		public double EnergyEntropyCalcFunction(double data[], double sampleLength) {
			double[] EnergyEntropy = new double[(int)sampleLength];	
			double[] EnergyEntropyPercent = new double[(int)sampleLength];	
			double[] EnergyEntropyPercentLog10 = new double[(int)sampleLength];			
			//double[] CenterGravityGrequencyCalc = new double[(int)sampleLength/2];
			double EnergyEntropyCalc = 0.0; 
			double sum1 = 0.0, sum2 = 0.0;
			for(int i = 0; i< (int)sampleLength; i++) {
				EnergyEntropy[i] = data[i]*data[i];
			}
			Log.e("MainActivity: JSInterfaceTest","testJSCall EnergyEntropyCalcFunction data[] = "+  Arrays.toString(EnergyEntropy));

			for(int i = 0; i< (int)sampleLength/2; i++) {
				sum1 = sum1 + EnergyEntropy[(int)sampleLength/2 + i];
			}

			for(int i = 0; i< (int)sampleLength/2; i++) {
				EnergyEntropyPercent[(int)sampleLength/2 + i] = EnergyEntropy[(int)sampleLength/2 + i]/sum1;
			}
			for(int i = 0; i< (int)sampleLength/2; i++) {
				EnergyEntropyPercentLog10[(int)sampleLength/2 + i] = (-1)*Math.log(EnergyEntropyPercent[(int)sampleLength/2 + i]);
			}
			for(int i = 0; i< (int)sampleLength/2; i++) {
				sum2 = sum2 + EnergyEntropyPercent[(int)sampleLength/2 + i]*EnergyEntropyPercentLog10[(int)sampleLength/2 + i];
			}
			Log.e("MainActivity: JSInterfaceTest","testJSCall EnergyEntropyCalcFunction sum2  = "+  String.valueOf(sum2));
			//if(sum2 == NaN)
			return sum2*sum2*sum2;
	
		}




		public class FFT{
			public  Complex[] getFFT(Complex[] input, int N) {
			if ((N / 2) % 2 == 0) {
				Complex[] even = new Complex[N / 2];// 偶数
				Complex[] odd = new Complex[N / 2];// 奇数
				for (int i = 0; i < N / 2; i++) {
					even[i] = input[2 * i];
					odd[i] = input[2 * i + 1];
				}
				even = getFFT(even, N / 2);
				odd = getFFT(odd, N / 2);
				for (int i = 0; i < N / 2; i++) {
					Complex[] res = Complex.butterfly(even[i], odd[i], Complex.GetW(i, N));
					input[i] = res[0];
					input[i + N / 2] = res[1];
				}
				return input;
			} else {// 两点DFT,直接进行碟形运算
				Complex[] res = Complex.butterfly(input[0], input[1], Complex.GetW(0, N));
				return res;
			}
			}
		}
}
