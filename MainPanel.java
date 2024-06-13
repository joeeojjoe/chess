import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/*
 类MainPanel主要完成如下功能：
 1、构建一个面板，在该面板上画上棋盘；
 2、处理在该棋盘上的鼠标事件
 */
public class MainPanel extends JPanel
        implements MouseListener, MouseMotionListener {
    private int width,height;  //棋盘的宽度和高度
    private ChessModel cm;

    //根据棋盘模式设定面板的大小
    MainPanel(ChessModel mm){
        cm=mm;
        width=cm.getWidth();
        height=cm.getHeight();
        addMouseListener(this);
    }

    //根据棋盘模式设定棋盘的宽度和高度
    public void setModel(ChessModel mm){
        cm = mm;
        width = cm.getWidth();
        height = cm.getHeight();
    }

    //根据坐标计算出棋盘方格棋子的信息（如白子还是黑子），
    //然后调用draw方法在棋盘上画出相应的棋子
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        for(int j = 0; j <= height; j++){
            for(int i = 0; i <= width; i++){
                int v = cm.getarrMapShow()[i][j];
                draw(g, i, j, v);
            }
        }
    }

    //根据提供的棋子信息（颜色、坐标）画棋子
    public void draw(Graphics g, int i, int j, int v){
        int x = 20 * i+20;
        int y = 20 * j+20;
        //画棋盘
        if(i!=width && j!=height){
            g.setColor(Color.white);
            g.drawRect(x,y,20,20);
        }
        //画黑色棋子
        if(v == 1 ){
            g.setColor(Color.gray);
            g.drawOval(x-8,y-8,16,16);
            g.setColor(Color.black);
            g.fillOval(x-8,y-8,16,16);
        }
        //画白色棋子
        if(v == 2 ){
            g.setColor(Color.gray);
            g.drawOval(x-8,y-8,16,16);
            g.setColor(Color.white);
            g.fillOval(x-8,y-8,16,16);
        }
        if(v ==3){
            g.setColor(Color.cyan);
            g.drawOval(x-8,y-8,16,16);
        }
    }

    //响应鼠标的点击事件，根据鼠标的点击来下棋，
    @Override
    public void mousePressed(MouseEvent evt){
        int x = (evt.getX()-10) / 20;
        int y = (evt.getY()-10) / 20;
        System.out.println(x+" "+y);
        if (evt.getModifiers()==MouseEvent.BUTTON1_MASK){
            cm.play(x,y);
            System.out.println(cm.getIsOdd()+" "+cm.getarrMapShow()[x][y]);
            repaint();
            if(cm.judgeSuccess(x,y,cm.getIsOdd())){
                cm.showSuccess(this);
                evt.consume();
                ChessFrame.iscomputer=false;
            }
            //判断是否为人机对弈
            if(ChessFrame.iscomputer&&!cm.getisExist()){
                cm.computerDo(cm.getWidth(),cm.getHeight());
                repaint();
                if(cm.judgeSuccess(cm.getX(),cm.getY(),cm.getIsOdd())){
                    cm.showDefeat(this);
                    evt.consume();
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent evt){}
    @Override
    public void mouseReleased(MouseEvent evt){}
    @Override
    public void mouseEntered(MouseEvent mouseevt){}
    @Override
    public void mouseExited(MouseEvent mouseevent){}
    @Override
    public void mouseDragged(MouseEvent evt){}

    //响应鼠标的拖动事件
    @Override
    public void mouseMoved(MouseEvent moveevt){
        int x = (moveevt.getX()-10) / 20;
        int y = (moveevt.getY()-10) / 20;
        cm.readyplay(x,y);
        repaint();
    }
}

class ChessWindowEvent extends WindowAdapter {
    @Override
    public void windowClosing(WindowEvent e){
        System.exit(0);
    }

    ChessWindowEvent()

    {
    }
}
