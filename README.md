# input.log 파일 읽기
- Resorece 패키지 하위에 input.log 파일을 읽은후 통계파일생성 
- 로그파일 샘플
  - [200][http://apis.daum.net/search/knowledge?apikey=23jf&q=daum][IE][2012-06-10 08:00:00] 


## v1 Log파일의 모든라인을 List로 읽어온뒤 필요한데이터를 필터링 하는방식
- 문제점
  - 이 프로그렘은 동적 프로그렘이 아니며 최초 구동시 설정한 정보대로 실행됨
  - 요구사항에 없는 List정보는 사용되지 않으며 메모리만 차지함

## v2 Log파일을 1라인씩 읽은뒤 통계데이터를 계산해 객체에 세팅
- 주의점
  - 요구사항이 변경되는 경우 유연하게 추가할수 있어야함
- 필요정보
  - 최다호출_apikey_조회
    - apikey별 호출횟수 계산
      - 로직1
        - 방법
          - log파일 라인을 읽은뒤 apikey를 추출해 map에 key를 apikey, value를 호출수로 만들어 결과 객체에 세팅
          - 순서가 중요하므로 호출수 DESC 정렬을한다.
          - apikey별 호출횟수가 담긴 map을 순회하하며 value가 가장큰 값을 리턴
        - 문제점
          - 정릴시 apikey 갯수가 늘어나면 O(N)? 만큼 시간복잡도가 늘어남    
  - 최대호출_상위_3개_api_server_id
    - api_server_id별 호출횟수 계산
      - 방법
        - log파일 라인을 읽으면서 api_server_id key, 회출횟수를 value로하는 map에 저장후 순회
        - log파일을 전부 읽은뒤 호출횟수가 담긴 map을 순회하하며 value가 큰 순서대로 정렬
        - 정렬후 상위3개 리턴
  - 웹브라우저_사용비율
    - 웹브라우저별 호출횟수 계산
      - log파일의 라인을 읽어 브라우저값을 읽은뒤 브라우저아이디를 key, 호출횟수를 value로 하는 map에 넣음      
    - 총 호출횟수 계산
      - log라인수를 순회할때마다 +1 한뒤 count 변수에 저장
    - 사용비율계산
      - 웹브라우저별 호출횟수를 순회하며 브라우저호출횟수/총호출횟수*100 으로 계산함

### 기능도출
- resources 경로에 위치한 파일을 라인별로 읽는 기능
- 읽은 파일을 분석해 로그정보로 변환하는 기능
- apikey별 호출횟수 계산기능
- api_server_id별 호출횟수 계산기능
- 웹브라우저별 호출횟수 계산기능
- 총 로그라인수 계산기능
- 브라우저별 사용비율계산 계산기능

### 설계
- LogReader.class
  - 로그파일을 읽는 클래스
  - 전체 기능의 실행을 담당한다
  - resources 경로에 위치한 파일을 라인별로 읽는 기능
- LogInfo.class
  - 로그정보를 저장한다
  - 로그 String 라인 을 읽어 로그객체 정보로 변한함
- Report.class
  - 출력해야하는 로그파일의 통게정보를 저장하는 클래스.
  - LogInfo를 입력받아 통계정보를 계산한다.
    - apikey별 호출횟수 계산기능
    - api_server_id별 호출횟수 계산기능
    - 웹브라우저별 호출횟수 계산기능
    - 총 로그라인수 계산기능
    - 브라우저별 사용비율계산 계산기능

# 예외처리
- 익셉션은 모두 상위로 던진후 상위클래스에서 잡아 처리함
  - 에러정보 error_log_{yyyymmdd}.txt 파일에 출력
- 로그파일을 읽는데 특정라인데이터 형식이 잘못된 경우  
- 파일이 비어있거나 없는경우  
- 실행중 중단되는 경우
  - 잡을수있나?

# 개선내용
1. LogInfo getParam시 key가 String 타입 -> 상수나 emum사용하도록 변경
2. Loginfo.java getParam 실행시 조회결과 없으면 "" 리턴하도록 수정 -> 조회되지 않아도 NPE 터지지않도록
3. apikey 파라미터가 없는경우 카운팅 하지 않도록 수정
4. 로그파일 리소스 경로가 아니라 외부경로에서 받아오도록 수정
   - 로그파일은 프로젝트 외부에 있는게 일반적임
5. log4j2 추가

# 고민중
- Report.java 클래스의 각 proc(), clear()를 외부에서 호출해야하나?
- LogReader.class는 현재 파일명을 받아 객체를 생성하로 Report객체를 생성하는 일만 담당중.
- logger 선언 스타일
  ```java
  private final Logger log = LoggerFactory.getLogger(LogInfo.class);
  ```
   ```java
  private static final Logger log = LoggerFactory.getLogger(LogInfo.class);
  ```
- 결과파일 출력시 BufferedWriter는
```java
BufferedWriter bw = new BufferedWriter(descPath)
bw.
```