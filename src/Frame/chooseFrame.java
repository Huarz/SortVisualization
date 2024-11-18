package Frame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public final class chooseFrame extends MyFrame {



    public chooseFrame() {
        this.setTitle("选择界面");
        // 使用BorderLayout来布局
        this.setLayout(new BorderLayout());

        // 创建提示区域
        JLabel promptLabel = new JLabel("请选择你想要测试的算法", SwingConstants.CENTER);
        promptLabel.setFont(new Font("宋体", Font.BOLD, 24));
        promptLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
        this.add(promptLabel, BorderLayout.NORTH);

        // 按钮面板
        JPanel buttonPanel = new JPanel(new GridLayout(3, 3, 20, 20));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        initButton(buttonPanel);
        this.add(buttonPanel, BorderLayout.CENTER);
    }



    // 按钮初始化
    private void initButton(JPanel buttonPanel) {

        JButton bubbleBtn = new JButton("BubbleSort");
        JButton quickBtn = new JButton("QuickSort");
        JButton mergeRecursiveBtn = new JButton("MergeSort");

        JButton heapBtn = new JButton("HeapSort");
        JButton shellBtn = new JButton("ShellSort");
        JButton countingBtn = new JButton("CountingSort");
        JButton insertionBtn = new JButton("InsertionSort");
        JButton selectionBtn = new JButton("SelectionSort");
        JButton bucketBtn = new JButton("BucketSort");

        // 为每一个按钮添加事件监听
        bubbleBtn.addActionListener(_ -> {
            SwingUtilities.invokeLater(() -> new BubbleFrame());
            dispose();
        });
        quickBtn.addActionListener(_ -> {
            SwingUtilities.invokeLater(() -> new QuickFrame());
            dispose();
        });
        mergeRecursiveBtn.addActionListener(_ -> {
            SwingUtilities.invokeLater(() -> new MergeRecursiveFrame());
            dispose();
        });
        heapBtn.addActionListener(_ -> {
            SwingUtilities.invokeLater(() -> new HeapFrame());
            dispose();
        });
        shellBtn.addActionListener(_ -> {
            SwingUtilities.invokeLater(() -> new ShellFrame());
            dispose();
        });
        countingBtn.addActionListener(_ -> {
            SwingUtilities.invokeLater(() -> new CountingFrame());
            dispose();
        });
        insertionBtn.addActionListener(_ -> {
            SwingUtilities.invokeLater(() -> new InsertionFrame());
            dispose();
        });
        selectionBtn.addActionListener(_ -> {
            SwingUtilities.invokeLater(() -> new SelectionFrame());
            dispose();
        });
        bucketBtn.addActionListener(_ -> {
            SwingUtilities.invokeLater(() -> new BucketFrame());
            dispose();
        });

        JButton[] buttons = {
                bubbleBtn,
                quickBtn,
                mergeRecursiveBtn,
                heapBtn,
                shellBtn,
                countingBtn,
                insertionBtn,
                selectionBtn,
                bucketBtn
        };

        // 将按钮添加到面板
        for (JButton button : buttons) {
            buttonPanel.add(button);
        }
    }
}
