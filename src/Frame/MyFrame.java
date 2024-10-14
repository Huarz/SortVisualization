package Frame;

import javax.swing.*;

public abstract class MyFrame extends JFrame {

    static boolean input=true;

    public MyFrame(){
        //界面初始化
        initFrame();
        //设为可视
        this.setVisible(true);
    }

    //初始化界面
    private void initFrame() {
        this.setSize(1000,600);
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        //此处关闭方式可能需要修改！！！
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
    }

}
