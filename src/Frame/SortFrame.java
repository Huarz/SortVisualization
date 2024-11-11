package Frame;

import Util.MyArrayUtil;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public abstract class SortFrame extends MyFrame {

    //创建Integer数组
    public ArrayList<Integer> inputArr = new ArrayList<>();

    public ArrayList<Integer> usedArr=new ArrayList<>();

    public SortFrame() {
        if(MyFrame.input) {
            dataInputDialog();
        }
    }

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

        dialog.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                // 阻止关闭操作，可以弹出一个提示框
                int result = JOptionPane.showConfirmDialog(dialog, "您确定要关闭输入吗?", "关闭确认",
                        JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.NO_OPTION) {
                    dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
                } else {
                    dialog.dispose();
                    dispose();
                    new chooseFrame(); // 确认关闭时，关闭对话框
                }
            }
        });


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
                usedArr.addAll(inputArr);
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
