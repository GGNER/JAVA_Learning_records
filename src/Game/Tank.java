package Game;

import com.sun.javafx.scene.traversal.Direction;

import java.awt.*;
import java.util.ArrayList;

public abstract class Tank extends GameObject {
    //游戏内Tank尺寸
    public int width = 40;
    public int height = 50;
    //速度
    private int speed = 3;
    //方向
    //创建一个枚举类 规范Tank的移动方向，参数
    public Direction direction = Direction.UP;
    //Tank生命，表示其是否存活
    public boolean alive = false;
    //添加四个方向的图片，这样简化了Tank每次移动时，键盘跟随指针图片移动
    private String upImg;
    private String leftImg;
    private String rightImg;
    private String downImg;

    //子弹的攻击冷却状态
    private boolean attackCoolDown = true;
    //攻击冷却时间间隔1000ms
    private int attackCoolDownTime = 1000;

    public Tank(String img, int x, int y, GamePanel gamePanel,
                String upImg, String leftImg, String rightImg, String downImg) {
        super(img, x, y, gamePanel);
        this.upImg = upImg;
        this.leftImg = leftImg;
        this.rightImg = rightImg;
        this.downImg = downImg;
    }

    //将Tank图标上下左右移动
    public void leftward() {
        //下面这一行只是控制坦克的移动
//        x-=speed;
        setImg(leftImg);
        //与围墙碰撞检测后的Tank移动方式
        if (!hitWall(x - speed, y) && !moveToBorder(x - speed, y)) {
            this.x -= speed;
        }
        //同下面四个，先前移动方式
        direction = Direction.LEFT;
    }

    public void rightward() {
        //下面这一行只是控制坦克的移动
//        x+=speed;
        setImg(rightImg);
        //加了Tank与墙壁,边界的碰撞检测后才改成如下
        if (!hitWall(x + speed, y) && !moveToBorder(x + speed, y)) {
            this.x += speed;
        }
        direction = Direction.RIGHT;
    }

    public void upward() {
        //下面这一行只是控制坦克的移动
//        y-=speed;
        setImg(upImg);
        if (!hitWall(x, y - speed) && !moveToBorder(x, y - speed)) {
            this.y -= speed;
        }
        direction = Direction.UP;
    }

    public void downward() {
        //下面这一行只是控制坦克的移动
//        y+=speed;
        setImg(downImg);
        if (!hitWall(x, y + speed) && !moveToBorder(x, y + speed)) {
            this.y += speed;
        }
        direction = Direction.DOWN;
    }

    //子弹攻击
    public void attack() {
        if (attackCoolDown && alive) {
            Point p = this.getHeadPoint();
            Bullet bullet = new Bullet("D:\\IDEA\\zuoye\\duixiang\\images\\bullet.png",
                    p.x, p.y, this.gamePanel, direction);
            this.gamePanel.bulletList.add(bullet);
            //线程开始
            new AttackCD().start();
        }
    }

    //新线程
    //内部类
    class AttackCD extends Thread {
        public void run() {
            //将攻击功能设置为冷却状态
            attackCoolDown = false;
            //休眠一秒
            try {
                Thread.sleep(attackCoolDownTime);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //将攻击功能解除冷却状态
            attackCoolDown = true;
            //线程终止
            this.stop();
        }
    }

    //Tank头部坐标获取，这样可以用来将子弹发射的方向与Tank对应
    public Point getHeadPoint() {
        switch (direction) {
            case LEFT:
                return new Point(x, y + height / 2);
            case RIGHT:
                return new Point(x + width, y + height / 2);
            case UP:
                return new Point(x + width / 2, y);
            case DOWN:
                return new Point(x + width / 2, y + height);
            default:
                return null;
        }
    }

//    public boolean left;
//    public boolean down;
//    public boolean right;
//    public boolean up;

    //Tank与围墙碰撞检测
    public boolean hitWall(int x, int y) {
        //围墙列表
        ArrayList<Wall> walls = this.gamePanel.wallList;
        //规定围墙范围的矩形
        Rectangle next = new Rectangle(x, y, width, height);
        //遍历列表
        for (Wall wall : walls) {
            //与每一个围墙进行碰撞检测
            if (next.intersects(wall.getRec())) {
                //发生碰撞返回true
                return true;
            }
        }
        return false;
    }

    //指针图片控制
    public void setImg(String img) {
        this.img = Toolkit.getDefaultToolkit().getImage(img);
    }

    //Tank到达边界检测装置
    public boolean moveToBorder(int x, int y) {
        //检测坦克到达边界最左边
        if (x < 0) {
            return true;
        }
        //检测坦克到达边界最右边
        else if (x + width > this.gamePanel.getWidth()) {
            return true;
        }
        //检测坦克到达边界最上边
        else if (y < 0) {
            return true;
        }
        //检测坦克到达边界最下边
        else if (y + height > this.gamePanel.getHeight()) {
            return true;
        }
        return false;
    }

    @Override
    public abstract void paintSelf(Graphics g);

    @Override
    public abstract Rectangle getRec();
}