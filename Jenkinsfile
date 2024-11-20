pipeline {
    agent any

    environment {
        JAR_NAME = "devops-playground-0.0.1-SNAPSHOT.jar"
        BUILD_DIR = "${workspace}/build/libs"
        LOG_FILE = "/var/log/devops-playground.log"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/peaple14/devops-playground.git'
            }
        }

        stage('Set Executable Permission') { // gradlew 실행 권한 부여
            steps {
                sh 'chmod +x gradlew'
            }
        }

        stage('Build') {
            steps {
                script {
                    sh './gradlew build'
                }
            }
        }

        stage('Test') { // test 코드 실행
            steps {
                script {
                    sh './gradlew test'
                }
            }
        }

        stage('Stop Existing Application') { // 기존 애플리케이션 종료
            steps {
                script {
                    sh '''
                        echo "Checking for running processes..."
                        PID=$(ps -eaf | grep 'devops-playground-0.0.1-SNAPSHOT.jar' | grep -v grep | awk '{print $2}')
                        if [ -z "$PID" ]; then
                            echo "No running process found for devops-playground-0.0.1-SNAPSHOT.jar"
                        else
                            echo "Killing process with PID: $PID"
                            kill -9 $PID
                            echo "Process with PID $PID has been stopped"
                        fi
                    '''
                }
            }
        }

        stage('Deploy') { // jar 실행
            steps {
                script {
                    sh """
                    echo "Starting $JAR_NAME ..."
                    nohup java -Dserver.port=8081 -jar ${BUILD_DIR}/${JAR_NAME} > ${LOG_FILE} 2>&1 &
                    echo "$JAR_NAME is running on port 8081.."
                    """
                }
            }
        }
    }

    post {
        success {
            echo 'Build, Test, and Deploy were successful!'
        }
        failure {
            echo 'Build, Test, or Deploy failed.'
        }
    }
}
