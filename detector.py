import cv2 as cv
import numpy as np
from threading import Thread, Lock
from matchtemplate import MatchTemplate


class Detector:
    rectangles = []
    screenshot = None
    current_target = None

    def __init__(self):
        self.lock = Lock()

    def update(self, screenshot):
        self.lock.acquire()
        self.screenshot = screenshot
        self.lock.release()

    def update_target(self, target):
        self.lock.acquire()
        self.current_target = target
        self.lock.release()

    def start(self):
        self.stopped = False
        t = Thread(target=self.run)
        t.start()

    def stop(self):
        self.stopped = True

    def run(self):
        while not self.stopped:
            if not self.screenshot is None:
                rectangles = self.current_target.find(self.screenshot)

                self.rectangles = rectangles
