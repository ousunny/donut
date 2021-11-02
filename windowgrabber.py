import numpy as np
import win32gui, win32ui, win32con


class WindowGrabber:
    def __init__(self, window_name=None, desktop_capture=False):
        self.hwnd = win32gui.FindWindow(None, window_name)

        if not self.hwnd:
            raise Exception('"{}" window not found'.format(window_name))

        window_rect = win32gui.GetWindowRect(self.hwnd)

        self.w = window_rect[2] - window_rect[0]
        self.h = window_rect[3] - window_rect[1]

        border_pixels = 8
        titlebar_pixels = 30
        self.w = self.w - (border_pixels * 2)
        self.h = self.h - titlebar_pixels - border_pixels
        self.cropped_x = border_pixels
        self.cropped_y = titlebar_pixels

        if desktop_capture:
            self.cropped_x = window_rect[0] + border_pixels
            self.cropped_y = window_rect[1] + titlebar_pixels
            self.hwnd = win32gui.GetDesktopWindow()

        self.offset_x = window_rect[0] + self.cropped_x
        self.offset_y = window_rect[1] + self.cropped_y

    def get(self):
        w_dc = win32gui.GetWindowDC(self.hwnd)
        dc = win32ui.CreateDCFromHandle(w_dc)
        c_dc = dc.CreateCompatibleDC()
        bitmap = win32ui.CreateBitmap()
        bitmap.CreateCompatibleBitmap(dc, self.w, self.h)
        c_dc.SelectObject(bitmap)
        c_dc.BitBlt(
            (0, 0),
            (self.w, self.h),
            dc,
            (self.cropped_x, self.cropped_y),
            win32con.SRCCOPY,
        )

        bitmap_bits = bitmap.GetBitmapBits(True)
        img = np.fromstring(bitmap_bits, dtype="uint8")
        img.shape = (self.h, self.w, 4)

        dc.DeleteDC()
        c_dc.DeleteDC()
        win32gui.ReleaseDC(self.hwnd, w_dc)
        win32gui.DeleteObject(bitmap.GetHandle())

        img = img[..., :3]

        img = np.ascontiguousarray(img)

        return img
