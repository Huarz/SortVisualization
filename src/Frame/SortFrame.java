package Frame;

import Util.MyArrayUtil;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public abstract class SortFrame extends MyFrame {

    //创建Integer数组
    public ArrayList<Integer> inputArr = new ArrayList<>();


    public SortFrame() {
        if(MyFrame.input) {
            dataInputDialog();
        }
        initJMenuBar();
        //setControlButton();//需要大改
    }

    //初始化功能条
    public  void initJMenuBar() {

        JMenuBar sortJMenuBar = new JMenuBar();

        JMenu choiceJMenu = new JMenu("choice");

        // 创建菜单项
        JMenuItem replayItem = new JMenuItem("replay");
        JMenuItem inputDataAgainItem = new JMenuItem("inputDataAgain");
        JMenuItem otherAlgorithmsItem = new JMenuItem("otherAlgorithms");


        inputDataAgainItem.addActionListener(_ -> dataInputDialog());

        otherAlgorithmsItem.addActionListener(_ -> {
            new chooseFrame();
            MyFrame.input=false;
        });

        //添加菜单项
        choiceJMenu.add(replayItem);
        choiceJMenu.add(inputDataAgainItem);
        choiceJMenu.add(otherAlgorithmsItem);

        //添加菜单
        sortJMenuBar.add(choiceJMenu);

        //添加JMenuBar
        this.setJMenuBar(sortJMenuBar);

    }

    //这里的代码需要修改 不需要这么复杂的逻辑
    //判断运行
    //public boolean running = true;
//    public void setControlButton(){
//
//        //初始化按钮
//        JButton startBtn = new JButton("Start");
//        JButton pauseBtn = new JButton("Pause");
//        JButton continueBtn = new JButton("Continue");
//        JButton stepBtn=new JButton("Step");
//
//        startBtn.setBounds(400,530,60,30);
//        pauseBtn.setBounds(460,530,60,30);
//        continueBtn.setBounds(520,530,60,30);
//        stepBtn.setBounds(580,530,60,30);
//
//        pauseBtn.setEnabled(false);
//        continueBtn.setEnabled(false);
//        stepBtn.setEnabled(false);
//
//        startBtn.addActionListener(_ -> {
//            startBtn.setEnabled(false);
//            pauseBtn.setEnabled(true);
//            running = true;
//
//        });
//
//
//        pauseBtn.addActionListener(_ -> {
//            pauseBtn.setEnabled(false);
//            continueBtn.setEnabled(true);
//            stepBtn.setEnabled(true);
//            running = false;
//        });
//
//        continueBtn.addActionListener(_ -> {
//            pauseBtn.setEnabled(true);
//            continueBtn.setEnabled(false);
//            stepBtn.setEnabled(false);
//            running = true;
//        });
//
//        stepBtn.addActionListener(_ -> {
//            pauseBtn.setEnabled(true);
//            continueBtn.setEnabled(false);
//            stepBtn.setEnabled(false);
//            running = true;
//        });
//
//        this.getContentPane().add(startBtn);
//        this.getContentPane().add(pauseBtn);
//        this.getContentPane().add(continueBtn);
//        this.getContentPane().add(stepBtn);
//    }

    //输入的模态对话框
    public void dataInputDialog(){
        // 创建一个模态对话框
        JDialog dialog = new JDialog(this, "输入整数", true);
        dialog.setBounds(800,300,200,200);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(300, 150);

        // 创建 JTextArea 实例，作为文本输入框
        JTextArea textArea = new JTextArea(5, 20);
        textArea.setLineWrap(true); // 设置自动换行
        dialog.add(new JScrollPane(textArea), BorderLayout.CENTER); // 添加滚动条支持

        // 创建一个确认按钮
        JButton confirmButton = getJButton(textArea, dialog);

        // 创建一个面板，用于放置按钮
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel.add(confirmButton); // 将确认按钮添加到面板中

        // 将面板添加到对话框的底部
        dialog.add(panel, BorderLayout.SOUTH);

        // 显示对话框
        dialog.setVisible(true);

    }
    private JButton getJButton(JTextArea textArea, JDialog dialog) {
        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(_ -> {
            String input = textArea.getText();
            if (MyArrayUtil.isValidStr(input)) {
                // 显示输入的内容并关闭对话框
                JOptionPane.showMessageDialog(dialog,
                        "您输入的内容是: \n" + input);
                inputArr=MyArrayUtil.getIntArr(input);
                dialog.dispose();
            } else {
                // 清空输入框并提示用户重新输入
                textArea.setText(""); // 清空输入框
                JOptionPane.showMessageDialog(dialog,
                        "输入无效，请输入多个整数!",
                        "输入错误",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        return confirmButton;
    }


}
