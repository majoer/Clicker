/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clicker;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Mats
 */
public class Changelog extends JFrame {

    private String changelog = "";

    public Changelog(String title, JFrame parent) {
        loadChangelog();
        setTitle(title);
        setSize(parent.getSize());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JTextArea log = new JTextArea(changelog);
        log.setEditable(false);
        add(new JScrollPane(log));
    }

    public void update(Dimension size, Point pos) {
        setLocation(bestPosition(size, this.getSize(), pos));

    }

    private Point bestPosition(Dimension parent, Dimension child, Point parentPos) {
        Point[] candidates = {
            right(parent, parentPos),
            left(child, parentPos),
            below(parent, parentPos),
            above(child, parentPos)
        };
        Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle bounds = new Rectangle(resolution);
        for (Point p : candidates) {
            Rectangle window = new Rectangle(p, child);
            if (bounds.contains(window)) {
                return p;
            }
        }
        return center(parent, child, parentPos);

    }

    private Point center(Dimension parent, Dimension child, Point parentPos) {
        Point centerParent = new Point(parent.width / 2, parent.height / 2);
        Point childPos = new Point(centerParent.x - (child.width / 2),
                centerParent.y - (child.height / 2));
        childPos.x += parentPos.x;
        childPos.y += parentPos.y;
        return childPos;
    }

    private Point right(Dimension parent, Point parentPos) {
        Point childPos = new Point(parentPos.x + parent.width, parentPos.y);
        return childPos;
    }

    private Point left(Dimension child, Point parentPos) {
        Point childPos = new Point(parentPos.x - child.width, parentPos.y);
        return childPos;
    }

    private Point above(Dimension child, Point parentPos) {
        Point childPos = new Point(parentPos.x, parentPos.y - child.height);
        return childPos;
    }

    private Point below(Dimension parent, Point parentPos) {
        Point childPos = new Point(parentPos.x, parentPos.y + parent.height);
        return childPos;
    }

    private void loadChangelog() {
        FileReader reader = null;
        BufferedReader br = null;
        try {
            reader = new FileReader(this.getClass().getResource("/res/changelog.txt").getFile());
            br = new BufferedReader(reader);

            String line;
            while ((line = br.readLine()) != null) {
                changelog += line + "\n";
            }

        } catch (IOException e) {
            Logger.getLogger(Changelog.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(Changelog.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
