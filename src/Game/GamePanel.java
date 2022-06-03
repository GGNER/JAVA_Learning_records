package Game;

/*
Q1:.classpath配置出现问题，图标显示不出来
Q2:页面闪烁，字体不稳
Q3:加入双缓存后，产生新的问题，字体不闪烁但还是没有小坦克指针
G:路径配置不全
Q4:1,2键位响应有问题
G：中英文按键
Q5:P1中的按键无法使用，原因是未给局部变量定义
G:定义四个boolean类型的变量给键盘控制时让其有变化
Q6：使用Tank类参数时无法使用Direction
G：导包！！！
Q7:创建Wall类时，墙体为固定位置的类
它的长宽应当一样长短，在Wall类中加入length，控制变量，使得墙体块是个正方形
*/

import com.sun.deploy.appcontext.AppContext;

import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JFrame {
    public AppContext WallList;
    //定义双缓存图片
    Image offScreemImage = null;
    //窗口长度
//    int width = 800;
//    int height = 610;
    int width = 1500;
    int height = 1000;

    //指针图片
    Image select = Toolkit.getDefaultToolkit().getImage("D:\\IDEA\\zuoye\\duixiang\\images\\NewTank.png");
    //指针初始纵坐标
    int y = 250;
    //游戏模式 0 游戏未开始 1 单人 2 双人 5 win game
    int state = 0;
    int a = 1;

    //重绘次数
    int count = 0;
    //已生成敌人数量
    int enemyCount = 0;

    //游戏元素列表<子弹>
    ArrayList<Bullet> bulletList = new ArrayList<Bullet>();
    //游戏元素列表<emeny>
    ArrayList<Bot> botList = new ArrayList<Bot>();
    //碰撞检测 子弹与墙壁
    ArrayList<Bullet> removeList = new ArrayList<Bullet>();
    //玩家列表
    ArrayList<Tank> playerList = new ArrayList<Tank>();
    //墙壁列表
    ArrayList<Wall> wallList = new ArrayList<Wall>();
    //基地列表
    ArrayList<Base> baseList = new ArrayList<Base>();
    //爆炸元素列表
    ArrayList<Boom> boomList = new ArrayList<Boom>();

//    //Bot
//    Bot bot = new Bot("D:\\IDEA\\zuoye\\duixiang\\images\\enemyU1.png",
//            500, 110, this,
//            "D:\\IDEA\\zuoye\\duixiang\\images\\enemyU1.png",
//            "D:\\IDEA\\zuoye\\duixiang\\images\\enemyL1.png",
//            "D:\\IDEA\\zuoye\\duixiang\\images\\enemyR1.png",
//            "D:\\IDEA\\zuoye\\duixiang\\images\\enemyD1.png");

    //PlayerOne
    PlayerOne playerOne = new PlayerOne("D:\\IDEA\\zuoye\\duixiang\\images\\myTankU1.png",
            375, 875, this,
            "D:\\IDEA\\zuoye\\duixiang\\images\\myTankU1.png",
            "D:\\IDEA\\zuoye\\duixiang\\images\\myTankL1.png",
            "D:\\IDEA\\zuoye\\duixiang\\images\\myTankR1.png",
            "D:\\IDEA\\zuoye\\duixiang\\images\\myTankD1.png");

    //PlayerTwo
    PlayerTwo playerTwo = new PlayerTwo("D:\\IDEA\\zuoye\\duixiang\\images\\otherTankU1.png",
            1125, 875, this,
            "D:\\IDEA\\zuoye\\duixiang\\images\\otherTankU1.png",
            "D:\\IDEA\\zuoye\\duixiang\\images\\otherTankL1.png",
            "D:\\IDEA\\zuoye\\duixiang\\images\\otherTankR1.png",
            "D:\\IDEA\\zuoye\\duixiang\\images\\otherTankD1.png");

    //Base
    Base base = new Base("D:\\IDEA\\zuoye\\duixiang\\images\\base.png",
            750, 935, this);

    //窗口启动
    public void launch() {
        //标题
        setTitle("GGNER'S Tank Battle");
        //窗口初始大小
        setSize(width, height);
        //居中屏幕
        setLocationRelativeTo(null);
        // 添加关闭事件
        setDefaultCloseOperation(3);
        //用户不能调整大小
        setResizable(false);
        //使窗口可见
        setVisible(true);
        //添加键盘监视器
        this.addKeyListener(new KeyMonitor());

        //添加围墙
        for (int i = 0; i < 25; i++) {
            wallList.add(new Wall("D:\\IDEA\\zuoye\\duixiang\\images\\wall.png",
                    i * 60, 200, this));
//            wallList.add(new Wall("D:\\IDEA\\zuoye\\duixiang\\images\\wall.png",
//                    i*60,320,this));
//            wallList.add(new Wall("D:\\IDEA\\zuoye\\duixiang\\images\\wall.png",
//                    i*60,380,this));
//            wallList.add(new Wall("D:\\IDEA\\zuoye\\duixiang\\images\\wall.png",
//                    i*60,440,this));
//            wallList.add(new Wall("D:\\IDEA\\zuoye\\duixiang\\images\\wall.png",
//                    i*60,500,this));
//            wallList.add(new Wall("D:\\IDEA\\zuoye\\duixiang\\images\\wall.png",
//                    i*60,560,this));
            wallList.add(new Wall("D:\\IDEA\\zuoye\\duixiang\\images\\wall.png",
                    i * 60, 680, this));
        }
        //LOGO-GGNER  图像显示
        for (int n = 0; n < 10; n++) {
            wallList.add(new Wall("D:\\IDEA\\zuoye\\duixiang\\images\\wall.png",
                    n * 60, 320, this));
            wallList.add(new Wall("D:\\IDEA\\zuoye\\duixiang\\images\\wall.png",
                    n * 60, 560, this));
        }
        for (int n1 = 10; n1 < 12; n1++) {
            wallList.add(new Wall("D:\\IDEA\\zuoye\\duixiang\\images\\wall.png",
                    n1 * 60, 320, this));
        }
        for (int n2 = 14; n2 < 25; n2++) {
            wallList.add(new Wall("D:\\IDEA\\zuoye\\duixiang\\images\\wall.png",
                    n2 * 60, 320, this));
        }
        for (int n3 = 13; n3 < 21; n3++) {
            wallList.add(new Wall("D:\\IDEA\\zuoye\\duixiang\\images\\wall.png",
                    n3 * 60, 560, this));
        }
        for (int n4 = 15; n4 < 19; n4++) {
            wallList.add(new Wall("D:\\IDEA\\zuoye\\duixiang\\images\\wall.png",
                    n4 * 60, 440, this));
        }
        for (int n5 = 21; n5 < 25; n5++) {
            wallList.add(new Wall("D:\\IDEA\\zuoye\\duixiang\\images\\wall.png",
                    n5 * 60, 440, this));
        }
        for (int l = 0; l < 3; l++) {
            wallList.add(new Wall("D:\\IDEA\\zuoye\\duixiang\\images\\wall.png",
                    0, l * 60 + 380, this));
            wallList.add(new Wall("D:\\IDEA\\zuoye\\duixiang\\images\\wall.png",
                    300, l * 60 + 380, this));
            wallList.add(new Wall("D:\\IDEA\\zuoye\\duixiang\\images\\wall.png",
                    600, l * 60 + 380, this));
            wallList.add(new Wall("D:\\IDEA\\zuoye\\duixiang\\images\\wall.png",
                    720, l * 60 + 380, this));
            wallList.add(new Wall("D:\\IDEA\\zuoye\\duixiang\\images\\wall.png",
                    840, l * 60 + 380, this));
            wallList.add(new Wall("D:\\IDEA\\zuoye\\duixiang\\images\\wall.png",
                    900, l * 60 + 380, this));
            wallList.add(new Wall("D:\\IDEA\\zuoye\\duixiang\\images\\wall.png",
                    1200, l * 60 + 380, this));
        }
        for (int l1 = 0; l1 < 2; l1++) {
            wallList.add(new Wall("D:\\IDEA\\zuoye\\duixiang\\images\\wall.png",
                    240, l1 * 60 + 440, this));
            wallList.add(new Wall("D:\\IDEA\\zuoye\\duixiang\\images\\wall.png",
                    540, l1 * 60 + 440, this));
        }
        wallList.add(new Wall("D:\\IDEA\\zuoye\\duixiang\\images\\wall.png",
                1440, 560, this));
        wallList.add(new Wall("D:\\IDEA\\zuoye\\duixiang\\images\\wall.png",
                600, 560, this));
        wallList.add(new Wall("D:\\IDEA\\zuoye\\duixiang\\images\\wall.png",
                180, 440, this));
        wallList.add(new Wall("D:\\IDEA\\zuoye\\duixiang\\images\\wall.png",
                480, 440, this));
        wallList.add(new Wall("D:\\IDEA\\zuoye\\duixiang\\images\\wall.png",
                1320, 500, this));
        wallList.add(new Wall("D:\\IDEA\\zuoye\\duixiang\\images\\wall.png",
                1380, 560, this));
        wallList.add(new Wall("D:\\IDEA\\zuoye\\duixiang\\images\\wall.png",
                1440, 380, this));

        //base旁边的砖
        wallList.add(new Wall("D:\\IDEA\\zuoye\\duixiang\\images\\wall.png",
                690, 935, this));
        wallList.add(new Wall("D:\\IDEA\\zuoye\\duixiang\\images\\wall.png",
                690, 875, this));
        wallList.add(new Wall("D:\\IDEA\\zuoye\\duixiang\\images\\wall.png",
                750, 875, this));
        wallList.add(new Wall("D:\\IDEA\\zuoye\\duixiang\\images\\wall.png",
                810, 935, this));
        wallList.add(new Wall("D:\\IDEA\\zuoye\\duixiang\\images\\wall.png",
                810, 875, this));

        //添加基地
        baseList.add(base);

        //重绘
        while (true) {

            //游戏胜利判定
            if (botList.size() == 0 && enemyCount == 10) {
                state = 5;
            }
            //游戏失败判定
            if (playerList.size() == 0 && (state == 1 || state == 2) || baseList.size() == 0) {
                state = 4;
            }

            //添加emeny
            if (count % 100 == 1 && enemyCount < 10 && (state == 1 || state == 2)) {
                //随机生成enemy
                Random random = new Random();
                int rnum = random.nextInt(800);
                //添加enemy
                botList.add(new Bot("D:\\IDEA\\zuoye\\duixiang\\images\\enemyU1.png",
                        rnum, 110, this,
                        "D:\\IDEA\\zuoye\\duixiang\\images\\enemyU1.png",
                        "D:\\IDEA\\zuoye\\duixiang\\images\\enemyL1.png",
                        "D:\\IDEA\\zuoye\\duixiang\\images\\enemyR1.png",
                        "D:\\IDEA\\zuoye\\duixiang\\images\\enemyD1.png"));
                enemyCount++;
            }

            repaint();
            try {
                Thread.sleep(25);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //paint方法
    @Override
    public void paint(Graphics g) {
        System.out.println(bulletList.size());
        //创建和窗口一样大小的图片
        if (offScreemImage == null) {
            offScreemImage = this.createImage(width, height);
        }
        //获得该图片画笔
        Graphics gImage = offScreemImage.getGraphics();
        //画笔颜色
        gImage.setColor(Color.gray);
        //绘制实心矩形（填充整个窗口）
        gImage.fillRect(0, 0, width, height);
        //画笔颜色
        gImage.setColor(Color.blue);
        //字体格式
        gImage.setFont(new Font("黑体", Font.BOLD, 80));
        //state=0,游戏未开始
        if (state == 0) {
            //添加文字
            gImage.drawString("选择游戏模式", 480, 200);
            //画笔颜色
            gImage.setColor(Color.white);
            //字体格式
            gImage.setFont(new Font("黑体", Font.BOLD, 50));
            gImage.drawString("One Player", 540, 300);
            gImage.drawString("Two Players", 540, 400);
            //画笔颜色
            gImage.setColor(Color.blue);
            //字体格式
            gImage.setFont(new Font("黑体", Font.BOLD, 80));
            gImage.drawString("1，2选择模式，ENTER开始游戏", 160, 550);
            gImage.setColor(Color.red);
            gImage.drawString("*切换键盘到英文模式*", 320, 800);
            //绘制指针
            gImage.drawImage(select, 480, y, null);
        }
        //state==1||2,游戏开始
        else if (state == 1 || state == 2) {
            //增加后的游戏画面
            //画笔颜色
            gImage.setColor(Color.black);
            //绘制实心矩形（填充整个窗口）
            gImage.fillRect(0, 0, width, height);
            gImage.setFont(new Font("仿宋", Font.BOLD, 30));
            gImage.setColor(Color.red);
            gImage.drawString("剩余敌人:" + botList.size(), 10, 70);
            //刚开始的游戏画面
//            gImage.drawString("Game playing...", 220, 100);
//            if (state == 1) {
//                gImage.drawString("One Player", 220, 200);
//            } else {
//                gImage.drawString("Two Players", 220, 300);
//            }

            //绘制游戏元素P1
            for (Tank player : playerList) {
                player.paintSelf(gImage);
            }
//            playerOne.paintSelf(gImage);
            for (Bullet bullet : bulletList) {
                bullet.paintSelf(gImage);
            }
            bulletList.removeAll(removeList);
            for (Bot bot : botList) {
                bot.paintSelf(gImage);
            }
            for (Wall wall : wallList) {
                wall.paintSelf(gImage);
            }
            for (Base base : baseList) {
                base.paintSelf(gImage);
            }
            for (Boom boom : boomList) {
                boom.paintSelf(gImage);
            }
            //重绘一次
            count++;
//            for(Wall wall:wallList){
//                wall.paintSelf(gImage);
//            }
//            wallList.paintSelf(gImage);
        } else if (state == 3) {
            //画笔颜色
            gImage.setColor(Color.white);
            //字体格式
            gImage.setFont(new Font("黑体", Font.BOLD, 100));
            gImage.drawString("Pause", 600, 500);
        } else if (state == 4) {
            //画笔颜色
            gImage.setColor(Color.red);
            //字体格式
            gImage.setFont(new Font("黑体", Font.BOLD, 300));
            gImage.drawString("Defeat", 250, 600);
        } else if (state == 5) {
            //画笔颜色
            gImage.setColor(Color.blue);
            //字体格式
            gImage.setFont(new Font("黑体", Font.BOLD, 300));
            gImage.drawString("Victory", 220, 600);
        }
        //将缓存区的图片一次绘制在窗口当中
        g.drawImage(offScreemImage, 0, 0, null);
        //添加重绘次数

    }

    //键盘监视器
    class KeyMonitor extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
//            System.out.println(e.getKeyChar());
            //返回键值
            int key = e.getKeyCode();
            //System.out.println(key);
            switch (key) {
                case KeyEvent.VK_1:
                    a = 1;
                    y = 250;
                    break;
                case KeyEvent.VK_2:
                    a = 2;
                    y = 350;
                    break;
                case KeyEvent.VK_ENTER:
                    state = a;
                    playerList.add(playerOne);
                    if (state == 2) {
                        playerList.add(playerTwo);
                        playerTwo.alive = true;
                    }
                    playerOne.alive = true;
                    break;
                case KeyEvent.VK_P:
                    if (state != 3) {
                        a = state;
                        state = 3;
                    } else {
                        state = a;
                        if (a == 0) {
                            a = 1;
                        }
                    }
                default:
                    playerOne.keyPressed(e);
                    playerTwo.keyPressed(e);
            }
        }

        //松开键盘
        @Override
        public void keyReleased(KeyEvent e) {
            playerOne.keyReleased(e);
            playerTwo.keyReleased(e);
        }
    }

    //main
    public static void main(String[] args) {
        GamePanel gp = new GamePanel();
        gp.launch();
    }
}