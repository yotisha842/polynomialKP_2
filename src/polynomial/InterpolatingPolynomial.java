package polynomial;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.List;
import java.util.Map;

public class InterpolatingPolynomial extends Polynomial {
    private final TreeMap<Double, Double> points;

    public Map<Double, Double> getPoints(){
        return new TreeMap<>(points);
    }

    public InterpolatingPolynomial(){
        super();
        this.points = new TreeMap<>();
    }

    public InterpolatingPolynomial(Map<Double, Double> points){
        super();
        this.points = new TreeMap<>(points);
        createNewton();
    }

    public void addPoint(double x, double y) {
        if (points.containsKey(x)) {
            throw new IllegalArgumentException("Точка с x уже существует");
        }

        boolean isEnd = points.isEmpty() || x > points.lastKey();

        points.put(x, y);

        if (isEnd) incrementalAddToEnd(x, y);
        else createNewton();
    }

    private void incrementalAddToEnd(double x, double y) {
        if (points.size() == 1) {
            coeffs.clear();
            coeffs.put(0, y);
            return;
        }

        List<Double> xs = new ArrayList<>(points.keySet());
        xs.removeLast();

        double px = calc(x);

        double prod = 1.0;
        for (double xi : xs) {
            prod *= (x - xi);
        }

        double newDiff = (y - px) / prod;

        Polynomial pi = new Polynomial(1.0);
        for (double xi : xs) {
            pi = pi.times(new Polynomial(-xi, 1.0));
        }

        Polynomial newTerm = pi.times(newDiff);

        Polynomial result = new Polynomial();
        result.coeffs.putAll(this.coeffs);
        result = result.plus(newTerm);

        coeffs.clear();
        coeffs.putAll(result.getCoeffs());
    }

    private void createNewton() {
        if (points.isEmpty()) {
            coeffs.clear();
            coeffs.put(0, 0.0);
            return;
        }

        List<Double> xList = new ArrayList<>(points.keySet());
        List<Double> yList = new ArrayList<>(points.values());

        double[] divided = computeDividedDifferences(xList, yList);

        Polynomial result = new Polynomial(divided[0]);
        Polynomial pi = new Polynomial(1.0);

        for (int k = 1; k < xList.size(); k++) {
            Polynomial factor = new Polynomial(-xList.get(k - 1), 1.0);
            pi = pi.times(factor);

            Polynomial term = pi.times(divided[k]);
            result = result.plus(term);
        }

        coeffs.clear();
        coeffs.putAll(result.getCoeffs());
    }

    private double[] computeDividedDifferences(List<Double> xList, List<Double> yList) {
        int n  = xList.size();

        double[] dd = new double[n];
        for (int i = 0; i < n; i++) {
            dd[i] = yList.get(i);
        }

        for (int j = 1; j < n; j++) {
            for (int k = n - 1; k >= j; k--) {
                dd[k] = (dd[k] - dd[k - 1]) / (xList.get(k) - xList.get(k - j));
            }
        }

        return dd;
    }

    public boolean removePoint(double x, double y) {
        if (points.containsKey(x)) {
            points.remove(x);
            createNewton();
            return true;
        }
        return false;
    }
}