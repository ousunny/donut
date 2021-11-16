from routineaction import ActionType, RoutineAction


class Routine:
    current_action = None

    def __init__(self):
        self.actions = []
        self.action_index = 0

    def add_action(self, routine_action):
        self.actions.append(routine_action)
        if len(self.actions) == 1:
            self.current_action = self.actions[0]

    def next_action(self):
        if self.action_index >= len(self.actions) - 1:
            self.action_index = 0
        else:
            self.action_index += 1
        self.current_action = self.actions[self.action_index]
