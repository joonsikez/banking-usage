## 개요
스프링부트를 이용한 디바이스별 인터넷뱅킹 이용현황 정보 제공에 대한 간단한 API입니다.

### package structure
```
banking-usage/
/src
    /main
        /java
            /com/pjs/bankingusage
                /common
                    /exception
                    /interceptor
                /controller
                /model
                    /entity
                    /dto
                /repository
                /service
        /resources
    /test
        /java
            /com/pjs/bankingusage
                /controller
                /service
pom.xml  
```
### API SPEC
#### 1. 전체 디바이스 목록 조회
```
Http Method : GET
URI Path : /api/banking/devices
Description : DB에 저장된 전체 디바이스 목록을 리턴한다
```
#### 2. 년도별 가장 많이 이용하는 디바이스 정보 조회
```
Http Method : GET
URI Path : /api/banking/devices/year
Description : 년도별로 가장 많이 이용하는 디바이스 정보를 추출하여 리턴한다
```
#### 3. 특정 년도에 가장 많이 이용된 디바이스 정보 조회
```
Http Method : GET
URI Path : /api/banking/devices/year/{year}
Description : PathVariable로 입력받은 년도에 해당하는 전체 디바이스 이용률 중 이용률이 가장 높은 디바이스 정보를 리턴한다
```

#### 4. 특정 디바이스가 가장 많이 이용된 년도 정보 조회
```
Http Method : GET
URI Path : /api/banking/year/{deviceId}
Description : PathVariable로 입력받은 디바이스 정보 중 이용률이 가장 높은 년도 정보를 리턴한다
```

#### 5. 사용자 정보 등록
```
Http Method : POST
URI Path : /api/banking/user/signup
payload : userId, password
Description : 사용자ID와 PASSWORD를 입력으로 받아 DB에 저장하고 JWT Token 정보를 리턴한다
```

#### 6. 사용자 정보 조회
```
Http Method : POST
URI Path : /api/banking/user/signin
payload : userId, password
Description : 사용자ID와 PASSWORD를 입력으로 받아 DB에서 조회하여 JWT Token 정보를 리턴한다
```

#### 7. 사용자 토큰 정보 갱신
```
Http Method : PUT
URI Path : /api/banking/user/refresh
Header : Authorization
Description : HttpRequest Header의 Authorization에 "Bearer Token" + 토큰 입력 할 경우 토큰 재발행 
```

#### 8. 디바이스 데이터 캐시 삭제
```
Http Method : PUT
URI Path : /api/banking/cache/evict
Description : device 정보 및 이용률 조회 데이터 캐시 삭제 
```

#### 9. 초기 데이터 리로드
```
Http Method : PUT
URI Path : /api/banking/data/reload
Description : device 정보 및 이용률 초기 data reload 
```

### 초기 데이터 구성
스프링부트 CommandLineRunner를 이용하여 어플리케이션 실행 시점에 인터넷 뱅킹 이용 현황 데이터 H2 DB에 적재

|device|기기 기본 정보 테이블|
|:------|:---|
|deviceId(PK)|기기 ID|
|deviceName|기기 명|

|device_usage|년도별 기기 이용률 테이블|
|------|---|
|id(PK)|sequence ID|
|year|년도|
|rate|이용률|
|device_id(FK)|기기 ID|

|user|사용자 정보 테이블|
|------|---|
|user_id(PK)|사용자 ID|
|password|사용자 암호|
|token|인증 토큰|


### 문제 해결 전략
#### 1. 전체 디바이스 조회
```
H2 DB에서 디바이스 전체 목록 조회
```
#### 2. 각 년도별 이용률이 가장 높은 디바이스 조회
```
H2 DB에서 전체 디바이스 이용률을 조회
Collectors.groupingBy를 이용하여 년도별로 그룹핑한 데이터 생성
년도별로 그룹핑한 데이터를 이용률 기준 최대값 추출
```
#### 3. 특정 년도의 이용률이 가장 높은 디바이스 조회
```
H2 DB에서 특정 년도에 해당하는 디바이스 이용률 조회 후 이용률 기준 최대값 추출
```
#### 4. 특정 디바이스의 이용률이 가장 높은 년도 조회
```
H2 DB에서 Jpa Native Query를 이용하여 조회
```
#### 5. 특정 디바이스의 2019년도 이용률 예측
```
전체 이용률의 증감 추이 평균으로 2019년 이용률 예측
(정확한 예측을 위해서는 이용률 변화 추이에 가중치를 부여하는 등의 작업이 필요해보임) 
```
#### 6. JWT 이용 token 인증
```
JWT 토큰의 경우 jjwt 라이브러리 이용하여 생성
HttpRequest의 Header에 Authorization에 "Bearer" + {token} 을 추가하여 호출
토큰에 대한 검증은 interceptor를 이용하여 이루어짐(URI Path가 /api/banking/** 일 때만 동작)
```

#### 
### 빌드 및 실행 방법
```
빌드
mvn clean package
실행
java -jar banking-usage-0.0.1-SNAPSHOT.jar
```
