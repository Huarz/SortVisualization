package Frame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public final class BubbleFrame extends SortFrame {

    public BubbleFrame() {
        this.setTitle("BubbleSort");
        SortingVisualizer panel = new SortingVisualizer(inputArr);
        this.setLayout(new BorderLayout());
        this.getContentPane().add(panel, BorderLayout.CENTER);
        this.pack();
    }

    static class SortingVisualizer extends JPanel {
        private final ArrayList<Integer> array;
        private Timer timer;
        private boolean sorting = true;
        private int comparisonCount = 0; // 比较次数计数器
        private JLabel countLabel; // 比较次数标签
        private JLabel timeLabel; // 计时标签
        private long startTime; // 计时开始时间

        public SortingVisualizer(ArrayList<Integer> array) {
            this.array = array;
            setPreferredSize(new Dimension(1000, 600));
            createControlPanel(); // 创建控制面板
        }

        private void createControlPanel() {
            JPanel controlPanel = new JPanel();
            JButton startButton = new JButton("Start");
            JButton stopButton = new JButton("Stop");
            countLabel = new JLabel("Comparisons: 0"); // 将比较次数标签定义为类成员
            timeLabel = new JLabel("Time: 0 ms"); // 计时标签

            startButton.addActionListener(_ -> startSorting());
            stopButton.addActionListener(_ -> stopSorting());

            controlPanel.add(startButton);
            controlPanel.add(stopButton);
            controlPanel.add(countLabel);
            controlPanel.add(timeLabel); // 添加计时标签到控制面板

            this.add(controlPanel, BorderLayout.SOUTH); // 将按钮面板添加到主面板的底部
        }

        public void startSorting() {
            stopSorting(); // 确保没有重复定时器
            sorting = true;
            comparisonCount = 0; // 重置计数器
            startTime = System.currentTimeMillis(); // 记录开始时间
            repaint(); // 刷新面板

            // 动画延迟时间（毫秒）
            final int DELAY = 2000;
            timer = new Timer(DELAY, _ -> {
                if (sorting) {
                    bubbleSortStep();
                    repaint();
                    // 更新比较次数标签
                    countLabel.setText("Comparisons: " + comparisonCount);
                    // 更新计时标签
                    long elapsedTime = System.currentTimeMillis() - startTime;
                    timeLabel.setText("Time: " + elapsedTime + " ms");
                } else {
                    timer.stop();
                }
            });
            timer.start();
        }

        public void stopSorting() {
            sorting = false;
            if (timer != null) {
                timer.stop();
            }
        }

        private void bubbleSortStep() {
            boolean swapped = false;
            for (int i = 0; i < array.size() - 1; i++) {
                comparisonCount++; // 增加比较次数
                if (array.get(i) > array.get(i + 1)) {
                    // 交换元素
                    int temp = array.get(i);
                    array.set(i, array.get(i + 1));
                    array.set(i + 1, temp);
                    swapped = true;
                }
            }
            sorting = swapped;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int componentWidth = getWidth();
            int componentHeight = getHeight();
            int blockSize = componentWidth / array.size();

            for (int i = 0; i < array.size(); i++) {
                int value = array.get(i);
                int barHeight = (int) (componentHeight * ((double) value / getMaxValue()));
                Color barColor = new Color(100, 149, 237, 150);
                g.setColor(barColor);
                g.fillRect(i * blockSize, componentHeight - barHeight, blockSize - 1, barHeight);

                // 在方块上方绘制数字
                g.setColor(new Color(0, 0, 0, 120)); // 淡黑色，半透明
                String numberString = String.valueOf(value);
                FontMetrics fontMetrics = g.getFontMetrics();
                int stringWidth = fontMetrics.stringWidth(numberString);
                g.drawString(numberString, i * blockSize + (blockSize - stringWidth) / 2, componentHeight - barHeight / 2 - fontMetrics.getHeight() / 4);
            }
        }

        // 寻找最大值归一化高度
        private int getMaxValue() {
            int max = array.getFirst();
            for (int value : array) {
                if (value > max) {
                    max = value;
                }
            }
            return max;
        }
    }
}
