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
                checkout scm  // 최신 코드 확인을 위해 scm 체크아웃 사용
            }
        }

        stage('Set Executable Permissions') {
            steps {
                sh 'chmod +x gradlew'
                sh 'chmod +x build/libs/devops-playground-0.0.1-SNAPSHOT.jar'
                sh 'sudo chmod 777 /var/log/devops-playground.log'
            }
        }

        stage('Build') {
            steps {
                script {
                    sh './gradlew build'  // Gradle 빌드 실행
                }
            }
        }

        stage('Stop Existing Application') {
            steps {
                script {
                    sh '''
                        echo "Checking for running processes..."
                        PID=$(ps -eaf | grep 'devops-playground-0.0.1-SNAPSHOT.jar' | grep -v grep | awk '{print $2}')
                        if [ -z "$PID" ]; then
                            echo "No running process found for devops-playground-0.0.1-SNAPSHOT.jar"
                        else
                            echo "Killing process with PID: $PID"
                            sudo kill -9 $PID  # 기존 애플리케이션 종료
                            echo "Process with PID $PID has been stopped"
                        fi
                    '''
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    sh """
                    echo "Starting $JAR_NAME ..."
                    sudo nohup java -Dserver.port=8080 -jar /home/ubuntu/devops-playground/build/libs/devops-playground-0.0.1-SNAPSHOT.jar > /var/log/devops-playground.log 2>&1 &
                    sleep 5  # 잠시 대기 후 프로세스 확인
                    PID=\$(ps -eaf | grep 'devops-playground-0.0.1-SNAPSHOT.jar' | grep -v grep | awk '{print \$2}')
                    echo "Application started with PID: \$PID"
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
