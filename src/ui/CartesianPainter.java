package ui;

import converting.Converter;

import java.awt.*;

public class CartesianPainter implements Painter {
    private Converter converter;
    private int width;
    private int height;

    public CartesianPainter(Converter converter) {
        this.converter = converter;
    }

    @Override
    public Dimension getSize() {
        return new Dimension(width, height);
    }

    @Override
    public void setSize(Dimension d) {
        setSize(d.width, d.height);
    }

    @Override
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        converter.setWidth(width);
        converter.setHeight(height);
    }

    @Override
    public void paint(Graphics g) {
        // Рисуем оси
        g.setColor(Color.BLACK);

        int x0 = converter.xCrtToScr(0);
        int y0 = converter.yCrtToScr(0);

        // Ось X
        if (x0 >= 0 && x0 <= width) {
            g.drawLine(0, y0, width, y0);
        }

        // Ось Y
        if (y0 >= 0 && y0 <= height) {
            g.drawLine(x0, 0, x0, height);
        }

        // Рисуем деления по целым числам
        g.setColor(Color.GRAY);

        // Деления по X
        int xStart = (int)Math.ceil(converter.getxMin());
        int xEnd = (int)Math.floor(converter.getxMax());

        for (int x = xStart; x <= xEnd; x++) {
            int xScr = converter.xCrtToScr(x);
            if (xScr >= 0 && xScr <= width) {
                g.drawLine(xScr, y0 - 5, xScr, y0 + 5);
                g.drawString(String.valueOf(x), xScr - 5, y0 + 20);
            }
        }

        // Деления по Y
        int yStart = (int)Math.ceil(converter.getyMin());
        int yEnd = (int)Math.floor(converter.getyMax());

        for (int y = yStart; y <= yEnd; y++) {
            int yScr = converter.yCrtToScr(y);
            if (yScr >= 0 && yScr <= height) {
                g.drawLine(x0 - 5, yScr, x0 + 5, yScr);
                g.drawString(String.valueOf(y), x0 + 10, yScr + 5);
            }
        }
    }
}