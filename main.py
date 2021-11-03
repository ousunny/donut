import cv2 as cv
from time import time
from windowgrabber import WindowGrabber
from detector import Detector
from vision import Vision
from matchtemplate import MatchTemplate

window_grabber = WindowGrabber(
    "Diablo II: Resurrected", desktop_capture=True, titlebar=False
)
detector = Detector()
vision = Vision()

red_portal_template = MatchTemplate(
    needle_img_path="images/needle/red_portal.png", threshold=0.5
)

window_grabber.start()
detector.start()

loop_time = time()
while True:
    if window_grabber.screenshot is None:
        continue

    detector.update(window_grabber.screenshot)
    detector.updateTarget(red_portal_template)

    output_img = vision.draw_rectangles(window_grabber.screenshot, detector.rectangles)

    # img_resized = cv.resize(img, (960, 540))
    cv.imshow("Donut", output_img)

    print("FPS: {}".format(int(1 / (time() - loop_time))))
    loop_time = time()

    key = cv.waitKey(1)
    if key == ord("q"):
        window_grabber.stop()
        detector.stop()
        cv.destroyAllWindows()
        break
