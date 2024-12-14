# TaffyShop_2023

# 어드벤처 디자인: 태피 상점 큐 시뮬레이션

## 프로젝트 개요
본 프로젝트는 태피 상점에서 고객 서비스 및 대기 시간을 최적화하기 위해 큐 자료구조를 활용한 시뮬레이션 프로그램입니다. GUI와 다중 스레드를 적용해 시뮬레이션을 시각적으로 표현하며, 통계를 통해 상점 운영 효율성을 분석할 수 있습니다.

---

## 프로젝트 요약

### 실행 흐름도

<img src="https://github.com/user-attachments/assets/032798ff-48cb-4f88-9313-8d3e57b343bf" width="70%">


### 시스템 구성

<img src="https://github.com/user-attachments/assets/77cf6cd9-2dd8-46b8-b992-41972b08138b" width="70%">

### 모듈 설계

<img src="https://github.com/user-attachments/assets/f1358a4a-37a2-4be1-8a65-5de70ef185dc" width="70%">

### 자료구조 설계

입력 데이터(고객의 도착 시간, 서비스 시간 등)의 입력 데이터는 `Customer` 클래스에서 관리됩니다.  
큐 자료구조는 `LinkedList`를 이용해 구현했으며, 이에 따른 메소드는 아래와 같습니다.

#### Queue

```java
public class Queue {
    private LinkedList<Customer> que;

    public Queue() { que = new LinkedList<>(); }

    public synchronized void enqueue(Customer data) {
        que.add(data); // 정상적으로 추가
    }

    public synchronized Customer dequeue() {
        return que.removeFirst(); // 첫번째 요소 제거
    }

    public synchronized int getSize() { return que.size(); }

    public synchronized boolean isEmpty() { return que.isEmpty(); }
}
```

#### Customer 자료구조
`Customer` 자료구조는 설계와 거의 비슷하며, 전화 기능과 관련된 변수와 `get`/`set` 함수가 포함되어 있습니다.

```java
public class Customer {
    private int arriveTime; // 도착 시간
    private int svcStartTime; // 서비스 시작 시간
    private int waitTime; // 대기 시간
    private int svcTime; // 서비스 소요 시간
    private int Num; // 고객 번호
    private boolean isPhoneSvc; // 전화 서비스 여부

    private static int customerCount = 0; // 고객 수, 고객 번호 부여에 사용

    public Customer(int arriveTime) {
        this.Num = ++customerCount;
        this.arriveTime = arriveTime;
        this.svcStartTime = 0;
        this.waitTime = 0;
        this.svcTime = 0;
        this.isPhoneSvc = false;
    }
}
```

---

## 주요 기능
- **큐 시뮬레이션**: 고객의 도착 시간, 대기 시간, 서비스 시간을 관리.
- **랜덤 데이터 생성**: 난수를 이용해 고객 정보를 자동 생성.
- **통계 분석**: 평균 대기 시간, 서비스 시간 등 통계 자료 출력.
- **GUI 제공**: 실시간 시뮬레이션 과정을 시각적으로 표현.
- **다중 스레드**: 창구별로 개별 스레드 처리.
- **CSV 출력**: 통계 자료를 CSV 파일로 저장.
- **배속 기능**: 시뮬레이션의 속도를 조절.

---

## 시스템 요구 사항
- **개발 언어**: Java
- **개발 환경**: IntelliJ IDEA Community Edition
- **필요 라이브러리**: Java 기본 라이브러리

---

## 실행 방법
1. 프로그램을 실행하면 메뉴 창이 출력됩니다.
2. 시뮬레이션 시작을 선택하면 창구 개수와 전화 기능 사용 여부를 설정합니다.
3. 시뮬레이션이 시작되면 실시간으로 고객 도착, 대기, 서비스 과정을 GUI로 확인할 수 있습니다.
4. 하루가 종료되면 통계 자료가 출력되며, 다음 날 시뮬레이션을 진행하거나 종료할 수 있습니다.
5. 종료 시 통계 자료는 CSV 파일로 저장됩니다.

---

