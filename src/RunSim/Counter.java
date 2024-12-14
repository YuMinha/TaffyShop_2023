package RunSim;

import Data.Customer;
import Data.Queue;

import java.util.Random;

public class Counter implements Runnable {
    private final int ID;//창구의 ID
    private final Queue queue;
    private final Queue call;
    private boolean isWork;
    private static final int unitTime = TaffyShop.getUnitTime();
    ;

    public Counter(int ThreadID, Queue queue, Queue callque) {
        this.call = callque;
        this.ID = ThreadID;
        this.queue = queue;
        this.isWork = false;
    }

    public boolean isWorking() {
        return this.isWork;
    }

    @Override
    public void run() {
        /*각 창구는 쉬고 있다면-> 대기 큐가 비어있는 지 확인
	        비어있지않다면 꺼내오기
	        비어있다면 계속 확인
        꺼내왔으면 서비스 시작 시간 체크
	    (고객 객체의 시작 시간 저장, 현재시각에서 고객 도착 시간을 빼서 전체 대기 시간에 저장)
        서비스 시간 난수로 생성(1~10) -> 고객 객체의 서비스 시간에 저장, 전체 서비스 시간에 저장
        (시작 시간 + 나온 난수)가 되었다면 서비스 종료*/
        Random random = new Random();
        while (true) {
            TaffyShop.updateCounterStatus(ID, isWork);//먼저 상태 업데이트
            if (TaffyShop.getTime() == 480) {
                break;
            }
            if (queue.isEmpty()) {
                try {
                    Thread.sleep(10 / TaffyShop.getSpeedMultiplier());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (!call.isEmpty() && !isWork)//일을 하지않고 전화 대기 큐에 사람이 있으면 진행
            {
                Customer customer = call.dequeue();//고객 디큐해오고
                TaffyShop.updateQueueDelete();//큐 GUI의 상태 업데이트

                if (customer != null) {
                    this.isWork = true;
                    TaffyShop.updateText("창구 " + ID + " | 전화 서비스 수행 중");
                    ;
                    TaffyShop.updateCounterCall(ID, isWork);
                    customer.setSvcStartTime(TaffyShop.getTime());//서비스 시작 시간을 설정
                    int callSvc = random.nextInt(10) + 1;
                    customer.setSvcTime(callSvc);
                    if (customer.getSvcEndTime() >= 480) {//전화 서비스 종료 시간이 480분을 넘어가면 480분이 될 떄 종료 설정
                        int remainTime = 480 - customer.getSvcStartTime();
                        if (remainTime > 0) {
                            int SvcTime = remainTime - 1;
                            customer.setSvcTime(SvcTime);
                        } else {
                            customer.setSvcTime(0);
                            break;
                        }
                    }
                    try {
                        Thread.sleep(unitTime * (callSvc) / TaffyShop.getSpeedMultiplier());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    TaffyShop.updateText("창구 " + ID + " | 전화 서비스 종료");
                    TaffyShop.addServicedCustomer(customer);
                    this.isWork = false;
                    try {
                        Thread.sleep(unitTime / TaffyShop.getSpeedMultiplier());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else if (!queue.isEmpty() && !isWork) {
                Customer customer = queue.dequeue();//대기 중인 고객 데려오기
                TaffyShop.updateQueueDelete();

                if (customer != null) {
                    this.isWork = true;
                    TaffyShop.updateCounterStatus(ID, isWork);
                    TaffyShop.updateText("창구 " + ID + " | 서비스 시작");
                    customer.setSvcStartTime(TaffyShop.getTime());

                    int svcDuration = random.nextInt(10) + 1;
                    customer.setSvcTime(svcDuration);

                    if (customer.getSvcEndTime() >= 480) {//서비스 종료 시간이 480분을 넘어가면 480분이 될 떄 종료 설정
                        int remainTime = 480 - customer.getSvcStartTime();
                        if (remainTime > 0) {
                            int SvcTime = remainTime - 1;
                            customer.setSvcTime(SvcTime);
                        } else {
                            customer.setSvcTime(0);
                            break;
                        }
                    }
                    try {
                        Thread.sleep(unitTime * (svcDuration) / TaffyShop.getSpeedMultiplier());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    TaffyShop.updateText("창구 " + ID + " | 서비스 종료");
                    TaffyShop.addServicedCustomer(customer);
                    this.isWork = false;

                    try {
                        Thread.sleep(unitTime / TaffyShop.getSpeedMultiplier());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


            }
        }
    }
}
