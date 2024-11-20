## 프로젝트 진행 단계

1. **~~ngrok을 사용하여 Jenkins 연결~~**  
   _(소규모라 EC2 서버에 Jenkins 설치가 효율적이라고 판단)_  
2. **EC2 배포 테스트** _(완료)_  
3. **EC2에 Jenkins 설치 및 설정** _(완료)_  
   - **~~swap 메모리 할당 또는 EBS 볼륨 확장~~**  
     Jenkins 내 불필요한 캐시나 데이터가 없었기에, **임계값 수정**만으로 문제 해결
4. **EC2의 Jenkins와 서버 배포 자동화** _(8081은 젠킨스, 8080은 JAR 서버)_ _(완료)_
5. **테스트 코드 작성 및 공부 후 CI/CD 완성**

---

## 문제 해결 과정

### Jenkins 디스크 공간 문제
- **문제**: 경고 메시지 - `Disk space is below threshold of 1.00 GiB. Only 970.56 MiB left on /var/lib/Jenkins.`
- **해결책**:
  - 옵션 1: **Swap 메모리 추가** 또는 **EBS 볼륨 확장** _(장기적 해결책)_
  - 옵션 2: **Jenkins 디스크 공간 경고 임계값 낮추기** _(현재 Jenkins에서 큰 용량 작업이 없으므로 적용)_

### Jenkins 빌드 중 멈춤 현상
- **문제**: Jenkins 빌드 중 멈추는 현상 발생
- **해결책**: Swap 영역을 늘려 메모리 부족 문제 해결

### Jenkins 권한 문제
- **문제**: `Stop Existing Application` 단계에서 작동 중인 프로세스를 종료하는 과정에서 오류 발생
- **해결책**: `sudoers` 파일에서 Jenkins 사용자의 `kill` 권한을 추가, 파이프라인에서 `kill` 명령어에 `sudo` 권한 추가

### Jenkins 구버전 JAR 파일 구동 문제
- **문제**: Jenkins 파이프라인을 통해 구버전의 JAR 파일이 구동되는 문제
- **해결책**: JAR 파일 경로 지정 방식을 수정하고, 애플리케이션 시작 과정에서 불필요한 대기 및 PID 확인 절차를 제거하여 구버전 문제 해결

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
echo "$JAR_NAME is now running"
```


## 참고한 자료

## 참고한 자료
- [gong-story 블로그](https://gong-story.tistory.com/40)
- [mntdev 블로그](https://mntdev.tistory.com/33)
- [hellorennon 블로그](https://hellorennon.tistory.com/18)
- [cocococo 블로그](https://cocococo.tistory.com/entry/Jenkins-%EC%98%A4%EB%A5%98-Kill-9-%EB%AA%85%EB%A0%B9%EC%96%B4-%EC%98%A4%EB%A5%98-%ED%95%B4%EA%B2%B0%EB%8F%99%EC%9D%BC-%EC%84%9C%EB%B2%84)

