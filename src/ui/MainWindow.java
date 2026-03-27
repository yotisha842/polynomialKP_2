package ui;

import converting.Converter;
import polynomial.InterpolatingPolynomial;

import javax.swing.*;
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

    private JCheckBox showPointsCheckBox;
    private JCheckBox showGraphCheckBox;
    private boolean showPoints = true;
    private boolean showGraph = true;

    public MainWindow() {
        setTitle("Интерполяционный полином");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(800, 700));

        polynomial = new InterpolatingPolynomial();
        functionPainter = new FunctionPainter(polynomial::calc);
        converter = functionPainter.getConverter();

        cartesianPainter = new CartesianPainter(converter);

        initComponents();
        layoutComponents();
    }

    private void initComponents() {
        drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                cartesianPainter.paint(g);
                if (showGraph) {
                    functionPainter.paint(g);
                }
                if (showPoints) {
                    drawPointsOnPanel(g);
                }
            }
        };
        drawingPanel.setBackground(Color.WHITE);
        drawingPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                cartesianPainter.setSize(drawingPanel.getWidth(), drawingPanel.getHeight());
                functionPainter.setWidth(drawingPanel.getWidth());
                functionPainter.setHeight(drawingPanel.getHeight());
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
                    JOptionPane.showMessageDialog(MainWindow.this,
                            "Точка с x = " + x + " уже существует",
                            "Ошибка",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        xMinModel = new SpinnerNumberModel(-5.0, -100.0, 4.9, 0.1);
        xMaxModel = new SpinnerNumberModel(5.0, -4.9, 100.0, 0.1);
        yMinModel = new SpinnerNumberModel(-5.0, -100.0, 4.9, 0.1);
        yMaxModel = new SpinnerNumberModel(5.0, -4.9, 100.0, 0.1);

        xMinSpinner = new JSpinner(xMinModel);
        xMaxSpinner = new JSpinner(xMaxModel);
        yMinSpinner = new JSpinner(yMinModel);
        yMaxSpinner = new JSpinner(yMaxModel);

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
        add(drawingPanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel xLinePanel = new JPanel(new BorderLayout());

        JPanel xPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        xPanel.add(new JLabel("X min:"));
        xPanel.add(xMinSpinner);
        xPanel.add(new JLabel("X max:"));
        xPanel.add(xMaxSpinner);
        xLinePanel.add(xPanel, BorderLayout.WEST);

        JPanel checkboxPanel = new JPanel();
        checkboxPanel.setLayout(new BoxLayout(checkboxPanel, BoxLayout.Y_AXIS));
        checkboxPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        showGraphCheckBox = new JCheckBox("Отображать график", true);
        showPointsCheckBox = new JCheckBox("Отображать точки", true);

        showGraphCheckBox.addItemListener(e -> {
            showGraph = showGraphCheckBox.isSelected();
            drawingPanel.repaint();
        });

        showPointsCheckBox.addItemListener(e -> {
            showPoints = showPointsCheckBox.isSelected();
            drawingPanel.repaint();
        });

        checkboxPanel.add(showGraphCheckBox);
        checkboxPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        checkboxPanel.add(showPointsCheckBox);
        xLinePanel.add(checkboxPanel, BorderLayout.EAST);

        controlPanel.add(xLinePanel);

        JPanel yPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        yPanel.add(new JLabel("Y min:"));
        yPanel.add(yMinSpinner);
        yPanel.add(new JLabel("Y max:"));
        yPanel.add(yMaxSpinner);
        controlPanel.add(yPanel);

        add(controlPanel, BorderLayout.SOUTH);

        pack();
    }

    private void drawPointsOnPanel(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.RED);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (var entry : polynomial.getPoints().entrySet()) {
            int xScr = converter.xCrtToScr(entry.getKey());
            int yScr = converter.yCrtToScr(entry.getValue());
            g2d.fillOval(xScr - 3, yScr - 3, 6, 6);
        }
    }
}