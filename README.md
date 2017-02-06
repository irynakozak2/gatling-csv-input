###### This example is based on the official documentation from the following websites;
- http://gatling.io
- https://jenkins.io/

###### Explanation

Using Jenkins to kick of a pipeline job that uses Gatling (load) test multiple url's stored in a CSV.


###### Install Gatling Jenkins (master/slave) nodes

``` bash
$ mkdir /opt/gatling/
$ wget https://repo1.maven.org/maven2/io/gatling/highcharts/gatling-charts-highcharts-bundle/2.2.3/gatling-charts-highcharts-bundle-2.2.3-bundle.zip -P /opt/gatling/
$ unzip -o /opt/gatling/gatling-charts-highcharts-bundle-2.2.3-bundle.zip -d /opt/gatling
```

###### Install the Jenkins gatling plugin
- https://wiki.jenkins-ci.org/display/JENKINS/Gatling+Plugin

###### Create a pipeline job in Jenkins

![alt text](https://github.com/rajanarkenbout/gatling-csv-input/blob/master/jenkins-config.png "Jenkins pipeline job and git config")

###### Pipeline job post configuration

![alt text](https://github.com/rajanarkenbout/gatling-csv-input/blob/master/jenkins-java-options.png "Jenkins pipeline job and java options")

###### Or; run gatling simulation from the command line

- firstly; setting all variables
``` bash
$ export JAVA_OPTS="-DcsvFile=TestApiSimulation.csv -Dduration=10 -DrampNUsers=1 -DrampDuration=1 -DrunInvalidator=false"
```
- secondly; running the actual simulation
``` bash
$ ./gatling.sh -s my.gatling.TestApiSimulation -sf /opt/gatling-tests/TestApiSimulation -df /opt/gatling-tests/TestApiSimulation -rf /opt/gatling-tests/TestApiSimulation
```
