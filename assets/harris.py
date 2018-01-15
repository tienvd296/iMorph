import cv2
import numpy as np
import os
import sys

img = cv2.imread(os.path.join("~",sys.argv[1]))
height, width, channels = img.shape
img = img[100:height-100,0:width]
img = cv2.resize(img,(width/4,height/4))
imgray = cv2.cvtColor(img,cv2.COLOR_BGR2GRAY)
imgray = np.float32(imgray)
dst = cv2.cornerHarris(imgray,2,3,0.04)

#result is dilated for marking the corners, not important
dst = cv2.dilate(dst,None)

# Threshold for an optimal value, it may vary depending on the image.
img[dst>0.01*dst.max()]=[0,0,255]

cv2.imshow('dst',img)
if cv2.waitKey(0) & 0xff == 27:
    cv2.destroyAllWindows()