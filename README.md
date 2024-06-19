### 처음 해야할 셋팅.
1. team5 오른쪽 클릭 후, 오픈 모듈 셋팅 클릭.
2. project -> sdk 1.8 체크 -> 프로젝트 아래에 있는 모듈 클릭 -> 모듈 sdk 1.8 체크
3. 1.8 안보이거나 또는 없다면, oracle 홈페이지에서 1.8 설치.
4. run 해보기.
5. Unsupported class file major version 66 오류가 뜬다면, setting -> gradle -> 버전 product 1.8로 설정.

### 1. `controller`
- **목적**: 이 디렉토리는 사용자의 요청을 받아 처리하고 응답을 반환하는 컨트롤러 클래스들을 포함합니다. MVC 패턴에서의 컨트롤러 역할을 수행합니다.
- **기능**: HTTP 요청을 적절한 서비스 로직에 매핑하고, 결과 데이터를 클라이언트에게 전송하거나 뷰를 렌더링하는 역할을 합니다.

### 2. `config`
- **목적**: 애플리케이션의 구성 관련 클래스들을 저장하는 곳입니다. 이는 보안 설정, 데이터베이스 구성, MVC 구성 등을 포함할 수 있습니다.
- **기능**: Spring Boot의 자동 구성(Auto-configuration) 외에 필요한 추가적인 설정을 제공합니다. 예를 들어, `SecurityConfig` 클래스는 보안 관련 설정을 담당합니다.

### 3. `dto` (Data Transfer Object)
- **목적**: 계층 간 데이터 교환을 위해 사용되는 객체들의 모음입니다. 이 객체들은 일반적으로 데이터베이스 엔티티의 구조를 클라이언트에게 직접 노출하지 않으면서 필요한 데이터만을 전달하는 데 사용됩니다.
- **기능**: 다양한 계층(예: 컨트롤러와 서비스) 간에 데이터를 전달하는 데 사용됩니다. 이는 데이터의 안전한 전송을 보장하고, 계층 간의 결합도를 낮춥니다.

### 4. `model`
- **목적**: 데이터베이스의 테이블 구조와 매핑되는 엔티티 클래스들을 포함합니다.
- **기능**: 이 모델 클래스들은 데이터베이스와의 상호작용을 위해 JPA (Java Persistence API)와 같은 ORM(Object-Relational Mapping) 툴에 의해 사용됩니다.

### 5. `repository`
- **목적**: 데이터베이스와의 상호작용을 캡슐화하는 JPA 리포지토리 인터페이스를 포함합니다.
- **기능**: 리포지토리 인터페이스는 데이터베이스로부터 데이터를 조회하거나 저장하는 메소드를 정의하며, Spring Data JPA가 구현을 자동으로 제공합니다.

### 6. `service`
- **목적**: 비즈니스 로직을 처리하는 서비스 클래스들을 저장합니다. 이 클래스들은 비즈니스 요구사항에 따른 로직을 실행하여, 컨트롤러와 모델 사이의 중간 역할을 수행합니다.
- **기능**: 데이터 검증, 비즈니스 규칙 처리, 여러 데이터베이스 작업을 트랜잭션으로 묶는 등의 복잡한 처리를 담당합니다.

### application.yml
application.yml 파일은 Spring Boot 애플리케이션의 설정을 위한 주요 파일 중 하나입니다.
YAML(Yet Another Markup Language) 형식을 사용하여 읽기 쉽고, 계층적인 구성 정보를 제공합니다.
이 파일은 Java의 application.properties 파일과 유사한 역할을 하지만, YAML의 구조적 특성으로 인해 더 명확하게 설정을 그룹화하고 관리할 수 있습니다.

주요 기능 및 사용법
환경 구분: application.yml 파일을 통해 개발(development), 테스트(testing), 운영(production) 등 다양한 환경에 대한 설정을 다르게 할 수 있습니다.
설정 값 관리: 데이터베이스 URL, 사용자 이름 및 비밀번호, API 키, 서버 포트 번호 등 애플리케이션 실행에 필요한 다양한 파라미터를 관리합니다.
프로파일 관리: Spring 프로파일을 사용하여 특정 환경을 위한 구성을 활성화할 수 있습니다. spring.profiles.active 키를 사용하여 활성화할 프로파일을 지정합니다.

  datasource:
    url: jdbc:mysql://localhost:3306/데이터베이스이름?useSSL=false&serverTimezone=UTC
    username: root
    password: root
    driver-class-name: com.mysql.cj.jdbc.Driver

    [ build.gradle ]
    - implementation 'mysql:mysql-connector-java:8.0.28'
