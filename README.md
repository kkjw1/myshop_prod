## 배포 (Deployment)

Oracle Cloud Infrastructure(OCI) 백엔드 배포 환경입니다. 제한된 무료 리소스(1 OCPU / 1GB RAM) 안에서 안정적으로 동작하도록 아키텍처와 설정을 직접 설계·튜닝했습니다.


### 인프라 구성

```
[사용자]
   │  HTTP (도메인 접속), http://jwshop.cloud, http://www.jwshop.cloud
   ▼
[Nginx]  (도메인 연결, 80 포트, 리버스 프록시)
   │  127.0.0.1:8080
   ▼
[Spring Boot]  (VM.Standard.E2.1.Micro, 1 OCPU / 1GB RAM)
   ├─ Oracle Autonomous AI Database  (TLS 인증, Wallet 불필요)
   ├─ Redis  (localhost, 비밀번호 인증 + Keyspace Notification)
   └─ OCI Object Storage  (상품 이미지, Public Bucket)
```

- **컴퓨트**: OCI Always Free `VM.Standard.E2.1.Micro` (1 OCPU, 1GB RAM) 위에서 애플리케이션 구동
- **도메인 연결**: 가비아(Gabia)에서 구매한 맞춤형 도메인(`jwshop.cloud`)을 OCI 인스턴스 공인 IP와 A 레코드로 연동하여 HTTP 기반 외부 접속 환경 구축
- **DB**: Oracle Autonomous AI Database(Always Free)를 TLS 전용 인증 방식으로 연결하여 전자지갑(Wallet) 파일 없이 접속
- **캐시/세션**: Redis를 서버 로컬에 설치, `requirepass`로 인증을 걸고 Keyspace Notification(`notify-keyspace-events Ex`)을 활성화하여 재고 선점 TTL 만료를 이벤트 기반으로 감지
- **이미지 저장소**: 상품 이미지는 OCI Object Storage(Public Bucket)에 업로드하고, 브라우저가 Object Storage 엔드포인트에 직접 요청하도록 구성 — 애플리케이션 서버의 정적 파일 서빙 부하 분산
- **리버스 프록시**: Nginx가 80 포트로 들어오는 도메인 요청을 수신하여 `127.0.0.1:8080`으로 프록시하며, 애플리케이션 포트와 DB/Redis 포트는 외부 접근 차단
- **프로세스 관리**: systemd 유닛으로 등록하여 서버 재부팅 시 자동 기동 및 비정상 종료 시 자동 재시작(`Restart=on-failure`) 보장

---

### CI/CD

GitHub Actions를 이용해 `main` 브랜치에 push하면 자동으로 빌드·배포되는 파이프라인을 구성했습니다.(`.github/workflows/github-action-demo`)

1. GitHub Actions 러너에서 `./gradlew bootJar`로 빌드 (저사양 운영 서버에서 직접 빌드하지 않아 빌드 중 메모리 부족 위험 차단)
2. 빌드된 `.jar` 파일을 SSH로 서버에 전송
3. 서버에서 `systemctl restart`로 무중단에 가깝게 애플리케이션 교체

`.env`, OCI 인증 설정 파일(API 키), 데이터베이스 접속 정보 등 민감 정보는 저장소에 올리지 않고 서버와 깃허브 `Repository secrets`에 별도로 배치하여 주입합니다.


---

> 💡 백엔드 시스템 구현 및 소스 코드에 대한 자세한 정보는 [myshop_dev GitHub Repository](https://github.com/kkjw1/myshop_dev)에서 확인하실 수 있습니다.
