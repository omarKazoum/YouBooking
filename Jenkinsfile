pipeline {
  agent any
  stages {
    stage('checkout code') {
      steps {
        git(url: 'https://github.com/omarKazoum/YouBooking.git', branch: 'main', changelog: true, poll: true)
      }
    }

    stage('runing tests') {
      parallel {
        stage('list files') {
          steps {
            sh 'ls -la'
          }
        }

        stage('back-end test') {
          steps {
            sh 'cd back-end && mvn dependency:resolve compile -DskipTests'
          }
        }

      }
    }

    stage('build docker images') {
      steps {
        sh 'cd back-end && mvn package -DskipTests'
        sh 'docker build -t backend-made-by-jenkins-test ./back-end'
      }
    }

    stage('run app') {
      steps {
        sh '''docker network create youbooking ||true &&
docker stop rabbitmq || true &&
docker rm rabbitmq || true &&
docker run --name backend-made-by-jenkins-test-con -p 9090:8088
--network youbooking --hostname=backend
  backend-made-by-jenkins-test
'''
      }
    }

  }
}