import cv2 as cv
from random import randint


class Vision:
    def get_click_points(self, rectangles, random=False):
        points = []

        for (x, y, w, h) in rectangles:
            if random:
                click_x = randint(x, x + w)
                click_y = randint(y, y + h)
            else:
                click_x = x + int(w / 2)
                click_y = y + int(h / 2)

            points.append((click_x, click_y))

        return points

    def draw_rectangles(self, haystack_img, rectangles):
        line_color = (0, 255, 0)
        line_type = cv.LINE_4

        for (x, y, w, h) in rectangles:
            top_left = (x, y)
            bottom_right = (x + w, y + h)
            cv.rectangle(
                haystack_img,
                top_left,
                bottom_right,
                color=line_color,
                lineType=line_type,
            )

        return haystack_img

    def draw_targets(self, haystack_img, points):
        marker_color = (255, 0, 255)
        marker_type = cv.MARKER_CROSS

        for (center_x, center_y) in points:
            cv.drawMarker(
                haystack_img,
                (center_x, center_y),
                color=marker_color,
                markerType=marker_type,
            )

        return haystack_img
