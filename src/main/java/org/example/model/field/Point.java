package org.example.model.field;

public class Point {

    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setX(int x) { this.x = x; }

    public void setY(int y) { this.y = y; }

    public int getX() { return x; }

    public int getY() { return y; }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Point point2 = (Point) obj;
        return point2.x == this.x && point2.y == this.y;
    }

    @Override
    public String toString() {
        return "Point: x = " + x + " y = " + y + ";";
    }
}
