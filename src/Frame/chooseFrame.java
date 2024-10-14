package Frame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public final class chooseFrame extends MyFrame{



    public ArrayList<Integer> inputArr=new ArrayList<>();

    public chooseFrame(){

        this.setTitle("选择界面");
        //布局初始化
        this.setLayout(new GridLayout(5,2,5,5));

        initButton();

    }
    public void setInputArr(ArrayList<Integer> inputArr){
        this.inputArr=inputArr;
        MyFrame.input=false;
    }

    //按钮初始化
    private void initButton() {

        JButton bubbleBtn=new JButton("BubbleSort");
        JButton quickBtn=new JButton("QuickSort");
        JButton mergeRecursiveBtn=new JButton("MergeRecursiveSort");
        JButton mergeNonRecursiveBtn=new JButton("MergeNonRecursiveSort");
        JButton heapBtn=new JButton("HeapSort");
        JButton shellBtn=new JButton("ShellSort");
        JButton countingBtn=new JButton("CountingSort");
        JButton insertionBtn=new JButton("InsertionSort");
        JButton selectionBtn=new JButton("SelectionSort");
        JButton bucketBtn=new JButton("BucketSort");

        //为每一个按钮添加事件监听按钮
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
        mergeNonRecursiveBtn.addActionListener(_ -> {

            SwingUtilities.invokeLater(() -> new MergeNonRecursiveFrame());
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
        insertionBtn.addActionListener(_ -> { SwingUtilities.invokeLater(() -> new InsertionFrame());
            dispose();
        });
        selectionBtn.addActionListener(_ -> { SwingUtilities.invokeLater(() -> new SelectionFrame());
        dispose();
        });
       bucketBtn.addActionListener(_ -> { SwingUtilities.invokeLater(() -> new BucketFrame());
       dispose();
       });

        Container contentPane=this.getContentPane();

        JButton[] buttons = {
                bubbleBtn,
                quickBtn,
                mergeRecursiveBtn,
                mergeNonRecursiveBtn,
                heapBtn,
                shellBtn,
                countingBtn,
                insertionBtn,
                selectionBtn,
                bucketBtn
        };

        // 将按钮添加到面板
        for (JButton button : buttons) {
            contentPane.add(button);
        }

    }

}
