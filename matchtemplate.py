import cv2 as cv
import numpy as np


class MatchTemplate:
    def find(
        haystick_img,
        needle_img_path,
        threshold=0.5,
        max_results=10,
        method=cv.TM_CCOEFF_NORMED,
    ):
        needle_img = cv.imread(needle_img_path, cv.IMREAD_UNCHANGED)

        needle_w = needle_img.shape[1]
        needle_h = needle_img.shape[0]

        result = cv.matchTemplate(haystick_img, needle_img, method)

        locations = np.where(result >= threshold)
        locations = list(zip(*locations[::-1]))

        if not locations:
            return np.array([], dtype=np.int32).reshape(0, 4)

        rectangles = []
        for location in locations:
            rectangle = [
                int(location[0]),
                int(location[1]),
                needle_w,
                needle_h,
            ]
            rectangles.append(rectangle)
            rectangles.append(rectangle)

        rectangles, weights = cv.groupRectangles(rectangles, groupThreshold=1, eps=0.5)

        if len(rectangles) > max_results:
            print("Too many results. Consider raising the threshold.")
            rectangles = rectangles[:max_results]

        return rectangles
