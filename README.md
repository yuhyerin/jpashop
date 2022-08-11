# jpashop

본 프로젝트의 목표는 springboot + jpa를 활용하여 빠르게 간단한 웹 어플리케이션 개발해보는 것입니다.

### 사용기능
- web
- thymeleaf
- jpa
- h2 데이터베이스 : 개발이나 테스트 용도로 가볍고 편리한 DB. 웹 화면 제공.  
- lombok

### 테스트 환경
- Junit4 를 사용합니다. 

### 프로젝트 환경세팅 순서 
#### 1. gradle 설정
- `build.gradle` 파일 참고. 
- java 버전은 11 을 사용합니다.
- springboot 버전은 2.7.2 를 사용합니다.
- gradle 의존관계 보기 : `./gradlew dependencies —configuration compileClasspath`

#### 스프링부트 라이브러리
- spring-boot-starter-web
    - spring-boot-starter-tomcat: 톰캣(웹서버)
    - spring-webmvc : 스프링 웹 MVC
- spring-boot-starter-thymeleaf : 타임리프 템플릿 엔진(View) 3버전
- spring-boot-starter-data-jpa
    - spring-boot-starter-aop
    - spring-boot-starter-jdbc
        - **HikariCP 커넥션 풀** (부트 2.0기본)
    - hibernate + JPA : 하이버네이트+JPA
    - spring-data-jpa : 스프링 데이터 JPA
- spring-boot-starter(공통) : 스프링 부트 + 스프링 코어 + 로깅
    - spring-boot
        - spring-core
    - spring-boot-starter-logging
        - logback, slf4j

#### 테스트 라이브러리

- spring-boot-starter-test
    - junit : 테스트 프레임워크
    - mockito : 목 라이브러리
    - assertj : 테스트코드를 좀 더 편하게 작성하게 도와주는 라이브러리
    - spring-test : 스프링 통합 테스트 지원

#### 핵심 라이브러리

- 스프링 MVC
- 스프링 ORM
- JPA, 하이버네이트
- 스프링 데이터 JPA

#### 2. JPA, DB 설정
application.yaml
```
spring:
  datasource:
    url: jdbc:h2:tcp://localhost/~/jpashop
    username: sa
    password:
    driver-class-name: org.h2.Driver

  jpa:
    hibernate:
      ddl-auto: create
    properties:
      hibernate:
#        show_sql: true
        format_sql: true

logging.level:
  org.hibernate.SQL: debug
  org.hibernate.type: trace # binding parameter를 로그로 찍어준다
```
- `spring.jpa.hibernate.ddl-auto: create` : 애플리케이션 실행 시점에 테이블을 drop 하고, 다시 생성한다
- `show_sql` : System.out 에 하이버네이트 실행 SQL을 남긴다.
- `org.hibernate.SQL` :  logger를 통해 하이버네이트 실행 SQL을 남긴다.
- `org.hibernate.type` : SQL 실행 파라미터를 로그로 남긴다. 외부 라이브러리를 사용하여 파라미터 값도 출력할 수 있지만 
시스템 자원을 사용하므로, 개발단계에서만 사용하고 운영시스템에 적용 시에는 성능테스트를 꼭 해야 한다. 

### 구현 순서
- 회원 도메인 개발
- 상품 도메인 개발
- 주문 도메인 개발
