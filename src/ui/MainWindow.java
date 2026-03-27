package ui;

import converting.Converter;
import polynomial.InterpolatingPolynomial;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainWindow extends JFrame {
    private JPanel drawingPanel;
    private CartesianPainter cartesianPainter;
    private FunctionPainter functionPainter;
    private Converter converter;
    private InterpolatingPolynomial polynomial;

    private JSpinner xMinSpinner;
    private JSpinner xMaxSpinner;
    private JSpinner yMinSpinner;
    private JSpinner yMaxSpinner;

    private SpinnerNumberModel xMinModel;
    private SpinnerNumberModel xMaxModel;
    private SpinnerNumberModel yMinModel;
    private SpinnerNumberModel yMaxModel;

    public MainWindow() {
        setTitle("Интерполяционный полином");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(800, 650));

        polynomial = new InterpolatingPolynomial();
        converter = new Converter(-5.0, 5.0, -5.0, 5.0);

        cartesianPainter = new CartesianPainter(converter);
        functionPainter = new FunctionPainter(converter, polynomial);

        initComponents();
        layoutComponents();

        // Начальные точки для демонстрации
        polynomial.addPoint(-4, -4);
        polynomial.addPoint(-2, 0);
        polynomial.addPoint(0, 2);
        polynomial.addPoint(2, 0);
        polynomial.addPoint(4, -4);
    }

    private void initComponents() {
        drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                cartesianPainter.paint(g);
                functionPainter.paint(g);
            }
        };
        drawingPanel.setBackground(Color.WHITE);
        drawingPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                cartesianPainter.setSize(drawingPanel.getWidth(), drawingPanel.getHeight());
                functionPainter.setSize(drawingPanel.getWidth(), drawingPanel.getHeight());
                drawingPanel.repaint();
            }
        });

        drawingPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                double x = converter.xScrToCrt(e.getX());
                double y = converter.yScrToCrt(e.getY());
                try {
                    polynomial.addPoint(x, y);
                    drawingPanel.repaint();
                } catch (IllegalArgumentException ex) {
                    // Точка с таким x уже существует
                }
            }
        });

        // Создаем спиннеры для границ
        xMinModel = new SpinnerNumberModel(-5.0, -100.0, 4.9, 0.1);
        xMaxModel = new SpinnerNumberModel(5.0, -4.9, 100.0, 0.1);
        yMinModel = new SpinnerNumberModel(-5.0, -100.0, 4.9, 0.1);
        yMaxModel = new SpinnerNumberModel(5.0, -4.9, 100.0, 0.1);

        xMinSpinner = new JSpinner(xMinModel);
        xMaxSpinner = new JSpinner(xMaxModel);
        yMinSpinner = new JSpinner(yMinModel);
        yMaxSpinner = new JSpinner(yMaxModel);

        // Добавляем слушателей для контроля границ
        xMinSpinner.addChangeListener(e -> {
            double min = (double) xMinModel.getValue();
            double max = (double) xMaxModel.getValue();
            if (min >= max) {
                xMinModel.setValue(max - 0.1);
            } else {
                xMaxModel.setMinimum(min + 0.1);
                updateXRange();
            }
        });

        xMaxSpinner.addChangeListener(e -> {
            double min = (double) xMinModel.getValue();
            double max = (double) xMaxModel.getValue();
            if (max <= min) {
                xMaxModel.setValue(min + 0.1);
            } else {
                xMinModel.setMaximum(max - 0.1);
                updateXRange();
            }
        });

        yMinSpinner.addChangeListener(e -> {
            double min = (double) yMinModel.getValue();
            double max = (double) yMaxModel.getValue();
            if (min >= max) {
                yMinModel.setValue(max - 0.1);
            } else {
                yMaxModel.setMinimum(min + 0.1);
                updateYRange();
            }
        });

        yMaxSpinner.addChangeListener(e -> {
            double min = (double) yMinModel.getValue();
            double max = (double) yMaxModel.getValue();
            if (max <= min) {
                yMaxModel.setValue(min + 0.1);
            } else {
                yMinModel.setMaximum(max - 0.1);
                updateYRange();
            }
        });
    }

    private void updateXRange() {
        converter.setXRange((double) xMinModel.getValue(), (double) xMaxModel.getValue());
        drawingPanel.repaint();
    }

    private void updateYRange() {
        converter.setYRange((double) yMinModel.getValue(), (double) yMaxModel.getValue());
        drawingPanel.repaint();
    }

    private void layoutComponents() {
        setLayout(new BorderLayout());

        // Центральная панель для рисования
        add(drawingPanel, BorderLayout.CENTER);

        // Нижняя панель управления
        JPanel controlPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        controlPanel.add(new JLabel("X min:"), gbc);

        gbc.gridx = 1;
        controlPanel.add(xMinSpinner, gbc);

        gbc.gridx = 2;
        controlPanel.add(new JLabel("X max:"), gbc);

        gbc.gridx = 3;
        controlPanel.add(xMaxSpinner, gbc);

        gbc.gridx = 4;
        controlPanel.add(new JLabel("Y min:"), gbc);

        gbc.gridx = 5;
        controlPanel.add(yMinSpinner, gbc);

        gbc.gridx = 6;
        controlPanel.add(new JLabel("Y max:"), gbc);

        gbc.gridx = 7;
        controlPanel.add(yMaxSpinner, gbc);

        add(controlPanel, BorderLayout.SOUTH);

        pack();
    }
}