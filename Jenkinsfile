def simulationName = "my.gatling.TestApiSimulation"  // Setting the simulationName, which equals to package.classname in CouchbaseSimulation.scala
def simulationDir = "TestApiSimulation" // The directory where the simulation is stored, preferably in the  Jenkins workspace.
def TestApiSimulationDir = "/home/deployer/workspace/gatling-csv-input/${simulationDir}/" // the complete path to the Jenkisn workspace
def gatlingDir = "/opt/gatling/gatling-charts-highcharts-bundle-2.2.3/bin" // the Gatling binary directory
def gatlingEnv = "GATLING_HOME=/opt/gatling/gatling-charts-highcharts-bundle-2.2.3" // setting the correct environment
def gatlingSettings = " -s ${simulationName} -sf ${TestApiSimulationDir} -df ${TestApiSimulationDir} -rf ${TestApiSimulationDir}" // all startup parameters
// -s the name of the simulation you want to run
// -sf where to find the simulation file (scala file)
// -df where to find the data file (csv)
// -rf where to store the test results (which we need for the Jenkins Gatling plugin)

stage('STAGE 1') { // provide a name
parallel 'parallel step 1':{ // create multiple parallel jobs (do not forget to assign these to different slaves :-)
    stage("test 1") { // each step/stage in a parallel job needs a name
    node('your-jenkins-001.slave.com'){ // running this code on a specific server
        // checking out the gatling simulation scala file and csv file
        checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[url: 'ssh://git@your-stash.domain.com:7999/directory/gatling-csv-input.git']]])
        sh "export ${gatlingEnv} && cd ${gatlingDir} && ./gatling.sh ${gatlingSettings}" // running the actual test
        gatlingArchive() // ensuring the test results are saved and presented in your Jenkins job dashboard
    }
    }
}
}

stage('STAGE 2') {
  node('your-jenkins-001.slave.com'){
    deleteDir() // cleaning up workspace
  }
  node('your-jenkins-002.slave.com'){
    deleteDir() // cleaning up workspace
  }
  node('your-jenkins-003.slave.com'){
    deleteDir() // cleaning up workspace
  }
}
