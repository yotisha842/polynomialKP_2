package converting;

public class Converter {
    private int width;
    private int height;
    private double xMin;
    private double xMax;
    private double yMin;
    private double yMax;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = Math.max(height, 1);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = Math.max(width, 1);
    }

    public double getxMin() {
        return xMin;
    }

    public double getxMax() {
        return xMax;
    }

    public double getyMin() {
        return yMin;
    }

    public double getyMax() {
        return yMax;
    }

    public void setXRange(double xMin, double xMax){
        if(xMin == xMax) throw new IllegalArgumentException("Границы отрезка не могут совпадать");
        this.xMin = Math.min(xMax, xMin);
        this.xMax = Math.max(xMax, xMin);
    }

    public void setYRange(double yMin, double yMax){
        if(yMin == yMax) throw new IllegalArgumentException("Границы отрезка не могут совпадать");
        this.yMin = Math.min(yMax, yMin);
        this.yMax = Math.max(yMax, yMin);
    }

    public Converter(double xMin,
                     double xMax,
                     double yMin,
                     double yMax){
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
    }

    public Converter(double xMin,
                     double xMax,
                     double yMin,
                     double yMax,
                     int width,
                     int height){
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
        this.width = width;
        this.height = height;
    }

    private int getXDen(){
        return (int)(width / (xMax - xMin));
    }

    private int getYDen(){
        return (int)(height / (yMax - yMin));
    }

    public int xCrtToScr(double x){
        return (int)(getXDen() * (x - xMin));
    }
    public int yCrtToScr(double y){
        return (int)(getYDen() * (yMax - y));
    }
    public double xScrToCrt(int x){
        return ((double) x / getXDen()) + xMin;
    }
    public double yScrToCrt(int y){
        return yMax - ((double) y / getYDen());
    }
}
