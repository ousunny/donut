from typing import Match
import cv2 as cv
from time import time
from windowgrabber import WindowGrabber
from detector import Detector
from vision import Vision
from bot import BotState, Bot
from matchtemplate import MatchTemplate
from cascade import Cascade

import routines.routine_opencloseskillwindow as routine_opencloseskillwindow


window_grabber = WindowGrabber("Hero Siege", desktop_capture=False, titlebar=True)
detector = Detector()
vision = Vision()
bot = Bot(
    (window_grabber.offset_x, window_grabber.offset_y),
    (window_grabber.w, window_grabber.h),
)

# cascade_campfire = Cascade("images/campfire/cascade/cascade.xml")

routine = routine_opencloseskillwindow.get()

window_grabber.start()
detector.start()
bot.start()

bot.update_routine(routine)

loop_time = time()
while True:
    if window_grabber.screenshot is None:
        continue

    detector.update(window_grabber.screenshot)
    detector.update_target(routine.current_action.detection_item)

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
            "images/campfire/positive/{}.jpg".format(loop_time),
            window_grabber.screenshot,
        )
    elif key == ord("d"):
        cv.imwrite(
            "images/campfire/negative/{}.jpg".format(loop_time),
            window_grabber.screenshot,
        )
