package test;

import converting.Converter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class ConverterTest {

    private Converter conv1;
    private Converter conv2;
    private Converter conv3;
    @BeforeEach
    void setUp() {
        conv1=new Converter(-2,4,-2,4);
        conv1.setWidth(600);
        conv1.setHeight(600);

        conv2=new Converter(2,4,2,4);
        conv2.setWidth(400);
        conv2.setHeight(400);

        conv3=new Converter(-6,-4,-6,-4);
        conv3.setWidth(400);
        conv3.setHeight(400);
    }

    @ParameterizedTest
    @DisplayName("Корректность преобразования абсциссы из декартовой системы координат в экранную при концах отрезка разного знака")
    @CsvSource({
            "-3,-100",
            "-2,0",
            "-1,100",
            "0,200",
            "2,400",
            "4,600",
            "5,700"
    })
    void xCrtToScr1(double cx, int sx) {
        int actual = conv1.xCrtToScr(cx);
        int expected = sx;
        assertEquals(expected,actual);
    }

    @ParameterizedTest
    @DisplayName("Корректность преобразования абсциссы из декартовой системы координат в экранную при положительных концах отрезка")
    @CsvSource({
            "-3,-1000",
            "-2,-800",
            "-1,-600",
            "0,-400",
            "2,0",
            "4,400",
            "5,600"
    })
    void xCrtToScr2(double cx, int sx) {
        int actual = conv2.xCrtToScr(cx);
        int expected = sx;
        assertEquals(expected,actual);
    }

    @ParameterizedTest
    @DisplayName("Корректность преобразования абсциссы из декартовой системы координат в экранную при отрицательных концах отрезка")
    @CsvSource({
            "-7,-200",
            "-6,0",
            "-5,200",
            "-4,400",
            "-3,600",
            "-2,800",
            "-1,1000",
            "0,1200"
    })
    void xCrtToScr3(double cx, int sx) {
        int actual = conv3.xCrtToScr(cx);
        int expected = sx;
        assertEquals(expected,actual);
    }

    @ParameterizedTest
    @DisplayName("Корректность преобразования ординаты из декартовой системы координат в экранную при концах отрезка разного знака")
    @CsvSource({
            "-3,700",
            "-2,600",
            "-1,500",
            "0,400",
            "2,200",
            "4,0",
            "5,-100"
    })
    void yCrtToScr1(double cy, int sy) {
        int actual = conv1.yCrtToScr(cy);
        int expected = sy;
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @DisplayName("Корректность преобразования ординаты из декартовой системы координат в экранную при положительных концах отрезка")
    @CsvSource({
            "-3,1400",
            "-2,1200",
            "-1,1000",
            "0,800",
            "2,400",
            "4,0",
            "5,-200"
    })
    void yCrtToScr2(double cy, int sy) {
        int actual = conv2.yCrtToScr(cy);
        int expected = sy;
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @DisplayName("Корректность преобразования ординаты из декартовой системы координат в экранную при отрицательных концах отрезка")
    @CsvSource({
            "-7,600",
            "-6,400",
            "-5,200",
            "-4,0",
            "-3,-200",
            "-2,-400",
            "-1,-600",
            "0,-800"
    })
    void yCrtToScr3(double cy, int sy) {
        int actual = conv3.yCrtToScr(cy);
        int expected = sy;
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @DisplayName("Корректность преобразования абсциссы из экранной системы координат в декартову при концах отрезка разного знака")
    @CsvSource({
            "-100,-3",
            "0,-2",
            "100,-1",
            "200,0",
            "400,2",
            "600,4",
            "700,5"
    })
    void xScrToCrt1(int sx, double cx) {
        double actual = conv1.xScrToCrt(sx);
        double expected = cx;
        assertEquals(expected, actual, 0.01);
    }

    @ParameterizedTest
    @DisplayName("Корректность преобразования абсциссы из экранной системы координат в декартову при положительных концах отрезка")
    @CsvSource({
            "-1000,-3",
            "-800,-2",
            "-600,-1",
            "-400,0",
            "0,2",
            "400,4",
            "600,5"
    })
    void xScrToCrt2(int sx, double cx) {
        double actual = conv2.xScrToCrt(sx);
        double expected = cx;
        assertEquals(expected, actual, 0.01);
    }

    @ParameterizedTest
    @DisplayName("Корректность преобразования абсциссы из экранной системы координат в декартову при отрицательных концах отрезка")
    @CsvSource({
            "-200,-7",
            "0,-6",
            "200,-5",
            "400,-4",
            "600,-3",
            "800,-2",
            "1000,-1",
            "1200,0"
    })
    void xScrToCrt3(int sx, double cx) {
        double actual = conv3.xScrToCrt(sx);
        double expected = cx;
        assertEquals(expected, actual, 0.01);
    }

    @ParameterizedTest
    @DisplayName("Корректность преобразования ординаты из экранной системы координат в декартову при концах отрезка разного знака")
    @CsvSource({
            "700,-3",
            "600,-2",
            "500,-1",
            "400,0",
            "200,2",
            "0,4",
            "-100,5"
    })
    void yScrToCrt1(int sy, double cy) {
        double actual = conv1.yScrToCrt(sy);
        double expected = cy;
        assertEquals(expected, actual, 0.01);
    }

    @ParameterizedTest
    @DisplayName("Корректность преобразования ординаты из экранной системы координат в декартову при положительных концах отрезка")
    @CsvSource({
            "-1000,9",
            "-800,8",
            "-600,7",
            "-400,6",
            "0,4",
            "400,2",
            "600,1"
    })
    void yScrToCrt2(int sy, double cy) {
        double actual = conv2.yScrToCrt(sy);
        double expected = cy;
        assertEquals(expected, actual, 0.01);
    }

    @ParameterizedTest
    @DisplayName("Корректность преобразования ординаты из экранной системы координат в декартову при отрицательных концах отрезка")
    @CsvSource({
            "1200,-10",
            "1000,-9",
            "800,-8",
            "600,-7",
            "400,-6",
            "200,-5",
            "0,-4",
            "-200,-3"
    })
    void yScrToCrt3(int sy, double cy) {
        double actual = conv3.yScrToCrt(sy);
        double expected = cy;
        assertEquals(expected, actual, 0.01);
    }

    @Test
    @DisplayName("Проверка установки ширины - отрицательное значение заменяется на 1")
    void setWidthNegative() {
        Converter conv = new Converter(0, 10, 0, 10);
        conv.setWidth(-100);
        assertEquals(1, conv.getWidth());
    }

    @Test
    @DisplayName("Проверка установки высоты - отрицательное значение заменяется на 1")
    void setHeightNegative() {
        Converter conv = new Converter(0, 10, 0, 10);
        conv.setHeight(-100);
        assertEquals(1, conv.getHeight());
    }

    @Test
    @DisplayName("Проверка установки диапазона X - границы автоматически сортируются")
    void setXRangeSortsBounds() {
        Converter conv = new Converter(0, 10, 0, 10);
        conv.setXRange(10, 0);
        assertEquals(0, conv.getxMin());
        assertEquals(10, conv.getxMax());
    }

    @Test
    @DisplayName("Проверка установки диапазона Y - границы автоматически сортируются")
    void setYRangeSortsBounds() {
        Converter conv = new Converter(0, 10, 0, 10);
        conv.setYRange(10, 0);
        assertEquals(0, conv.getyMin());
        assertEquals(10, conv.getyMax());
    }

    @Test
    @DisplayName("Проверка выброса исключения при одинаковых границах X")
    void setXRangeThrowsExceptionWhenEqual() {
        Converter conv = new Converter(0, 10, 0, 10);
        assertThrows(IllegalArgumentException.class, () -> conv.setXRange(5, 5));
    }

    @Test
    @DisplayName("Проверка выброса исключения при одинаковых границах Y")
    void setYRangeThrowsExceptionWhenEqual() {
        Converter conv = new Converter(0, 10, 0, 10);
        assertThrows(IllegalArgumentException.class, () -> conv.setYRange(5, 5));
    }
}