package ui;

import converting.Converter;
import polynomial.InterpolatingPolynomial;

import java.awt.*;
import java.util.Map;

public class FunctionPainter implements Painter {
    private InterpolatingPolynomial polynomial;
    private Converter converter;
    private int width;
    private int height;

    public FunctionPainter(Converter converter, InterpolatingPolynomial polynomial) {
        this.converter = converter;
        this.polynomial = polynomial;
    }

    public void setPolynomial(InterpolatingPolynomial polynomial) {
        this.polynomial = polynomial;
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
        if (polynomial == null) return;

        g.setColor(Color.BLUE);

        // Рисуем график
        for (int i = 0; i < width - 1; i++) {
            double x1 = converter.xScrToCrt(i);
            double x2 = converter.xScrToCrt(i + 1);

            double y1 = polynomial.calc(x1);
            double y2 = polynomial.calc(x2);

            int yScr1 = converter.yCrtToScr(y1);
            int yScr2 = converter.yCrtToScr(y2);

            // Проверяем, что точки в пределах видимой области
            if (yScr1 >= -100 && yScr1 <= height + 100 &&
                    yScr2 >= -100 && yScr2 <= height + 100) {
                g.drawLine(i, yScr1, i + 1, yScr2);
            }
        }

        // Рисуем точки
        g.setColor(Color.RED);
        Map<Double, Double> points = polynomial.getPoints();
        for (Map.Entry<Double, Double> point : points.entrySet()) {
            int x = converter.xCrtToScr(point.getKey());
            int y = converter.yCrtToScr(point.getValue());
            if (x >= 0 && x <= width && y >= 0 && y <= height) {
                g.fillOval(x - 3, y - 3, 6, 6);
            }
        }
    }
}