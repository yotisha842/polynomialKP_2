package ui;

import converting.Converter;

import java.awt.*;
import java.util.function.Function;

public class FunctionPainter {
    private Function<Double, Double> f;

    private int width;
    private int height;

    private final Converter c;
    public Converter getConverter(){
        return c;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
        c.setWidth(width);
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
        c.setHeight(height);
    }

    public FunctionPainter(Function<Double, Double> f){
        this.f = f;
        c = new Converter(-5.0, 5.0, -5.0, 5.0);
    }
    public void paint(Graphics g){
        for (int i = 0; i < width; i++){
            var x1 = i;
            var x2 = i+1;
            var dx1 = c.xScrToCrt(x1);
            var dx2 = c.xScrToCrt(x2);
            var dy1 = f.apply(dx1);
            var dy2 = f.apply(dx2);
            var y1 = c.yCrtToScr(dy1);
            var y2 = c.yCrtToScr(dy2);
            g.drawLine(x1, y1, x2, y2);
        }
    }
}