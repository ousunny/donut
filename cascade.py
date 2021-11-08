import cv2 as cv


class Cascade:
    def __init__(self, cascade_path):
        self.cascade = cv.CascadeClassifier(cascade_path)

    def find(self, screenshot):
        return self.cascade.detectMultiScale(screenshot)
