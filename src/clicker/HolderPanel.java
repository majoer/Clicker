package clicker;

import java.awt.AWTException;
import java.awt.GridLayout;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.SpinnerNumberModel;

public class HolderPanel extends JPanel implements Runnable, ActionListener {

    private JTabbedPane parent;
    private Thread thread;
    private JSpinner tts, tth;
    private boolean stopped;

    public HolderPanel(JTabbedPane parent) {
        this.parent = parent;
        setLayout(new GridLayout(3, 2));

        JLabel ttsl = new JLabel("Time to start");
        ttsl.setToolTipText("Time to start in milliseconds.");
        add(ttsl);
        add(tts = new JSpinner(new SpinnerNumberModel(3000, 0, 50000, 1000)));

        JLabel tthl = new JLabel("Time to hold");
        tthl.setToolTipText("Time to hold in milliseconds.");
        add(tthl);
        add(tth = new JSpinner(new SpinnerNumberModel(3000, 0, 1000000, 1000)));

        JButton b = new JButton("Start");
        b.addActionListener(this);
        add(b);

        JButton s = new JButton("Stop");
        s.addActionListener(this);
        add(s);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Stop":
                stopped = true;
                thread.interrupt();
                break;
            case "Start":
                thread = new Thread(this);
                thread.start();
                break;
        }
    }

    @Override
    public void run() {
        parent.setEnabled(false);
        stopped = false;
        Robot r = null;
        try {
            r = new Robot();
            Thread.sleep((int) tts.getValue());

            int start = (int) tth.getValue();
            int i = 0;

            r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            while (i <= start && !stopped) {
                Thread.sleep(100);
                i += 100;
            }
        } catch (InterruptedException | AWTException ex) {
        } finally {
            stopped = true;
            if (r != null) {
                r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            }
            parent.setEnabled(true);
        }
    }

}
