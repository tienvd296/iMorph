
import cv2
import os
import sys
import numpy as np
from os.path import basename
from matplotlib import pyplot as plt



# RGB to Gray
def rgb2gray(image):
    gray_image = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    return gray_image
#END RGB to Gray

# Check image is gray
#def is_grey_scale(image):
#    im = Image.open(image).convert('RGB')
#    w,h = im.size
#    for i in range(w):
#        for j in range(h):
#            r,g,b = im.getpixel((i,j))
#            if r != g != b: return False
#    return True

# RGB to Binary

def fast_corner(img):
    height, width, channels = img.shape
    img = img[100:height-100,0:width]
    img = rgb2bin(img,"gaussian")
    fast = cv2.FastFeatureDetector()

    # find and draw the keypoints
    kp = fast.detect(img,None)
    img2 = cv2.drawKeypoints(img, kp, color=(255,0,0))
    return img2

def harris_detection(img):
    height, width, channels = img.shape
    #Remove unneccessary area
    img = img[100:height-100,0:width]

    imgray = rgb2gray(img)
    imgray = np.float32(imgray)
    dst = cv2.cornerHarris(imgray,2,3,0.04)

    #result is dilated for marking the corners, not important
    dst = cv2.dilate(dst,None)

    # Threshold for an optimal value, it may vary depending on the image.
    img[dst>0.1*dst.max()]=[0,0,255]
    return img

def shi_tomashi_corner_detection(img):
    height, width, channels = img.shape
    #Remove unneccessary area
    img = img[100:height-100,0:width]
    gray = rgb2bin(img,'mean')
   # gray = cv2.bitwise_not(gray)
    corners = cv2.goodFeaturesToTrack(gray,25,0.01,10)
    corners = np.int0(corners)

    for i in corners:
        x,y = i.ravel()
        cv2.circle(gray,(x,y),5,255,-1)
    return gray


def rgb2bin(image,option):
    #gray_image = image
    #if is_grey_scale(image) is True:
    #    gray_image = image
    #else:
    gray_image = rgb2gray(image)
    gray_image = cv2.GaussianBlur(gray_image,(7,7),1)
    result = gray_image
    if option is 'global':
        retval, result = cv2.threshold(gray_image, 12, 255, cv2.THRESH_BINARY)
    elif option is 'mean':
        result = cv2.adaptiveThreshold(gray_image, 255, cv2.ADAPTIVE_THRESH_MEAN_C, \
                                        cv2.THRESH_BINARY, 11, 2)
    elif option is 'gaussian':
        result = cv2.adaptiveThreshold(gray_image, 255, cv2.ADAPTIVE_THRESH_GAUSSIAN_C,\
                                        cv2.THRESH_BINARY, 11, 2)
    return result

#END RGB to Binary
def gray2bin(image,option):
    result = image
    if option is 'global':
        retval, result = cv2.threshold(image, 12, 255, cv2.THRESH_BINARY)
    elif option is 'mean':
        result = cv2.adaptiveThreshold(image, 255, cv2.ADAPTIVE_THRESH_MEAN_C, cv2.THRESH_BINARY, 11, 2)
    elif option is 'gaussian':
        result = cv2.adaptiveThreshold(image, 255, cv2.ADAPTIVE_THRESH_GAUSSIAN_C, cv2.THRESH_BINARY, 11, 2)
    return result

#Skeletonization
def findSkele(image):
    image = rgb2bin(image,'mean')
    size = np.size(image)
    skel = np.zeros(image.shape,np.uint8)
    element = cv2.getStructuringElement(cv2.MORPH_CROSS,(3,3))
    isFinished = False
    image_inv = cv2.bitwise_not(image)
    while (not isFinished) :
        eroded = cv2.erode(image_inv,element)
        temp = cv2.dilate(eroded,element)
        temp = cv2.subtract(image_inv,temp)
        skel = cv2.bitwise_or(skel,temp)
        image_inv = eroded.copy()
        
        #zeros = size -
        if cv2.countNonZero(image_inv) == 0:    
            isFinished = True
    contour,hier = cv2.findContours(skel,cv2.RETR_CCOMP,cv2.CHAIN_APPROX_SIMPLE)
    for cnt in contour:
        cv2.drawContours(skel,[cnt],0,255,-1)

    #Fill Holes: connect broken line
    kernel = np.ones((7,7), np.uint8)
    d_im = cv2.dilate(skel, kernel)
    result = cv2.erode(d_im, kernel)
    result = cv2.bitwise_not(result)
    return result
#END SKeleton

#Conner Detection


#Image Erode multiple
def imageErode(image, count):
    image = rgb2bin(image,'mean')
    image = cv2.bitwise_not(image)
    element  = cv2.getStructuringElement(cv2.MORPH_CROSS,(3,3))
    while(count >0 ):
        count-= 1
        image = cv2.erode(image,element)
        cv2.imshow('Eroding',image)
        cv2.waitKey(0)
    return image
#END Eroding

# Main
image = cv2.imread(os.path.join("~",sys.argv[1]))
filename = basename(sys.argv[1])
result = image
if image is None:
    print("Image reading failed")
else:
    if sys.argv[2].lower() == 'rgb2gray':
        result = rgb2gray(image)
    elif sys.argv[2].lower() == 'rgb2bin':
        option = None
        if len(sys.argv) < 4 :
            option = 'global'
        else:
            option = sys.argv[3]
            result = rgb2bin(image,option)
        
    elif sys.argv[2].lower() == 'skeleton':
        result = findSkele(image)
        
    elif sys.argv[2].lower() == 'erode':
        result = imageErode(image,10)
    elif sys.argv[2].lower() == 'harris':
        result = harris_detection(image)
    elif sys.argv[2].lower() == 'fast':
        result = fast_corner(image)
    elif sys.argv[2].lower() == 'shi_tomashi':
        result = shi_tomashi_corner_detection(image)
#cv2.imshow("Result",result)
#cv2.waitKey(0)
resultPath =os.path.join(os.path.dirname(os.path.abspath(sys.argv[1])),'temp_'+filename)
print(resultPath)
cv2.imwrite(resultPath,result)

