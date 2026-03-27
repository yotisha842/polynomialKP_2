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
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int x0 = converter.xCrtToScr(0);
        int y0 = converter.yCrtToScr(0);

        // Рисуем оси
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));

        // Ось X
        if (y0 >= 0 && y0 <= height) {
            g2d.drawLine(0, y0, width, y0);
        } else if (y0 < 0) {
            g2d.drawLine(0, 0, width, 0);
        } else if (y0 > height) {
            g2d.drawLine(0, height, width, height);
        }

        // Ось Y
        if (x0 >= 0 && x0 <= width) {
            g2d.drawLine(x0, 0, x0, height);
        } else if (x0 < 0) {
            g2d.drawLine(0, 0, 0, height);
        } else if (x0 > width) {
            g2d.drawLine(width, 0, width, height);
        }

        // Рисуем стрелки на осях
        g2d.setColor(Color.BLACK);

        // Стрелка для оси X
        if (y0 >= 0 && y0 <= height) {
            g2d.drawLine(width - 10, y0 - 5, width, y0);
            g2d.drawLine(width - 10, y0 + 5, width, y0);
        }

        // Стрелка для оси Y
        if (x0 >= 0 && x0 <= width) {
            g2d.drawLine(x0 - 5, 10, x0, 0);
            g2d.drawLine(x0 + 5, 10, x0, 0);
        }

        // Рисуем деления по целым числам
        g2d.setColor(Color.GRAY);
        g2d.setStroke(new BasicStroke(1));

        // Деления по X
        int xStart = (int)Math.ceil(converter.getxMin());
        int xEnd = (int)Math.floor(converter.getxMax());

        for (int x = xStart; x <= xEnd; x++) {
            int xScr = converter.xCrtToScr(x);
            if (xScr >= 0 && xScr <= width) {
                // Вертикальная линия деления
                if (y0 >= 0 && y0 <= height) {
                    g2d.drawLine(xScr, y0 - 5, xScr, y0 + 5);
                } else if (y0 < 0) {
                    g2d.drawLine(xScr, 5, xScr, 15);
                } else {
                    g2d.drawLine(xScr, height - 5, xScr, height - 15);
                }

                // Подпись
                if (y0 >= 0 && y0 <= height) {
                    g2d.drawString(String.valueOf(x), xScr - 5, y0 + 20);
                } else if (y0 < 0) {
                    g2d.drawString(String.valueOf(x), xScr - 5, 25);
                } else {
                    g2d.drawString(String.valueOf(x), xScr - 5, height - 10);
                }
            }
        }

        // Деления по Y
        int yStart = (int)Math.ceil(converter.getyMin());
        int yEnd = (int)Math.floor(converter.getyMax());

        for (int y = yStart; y <= yEnd; y++) {
            int yScr = converter.yCrtToScr(y);
            if (yScr >= 0 && yScr <= height) {
                // Горизонтальная линия деления
                if (x0 >= 0 && x0 <= width) {
                    g2d.drawLine(x0 - 5, yScr, x0 + 5, yScr);
                } else if (x0 < 0) {
                    g2d.drawLine(5, yScr, 15, yScr);
                } else {
                    g2d.drawLine(width - 5, yScr, width - 15, yScr);
                }

                // Подпись
                if (x0 >= 0 && x0 <= width) {
                    g2d.drawString(String.valueOf(y), x0 + 10, yScr + 5);
                } else if (x0 < 0) {
                    g2d.drawString(String.valueOf(y), 20, yScr + 5);
                } else {
                    g2d.drawString(String.valueOf(y), width - 30, yScr + 5);
                }
            }
        }

        // Рисуем подписи осей
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 12));

        // Подпись X
        if (y0 >= 0 && y0 <= height) {
            g2d.drawString("X", width - 15, y0 - 5);
        } else if (y0 < 0) {
            g2d.drawString("X", width - 15, 20);
        } else {
            g2d.drawString("X", width - 15, height - 10);
        }

        // Подпись Y
        if (x0 >= 0 && x0 <= width) {
            g2d.drawString("Y", x0 + 5, 15);
        } else if (x0 < 0) {
            g2d.drawString("Y", 15, 15);
        } else {
            g2d.drawString("Y", width - 20, 15);
        }
    }
}