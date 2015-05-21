#include <jni.h>
#include <stdio.h>
#include <stdlib.h>
#include <android/log.h>
#include <opencv2/opencv.hpp>
using namespace cv;

#define LOG_TAG "123"
//__android_log_print(ANDROID_LOG_INFO, LOG_TAG, "input: %d ", m.rows);

extern "C" {
JNIEXPORT jboolean JNICALL Java_com_yhd_gridcamera_manage_DealPictureThread_isIdle(
        JNIEnv* env, jobject obj, jstring bitmap);
JNIEXPORT jboolean JNICALL Java_com_yhd_gridcamera_manage_DealPictureThread_isIdle(
        JNIEnv* env, jobject obj, jstring bitmap) {
		const char* str = env->GetStringUTFChars(bitmap, false);
		Mat m = imread(str, 0);
		const float standardWhite = 0.98;
		const float standardBlack = 0.02;
		float index = 0;
		adaptiveThreshold(m,m,255,CV_ADAPTIVE_THRESH_MEAN_C,CV_THRESH_BINARY, 3, 5);
		float allCount = m.rows*m.cols;
		for (int i = 0; i < m.rows; i++){
			uchar* data = m.ptr<uchar>(i);
			for (int j = 0; j < m.cols; j++){
				if(data[j] == 255){
					index++;
				}
			}
		}
		float output = index/allCount;
		if(output > standardWhite || output < standardBlack){
			return true;
		}else{
			return false;
		}
	}
}

