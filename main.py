import cv2 as cv
from time import time
from windowgrabber import WindowGrabber
from detector import Detector
from vision import Vision

detector = Detector()
vision = Vision()

windowGrabber = WindowGrabber("Diablo II: Resurrected", True)
loop_time = time()
while True:
    img = windowGrabber.get()

    rectangles = detector.matchTemplate(
        haystack_img=img, needle_img_path="images/needle/red_portal.png", threshold=0.8
    )
    output_img = vision.draw_rectangles(img, rectangles)

    # img_resized = cv.resize(img, (960, 540))
    cv.imshow("Donut", output_img)

    print("FPS: {}".format(int(1 / (time() - loop_time))))
    loop_time = time()

    key = cv.waitKey(1)
    if key == ord("q"):
        cv.destroyAllWindows()
        break
