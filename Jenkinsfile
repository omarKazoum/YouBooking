    pipeline {
      agent any
      stages {

        stage('checkout code') {
          steps {
            git(url: 'https://github.com/omarKazoum/YouBooking.git', branch: 'main', changelog: true, poll: true)
          }
        }

        stage('test') {
          steps {
            sh 'docker run -d --name database-test -p 5432:5432  -e POSTGRES_USER=root -e POSTGRES_PASSWORD=root -e POSTGRES_DB=YouBooking postgres:9'
            sh 'cd back-end && mvn test'
            sh 'docker stop database-test && docker container rm database-test'
          }
        }

        stage('build docker images') {
          parallel {
            stage('build docker images') {
              steps {
                sh 'cd back-end && mvn package -DskipTests'
                sh 'docker build -t backend-made-by-jenkins-test ./back-end'
              }
          }
        }
        }
        stage("preparing for deployment"){
            stage('create docker network') {
                      steps {
                        sh 'docker network create youbooking || true'
                      }
                    }
        }
        stage('run database') {
          steps {
            sh 'docker run -d --name database -p 9990:5432 --network youbooking --hostname database -e POSTGRES_USER=root -e POSTGRES_PASSWORD=root -e POSTGRES_DB=YouBooking postgres:9|| docker start database'
          }
        }

        stage('run app') {
          parallel {
            stage('run backend') {
              steps {
                sh '''docker stop backend-made-by-jenkins-test-con || true &&
    docker rm backend-made-by-jenkins-test-con || true &&
    docker run -d --name backend-made-by-jenkins-test-con -p 9090:8088 --network youbooking --hostname backend backend-made-by-jenkins-test'''
              }
            }

            stage('run frontend') {
              steps {
                sh 'echo start front end script here'
              }
            }

          }
        }


    }
    }
    
