# devops-playground

## 프로젝트 진행 단계

1. **~~ngrok을 사용하여 Jenkins 연결~~** (소규모라 EC2 서버에 Jenkins 설치가 효율적이라고 판단)
2. **EC2 배포 테스트** (완료)
3. **EC2에 Jenkins 설치 및 설정** (완료)
3.1 **swap 메모리 할당 or EBS볼륨 확장**
4. **EC2의 Jenkins와 서버 배포 자동화** 
5. **테스트 코드 작성 및 공부 후 CI/CD 완성**

Jenkins의 용량문제 발견.
(disk space is below threshold of 1.00 GIB. Only 970.56Mib out of 6.71 GiB left on /varr/lib/Jenkins)
->swap을 통해 용량 증가or EBS볼륨확장


이 프로젝트는 DevOps 환경에서 Jenkins와 EC2를 활용하여 배포 자동화를 설정하는 방법을 다룹니다.
