package polynomial;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Polynomial {
    protected final Map<Integer, Double> coeffs;
    private static final double EPSILON = 1e-10;

    public Map<Integer, Double> getCoeffs() {
        return new TreeMap<>(coeffs);
    }

    public Polynomial() {
        coeffs = new TreeMap<>(
                (o1, o2) -> o2.compareTo(o1)
        );
        coeffs.put(0, 0.0);
    }

    public Polynomial(Map<Integer, Double> coeffs) {
        this.coeffs = new TreeMap<>(
                (o1, o2) -> o2.compareTo(o1)
        );
        this.coeffs.putAll(coeffs);
        filterCoeffs();
    }

    public Polynomial(double... coeffs) {
        this.coeffs = new TreeMap<>(
                (o1, o2) -> o2.compareTo(o1)
        );
        for (int i = 0; i < coeffs.length; i++) {
            this.coeffs.put(i, coeffs[i]);
        }
        filterCoeffs();
    }

    public Polynomial(List<Double> coeffs) {
        this.coeffs = new TreeMap<>(
                (o1, o2) -> o2.compareTo(o1)
        );
        for (int i = 0; i < coeffs.size(); i++) {
            this.coeffs.put(i, coeffs.get(i));
        }
        filterCoeffs();
    }

    private void filterCoeffs() {
        coeffs.entrySet().removeIf(
                entry ->
                        Math.abs(entry.getValue()) < EPSILON ||
                                entry.getKey() < 0
        );
        if (coeffs.isEmpty()) {
            coeffs.put(0, 0.0);
        }
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (var entry : coeffs.entrySet()){
            if (entry.getValue() >= 0) {
                if (i != 0) sb.append("+");
            } else {
                sb.append("-");
            }
            if (Math.abs(entry.getValue()) != 1.0
                    || entry.getKey() == 0
            ) {
                sb.append(Math.abs(entry.getValue()));
            }
            if (entry.getKey() != 0) sb.append("x");
            if (entry.getKey() > 1) {
                sb.append("^").append(entry.getKey());
            }
            i++;
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;

        if (!(o instanceof Polynomial that)) {
            return false;
        }

        if(this.coeffs.size() != that.coeffs.size()) {
            return false;
        }

        for(var entry : this.coeffs.entrySet()){
            Integer thisKey = entry.getKey();
            Double thisValue = entry.getValue();
            Double thatValue = that.coeffs.get(thisKey);

            if (Math.abs(thisValue - thatValue) >= EPSILON) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 1;

        for (var entry : coeffs.entrySet()) {
            result = 31 * result + entry.getKey().hashCode();
            result = 31 * result + entry.getValue().hashCode();
        }

        return result;
    }

    public int degree() {
        return coeffs.keySet().iterator().next();
    }

    public Polynomial plus(Polynomial other){
        var newCoeffs = new TreeMap<Integer, Double>(coeffs);
        for (var p:other.coeffs.entrySet()) {
            newCoeffs.put(p.getKey(), newCoeffs.getOrDefault(p.getKey(), 0.0)+ p.getValue());

        }
        return new Polynomial(newCoeffs);
    }

    public Polynomial minus(Polynomial other){
        var newCoeffs = new TreeMap<Integer, Double>(coeffs);
        for (var p:other.coeffs.entrySet()) {
            newCoeffs.put(p.getKey(), newCoeffs.getOrDefault(p.getKey(), 0.0) - p.getValue());
        }
        return new Polynomial(newCoeffs);
    }

    public Polynomial times(Polynomial other) {
        var newCoeffs = new TreeMap<Integer, Double>();

        for (var p1 : this.coeffs.entrySet()) {
            for (var p2 : other.coeffs.entrySet()) {
                int newDegree = p1.getKey() + p2.getKey();
                double newValue = p1.getValue() * p2.getValue();
                newCoeffs.put(newDegree, newCoeffs.getOrDefault(newDegree, 0.0) + newValue);
            }
        }
        return new Polynomial(newCoeffs);
    }

    public Polynomial times(double v){
        var newCoeffs = new TreeMap<Integer, Double>();
        for (var p: coeffs.entrySet()) {
            newCoeffs.put(p.getKey(), p.getValue() * v);
        }
        return new Polynomial(newCoeffs);
    }

    public Polynomial div(double v) {
        if (Math.abs(v) < EPSILON) {
            throw new ArithmeticException("Деление на ноль");
        }
        var newCoeffs = new TreeMap<Integer, Double>();
        for (var p : coeffs.entrySet()) {
            newCoeffs.put(p.getKey(), p.getValue() / v);
        }
        return new Polynomial(newCoeffs);
    }

    public double calc(double x) {
        double result = 0.0;
        for (var entry : coeffs.entrySet()) {
            result += entry.getValue() * Math.pow(x, entry.getKey());
        }
        return result;
    }
}