package _My.test03;

import javax.swing.*;

public class Player2 extends JLabel implements Moveable2 {

    // 플레이어의 현재 위치(좌표 값)
    private int x;
    private int y;

    // 좌우 방향 이미지 (방향키에 따라서 이미지 전환)
    private ImageIcon playerR;
    private ImageIcon playerL;

    // 속도 상수
    private final int SPEED = 4;            // 좌우 이동 속도(픽셀단위)
    private final int JUMP_SPEED = 2;       // 점프 낙하 속도
    private final int JUMP_HEIGHT = 130;    // 점프 최대 높이

    // 이동 상태 플래그(flag)
    // true -> 해당 방향으로 이동 중 (while 루프 조건)
    // false -> 정지 (while 루프 탈출 -> Thread 종료)

    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;

    // 벽 충동 상태 플래그(flag)
    private boolean leftWallCrash;
    private boolean rightWallCrash;

    //getter

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public boolean isUp() {
        return up;
    }

    public boolean isDown() {
        return down;
    }

    public boolean isLeftWallCrash() {
        return leftWallCrash;
    }

    public boolean isRightWallCrash() {
        return rightWallCrash;
    }

    // Bubble Frame의 key 이벤트에서 호출할 수 있도록 setter설정
    // setter

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setLeftWallCrash(boolean leftWallCrash) {
        this.leftWallCrash = leftWallCrash;
    }

    public void setRightWallCrash(boolean rightWallCrash) {
        this.rightWallCrash = rightWallCrash;
    }

    // 생성자
    public Player2() {
        initData();
        setInitLayout();
    }

    // 캐릭터 이미지 생성
    private void initData() {
        playerL = new ImageIcon("img/playerL.png");
        playerR = new ImageIcon("img/playerR.png");
    }

    private void setInitLayout() {
        //캐릭터 초기 설정
        x = 55;
        y = 535;
        setSize(50, 50);
        // 초기 방향
        setIcon(playerR);
        // 초기 위치
        setLocation(x, y);
    }

    // 동작 구현
    @Override
    public void left() {
        if (left) { // 중복을 통한 Thread 중복 생성 방지
            return;
        }
        left = true;
        setIcon(playerL);
        new Thread(new Runnable() {
            @Override
            public void run() {
                // left가 true인 동안 계속 이동
                // left를 keyReleased하면 setLeft를 false처리 함으로 while문 탈출
                while (left) {
                    x = x - SPEED;
                    setLocation(x, y);
                    //너무 빠르기 때문에 지연시간 추가 10ms
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        }).start(); // end of left_Thread
    } // end of left

    @Override
    public void right() {
        if (right) {
            return; // 오른쪽 중복 입력을 통한 Thread생성 방지
        }
        right = true;
        setIcon(playerR);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (right) {
                    x = x + SPEED;
                    setLocation(x, y);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        }).start(); // end of right_Thread
    } // end of right

    @Override
    public void up() {
        if (up) {
            return;
        }
        up = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                // JUMP_HEIGHT(130) /JUMP_SPEED(2) = 65 -> 65픽셀 이동
                for (int i = 0; i < JUMP_HEIGHT / JUMP_SPEED; i++) {
                    y = y - JUMP_SPEED;
                    setLocation(x, y);
                    try {
                        Thread.sleep(5); // 5ms 간격으로 상승
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } // end of up_for
                up = false; // 점프를 통한 최고점 도달 -> 상승 상테 해지
                down(); // down()메서드를 통해 점프 중력 구현
            }
        }).start();// end of up_Thread
    }

    @Override
    public void down() {
        down = true;

        new Thread(new Runnable() {
            @Override
            public void run() {
                // 상승과 동일하게 값으로 하강
                for (int i = 0; i < JUMP_HEIGHT / JUMP_SPEED; i++) {
                    y = y + JUMP_SPEED;
                    setLocation(x,y);
                    try {
                        Thread.sleep(3);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } // end of for
                down = false; // 최저점 도착 시 종료
            }
        }).start();// end of start
    }
} // end of player