## 주요 알고리즘
- **큐 관리**: 고객 도착 시 `enqueue` 및 서비스 종료 후 `dequeue` 수행.
- **전화 서비스 우선 처리**: 전화 서비스는 일반 서비스보다 높은 우선순위를 가짐.
- **배속 처리**: 배속 설정에 따라 시간 흐름 속도 조절.

---

## 실행 화면
### 메뉴 화면
<img src="https://github.com/user-attachments/assets/de561eae-987d-4ff3-8d8b-6aa6925714a0" width="50%">

### 창구 및 전화 기능 설정
<img src="https://github.com/user-attachments/assets/e8a821b8-15e4-4b6c-bf7a-bb7b38a8c986" width="50%">
<img src="https://github.com/user-attachments/assets/766f20be-3c67-4c85-8bbc-99d4be61eb50" width="50%">

### 시뮬레이션 종료
<img src="https://github.com/user-attachments/assets/4f7b5934-4be0-4e0c-b621-1b5b82391aef" width="50%">

### 통계 화면
<img src="https://github.com/user-attachments/assets/7ab19f3a-3ebe-4de4-8c96-f8b4a127899b" width="50%">
<img src="https://github.com/user-attachments/assets/153f9ec7-b576-4a44-bf77-c4ea1cbb396b" width="50%">


---

## 실험 결과


| **실행 순서 (창구 개수)** | **평균 대기 시간 (분)** | **평균 서비스 시간 (분)** | **평균 전화 서비스 시간 (분)** | **총 서비스 시간 (분)** | **총 전화 서비스 시간 (분)** | **서비스받은 총 고객 수** | **전화서비스 총 고객 수** | **서비스를 받지 못한 고객 수** |
|---------------------------|-------------------------|---------------------------|-------------------------------|-------------------------|-----------------------------|--------------------------|---------------------------|-----------------------------|
| **1번째 실행 (2개)**      | 24.646                 | 4.402                     | 4.95                          | 361                     | 99                          | 82                       | 20                        | 62                          |
| **2번째 실행 (3개)**      | 8.256                  | 4.248                     | 5.043                         | 548                     | 116                         | 129                      | 23                        | -                           |
| **3번째 실행 (4개)**      | 1.565                  | 4.256                     | 5.152                         | 715                     | 170                         | 168                      | 33                        | -                           |
| **4번째 실행 (1개)**      | 121.738                | 2.115                     | 5.69                          | 129                     | 165                         | 61                       | 29                        | 95                          |


### 해석
위의 표는 창구를 2개, 3개, 4개, 1개로 순서대로 실행했을 때 얻은 결과값을 나타냅니다. 창구 개수와 전화 기능 유무에 따라 다음과 같은 차이를 확인할 수 있습니다:
- 창구 개수가 많을수록 **평균 대기 시간이 감소**하고, **서비스받은 고객 수가 증가**합니다.
- 전화 서비스의 평균 시간이 일정한 수준을 유지하며, 총 전화 서비스 시간과 총 서비스 시간도 창구 개수에 따라 증가합니다.
- 창구가 1개일 경우 대기 시간이 매우 길어지고 서비스받지 못한 고객 수가 급증합니다.


---

## 결론
이 프로젝트를 통해 큐 자료구조와 다중 스레드를 활용한 시뮬레이션 프로그램을 개발했습니다. GUI와 통계 분석 기능을 통해 태피 상점의 운영 효율성을 효과적으로 분석할 수 있었으며, 향후 더 복잡한 시뮬레이션과 확장된 분석 기능을 추가할 가능성을 발견했습니다.

---

## 느낀 점
GUI 프로그래밍과 다중 스레드의 동기화 작업을 수행하며 많은 어려움이 있었으나, 프로젝트를 통해 시뮬레이션 설계 및 구현 능력을 크게 향상시킬 수 있었습니다. 향후에는 배속 기능 개선 및 다양한 입력 조건 지원을 통해 더욱 강력한 시뮬레이션 프로그램으로 발전시키고자 합니다.

---

## 첨부 자료
- 소스코드: `Customer.java`, `TaffyShop.java`, `Main.java`
- 통계 결과 CSV 파일
