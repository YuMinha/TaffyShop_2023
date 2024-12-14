package RunSim;

import Data.Customer;
import Data.Queue;
import GUI.SimulationGUI;

import java.util.ArrayList;
import java.util.Random;

public class TaffyShop {
    private static long speedMultiplier;//배속 설정 값
    private final static int unitTime = 60000;//단위 시간 설정
    private int CounterNum;//창구의 개수
    private final boolean iscall;//전화 기능 유무
    private static Queue queue;
    private static Queue waitPhQueue;
    private static ArrayList<Customer> customerSvc;//서비스를 받은 고객의 정보 저장
    private Thread[] counters;
    private static volatile int time; // 값이 자꾸 달라짐
    private static SimulationGUI gui;

    public TaffyShop(int CounterNum, boolean iscall) {
        customerSvc = new ArrayList<>();
        time = 0;
        speedMultiplier = 100;

        this.CounterNum = CounterNum;
        this.iscall = iscall;
        this.queue = new Queue();
        this.waitPhQueue = new Queue();

        counters = new Thread[CounterNum];
        for (int i = 0; i < CounterNum; i++) {
            counters[i] = new Thread(new Counter(i + 1, queue, waitPhQueue));
        }
        gui = new SimulationGUI(CounterNum);
        gui.updateTime(time);
    }

    public static void resetSimulation() {
        customerSvc.clear();
        time = 0;
        queue.clear();
        waitPhQueue.clear();

    }

    public void Start() {
        /*난수 생성(1~4), 4-> 고객 입장
		-고객 객체 생성
		-고객 객체의 고객 번호 저장
		-고객 객체의 도착 시간 저장
		-대기 큐에 넣기*/
        for (Thread counter : counters) {
            counter.start();
        }

        Thread simulationThread = new Thread(() ->
        {
            Random random = new Random();
            Random phoneRand = new Random();
            while (time < 480) {
                int arriveRandom = random.nextInt(4) + 1;
                if (arriveRandom == 4) {
                    Customer customer = createCustomer();
                    customer.setPhoneSvc(false);
                    updateText(" | 새로운 고객 입장 " + customer.getCustomerNumber() + " |");
                    gui.updateQueueAdd();
                    queue.enqueue(customer);
                }
                if (iscall)//전화 기능 유무
                {
                    int phRandom = phoneRand.nextInt(16) + 1;
                    if (phRandom == 16) {
                        Customer phcustomer = createCustomer();
                        phcustomer.setPhoneSvc(true);
                        updateText(" | 전화수신중" + phcustomer.getCustomerNumber() + " |");
                        waitPhQueue.enqueue(phcustomer);
                    }
                }
                try {
                    Thread.sleep(unitTime / speedMultiplier);//실제 1분 * 배속
                    gui.updateTime(time);
                    time++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (Thread counter : counters) {
                try {
                    counter.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            updateText("| 태피 상점 마감 |");
            gui.EndSim();
            printStatics();
            print_NoneSvc();
        });
        simulationThread.start();

    }

    public static synchronized void updateCounterCall(int ID, boolean status) {
        gui.updateCounterCall(ID, status);
    }

    public static synchronized void updateCounterStatus(int ID, boolean status) {
        gui.updateCounterStatus(ID, status);
    }

    public static synchronized void updateQueueDelete() {
        gui.updateQueueDelete();
    }

    public static synchronized void updateText(String text) {
        gui.updateText(text);
    }

    public static synchronized int getUnitTime() {
        return unitTime;
    }

    private Customer createCustomer() {
        return new Customer(time);
    }

    public synchronized static void addServicedCustomer(Customer customer) {
        customerSvc.add(customer);
    }

    public static int getTime() {
        return time;
    }

    public static void setSpeedMultiplier(long Mul) {
        speedMultiplier = Mul;
    }

    public static synchronized long getSpeedMultiplier() {
        return speedMultiplier;
    }

    public void print_NoneSvc() {
        if (!queue.isEmpty()) {
            To_row("서비스를 받지 못한 고객", queue.getSize());
        }
        if (!waitPhQueue.isEmpty()) {
            To_row("전화 서비스를 받지 못한 고객", waitPhQueue.getSize());
        }
    }

    public static double RoundMath(int number, int div) {
        double Num = (double) number / (double) div;
        return Num;
    }

    public static void printStatics() {
        int to_WaitingTime = 0;
        int to_SvcTiem = 0;
        int to_CallSvcTime = 0;
        int callSvcCustomer = 0;

        synchronized (customerSvc) {
            for (Customer customer : customerSvc) {
                if (customer.isPhoneSvc()) {
                    Object[] row = {customer.getCustomerNumber()
                            , customer.getArriveTime()
                            , customer.getSvcStartTime()
                            , customer.getWaitTime()
                            , customer.getSvcTime()};
                    gui.PrintCallStatus(row);
                    to_CallSvcTime += customer.getSvcTime();
                    callSvcCustomer++;
                } else {
                    Object[] rowData = {customer.getCustomerNumber()
                            , customer.getArriveTime()
                            , customer.getSvcStartTime()
                            , customer.getWaitTime()
                            , customer.getSvcTime()};
                    gui.PrintStatus(rowData);
                    to_WaitingTime += customer.getWaitTime();
                    to_SvcTiem += customer.getSvcTime();
                }
            }
        }
        int cus = customerSvc.size();
        To_row("평균 대기 시간", RoundMath(to_WaitingTime, cus));
        To_row("평균 서비스 시간", RoundMath(to_SvcTiem, cus));
        To_row("평균 전화 서비스 시간", RoundMath(to_CallSvcTime, callSvcCustomer));
        To_row("총 서비스 시간", to_SvcTiem);
        To_row("총 전화 서비스 시간", to_CallSvcTime);
        To_row("서비스받은 총 고객 수", customerSvc.size());
        To_row("전화서비스 총 고객 수", callSvcCustomer);

    }

    public static void To_row(String text, double a) {
        Object[] row = {text, String.format("%.3f", a)};
        gui.PrintTotalStatus(row);
    }
}
