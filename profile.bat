@echo off

set junit=C:\usr\maven\repository\junit\jars\junit-3.8.1.jar
set ant_opt=C:\usr\maven\repository\ant\jars\ant-1.5.3-1.jar
set xml_apis=C:\usr\maven\repository\xml-apis\jars\xml-apis-1.0.b2.jar
set xerces=C:\usr\maven\repository\xerces\jars\xercesImpl-2.4.0.jar
set colls=C:\usr\maven\repository\commons-collections\jars\commons-collections-2.1.jar

set cp=%classpath%
set cp=%cp%;./bin
set cp=%cp%;%junit%
set cp=%cp%;%ant_opt%
set cp=%cp%;%xml_apis%
set cp=%cp%;%xerces%
set cp=%cp%;%colls%

java -classpath "%cp%" org.binxml.app.ProfileBuilder %*

set cp=
