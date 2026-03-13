package Thread;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class BankAccount {

    private int money = 100_000;

    // 기능 : 입금, 출금
    // synchronized - 동기화
    // 입금 기능

    public void saveMoney(int money) {
        synchronized (this) {
            // 현재 금액을 지역 변수에 저장
            int currentMoney = getMoney(); // 어노테이션 이용

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            // 10만원(currentMoney) + 3만원(money)
            setMoney(currentMoney + money);
            System.out.println("입금 후 현재 계좌 잔액 : " + getMoney());
        }
    } // end  of saveMoney


    // 출금 기능
    public int withDraw(int money) {
        // 블럭을 사용해서 동기화 처리
        synchronized (this) {
            int currentMoney = getMoney();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            // 출금하는 금액만큼 차감
            // 방어코드 생략
            setMoney(currentMoney - money);
            System.out.println("출금 후 현재 계좌 잔액 : " + getMoney());
            return money;
        }
    } // end of withDraw
}
