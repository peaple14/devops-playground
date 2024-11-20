# devops-playground

## 프로젝트 진행 단계

1. **~~ngrok을 사용하여 Jenkins 연결~~**  
   _(소규모라 EC2 서버에 Jenkins 설치가 효율적이라고 판단)_  
2. **EC2 배포 테스트** _(완료)_  
3. **EC2에 Jenkins 설치 및 설정** _(완료)_  
   - **~~swap 메모리 할당 또는 EBS 볼륨 확장~~**  
     Jenkins 내 불필요한 캐시나 데이터가 없었기에, **임계값 수정**만으로 문제 해결  
4. **EC2의 Jenkins와 서버 배포 자동화** _(8080은 젠킨스,8081은 jar서버)_ _(완료)_
5. **테스트 코드 작성 및 공부 후 CI/CD 완성**

---

## 문제 해결 과정

### Jenkins 디스크 공간 문제
- 경고 메시지:  Disk space is below threshold of 1.00 GiB. Only 970.56 MiB out of 6.71 GiB left on /var/lib/Jenkins.

- 해결 과정:
- 옵션 1: **Swap 메모리 추가** 또는 **EBS 볼륨 확장** _(장기적 해결책)_  
- 옵션 2: **Jenkins 디스크 공간 경고 임계값 낮춤** _(현재 Jenkins에서 큰 용량 작업이 없으므로 적용)_
### Jenkins 디스크 공간 문제
- 문제: 빌드 중 Jenkins가 멈추는 현상 발생
- 해결: Swap 영역을 늘려 메모리 부족 문제를 해결
### Jenkins 디스크 공간 문제
- 문제: Stop Existing Application 단계에서 작동 중인 프로세스를 종료하는 과정에서 오류 발생
- 해결: sudoers 파일에서 Jenkins 사용자의 kill 권한을 추가,파이프라인에서 kill 명령어에 sudo 권한 추가
---

## ~~JAR 파일 실행 및 관리 스크립트~~
_Jenkins 파이프라인으로 변경 수정후 SCM으로 Jenkinsfile내 내용으로 실행)_  

```bash
JAR_NAME=devops-playground-0.0.1-SNAPSHOT.jar

# 기존 JAR 프로세스 종료
if [ -z "`ps -eaf | grep $JAR_NAME | grep -v grep`" ]; then
echo "Not found $JAR_NAME"
else
ps -eaf | grep $JAR_NAME | grep -v grep | awk '{print $2}' |
while read PID
do
  echo "Killing $PID"
  kill -9 $PID
  echo "$PID is shutdown"
done
fi

# 새 JAR 실행
echo "Starting $JAR_NAME ..."
nohup java -jar "/var/lib/jenkins/workspace/pipelinetest/build/libs/$JAR_NAME" > /dev/null 2>&1 &
echo "$JAR_NAME 돌아갑니다."

```
---

## 참고한 자료

- [gong-story 블로그](https://gong-story.tistory.com/40)  
- [velog Jenkins와 GitHub 연동](https://velog.io/@rungoat/CICD-Jenkins%EC%99%80-GitHub-%EC%97%B0%EB%8F%99%ED%95%98%EA%B8%B0)  
- [kwang1 블로그](https://kwang1.tistory.com/22)  


