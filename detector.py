import cv2 as cv
import numpy as np
from matchtemplate import MatchTemplate


class Detector:
    rectangles = []

    def __init__(self):
        pass

    def matchTemplate(
        self,
        haystack_img,
        needle_img_path,
        threshold=0.5,
        max_results=10,
        method=cv.TM_CCOEFF_NORMED,
    ):
        self.rectangles = MatchTemplate.find(
            haystack_img, needle_img_path, threshold, max_results, method
        )

        return self.rectangles
