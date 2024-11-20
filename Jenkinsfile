pipeline {
    agent any

    environment {
        JAR_NAME = 'devops-playground-0.0.1-SNAPSHOT.jar'
        JAR_PATH = "/var/lib/jenkins/workspace/${JOB_NAME}/build/libs/${JAR_NAME}"
    }

    stages {
        stage('Checkout code') {
            steps {
                checkout scm
            }
        }

        stage('Grant Execute Permission') {
            steps {
                sh 'chmod +x ./gradlew'  // gradlew에 실행 권한을 추가
            }
        }

        stage('Build') {
            steps {
                sh './gradlew clean build'
            }
        }

        stage('Kill existing JAR process') {
            steps {
                script {
                    def pids = sh(script: "ps -eaf | grep ${JAR_NAME} | grep -v grep | awk '{print \$2}'", returnStdout: true).trim().split('\n')
                    if (pids.size() > 0) {
                        pids.each { pid ->
                            echo "Killing process with PID: ${pid}"
                            sh "sudo kill -9 ${pid}"
                            echo "Process ${pid} is shutdown"
                        }
                    } else {
                        echo "No running process for ${JAR_NAME} found"
                    }
                }
            }
        }

        stage('Start new JAR') {
            steps {
                script {
                    echo "Starting ${JAR_NAME} ..."
                    sh "nohup sudo java -jar ${JAR_PATH} > /dev/null 2>&1 &"
                    echo "${JAR_NAME} is now running"
                }
            }
        }
    }
}
