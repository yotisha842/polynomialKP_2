package ui;

import converting.Converter;

import java.awt.*;
import java.util.function.Function;

public class FunctionPainter  implements Painter {
    private Function<Double, Double> f;
    private final Converter c;

    public FunctionPainter(Function<Double, Double> f){
        this.f = f;
        c = new Converter(-5.0, 5.0, -5.0, 5.0);
    }

    @Override
    public Dimension getSize() {
        return new Dimension(c.getWidth(), c.getHeight());
    }

    @Override
    public void setSize(Dimension d) {
        setSize(d.width, d.height);
    }

    @Override
    public void setSize(int width, int height) {
        c.setWidth(width);
        c.setHeight(height);
    }

    public Converter getConverter(){
        return c;
    }

    @Override
    public void paint(Graphics g){
        var x1 = 0;
        var dx1 = c.xScrToCrt(x1);
        var dy1 = f.apply(dx1);
        var y1 = c.yCrtToScr(dy1);
        for (int i = 1; i < c.getWidth(); i++){
            var x2 = x1+1;
            var dx2 = c.xScrToCrt(x2);
            var dy2 = f.apply(dx2);
            var y2 = c.yCrtToScr(dy2);
            g.drawLine(x1, y1, x2, y2);
            x1 = x2;
            y1 = y2;
        }
    }
}