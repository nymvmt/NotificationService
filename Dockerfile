# ---------- Build stage ----------
    FROM eclipse-temurin:17-jdk AS builder
    WORKDIR /workspace
    
    # 소스 복사
    COPY . .
    
    # Windows에서 실행 권한 이슈 예방
    RUN chmod +x ./gradlew || true
    
    # 부트 실행 JAR만 생성 (tests skip)
    RUN ./gradlew clean bootJar -x test
    
    # ---------- Runtime stage ----------
    FROM eclipse-temurin:17-jre
    WORKDIR /app
    
    # build.gradle에서 bootJar 이름을 app.jar로 고정했으므로 정확히 복사
    COPY --from=builder /workspace/build/libs/app.jar /app/app.jar
    
    # Azure가 주는 포트를 그대로 사용 (미제공 시 8086)
    ENV WEBSITES_PORT=8086
    EXPOSE 8086
    
    # Spring Boot를 환경 포트로 바인딩해서 실행
    ENTRYPOINT ["sh","-lc","exec java -Dserver.port=${WEBSITES_PORT:-8086} -jar /app/app.jar"]
    