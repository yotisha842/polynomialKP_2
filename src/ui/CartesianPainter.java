package ui;

import converting.Converter;

import java.awt.*;

public class CartesianPainter implements Painter {
    private Converter converter;

    public CartesianPainter(Converter converter) {
        this.converter = converter;
    }

    @Override
    public Dimension getSize() {
        return new Dimension(converter.getWidth(), converter.getHeight());
    }

    @Override
    public void setSize(Dimension d) {
        setSize(d.width, d.height);
    }

    @Override
    public void setSize(int width, int height) {
        converter.setWidth(width);
        converter.setHeight(height);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int x0 = converter.xCrtToScr(0);
        int y0 = converter.yCrtToScr(0);

        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));

        if (y0 >= 0 && y0 <= converter.getHeight()) {
            g2d.drawLine(0, y0, converter.getWidth(), y0);
        } else if (y0 < 0) {
            g2d.drawLine(0, 0, converter.getWidth(), 0);
        } else {
            g2d.drawLine(0, converter.getHeight(), converter.getWidth(), converter.getHeight());
        }

        if (x0 >= 0 && x0 <= converter.getWidth()) {
            g2d.drawLine(x0, 0, x0, converter.getHeight());
        } else if (x0 < 0) {
            g2d.drawLine(0, 0, 0, converter.getHeight());
        } else {
            g2d.drawLine(converter.getWidth(), 0, converter.getWidth(), converter.getHeight());
        }

        g2d.setColor(Color.BLACK);

        if (y0 >= 0 && y0 <= converter.getHeight()) {
            g2d.drawLine(converter.getWidth() - 10, y0 - 5, converter.getWidth(), y0);
            g2d.drawLine(converter.getWidth() - 10, y0 + 5, converter.getWidth(), y0);
        }

        if (x0 >= 0 && x0 <= converter.getWidth()) {
            g2d.drawLine(x0 - 5, 10, x0, 0);
            g2d.drawLine(x0 + 5, 10, x0, 0);
        }

        g2d.setColor(Color.GRAY);
        g2d.setStroke(new BasicStroke(1));

        int xStart = (int)Math.ceil(converter.getxMin());
        int xEnd = (int)Math.floor(converter.getxMax());

        for (int x = xStart; x <= xEnd; x++) {
            int xScr = converter.xCrtToScr(x);
            if (xScr >= 0 && xScr <= converter.getWidth()) {
                if (y0 >= 0 && y0 <= converter.getHeight()) {
                    g2d.drawLine(xScr, y0 - 5, xScr, y0 + 5);
                } else if (y0 < 0) {
                    g2d.drawLine(xScr, 5, xScr, 15);
                } else {
                    g2d.drawLine(xScr, converter.getHeight() - 5, xScr, converter.getHeight() - 15);
                }

                if (y0 >= 0 && y0 <= converter.getHeight()) {
                    g2d.drawString(String.valueOf(x), xScr - 5, y0 + 20);
                } else if (y0 < 0) {
                    g2d.drawString(String.valueOf(x), xScr - 5, 25);
                } else {
                    g2d.drawString(String.valueOf(x), xScr - 5, converter.getHeight() - 10);
                }
            }
        }

        int yStart = (int)Math.ceil(converter.getyMin());
        int yEnd = (int)Math.floor(converter.getyMax());

        for (int y = yStart; y <= yEnd; y++) {
            int yScr = converter.yCrtToScr(y);
            if (yScr >= 0 && yScr <= converter.getHeight()) {
                if (x0 >= 0 && x0 <= converter.getWidth()) {
                    g2d.drawLine(x0 - 5, yScr, x0 + 5, yScr);
                } else if (x0 < 0) {
                    g2d.drawLine(5, yScr, 15, yScr);
                } else {
                    g2d.drawLine(converter.getWidth() - 5, yScr, converter.getWidth() - 15, yScr);
                }

                if (x0 >= 0 && x0 <= converter.getWidth()) {
                    g2d.drawString(String.valueOf(y), x0 + 10, yScr + 5);
                } else if (x0 < 0) {
                    g2d.drawString(String.valueOf(y), 20, yScr + 5);
                } else {
                    g2d.drawString(String.valueOf(y), converter.getWidth() - 30, yScr + 5);
                }
            }
        }

        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 12));

        if (y0 >= 0 && y0 <= converter.getHeight()) {
            g2d.drawString("X", converter.getWidth() - 15, y0 - 5);
        } else if (y0 < 0) {
            g2d.drawString("X", converter.getWidth() - 15, 20);
        } else {
            g2d.drawString("X", converter.getWidth() - 15, converter.getHeight() - 10);
        }

        if (x0 >= 0 && x0 <= converter.getWidth()) {
            g2d.drawString("Y", x0 + 5, 15);
        } else if (x0 < 0) {
            g2d.drawString("Y", 15, 15);
        } else {
            g2d.drawString("Y", converter.getWidth() - 20, 15);
        }
    }
}