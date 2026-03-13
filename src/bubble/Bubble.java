package bubble;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;

@Getter
@Setter

// 물방울 클래스
public class Bubble extends JLabel implements Moveable {

    private int x;
    private int y;

    private ImageIcon bubbleIcon;
    private Player player; //플레이어의 위치값 , 방향을 알아야하기 때문

    //버블 이동 상태 플래그
    private static final int HORIZONTAL_DISTANCE = 400;     // 수평 이동 거리
    private static final int BUBBLE_SPEED_MS = 1;           // 이동 간격(ms)
    private static final int SCREEN_TOP = 0;                 // 화면 상단 경계
    private boolean leftMoving = false;
    private boolean rightMoving = false;
    private boolean upMoving = false;

    // getter


    // DI
    public Bubble(Player player) {
        this.player = player;
        initData();
        setInitLayout();
        bubbleStartThread(); // 생성과 동시에 플레이어 방향 판단해서 바로 이동 시작
    }

    // 물방울 이동 Thread 시작
    public void bubbleStartThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (player.getPlayerWay() == PlayerWay.LEFT) {
                    left(); // 왼쪽으로 400 px 이동 ->> 완료 후 up()호출
                } else {
                    right(); // 왼쪽으로 400 px 이동 ->> 완료 후 up()호출
                }

            } // end of run
        }).start();
    }

    private void initData() {
        bubbleIcon = new ImageIcon("img/bubble.png");

    }

    private void setInitLayout() {
        x = player.getX();
        y = player.getY();

        setIcon(bubbleIcon);
        setSize(50, 50);
        setLocation(x, y);
        setVisible(true);
    }


    @Override
    public void left() {
        leftMoving = true;
        for (int i = 0; i < HORIZONTAL_DISTANCE; i++) {
            x--;
            setLocation(x,y);
            try {
                Thread.sleep(BUBBLE_SPEED_MS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        leftMoving = false;
        up(); // 수평이동 완료 후 상승 시작
    }

    @Override
    public void right() {
        rightMoving = true;
        for (int i = 0; i < HORIZONTAL_DISTANCE; i++) {
            x++;
            setLocation(x, y);
            try {
                Thread.sleep(BUBBLE_SPEED_MS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } // end of for
        rightMoving = false;
        up(); // 수평 이동 완료 후 상승
    }

    @Override
    public void up() {
        upMoving = true;
        while (y > SCREEN_TOP){  //y좌표가 0일떄까지
            y--;
            setLocation(x,y);
            try {
                Thread.sleep(BUBBLE_SPEED_MS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } // end of while
        upMoving = false;

    }
}
