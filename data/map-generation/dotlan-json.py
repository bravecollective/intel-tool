#!/usr/bin/python
import requests
import re
import operator
from lxml import etree
import sys
import time
import datetime
import MySQLdb
import json

from xml.dom import minidom

# ---------------------------------------------

if (len(sys.argv) != 2):
    print sys.argv[0] + " <filename>"
    sys.exit(1)

fin = sys.argv[1]
fout = fin + ".json"

print "Converting " + fin + " to " + fout + " ..."

db = MySQLdb.connect(host="localhost", user="root", passwd="root", db="toolkit_ruby")
cur = db.cursor()

# ---------------------------------------------

doc = minidom.parse(fin)  # parseString also exists

def hasStation(sysid):
    for node in doc.getElementsByTagName('symbol'):
	id = int(node.getAttribute('id')[3:])
	if (sysid != id):
	    continue

	rects = 0
	for n in node.childNodes:
	    if n.nodeName == 'rect':
		rects = rects + 1;
	return rects != 0
	#print "---- " + str(id) + "  " + str(rects)
    return false

def toRegion(sysid):
    for node in doc.getElementsByTagName('symbol'):
	id = int(node.getAttribute('id')[3:])
	if (sysid != id):
	    continue

	for n in node.getElementsByTagName('text'):
	    if (n.getAttribute('class') == 'er'):
		return n.firstChild.data
    return ''

systems = []
for node in doc.getElementsByTagName('use'):
    id = int(node.getAttribute('id')[3:])
    x = node.getAttribute('x')
    y = node.getAttribute('y')
    station = hasStation(id)
    region = toRegion(id)
    cur.execute("SELECT solarSystemName FROM toolkit_ruby.mapSolarSystems where solarSystemID=" + str(id))
    name = cur.fetchone()[0]
    systems.append({'id': id, 'name': name, 'x': x, 'y': y, 'hasStation': station, 'region': region})

conns = []
for node in doc.getElementsByTagName('line'):
    id = str(node.getAttribute('id'))
    sysA = int(id[2:10])
    sysB = int(id[11:19])
    type = node.getAttribute('class')
    x1 = node.getAttribute('x1')
    y1 = node.getAttribute('y1')
    x2 = node.getAttribute('x2')
    y2 = node.getAttribute('y2')
    conns.append({'a': sysA, 'b': sysB, 'type': type, 'x1': x1, 'y1': y1, 'x2': x2, 'y2': y2})

bridges = []
for node in doc.getElementsByTagName('path'):
    id = str(node.getAttribute('id'))
    sysA = int(id[2:10])
    sysB = int(id[11:19])
    path = str(node.getAttribute('d'))
    pathparts = re.split('[\ |,]', path)
    x1 = pathparts[1]
    y1 = pathparts[2]
    x2 = pathparts[4]
    y2 = pathparts[5]
    x3 = pathparts[6]
    y3 = pathparts[7]
    bridges.append({'a': sysA, 'b': sysB, 'x1': x1, 'y1': y1, 'x2': x2, 'y2': y2, 'x3': x3, 'y3': y3})

doc.unlink()

result = { 'map': { 'systems': systems, 'connections': conns, 'bridges': bridges } }

f = open(fout,'w')
json.dump(result, f)
f.close()
