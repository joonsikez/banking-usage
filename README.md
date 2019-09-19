## 개요
스프링부트를 이용한 디바이스별 인터넷뱅킹 이용현황 정보 제에 대한 간단한 API입니다.

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
URI Path : /api/user/signup
Parameter : id, password
Description : 사용자ID와 PASSWORD를 입력으로 받아 DB에 저장하고 JWT Token 정보를 리턴한다
```

#### 5. 사용자 정보 조회
```
Http Method : POST
URI Path : /api/user/signin
Parameter : id, password
Description : 사용자ID와 PASSWORD를 입력으로 받아 DB에서 조회하여 JWT Token 정보를 리턴한다
```

#### 5. 사용자 토큰 정보 갱신
```
Http Method : POST
URI Path : /api/user/refresh
Header : x-access-token, Authorization
Description : HttpRequest Header의 Authorization이 "Bearer Token"이고 x-access-token 인증이 성공하면
              신규 JWT Token을 발급하여 리턴한다 
```

### 초기 데이터 구성
스프링부트 CommandLineRunner를 이용하여 어플리케이션 실행 시점에 인터넷 뱅킹 이용 현황 데이터 H2 DB에 적재
