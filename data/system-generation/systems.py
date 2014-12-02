#!/usr/bin/python
import MySQLdb

# ---------------------------------------------

online = True

result = []

db = MySQLdb.connect(host="localhost", user="root", passwd="root", db="toolkit_ruby")
cur = db.cursor()

# ---------------------------------------------

f = open("../brave-intel-server/filters/systems.lst",'w')

cur.execute("SELECT solarSystemName,regionName FROM mapSolarSystems INNER JOIN mapRegions ON mapSolarSystems.regionID=mapRegions.regionID where security != '-0.99'")
for res in cur.fetchall() :
	name = str(res[0])
	region = str(res[1])
	f.write(name + "=" + region + "\n")

f.close()

# ---------------------------------------------
