import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 *类ChessFrame主要功能是创建五子棋游戏主窗体和菜单
 */
public class ChessFrame extends JFrame implements ActionListener {
    private String[] strsize={"20x15","30x20","40x30"};
    private String[] strmode={"人机对弈","人人对弈"};
    public static boolean iscomputer=true,checkcomputer=true;
    private int width,height;
    private ChessModel cm;
    private MainPanel mp;

    //构造五子棋游戏的主窗体
    public ChessFrame() {
        this.setTitle("五子棋游戏");
        cm=new ChessModel(1);
        mp=new MainPanel(cm);
        Container con=this.getContentPane();
        con.add(mp,"Center");
        this.setResizable(false);
        this.addWindowListener(new ChessWindowEvent());
        MapSize(20,15);
        JMenuBar mbar = new JMenuBar();
        this.setJMenuBar(mbar);
        JMenu gameMenu = new JMenu("游戏");
        mbar.add(makeMenu(gameMenu, new Object[] {
                "开局", "棋盘","模式", null, "退出"
        }, this));
        JMenu lookMenu =new JMenu("视图");
        mbar.add(makeMenu(lookMenu,new Object[] {
                "Metal","Motif","Windows"
        },this));
        JMenu helpMenu = new JMenu("帮助");
        mbar.add(makeMenu(helpMenu, new Object[] {
                "关于"
        }, this));
    }

    //构造五子棋游戏的主菜单
    public JMenu makeMenu(Object parent, Object items[], Object target){
        JMenu m = null;
        if (parent instanceof JMenu) {
            m = (JMenu)parent;
        } else if (parent instanceof String) {
            m = new JMenu((String)parent);
        } else {
            return null;
        }
        for(int i = 0; i < items.length; i++)
            if(items[i] == null)
                m.addSeparator();
            else if(items[i] == "棋盘"){
                JMenu jm = new JMenu("棋盘");
                ButtonGroup group=new ButtonGroup();
                JRadioButtonMenuItem rmenu;
                for (int j=0;j<strsize.length;j++){
                    rmenu=makeRadioButtonMenuItem(strsize[j],target);
                    if (j==0) {
                        rmenu.setSelected(true);
                    }
                    jm.add(rmenu);
                    group.add(rmenu);
                }
                m.add(jm);
            }else if(items[i] == "模式"){
                JMenu jm = new JMenu("模式");
                ButtonGroup group=new ButtonGroup();
                JRadioButtonMenuItem rmenu;
                for (int h=0;h<strmode.length;h++){
                    rmenu=makeRadioButtonMenuItem(strmode[h],target);
                    if(h==0)
                        rmenu.setSelected(true);
                    jm.add(rmenu);
                    group.add(rmenu);
                }
                m.add(jm);
            }else
                m.add(makeMenuItem(items[i], target));
        return m;
    }

    //构造五子棋游戏的菜单项
    public JMenuItem makeMenuItem(Object item, Object target){
        JMenuItem r = null;
        if(item instanceof String) {
            r = new JMenuItem((String)item);
        } else if(item instanceof JMenuItem) {
            r = (JMenuItem)item;
        } else {
            return null;
        }
        if(target instanceof ActionListener) {
            r.addActionListener((ActionListener)target);
        }
        return r;
    }

    //构造五子棋游戏的单选按钮式菜单项
    public JRadioButtonMenuItem makeRadioButtonMenuItem(
            Object item, Object target){
        JRadioButtonMenuItem r = null;
        if (item instanceof String) {
            r = new JRadioButtonMenuItem((String)item);
        } else if(item instanceof JRadioButtonMenuItem) {
            r = (JRadioButtonMenuItem)item;
        } else {
            return null;
        }
        if (target instanceof ActionListener) {
            r.addActionListener((ActionListener)target);
        }
        return r;
    }

    public void MapSize(int w,int h){
        setSize(w * 20+50 , h * 20+100 );
        if (this.checkcomputer) {
            this.iscomputer=true;
        } else {
            this.iscomputer=false;
        }
        mp.setModel(cm);
        mp.repaint();
    }

    public boolean getiscomputer(){
        return this.iscomputer;
    }

    public void restart(){
        int modeChess = cm.getModeChess();
        if(modeChess <= 3 && modeChess >= 1){
            cm = new ChessModel(modeChess);
            MapSize(cm.getWidth(),cm.getHeight());
        }else{
            System.out.println("\u81EA\u5B9A\u4E49");
        }
    }

    public void actionPerformed(ActionEvent e){
        String arg=e.getActionCommand();
        try{
            if (arg.equals("Windows")) {
                UIManager.setLookAndFeel(
                        "com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            } else if(arg.equals("Motif")) {
                UIManager.setLookAndFeel(
                        "com.sun.java.swing.plaf.motif.MotifLookAndFeel");
            } else {
                UIManager.setLookAndFeel(
                        "javax.swing.plaf.metal.MetalLookAndFeel" );
            }
            SwingUtilities.updateComponentTreeUI(this);
        }catch(Exception ee){}
        if(arg.equals("20x15")){
            this.width=20;
            this.height=15;
            cm=new ChessModel(1);
            MapSize(this.width,this.height);
            SwingUtilities.updateComponentTreeUI(this);
        }
        if(arg.equals("30x20")){
            this.width=30;
            this.height=20;
            cm=new ChessModel(2);
            MapSize(this.width,this.height);
            SwingUtilities.updateComponentTreeUI(this);
        }
        if(arg.equals("40x30")){
            this.width=40;
            this.height=30;
            cm=new ChessModel(3);
            MapSize(this.width,this.height);
            SwingUtilities.updateComponentTreeUI(this);
        }
        if(arg.equals("人机对弈")){
            this.checkcomputer=true;
            this.iscomputer=true;
            cm=new ChessModel(cm.getModeChess());
            MapSize(cm.getWidth(),cm.getHeight());
            SwingUtilities.updateComponentTreeUI(this);
        }
        if(arg.equals("人人对弈")){
            this.checkcomputer=false;
            this.iscomputer=false;
            cm=new ChessModel(cm.getModeChess());
            MapSize(cm.getWidth(),cm.getHeight());
            SwingUtilities.updateComponentTreeUI(this);
        }
        if (arg.equals("开局")){
            restart();
        }
        if (arg.equals("关于")) {
            JOptionPane.showMessageDialog(this, "五子棋游戏测试版本", "关于", 0);
        }
        if (arg.equals("退出")) {
            System.exit(0);
        }
    }
}