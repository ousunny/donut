import os
import argparse

parser = argparse.ArgumentParser(description="Generate negative description file")
parser.add_argument("--name", dest="name", action="store", required=True)

args = parser.parse_args()

NEGATIVE_PATH_LOCATION = "images/{}/negative/".format(args.name)

with open("images/{}/negative.txt".format(args.name), "w") as file:
    for filename in os.listdir(NEGATIVE_PATH_LOCATION):
        file.write("negative/" + filename + "\n")
