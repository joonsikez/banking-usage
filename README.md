# 개요
스프링부트를 이용한 디바이스별 인터넷뱅킹 이용현황 정보 제에 대한 간단한 API입니다.

## package structure
```
banking-usage/
/src
    /main
        /java
            /com/pjs/bankingusage
                /common      캐시 객체 및 victim 선택 enum 클래스
                    /exception
                    /interceptor
                /controller
                /model
                    /entity
                    /dto
                /repository
                /service
        /resources          logback설정 파일 위치
    /test
        /java
            /com/pjs/simplecache
                /cache      캐시 테스트 관련한 Junit 클래스
pom.xml  
```
## API SPEC
#### 1. 전체 디바이스 목록 조회
```
Http Method : GET
URI Path : /api/banking/devices
Description : 
```
#### 2. 년도별 가장 많이 이용하는 디바이스 정보 조회
```
Http Method : GET
URI Path : /api/banking/devices/year
Description : 
```
#### 3. 특정 년도에 가장 많이 이용된 디바이스 정보 조회
```
Http Method : GET
URI Path : /api/banking/devices/year/{year}
Description : 
```

#### 4. 특정 디바이스가 가장 많이 이용된 년도 정보 조회
```
Http Method : GET
URI Path : /api/banking/year/{deviceId}
Description : 
```

#### 5. 사용자 정보 등록
```
Http Method : POST
URI Path : /api/user/signup
Parameter : id, password
Description : 
```

# 스펙 구현 여부
### 1. memcached, redis 와 같은 데몬 방식이 아니라, 라이브러리 형식으로 동작하게 작성합니다.
#### `구현 완료`
별도의 프로세스로 실행하지 않고 라이브러리 형태로 동작하도록 구현하였습니다. 
classpath 지정 또는 메이븐 사용시 pom.xml에 dependency 추가 해야 합니다.
``` xml
<dependency>
    <groupId>com.pjs.cache</groupId>
	<artifactId>data-cache</artifactId>
	<version>1.0.0-SNAPSHOT</version>
</dependency>
```

### 2. 캐시객체를 생성할 때, 담을 수 있는 최대 객체 변수를 인자로 받습니다.
	- 한번 정한 최대치는 캐시 객체가 소멸될 때 까지 변경할 수 없습니다.
    - 캐시에 담을 수 있는 객체 개수가 부족할 때, victim을 고르는 전략을 구현해주세요. 이 전략은 캐시 모듈을 사용하는 사용자(개발자)가 교체할 수 있어야합니다.(가산점)
#### `구현 완료`
객체 생성 시 생성자를 이용하여 캐시의 최대 크기를 설정할 수 있습니다.
마찬가치로 생성자를 이용하여 캐시 교체 전략을 변경할 수 있습니다.(LRU, LFU, FIFO)
기본 설정값은 최대 크기 5, LRU 교체 알고리즘입니다.

### 3. 캐시에서 put(key, value) 메서드를 통해 값을 저장합니다.
	- key와 value는 모든 Java 객체가 가능합니다.
    - put(key, value, TTL)과 같이 세 번째 인자를 받아 캐시 유효기간을 지정할 수 있습니다. TTL을 넘기지 않을 경우, 해당 key의 TTL 은 무제한입니다.
    - key가 동일할 경우 기존 값을 덮어쓰고 기존 값을 반환합니다.
#### `구현 완료`
캐시의 key와 value는 모든 자바 객체가 가능합니다.
put 메서드 호출 시 세번째 인자값을 이용하여 TTL 처리가 가능합니다.
key가 동일할 경우에는 해당 key에 새로운 값을 저장하고 기존 값을 반환합니다.

### 4. 캐시에서 get(key) 메서드를 통해 저장한 값을 가져옵니다.
	- 값이 없을 경우 null 을 반환합니다.
#### `구현 완료`
get 메서드 호출로 캐시의 값을 조회할 수 있고, 해당 key에 대한 값이 없을 경우 null을 반환합니다.

### 5. addAndGet(key) 메서드를 통해 해당 key 값에 해당하는 값을 하나 증가해서 저장하고 반환합니다.
	- atomic하게 동작해야 합니다.
    - value에1을더할수없는숫자타입이아닌경우 InvalidTargetObjectTypeException을 발생시킵니다.
#### `구현 완료`
atomic을 보장하기 위해 AtomicInteger 등 자바 atomic 패키지 사용
캐시 value의 타입이 Number 타입이 아닐 경우 InvalidTargetObjectTypeException을 throw 합니다.

### 6. 생성한 캐시 객체를 동시에 여러 스레드가 사용할 수 있습니다.

#### `구현 완료`
multi thread에 안전하도록 캐시 객체를 설계

### 7. 앞에서 구현한 여러 스펙을 검증하는 테스트 케이스를 JUnit4를 이용해서 작성하세요.

#### `구현 완료`
각 스펙에 대한 JUnit 테스트 클래스를 생성하였습니다.

### 8. 캐시 구현체에서 logback 프레임워크 http://logback.qos.ch/를 이용하여 적절한 로그를합니다.
    - 로그 파일을 하루 단위로 분리합니다.
    - 로그 내용에 따라 적절한 로그 레벨을 적용합니다.
    - 오류 발생 시, StackTrace 전체를 로그 파일에 남깁니다.

#### `구현 완료`
resources/logback.xml 설정에서 RollingFileAppender를 이용하여 로그 파일을 하루 단위로 분리 합니다.
상황에 따라 DEBUG, INFO, WARN 등의 로그 레벨을 적용합니다.