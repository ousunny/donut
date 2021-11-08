import os

NEGATIVE_PATH_LOCATION = "images/red_portal/negative/"


def generate_negative_description_file():
    with open("negative.txt", "w") as file:
        for filename in os.listdir(NEGATIVE_PATH_LOCATION):
            file.write("negative/" + filename + "\n")
