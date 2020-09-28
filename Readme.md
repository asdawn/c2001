**Announcement**

The old www.c2001.net website is created on weebly, but it is down now. A lot of pages and experimental data are lost. However the net.c2001 library has a mirror here. In the next few years I will keep this domain and redirect it to https://github.com/asdawn/c2001.

The net.c2001 library was a good choice for GIS data mining. For beginners, it has a GUI, and can directly use ESRI shapefile and raster images, that is still a great job. Till now I've not seen such tools in QGIS or ArcGIS. The framework is extensible for sophisticated researchers. 

However, things has changed a lot in the past years. The basis is still okay but the implementation is out of date. It depends on Java 7 and GDAL 1.10, the latest version of Java seems to be 14, and GDAL 3.0+ was released months ago, interfaces might have been changed. I'm sorry but I do not have enough time to fix such bugs.

2020-09-28

d

--------------

# Codes, examples and documents
net.c2001: Library for spatio-temporal data analysis

-------------

## GARMF

GARMF: a general framework for association rule mining  

## RFL: association rule filtering library/language

RFL(Rule Filtering Language)  can be used for sorting, filtrating, and evaluating association rules. Once you converted association rules to the required format (see the API document or source code of net.c2001.dm.ar.base.*), you may save the rules to or read them form file, view and export frequent itemsets and association rules, sort and filtrate association rules using different methods, and evaluate rules with lift, novelty and other measures(see the API document or source code of net.c2001.dm.ar.rfl.*), just try it.

## DAP Shell: DAP Shell: a prototype for association rule mining

-----------

## Todo list

##### 1. Move the net.c2001 library to github
At first this library is published on www.c2001.net and Sourceforge. However, these domains might be difficult to access in the future, and github might be a more efficient way, so I move it here.

##### 2. Update it, if possible
This version is for Java 7. Java 8 had removed official ODBC support, so something had changed. Besides, GDAL 2.0+ is released, so I'll try to make time to update it.
