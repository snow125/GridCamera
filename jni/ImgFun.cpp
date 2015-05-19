#include <jni.h>
#include <stdio.h>
#include <stdlib.h>
#include <opencv2/opencv.hpp>
using namespace cv;
IplImage * change4channelTo3InIplImage(IplImage * src);

extern "C" {
JNIEXPORT jstring JNICALL Java_com_yhd_gridcamera_MainActivity_ImgFun(
        JNIEnv* env, jobject obj);
JNIEXPORT jstring JNICALL Java_com_yhd_gridcamera_MainActivity_ImgFun(
        JNIEnv* env, jobject obj) {

//    jint *cbuf;
//    cbuf = env->GetIntArrayElements(buf, false);
//    if (cbuf == NULL) {
//        return 0;
//    }
//
//    Mat myimg(h, w, CV_8UC4, (unsigned char*) cbuf);
//    IplImage image=IplImage(myimg);
//    IplImage* image3channel = change4channelTo3InIplImage(&image);
//
//    IplImage* pCannyImage=cvCreateImage(cvGetSize(image3channel),IPL_DEPTH_8U,1);
//
//    cvCanny(image3channel,pCannyImage,50,150,3);
//
//    int* outImage=new int[w*h];
//    for(int i=0;i<w*h;i++)
//    {
//        outImage[i]=(int)pCannyImage->imageData[i];
//    }
//
//    int size = w * h;
//    jintArray result = env->NewIntArray(size);
//    env->SetIntArrayRegion(result, 0, size, outImage);
//    env->ReleaseIntArrayElements(buf, cbuf, 0);
	return env->NewStringUTF("Hello From JNI!");
}
}

IplImage * change4channelTo3InIplImage(IplImage * src) {
    if (src->nChannels != 4) {
        return NULL;
    }

    IplImage * destImg = cvCreateImage(cvGetSize(src), IPL_DEPTH_8U, 3);
    for (int row = 0; row < src->height; row++) {
        for (int col = 0; col < src->width; col++) {
            CvScalar s = cvGet2D(src, row, col);
            cvSet2D(destImg, row, col, s);
        }
    }

    return destImg;
}
