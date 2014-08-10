#!/bin/bash
ls -1 dotlan/*.svg | xargs -L 1 ./dotlan-json.py 
