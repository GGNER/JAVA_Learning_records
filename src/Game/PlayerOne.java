package Game;

import java.awt.*;
import java.awt.event.KeyEvent;

public class PlayerOne extends Tank {
    public PlayerOne(String img, int x, int y, GamePanel gamePanel,
                     String upImg, String leftImg, String rightImg, String downImg) {
        super(img, x, y, gamePanel, upImg, leftImg, rightImg, downImg);
    }
    //定义四个boolean类型的变量给键盘控制时让其有变化
//    public boolean left;
//    public boolean down;
//    public boolean right;
//    public boolean up;

    //通过键盘控制Tank移动
    //按下时为true，松开为false
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_A:
                left = true;
                break;
            case KeyEvent.VK_S:
                down = true;
                break;
            case KeyEvent.VK_D:
                right = true;
                break;
            case KeyEvent.VK_W:
                up = true;
                break;
            case KeyEvent.VK_SPACE:
                attack();
            default:
                break;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_A:
                left = false;
                break;
            case KeyEvent.VK_S:
                down = false;
                break;
            case KeyEvent.VK_D:
                right = false;
                break;
            case KeyEvent.VK_W:
                up = false;
                break;
            default:
                break;
        }
    }

    public void move() {
        if (left) {
            leftward();
        } else if (right) {
            rightward();
        } else if (up) {
            upward();
        } else if (down) {
            downward();
        }
    }

    @Override
    public void paintSelf(Graphics g) {
        g.drawImage(img, x, y, null);
        move();
    }

    @Override
    public Rectangle getRec() {
        return new Rectangle(x, y, width, height);
    }
}