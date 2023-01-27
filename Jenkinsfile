pipeline {
  agent any
  stages {
    stage('checkout code') {
      steps {
        git(url: 'https://github.com/omarKazoum/YouBooking.git', branch: 'main', changelog: true, poll: true)
      }
    }

    stage('list files') {
      parallel {
        stage('list files') {
          steps {
            sh 'ls -la'
          }
        }

        stage('back-end test') {
          steps {
            sh 'cd back-end && mvn dependency:resolve test'
          }
        }

      }
    }

  }
}