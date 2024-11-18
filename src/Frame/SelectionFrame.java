package Frame;

import Util.MyArrayUtil;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public final class SelectionFrame extends SortFrame {

    private SortingVisualizer panel; // 排序可视化面板
    private int comparisonCount = 0; // 比较次数计数器

    public SelectionFrame() {
        this.setTitle("Selection Sort");
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
        private int currentComparisonIndex1 = 0; // 当前比较的第一个元素索引
        private int currentComparisonIndex2 = 0; // 当前比较的第二个元素索引
        private int sortedBoundary; // 表示已排序元素的边界

        public SortingVisualizer(ArrayList<Integer> array) {
            this.array = array;

            setPreferredSize(new Dimension(1100, 800));
        }

        public void stopSorting() {
            sorting = false; // 停止排序
        }

        private boolean isSorting = false; // 添加一个标志位来表示是否正在排序

        public void startSorting() {
            if (isSorting) return; // 如果已经在排序中，则返回
            isSorting = true; // 设置为正在排序

            stopSorting(); // 确保从前一个状态停止
            sorting = true;

            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() {
                    for (int j = 0; j < array.size() - 1 && sorting; j++) {
                        boolean swapped = false;
                        for (int i = j; i < array.size()  && sorting; i++) {
                            currentComparisonIndex1=j;
                            currentComparisonIndex2=i;
                            comparisonCount++;
                            if (array.get(j) > array.get(i)) {
                                int temp = array.get(j);
                                array.set(j, array.get(i ));
                                array.set(i, temp);

                            }
                            repaint(); // 绘制图形
                            updateLabels();
                            try {
                                Thread.sleep(500); // 控制速度
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }


                    }
                    return null;
                }

                @Override
                protected void done() {
                    isSorting = false; // 排序完成，重置标志位
                    if (sorting) { // 检查是否是正常完成排序
                        currentComparisonIndex1=-1;
                        currentComparisonIndex2=-1;
                        repaint();
                        stopButton.setEnabled(false);
                        startButton.setEnabled(false);
                        stopButton.setBackground(Color.gray);
                        startButton.setBackground(Color.gray);
                        JOptionPane.showMessageDialog(SelectionFrame.this, "排序已经完成!", "提示", JOptionPane.INFORMATION_MESSAGE);
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

            for (int i = 0; i < array.size(); i++) {
                int value = array.get(i);
                int barHeight = (int) (componentHeight * ((double) value / MyArrayUtil.getMax(usedArr)));

                // 设置颜色
                if(sorting) {
                    if (i == currentComparisonIndex1 || i == currentComparisonIndex2) {
                        g.setColor(new Color(255, 165, 0, 150)); // 橙色表示正在比较
                    } else if (i >= sortedBoundary|| (currentComparisonIndex1 == -1 && currentComparisonIndex2 == -1)) {
                        g.setColor(new Color(144, 238, 144, 150)); // 绿色表示已排序
                    } else {
                        g.setColor(new Color(100, 149, 237, 150)); // 默认颜色
                    }
                }
                else{
                    g.setColor(new Color(144, 238, 144, 150));
                }
                g.fillRect(i * blockSize, componentHeight - barHeight, blockSize - 1, barHeight);
                g.setFont(new Font("Arial", Font.BOLD, 14));
                g.setColor(new Color(255, 0, 0));
                String numberString = String.valueOf(value);
                FontMetrics fontMetrics = g.getFontMetrics();
                int stringWidth = fontMetrics.stringWidth(numberString);

                int textY = componentHeight - barHeight - 5;
                textY = Math.max(textY, fontMetrics.getHeight());
                g.drawString(numberString, i * blockSize + (blockSize - stringWidth) / 2, textY);
            }
        }
    }

}
