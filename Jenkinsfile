pipeline {
    agent any

    environment {
        JAR_NAME = "devops-playground-0.0.1-SNAPSHOT.jar"
        BUILD_DIR = "/var/lib/jenkins/workspace/pipelinetest/build/libs"
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
                    sh """
                    if [ -z "`ps -eaf | grep $JAR_NAME | grep -v grep`" ]; then
                        echo "Not found $JAR_NAME"
                    else
                        ps -eaf | grep $JAR_NAME | grep -v grep | awk '{print \$2}' |
                        while read PID
                        do
                          echo "Killing \$PID"
                          kill -9 \$PID
                          echo "\$PID is shutdown"
                        done
                    fi
                    """
                }
            }
        }

        stage('Deploy') { // jar 실행
            steps {
                script {
                    sh """
                    echo "Starting $JAR_NAME ..."
                    nohup java -jar "$BUILD_DIR/$JAR_NAME" > /dev/null 2>&1 &
                    echo "$JAR_NAME 돌아갑니다."
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
