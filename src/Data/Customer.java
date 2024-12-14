package Data;

public class Customer {//고객 정보 저장
    private int arriveTime;//도착 시간
    private int svcStartTime;//서비스 시작 시간
    private int waitTime;//대기 시간
    private int svcTime;//서비스 소요 시간
    private int Num;//고객 번호
    private boolean isPhoneSvc;
    private static int customerCount = 0;//고객 수, 고객 번호 부여에 사용

    public Customer(int arriveTime) {
        this.Num = ++customerCount;
        this.arriveTime = arriveTime;
        this.svcStartTime = 0;
        this.waitTime = 0;
        this.svcTime = 0;
        this.isPhoneSvc = false;
    }//생성자

    public void setPhoneSvc(boolean isPhone) {
        this.isPhoneSvc = isPhone;
    }

    public void setSvcStartTime(int svcStartTime) {
        this.svcStartTime = svcStartTime;
        this.waitTime = svcStartTime - this.arriveTime;
    }

    public void setSvcTime(int svcTime) {
        int EndTime = this.svcStartTime + svcTime;
        if (EndTime >= 480) {
            this.svcTime = EndTime;
        } else {
            this.svcTime = svcTime;
        }
    }

    public int getCustomerNumber() {
        return this.Num;
    }

    public boolean isPhoneSvc() {
        return this.isPhoneSvc;
    }

    public int getSvcStartTime() {
        return this.svcStartTime;
    }

    public int getSvcTime() {
        return this.svcTime;
    }

    public int getWaitTime() {
        return this.waitTime;
    }

    public int getArriveTime() {
        return this.arriveTime;
    }

    public int getSvcEndTime() {
        return svcStartTime + svcTime;
    }
}