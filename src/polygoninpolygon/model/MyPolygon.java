/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polygoninpolygon.model;

import java.awt.Point;
import java.util.Stack;

/**
 *
 * @author marco
 */
public class MyPolygon {

    private Segment start;
    private Segment last;
    private int count;

    public MyPolygon() {
        start = null;
        last = null;
        count = 0;
//        shift = 5;
//        counterClockWise = true;
    }

    public void addPoint(Point p) {
        if (start == null) {
            start = new Segment(p, null);
            start.next = start;
//            start.previous = start;
            last = start;
        } else {
            last.next = new Segment(p, start);
//            last.next.previous = last;
            last = last.next;

        }
//        System.out.println("Adding point [" + p.x + "," + p.y + "]");
        count++;
    }

    public boolean addPointConvex(Point p) {
        boolean ok = true;
        if (start == null) {
            start = new Segment(p, null);
            start.next = start;
//            start.previous = start;
            last = start;
        } else {
            last.next = new Segment(p, start);
            if (!isConvex()) {
                last.next = start;
                ok = false;
            } else {
//                last.next.previous = last;
                last = last.next;
            }
        }
//        System.out.println("Adding point [" + p.x + "," + p.y + "]");
//        System.out.println(ok ? "\tOK!" : "\tNot OK! -> Concave (didn't add point)");
        if (ok) {
            count++;
        }
        return ok;
    }

    public boolean isConvex() {
        boolean convex = true;

        if (count > 2) {
            convex = true; // until ...
            Segment s = start;
            int direction = (s.getX() * s.next.getY()) - (s.next.getX() * s.getY());
//            System.out.println(direction);
            s = s.next;
            while (convex && s != start) {
                int d = (s.getX() * s.next.getY()) - (s.next.getX() * s.getY());
//                System.out.println(direction + " - " + d);
                if (direction == 0) {
                    direction = d;
                }
                convex = (direction * d) >= 0; // OR > 0 if you don't allow making 1 straight line with 2 lines.
                s = s.next;
            }
        }

        return convex;
    }

    public MyPolygon getInnerPolygon(int shiftAmount) {
        MyPolygon innerPolygon = null;

        // Check if all sides are big enough for the shift
        boolean isBigEnough = true;
        Segment s = start;
        while (isBigEnough && s.next != start) {
            if (s.getLength() <= shiftAmount * 1.5) {
                isBigEnough = false;
            }
            s = s.next;
        }

//        if (isBigEnough && isConvex()) {
        if (isBigEnough) {
            innerPolygon = new MyPolygon();

            // Calculate the first point
            s = start;
            double l = s.getLength();
            int x = (int) Math.round(s.p.x + (shiftAmount * s.getX() / l));
            int y = (int) Math.round(s.p.y + (shiftAmount * s.getY() / l));
//            innerPolygon.addPointConvex(new Point(x, y));
            innerPolygon.addPoint(new Point(x, y));
            s = s.next;
            while (s != start) {
                l = s.getLength();
                x = (int) Math.round(s.p.x + (shiftAmount * s.getX() / l));
                y = (int) Math.round(s.p.y + (shiftAmount * s.getY() / l));
//                innerPolygon.addPointConvex(new Point(x, y));
                innerPolygon.addPoint(new Point(x, y));

                s = s.next;
            }
        }

        return innerPolygon;
    }

    public void reverse() {
        Stack<Point> points = new Stack<>();
        Segment s = start;
        points.push(s.p);
        s = s.next;
        while (s != start) {
            points.push(s.p);
            s = s.next;
        }
        reset();
        while (!points.isEmpty()) {
            addPoint(points.pop());
        }
    }

    public void reset() {
        last = start = null;
        count = 0;
    }

    public int getCount() {
        return count;
    }
    
    public Point[] getPoints() {
        Point[] points = new Point[getCount()];
        Segment s = start;
        for (int i=0; i<getCount(); i++) {
            points[i] = s.p;
            s = s.next;
        }
        return points;
    }
    
    public int[] getXCoords() {
        int[] xcoords = new int[getCount()];
        Segment s = start;
        for (int i=0; i<getCount(); i++) {
            xcoords[i] = s.p.x;
            s = s.next;
        }
        return xcoords;
    }
    
    public int[] getYCoords() {
        int[] ycoords = new int[getCount()];
        Segment s = start;
        for (int i=0; i<getCount(); i++) {
            ycoords[i] = s.p.y;
            s = s.next;
        }
        return ycoords;
    }

    private class Segment {

        // The first point is stored in p, the second one in next.p
        Point p;
        Segment next;
//        // This is to enable double linked list properties
//        Segment previous;

        public Segment(Point p, Segment next) {
            this.p = p;
            this.next = next;
        }

        public int getX() {
            return next.p.x - p.x;
        }

        public int getY() {
            return next.p.y - p.y;
        }

        public double getLength() {
            return Math.sqrt(getX() * getX() + getY() * getY());
        }

    }

}
