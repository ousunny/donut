class ActionType:
    HOVER = 0
    CLICK = 1
    NO_ACTION = 2


class RoutineAction:
    type = None

    def __init__(self, detection_item, name, type, wait_time):
        self.detection_item = detection_item
        self.name = name
        self.type = type
        self.wait_time = wait_time
