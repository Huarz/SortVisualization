import Frame.chooseFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // 启动算法选择窗口
                new chooseFrame();
            }
        });

    }
}
