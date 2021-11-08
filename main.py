import cv2 as cv
from time import time
from windowgrabber import WindowGrabber
from detector import Detector
from vision import Vision
from bot import BotState, Bot
from matchtemplate import MatchTemplate
from cascade import Cascade

window_grabber = WindowGrabber(
    "Diablo II: Resurrected", desktop_capture=True, titlebar=True
)
detector = Detector()
vision = Vision()
bot = Bot(
    (window_grabber.offset_x, window_grabber.offset_y),
    (window_grabber.w, window_grabber.h),
)

red_portal_template = MatchTemplate(
    needle_img_path="images/needle/red_portal.png", threshold=0.9
)

cascade_red_portal = Cascade("images/red_portal/cascade/cascade.xml")

window_grabber.start()
detector.start()
bot.start()

loop_time = time()
while True:
    if window_grabber.screenshot is None:
        continue

    detector.update(window_grabber.screenshot)
    detector.updateTarget(cascade_red_portal)

    if bot.state == BotState.INITIALIZING:
        targets = vision.get_click_points(detector.rectangles, random=True)
        bot.update_targets(targets)
        bot.update_screenshot(window_grabber.screenshot)
    elif bot.state == BotState.SEARCHING:
        targets = vision.get_click_points(detector.rectangles, random=True)
        bot.update_targets(targets)
        bot.update_screenshot(window_grabber.screenshot)
    elif bot.state == BotState.MOVING:
        bot.update_screenshot(window_grabber.screenshot)
    elif bot.state == BotState.ACTION:
        pass

    output_img = vision.draw_rectangles(window_grabber.screenshot, detector.rectangles)

    # img_resized = cv.resize(img, (960, 540))
    cv.imshow("Donut", output_img)

    # print("FPS: {}".format(int(1 / (time() - loop_time))))
    loop_time = time()

    key = cv.waitKey(1)
    if key == ord("q"):
        window_grabber.stop()
        detector.stop()
        bot.stop()
        cv.destroyAllWindows()
        break
    elif key == ord("f"):
        cv.imwrite(
            "images/red_portal/positive/{}.jpg".format(loop_time),
            window_grabber.screenshot,
        )
    elif key == ord("d"):
        cv.imwrite(
            "images/red_portal/negative/{}.jpg".format(loop_time),
            window_grabber.screenshot,
        )
