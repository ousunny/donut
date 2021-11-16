import cv2 as cv
import pyautogui
from time import sleep, time
from threading import Thread, Lock
from math import sqrt


class BotState:
    INITIALIZING = 0
    SEARCHING = 1
    MOVING = 2
    ACTION = 3


class Bot:
    INITIALIZING_TIME_SECONDS = 5
    IGNORE_RADIUS = 150
    ACTION_TIME_SECONDS = 3
    MOVEMENT_STOPPED_THRESHOLD = 0.95

    state = None
    targets = []
    routine = None

    def __init__(self, window_offset, window_size):
        self.lock = Lock()

        self.window_w = window_size[0]
        self.window_h = window_size[1]
        self.window_offset = window_offset

        self.screenshot = None
        self.movement_screenshot = None

        self.state = BotState.INITIALIZING
        self.timestamp = time()

    def mouse_over_target(self, target_position):
        targets = self.order_targets_by_distance(self.targets)
        screen_x, screen_y = self.get_screen_position(targets[0])
        pyautogui.moveTo(x=screen_x, y=screen_y, duration=2)
        pyautogui.click()

    def click_targets(self):
        targets = self.order_targets_by_distance(self.targets)

        found_target = False
        target_index = 0
        while not found_target and target_index < len(targets):
            if self.stopped:
                break

            target_position = targets[target_index]
            screen_x, screen_y = self.get_screen_position(target_position)
            pyautogui.moveTo(screen_x, screen_y, duration=1)
            sleep(0.5)
            pyautogui.mouseDown(
                button=pyautogui.PRIMARY,
            )
            pyautogui.mouseUp(button=pyautogui.PRIMARY)

            target_index += 1

        return found_target

    def has_targets(self):
        return len(self.targets) > 0

    def has_stopped_moving(self):
        if self.movement_screenshot is None:
            self.movement_screenshot = self.screenshot.copy()
            return False

        result = cv.matchTemplate(
            self.screenshot, self.movement_screenshot, cv.TM_CCOEFF_NORMED
        )
        similarity = result[0][0]

        if similarity > self.MOVEMENT_STOPPED_THRESHOLD:
            return True

        self.movement_screenshot = self.screenshot.copy()

        return False

    def order_targets_by_distance(self, targets):
        character_position = (self.window_w / 2, self.window_h / 2)

        def distance(position):
            return sqrt(
                (position[0] - character_position[0]) ** 2
                + (position[1] - character_position[1]) ** 2
            )

        targets.sort(key=distance)

        targets = [
            target for target in targets if distance(target) > self.IGNORE_RADIUS
        ]

        return targets

    def get_screen_position(self, position):
        return (
            position[0] + self.window_offset[0],
            position[1] + self.window_offset[1],
        )

    def update_targets(self, targets):
        self.lock.acquire()
        self.targets = targets
        self.lock.release()

    def update_screenshot(self, screenshot):
        self.lock.acquire()
        self.screenshot = screenshot
        self.lock.release()

    def update_routine(self, routine):
        self.lock.acquire()
        self.routine = routine
        self.lock.release()

    def start(self):
        self.stopped = False
        t = Thread(target=self.run)
        t.start()

    def stop(self):
        self.stopped = True

    def run(self):
        while not self.stopped:
            if self.state == BotState.INITIALIZING:
                if time() > self.timestamp + self.INITIALIZING_TIME_SECONDS:
                    self.lock.acquire()

                    self.state = BotState.SEARCHING

                    self.lock.release()
            elif self.state == BotState.SEARCHING:
                has_targets = self.has_targets()

                if has_targets:
                    # self.mouse_over_target(self.targets[0])

                    self.lock.acquire()

                    self.state = BotState.MOVING

                    self.lock.release()
            elif self.state == BotState.MOVING:
                if not self.has_stopped_moving():
                    sleep(0.5)
                else:
                    self.lock.acquire()

                    self.timestamp = time()

                    self.state = BotState.ACTION

                    self.lock.release()

                    self.click_targets()
            elif self.state == BotState.ACTION:
                if time() > self.timestamp + self.routine.current_action.wait_time:
                    self.lock.acquire()

                    self.targets = []
                    self.routine.next_action()
                    print(self.routine.current_action.name)

                    self.state = BotState.SEARCHING

                    self.lock.release()
