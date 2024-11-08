pipeline {
    agent any

    tools {
        // Jenkins에서 사용할 Gradle 버전 (Gradle 7.x가 설치되어 있다고 가정)
        gradle 'Gradle 7.x'
    }

    environment {
        // 환경 변수 설정 (예: DB URL, 서버 IP 등)
    }

    stages {
        stage('Checkout') {
            steps {
                // Git 리포지토리에서 코드 체크아웃
                git 'https://github.com/peaple14/devops-playground.git'
            }
        }

        stage('Build') {
            steps {
                script {
                    sh './gradlew build'
                }
            }
        }

        stage('Test') {
            steps {
                // Gradle 테스트 실행
                script {
                    sh './gradlew test'
                }
            }
        }

        stage('Deploy') {
            steps {
                // 배포 단계 설정 (예: EC2에 WAR 파일 복사 등)
                script {
                }
            }
        }
    }

    post {
        success {
            echo 'Build and deploy were successful!'
        }
        failure {
            echo 'Build or deploy failed.'
        }
    }
}
