package Frame;

import Util.MyArrayUtil;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public final class MergeRecursiveFrame extends SortFrame {

    private SortingVisualizer panel; // 排序可视化面板
    private int comparisonCount = 0; // 比较次数计数器

    public MergeRecursiveFrame() {
        this.setTitle("Merge Sort");
        initJMenuBar();
        panel = new SortingVisualizer(usedArr);
        this.setLayout(new BorderLayout());
        this.getContentPane().add(panel, BorderLayout.CENTER);
        createControlPanel(); // 创建控制面板
        this.pack();
    }

    JLabel countLabel = new JLabel("Comparisons: " + comparisonCount);

    JButton startButton = new JButton("Start");
    JButton stopButton = new JButton("Stop");
    // 创建控制面板
    private void createControlPanel() {
        JPanel controlPanel = new JPanel();



        startButton.setBackground(Color.GREEN);
        stopButton.setBackground(Color.GRAY);

        startButton.addActionListener(_ -> {
            panel.startSorting();
            startButton.setBackground(Color.GRAY); // 改变颜色表示不可用
            stopButton.setBackground(Color.RED); // 确保停止按钮可用
        });

        stopButton.addActionListener(_ -> {
            panel.stopSorting();
            startButton.setBackground(Color.GREEN); // 重新可用时恢复颜色
            stopButton.setBackground(Color.GRAY); // 改变颜色表示不可用
        });

        controlPanel.add(startButton);
        //controlPanel.add(stopButton);
        controlPanel.add(countLabel);

        this.getContentPane().add(controlPanel, BorderLayout.SOUTH); // 将控制面板添加到主面板底部
    }

    // 在算法完成时更新标签
    private void updateLabels() {
        countLabel.setText("Comparisons: " + comparisonCount);
    }

    // 初始化功能条
    public void initJMenuBar() {
        JMenuBar sortJMenuBar = new JMenuBar();
        JMenu choiceJMenu = new JMenu("choice");

        // 创建菜单项
        JMenuItem replayItem = new JMenuItem("replay");
        JMenuItem inputDataAgainItem = new JMenuItem("inputDataAgain");
        JMenuItem otherAlgorithmsItem = new JMenuItem("otherAlgorithms");

        replayItem.addActionListener(_ -> updatePanel());
        inputDataAgainItem.addActionListener(_ -> {
            dataInputDialog();
            updatePanel();
        });
        otherAlgorithmsItem.addActionListener(_ -> {
            new chooseFrame();

        });

        // 添加菜单项
        choiceJMenu.add(replayItem);
        choiceJMenu.add(inputDataAgainItem);
        choiceJMenu.add(otherAlgorithmsItem);

        // 添加菜单
        sortJMenuBar.add(choiceJMenu);

        // 添加JMenuBar
        this.setJMenuBar(sortJMenuBar);
    }

    // replay
    private void updatePanel() {
        usedArr.clear();
        usedArr.addAll(inputArr);
        comparisonCount=0;
        startButton.setEnabled(true);
        startButton.setBackground(Color.GREEN);
        panel = new SortingVisualizer(usedArr); // 生成新可视化面板
        this.getContentPane().removeAll(); // 移除旧的面板
        this.setLayout(new BorderLayout());
        createControlPanel(); // 重新添加控制面板
        this.getContentPane().add(panel, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

    class SortingVisualizer extends JPanel {
        private final ArrayList<Integer> array;
        private boolean sorting;
        private int index1;
        private int index2;
        ArrayList<Integer> result = new ArrayList<>();
        private ArrayList<Boolean>flags=new ArrayList<Boolean>(usedArr.size());
        private int sortedBoundary; // 表示已排序元素的边界

        public SortingVisualizer(ArrayList<Integer> array) {
            this.array = array;
            setPreferredSize(new Dimension(1100, 800));
        }

        public void stopSorting() {
            sorting = false; // 停止排序
        }

        private boolean isSorting = false; // 添加一个标志位来表示是否正在排序

        private boolean flag=true;
        public void startSorting() {
            if (isSorting) return;
            isSorting = true;

            stopSorting();
            sorting = true;



            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() {
                    for(int i=0;i<array.size();i++){
                        flags.add(true);
                    }
                    int middle=array.size()/2;
                    for (int j = 1; j < middle && sorting; j++) {
                        if (array.get(j) >= array.get(j - 1)) {
                            repaint(); // 绘制图形
                            updateLabels();
                            try {
                                Thread.sleep(50); // 控制速度
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            continue;
                        }

                        for (int i = 0; i < middle  && sorting; i++) {

                            index1=j;
                            index2=i;
                            comparisonCount++;
                            if (array.get(j) < array.get(i)) {
                                array.add(i, array.get(j));
                                array.remove(j+1);
                            }

                            repaint(); // 绘制图形
                            updateLabels();
                            try {
                                Thread.sleep(50); // 控制速度
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }


                    }
                    for (int j = middle; j <= array.size()-1 && sorting; j++) {
                        if (array.get(j) >= array.get(j - 1)) {
                            repaint(); // 绘制图形
                            updateLabels();
                            try {
                                Thread.sleep(50); // 控制速度
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            continue;
                        }

                        for (int i = middle; i < array.size()-1 && sorting; i++) {

                            index1 = j;
                            index2 = i;
                            comparisonCount++;
                            if (array.get(j) < array.get(i)) {
                                array.add(i, array.get(j));
                                array.remove(j + 1);
                            }

                            repaint(); // 绘制图形
                            updateLabels();
                            try {
                                Thread.sleep(50); // 控制速度
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    index1= 0; index2 = middle;
                    while (index1 < middle && index2 < array.size()) {
                        if (array.get(index1) <= array.get(index2)) {
                            result.add(array.get(index1));
                            flags.set(index1,false);
                            index1++;

                        } else {
                            result.add(array.get(index2));
                            flags.set(index2,false);
                            index2++;

                        }
                        comparisonCount++;
                        repaint();
                        updateLabels();
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    while (index1 < middle) {
                        result.add(array.get(index1));
                        flags.set(index1,false);
                        index1++;
                        if(index1==middle)index1=-1;
                        repaint();
                        updateLabels();
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    while (index2 <array.size()) {
                        result.add(array.get(index2));
                        flags.set(index2,false);
                        index2++;
                        if(index2==array.size())index2=-1;
                        repaint();
                        updateLabels();
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    flag=false;
                    return null;
                }

                @Override
                protected void done() {
                    isSorting = false;
                    if (sorting) {
                        index1=-1; index2=-1;
                        repaint();
                        stopButton.setEnabled(false);
                        startButton.setEnabled(false);
                        stopButton.setBackground(Color.gray);
                        startButton.setBackground(Color.gray);
                        JOptionPane.showMessageDialog(MergeRecursiveFrame.this, "排序已经完成!", "提示", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }.execute();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int componentWidth = getWidth();
            int componentHeight = getHeight();
            int blockSize = componentWidth / array.size();
            double barHeightMultiplier = 0.4; // 条形图高度占组件高度的比例
            if(flag)
            for (int i = 0; i < array.size(); i++) {
                int value = array.get(i);
                int barHeight = (int) (componentHeight * (1 - barHeightMultiplier) * ((double) value / MyArrayUtil.getMax(array)));

                if (sorting) {

                    if (i == index1 || i == index2) {
                        g.setColor(new Color(255, 165, 0, 150)); // 橙色表示正在比较
                    }  else {
                        g.setColor(new Color(100, 149, 237, 150)); // 默认颜色
                    }
                } else {
                    g.setColor(new Color(144, 238, 144, 150));
                }
                // 绘制条形图
                g.fillRect(i * blockSize, (int) (componentHeight * (1 - barHeightMultiplier) - barHeight), blockSize - 1, barHeight);

                // 绘制数字
                g.setColor(Color.BLACK);
                String numberString = String.valueOf(value);
                FontMetrics fontMetrics = g.getFontMetrics();
                int stringWidth = fontMetrics.stringWidth(numberString);
                int textY = (int) (componentHeight * (1 - barHeightMultiplier) - barHeight + fontMetrics.getHeight() - fontMetrics.getDescent());
                g.drawString(numberString, i * blockSize + (blockSize - stringWidth) / 2, textY);

            }
            if(!result.isEmpty()){

                for(int i=0;i<result.size();i++){
                    int value=result.get(i);
                    g.setColor(new Color(144, 238, 144, 150));
                    int rangeBarHeight = (int) (200 * ((double) value / MyArrayUtil.getMax(array)));

                    // 绘制条形图
                    g.fillRect(i*blockSize,(int)componentHeight-rangeBarHeight, blockSize- 1, rangeBarHeight*value);

                    // 绘制数字
                    g.setColor(Color.BLACK);

                    // 绘制数字
                    g.setColor(Color.BLACK);
                    String numberString = String.valueOf(value);
                    FontMetrics fontMetrics = g.getFontMetrics();
                    int stringWidth = fontMetrics.stringWidth(numberString);
                    int textY = componentHeight - 20;
                    g.drawString(numberString, i *blockSize + (blockSize - stringWidth) / 2, textY);
                }
            }
        }
    }

}
