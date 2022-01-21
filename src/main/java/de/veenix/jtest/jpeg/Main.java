package de.veenix.jtest.jpeg;

public class Main {

    public static void main(String[] args) {
        int color = 0xA6617E;
        int red = (color >> 16) & 255;
        int green = (color >> 8) & 255;
        int blue = color & 255;

        double[] conv = rgbToYCbCr(red, green, blue);
        double y = conv[0];
        double pb = conv[1];
        double pr = conv[2];

        System.out.println(y + " | " + pb + " | " + pr);
    }

    public static double[] rgbToYCbCr(int red, int green, int blue) {
        return new double[] {(0.299*red)      +(0.587*green)      +(0.114*blue),
                             (-0.168736*red) +(-0.331264*green)  +(0.5*blue),
                             (0.5*red)       +(-0.418688*green)  +(-0.081312*blue)};
    }
}
