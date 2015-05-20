LOCAL_PATH := $(call my-dir)    
include $(CLEAR_VARS)    
OPENCV_LIB_TYPE:=STATIC  
ifeq ("$(wildcard $(OPENCV_MK_PATH))","")    
#try to load OpenCV.mk from default install location    
include E:\OpenCV\android\OpenCV-2.4.9-android-sdk\sdk\native\jni\OpenCV.mk   
else    
include $(OPENCV_MK_PATH)    
endif    
LOCAL_MODULE    := DealPicture  
LOCAL_SRC_FILES := DealPicture.cpp    
include $(BUILD_SHARED_LIBRARY)