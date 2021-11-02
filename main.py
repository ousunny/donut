import cv2 as cv
from time import time
from windowgrabber import WindowGrabber

windowGrabber = WindowGrabber("ELYON (64-bit, DX11)", True)
loop_time = time()
while True:
    img = windowGrabber.get()

    img_resized = cv.resize(img, (960, 540))
    cv.imshow("Donut", img_resized)

    print("FPS {}".format(1 / (time() - loop_time)))
    loop_time = time()

    key = cv.waitKey(1)
    if key == ord("q"):
        cv.destroyAllWindows()
        break
