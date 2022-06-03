package Game;

import com.sun.javafx.scene.traversal.Direction;

import java.awt.*;
import java.util.ArrayList;

public class Bullet extends GameObject {

    //尺寸
    int width = 10;
    int height = 10;
    //速度
    int speed = 7;
    //方向
    Direction direction;

    public Bullet(String img, int x, int y, GamePanel gamePanel, Direction direction) {
        super(img, x, y, gamePanel);
        this.direction = direction;
    }

    public void leftward() {
        x -= speed;
    }

    public void rightward() {
        x += speed;
    }

    public void upward() {
        y -= speed;
    }

    public void downward() {
        y += speed;
    }

    public void go() {
        switch (direction) {
            case LEFT:
                leftward();
                break;
            case RIGHT:
                rightward();
                break;
            case UP:
                upward();
                break;
            case DOWN:
                downward();
                break;
            default:
                break;
        }
        this.hitWall();
        this.moveToBorder();
        this.hitBase();
    }

    //子弹与墙壁碰撞检测
    public void hitWall() {
        //围墙列表
        ArrayList<Wall> walls = this.gamePanel.wallList;
        //遍历列表
        for (Wall wall : walls) {
            //与每个围墙进行碰撞检测
            if (this.getRec().intersects(wall.getRec())) {
                //删除围墙和子弹
                this.gamePanel.wallList.remove(wall);
                this.gamePanel.removeList.add(this);
                //停止循环
                break;
            }
        }
    }

    //碰撞检测子弹与tank
    public void hitBot() {
        ArrayList<Bot> bots = this.gamePanel.botList;
        for (Bot bot : bots) {
            if (this.getRec().intersects(bot.getRec())) {
                this.gamePanel.boomList.add(new Boom("", bot.x + 20, bot.y + 30, this.gamePanel));
                this.gamePanel.botList.remove(bot);
                this.gamePanel.removeList.add(this);
                break;
            }
        }
    }

    //子弹与基地碰撞检测
    public void hitBase() {
        ArrayList<Base> baseList = this.gamePanel.baseList;
        for (Base base : baseList) {
            if (this.getRec().intersects(base.getRec())) {
                this.gamePanel.baseList.remove(base);
                this.gamePanel.removeList.add(this);
                break;
            }
        }
    }

    public void moveToBorder() {
        if (x < 0 || x + width > this.gamePanel.getWidth()) {
            this.gamePanel.removeList.add(this);
        }
        if (y < 0 || y + height > this.gamePanel.getHeight()) {
            this.gamePanel.removeList.add(this);
        }
    }

    //在绘制里添加子弹运动轨迹这样就可以看见了
    @Override
    public void paintSelf(Graphics g) {
        g.drawImage(img, x, y, null);
        this.go();
        this.hitBot();
        this.hitWall();
    }

    @Override
    public Rectangle getRec() {
        return new Rectangle(x, y, width, height);
    }
}