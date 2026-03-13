package Thread;

public class Main {

    // 메인 쓰레드
    public static void main(String[] args) {

        //초기 조건 10만원 보유
        BankAccount bankAccount = new BankAccount();

        // 부모님이 같은 자원을 공유하게 설정되어 있음
        Father father = new Father(bankAccount);
        Mother mother = new Mother(bankAccount);

        // 멀티 쓰레드로 구성되어 있다.

        // 아버지가 먼저 입금 (10_000원)
        father.start();
        // 어머니가 그 이후에 출금(5_000원)
        mother.start();

    } // end of main
} // end of class
