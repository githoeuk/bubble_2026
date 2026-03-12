package _My.test03;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// 플레이어의 충돌을 감지하는 역할 - (백그라운드에서 계속 실행되어야 됨 == 메인 쓰레드에 사용 못함 )
public class BackgroundService2 implements Runnable {

    // ImageIcon , Image = 화면에 보여주기 위한 이미지 = 픽셀 데이터에 직접 접근이 불가능함
    // BufferedImage = 메모리에 픽셀 배열로 저장된 이미지
    // getRGB(x,y)를 이용해 특정 좌표의 색상값을 직접 읽을 수 있음

    private BufferedImage image;
    private Player2 player2;

    // 의존성 주입( DI : Dependency Injection )
    // Player를 생성자를 통해서 외부에서 주입 받음
    // 즉, 이 서비스가 직접 플레이어를 생성하지 않고 외부에서 주입 받아 사용 됨.

    public BackgroundService2(Player2 player2) {
        this.player2 = player2;

        try {
            image = ImageIO.read(new File("img/backgroundMapService.png"));
        } catch (IOException e) {
            throw new RuntimeException("충돌 감지 이미지를 찾을 수 없습니다. " + e);
        }
    }

    @Override
    public void run() {
        // 게임이 끝날 때까지 계속 실행되어야 함 -> 계속 반복
        while (true) {
            // buffered기능 , 충돌 영역
            Color leftColor = new Color(image.getRGB(player2.getX(), player2.getY() + 25));
            Color rightColor = new Color(image.getRGB(player2.getX() + 60, player2.getY() + 25));

            // 왼쪽 벽 충돌 감지
            if (isRed(leftColor)) { // isRed() 최하단에 생성
                //충돌 상태를 표현하는 변수
                player2.setLeftWallCrash(true); // true = 충돌이 감지됨
                player2.setLeft(false); // left_tread 강제 종료를 통해 이동 정지
            } else {
                // 벽에서 벗어나면 해제 -> 왼쪽으로 이동 가능
                player2.setLeftWallCrash(false);
            }
            // 오른쪽 벽 충돌 감지
            if (isRed(rightColor)) {
                player2.setRightWallCrash(true); // 충돌 감지
                player2.setRight(false); // right_tread 강제 종료
            } else {
                player2.setRightWallCrash(false);
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        } // end of while
    } // end of run

    private boolean isRed(Color color) {
        // rgb = 255,0,0  -> Color.color가  255,0,0 가 맞는지 확인.
        return color.getRed() == 255 && color.getGreen() == 0 && color.getBlue() == 0;
    }
}
