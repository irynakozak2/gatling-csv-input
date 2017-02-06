This example is based on the official documentation from ;
- http://gatling.io
- https://jenkins.io/

Sequence diagram displaying functionality

![alt text](https://github.com/rajanarkenbout/nginx_couchbase/blob/master/nginx_couchbase_sequence_diagram.jpg "Nginx couchbase sequence diagram")

Ensure Gatling is installed on your Jenkins (master/slave) nodes

``` bash
$ mkdir /opt/gatling/
$ wget https://repo1.maven.org/maven2/io/gatling/highcharts/gatling-charts-highcharts-bundle/2.2.3/gatling-charts-highcharts-bundle-2.2.3-bundle.zip -P /opt/gatling/
$ unzip -o /opt/gatling/gatling-charts-highcharts-bundle-2.2.3-bundle.zip -d /opt/gatling
```

Install the Jenkins gatling plugin

- https://wiki.jenkins-ci.org/display/JENKINS/Gatling+Plugin

Create a pipeline job in Jenkins

![alt text](https://github.com/rajanarkenbout/gatling-csv-input/blob/master/jenkins-config.png "Jenkins pipeline job and git config")

Pipeline job post configuration

![alt text](https://github.com/rajanarkenbout/gatling-csv-input/blob/master/jenkins-java-options.png "Jenkins pipeline job and java options")
