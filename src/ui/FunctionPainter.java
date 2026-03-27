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

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLUE);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(new BasicStroke(1.5f));

        // ✅ Адаптивный шаг: меньше шаг при высокой кривизне
        int steps = Math.max(width, 2000); // минимум 2000 шагов для точности
        double step = (double) width / steps;

        Integer prevX = null, prevY = null;

        for (int i = 0; i <= width; i++) {
            double xCrt = converter.xScrToCrt(i);
            double yCrt = polynomial.calc(xCrt);
            int yScr = converter.yCrtToScr(yCrt);

            // Пропускаем точки вне экрана с запасом
            if (yScr < -100 || yScr > height + 100) {
                prevX = null;
                continue;
            }

            if (prevX != null) {
                // ✅ Проверка на разрыв: если скачок > высоты экрана — разрыв
                if (Math.abs(yScr - prevY) < height * 1.5) {
                    g2d.drawLine(prevX, prevY, i, yScr);
                } else {
                    // Разрыв — не рисуем линию
                }
            }
            prevX = i;
            prevY = yScr;
        }

        drawPoints(g2d);
    }

    private void drawPoints(Graphics g) {
        Map<Double, Double> points = polynomial.getPoints();
        g.setColor(Color.RED);

        for (Map.Entry<Double, Double> point : points.entrySet()) {
            int x = converter.xCrtToScr(point.getKey());
            int y = converter.yCrtToScr(point.getValue());
            if (x >= 0 && x <= width && y >= 0 && y <= height) {
                g.fillOval(x - 4, y - 4, 8, 8);
                // Обводим белым для лучшей видимости
                g.setColor(Color.WHITE);
                g.drawOval(x - 4, y - 4, 8, 8);
                g.setColor(Color.RED);
            }
        }
    }
}