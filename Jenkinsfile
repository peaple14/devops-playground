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
                sh "chmod +x ${BUILD_DIR}/${JAR_NAME}"  // 빌드된 JAR 파일 권한 부여
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
                        echo "Stopping any running processes..."
                        PIDS=$(ps -eaf | grep 'devops-playground-0.0.1-SNAPSHOT.jar' | grep -v grep | awk '{print $2}')
                        if [ -z "$PIDS" ]; then
                            echo "No running process found."
                        else
                            echo "Killing processes: $PIDS"
                            echo $PIDS | xargs -r sudo kill -9
                        fi
                    '''
                }
            }
        }


        stage('Deploy') {
            steps {
                script {
                    echo "Starting devops-playground-0.0.1-SNAPSHOT.jar ..."
                    //계속 오류떠서 하드코딩
                    sh '''
                        sudo nohup java -Dserver.port=8080 -jar /var/lib/jenkins/workspace/devops-playground/build/libs/devops-playground-0.0.1-SNAPSHOT.jar &
                        sleep 5
                    '''
                    sh '''
                        ps -eaf | grep 'devops-playground-0.0.1-SNAPSHOT.jar' | grep -v grep
                    '''
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
