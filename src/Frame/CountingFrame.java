package Frame;

import Util.MyArrayUtil;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public final class CountingFrame extends SortFrame {

    private SortingVisualizer panel; // 排序可视化面板
    private int comparisonCount = 0; // 比较次数计数器

    public CountingFrame() {
        this.setTitle("Counting Sort");
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
        comparisonCount = 0;
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
        private int currentComparisonIndex = 0; // 当前比较的第一个元素索引

        private int sortedBoundary; // 表示已排序元素的边界

        public SortingVisualizer(ArrayList<Integer> array) {
            this.array = array;
            setPreferredSize(new Dimension(1100, 800));
        }

        public void stopSorting() {
            sorting = false; // 停止排序
        }

        private boolean isSorting = false; // 添加一个标志位来表示是否正在排序

        public class pair{
            public int index;
            public int value;
            public pair(int index, int value){
                this.index = index;
                this.value = value;
            }
        }
        int maxVal = MyArrayUtil.getMax(usedArr);
        int minVal = MyArrayUtil.getMin(usedArr);
        ArrayList<pair> rangeArr = new ArrayList<>();


        public void startSorting() {
            if (isSorting) return; // 如果已经在排序中，则返回
            isSorting = true; // 设置为正在排序

            stopSorting(); // 确保从前一个状态停止
            sorting = true;
            for (int i = minVal; i <= maxVal; i++) {
                rangeArr.add(new pair(i, 0));
            }

            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() {
                    currentComparisonIndex = 0;



                    for (int i = 1; i <= array.size() - 1 && sorting; i++) {
                        currentComparisonIndex = i;
                        rangeArr.get(array.get(i)-minVal).value++;
                        repaint(); // 绘制图形
                        updateLabels();
                        try {
                            Thread.sleep(500); // 控制速度
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    sorting=true;
                    return null;
                }

                @Override
                protected void done() {
                    isSorting = false; // 排序完成，重置标志位
                    if (sorting) { // 检查是否是正常完成排序
                        currentComparisonIndex = -1;
                        repaint();
                        stopButton.setEnabled(false);
                        startButton.setEnabled(false);
                        stopButton.setBackground(Color.gray);
                        startButton.setBackground(Color.gray);
                        JOptionPane.showMessageDialog(CountingFrame.this, "排序已经完成!", "提示", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }.execute();
        }


        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int componentWidth = getWidth();
            int componentHeight = getHeight();

            double barHeightMultiplier = 0.4; // 条形图高度占组件高度的比例

            if(currentComparisonIndex != -1) {
                // 绘制array
                for (int i = currentComparisonIndex; i < array.size(); i++) {
                    int blockSize = componentWidth / array.size();
                    int value = array.get(i);
                    int barHeight = (int) (componentHeight * (1 - barHeightMultiplier) * ((double) value / MyArrayUtil.getMax(array)));

                    // 设置颜色
                    if (sorting) {
                        if (i == currentComparisonIndex) {
                            g.setColor(new Color(255, 165, 0, 150)); // 橙色表示正在比较
                        } else if (i < sortedBoundary) {
                            g.setColor(new Color(100, 149, 237, 150)); // 蓝色表示未排序
                        } else {
                            g.setColor(new Color(144, 238, 144, 150)); // 绿色表示已排序
                        }
                    } else {
                        g.setColor(new Color(144, 238, 144, 150)); // 绿色表示已排序
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
            }
            if(!rangeArr.isEmpty()){
                // 绘制rangeArr
                int rangeXLength = (int)(componentWidth/rangeArr.size());

                for (int i = 0; i < rangeArr.size(); i++) {
                    int index = rangeArr.get(i).index;
                    int value = rangeArr.get(i).value;
                    int rangeBarHeight =100;
                    // 设置颜色
                    if(value == 0)g.setColor(new Color(200, 200, 200, 150));
                    else {
                        int red = (int)(255 * (value - minVal) / (double)(maxVal - minVal));
                        g.setColor(new Color(red, 165, 255, 150));
                    }


                    // 绘制条形图
                    g.fillRect(i*rangeXLength,(int)componentHeight-rangeBarHeight, rangeXLength - 1, rangeBarHeight);

                    // 绘制数字
                    g.setColor(Color.BLACK);
                    String numberString = String.valueOf(index);
                    String valueString = String.valueOf(value);
                    FontMetrics fontMetrics = g.getFontMetrics();
                    int stringWidth = fontMetrics.stringWidth(numberString);
                    int valueWidth = fontMetrics.stringWidth(valueString);
                    int textY1= componentHeight-20 ;
                    int textY2=componentHeight-50;
                    int loc = rangeXLength * i + (rangeXLength - stringWidth) / 2;
                    g.drawString(numberString, loc, textY1);
                    g.drawString(valueString, loc, textY2);
                }
            }
        }
    }
}