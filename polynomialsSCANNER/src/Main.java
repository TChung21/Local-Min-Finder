//TCHUNG 2019 Code
import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;
//smaller to bigger to calculate inflexion points
// 5x^5-30x case
//5x^{4}-3x^{2} same -.35 problem //not finding negative value

public class Main {

    public static double interval, m, m2, y1, y2, y3, y4, x2, x1, finalx, finaly, xint, xint2;
    public static int counter = 0;
    public static boolean found = false;
    public static boolean MultiZero = false;
    public static ArrayList<Term> terms = new ArrayList<Term>();

    public static void main(String[] args) {
        interval = .5;
        x2 = 100; //could change domain/range
        Scanner scan = new Scanner(System.in);
        String equation = scan.nextLine();
        split(equation);
        Main.MaxMin(terms);
        Main.secondMax(terms);
        interval = .5;
        x2 = 100;
        Main.zero(terms);
        Main.multiZero(terms);
        Main.multiZero(terms);

    }

    public static double[] MaxMin(ArrayList<Term> input) { //ArrayList<Term> input
        for (int i = 0; i < (2 / interval * 100); i++) {
            found = false;
            x1 = x2;
            x2 = x2 - interval;
            for (int j = 0; j < input.size(); j++) {
                if (j == 0) {
                    y1 = (input.get(j).coefficient * Math.pow(x1, input.get(j).power));
                    y2 = (input.get(j).coefficient * Math.pow(x2, input.get(j).power));
                    y3 = (input.get(j).coefficient * Math.pow((x1 - interval), input.get(j).power));
                    y4 = (input.get(j).coefficient * Math.pow((x2 - interval), input.get(j).power));
                } else {
                    y1 = y1 + (input.get(j).coefficient * Math.pow(x1, input.get(j).power));
                    y2 = y2 + (input.get(j).coefficient * Math.pow(x2, input.get(j).power));
                    y3 = y3 + (input.get(j).coefficient * Math.pow((x1 - interval), input.get(j).power));
                    y4 = y4 + (input.get(j).coefficient * Math.pow((x2 - interval), input.get(j).power));
                }
            }
            //System.out.println(x1 + ", " + x2 + ", " + y1 + ", " + y2 + ", " + m + ", " + m2);
            m = (y2 - y1) / (x2 - x1);
            m2 = (y4 - y3) / ((x1) - (x1 + interval));

            if (m > .0001 || m < -.0001) {
                if (m / m2 < 0) {
                    interval = interval / 10;
                    x2 = x1;
                    found = false;
                    //System.out.println(x1 + ", " + x2 + ", " + y1 + ", " + y2 + ", " + m + ", " + m2);
                }
            }
            if (m < .0001 && m > -.0001) {
                found = true;
                finalx = (x1 + x2) / 2;
                finaly = (y1 + y2) / 2;
                System.out.println("Max/Min Coordinate: (" + finalx + ", " + finaly + ")");
                return (new double[]{finalx, finaly});
            }
        }
        return (new double[]{420, 69});
    }

    public static double[] secondMax(ArrayList<Term> input2) {
        if (found) {
            interval = .5;
            x2 = finalx - (interval / 2);
            return MaxMin(input2);
        }
        return (new double[]{69, 420});
    }

    public static double[] zero(ArrayList<Term> input) {
        //when y is 0, x is...

        for (int i = 0; i < (2 / interval * 100); i++) {
            MultiZero = false;
            x1 = x2;
            x2 = x2 - interval;
            for (int j = 0; j < input.size(); j++) {
                if (j == 0) {
                    y1 = (input.get(j).coefficient * Math.pow(x1, input.get(j).power));
                    y2 = (input.get(j).coefficient * Math.pow(x2, input.get(j).power));
                } else {
                    y1 = y1 + (input.get(j).coefficient * Math.pow(x1, input.get(j).power));
                    y2 = y2 + (input.get(j).coefficient * Math.pow(x2, input.get(j).power));
                }
            }
            //System.out.println(x1 + ", " + x1 + ", " + y1 + ", " + y2);

            if ((y1 > .000001 || y2 < -.000001) || (y1 < -.000001 || y2 > .000001)) {
                if (y1 / y2 < 0) {
                    interval = interval / 10;
                    x2 = x1;
                }
            }

            if ((y1 < .000001 && y1 > 0 && y2 > -.000001 && y2 < 0) || (y2 < .000001 && y2 > 0 && y1 > -.000001 && y1 < 0)) { //need above too? works so that y1 can't be negative and y2 be positive and still fit the condition
                //System.out.println("inside condition: " + x1 + ", " + x1 + ", " + y1 + ", " + y2);
                MultiZero = true;
                counter++;
                xint = x1; //better not to average here
                System.out.println("zero " + counter + ": (" + xint + ", 0.0)");
                return (new double[]{xint});
            }

        }

        return (new double[]{xint});
    }

    public static double[] multiZero(ArrayList<Term> input) {

        if (MultiZero) {
            interval = .5;
            x2 = xint - (interval / 2);
            return zero(input);
        }

        return (new double[]{69, 420});

    }

    public static void split(String d) {
        int counter = 1;
        String[] arrOfStr = d.split("\\+");
        for (String a : arrOfStr) counter++;
        double[] coefficient;
        double[] power;
        coefficient = new double[counter];
        power = new double[counter];

        for (String a : arrOfStr) {
            //System.out.println("Term:" + a);
            String[] arrOfStr2 = a.split("\\^");
            String[] arrOfStr3 = arrOfStr2[0].split("x");
            for (String c : arrOfStr3) {
                //System.out.println("Cofficient: " + c);
                coefficient[counter - 1] = Double.parseDouble(c);
                if (arrOfStr2.length == 2) {
                    //System.out.println("Power:" + arrOfStr2[1]);
                    power[counter - 1] = Double.parseDouble(arrOfStr2[1]);
                } else {
                    power[counter - 1] = 1;
                }
                terms.add(new Term(coefficient[counter - 1], power[counter - 1]));
                //System.out.println("Term Coefficient is " + coefficient[counter - 1]);
                //System.out.println("Term Power is " + power[counter - 1]);
            }
        }
    }
}