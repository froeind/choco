
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.RealVar;

public class SolveIt {
    public static void main(String[] args) {

        // Erstelle ein neues Modell
        Model model = new Model("Simple Problems");

        // Definiere eine Integer-Variable x, die zwischen 1 und 10 liegen muss
        IntVar x = model.intVar("x", 1, 10);
        IntVar y = model.intVar("y", -1, 6);

        // Definiere eine Constraint: x muss größer oder gleich 5 sein
        model.arithm(x, ">=", 5).post();
        model.arithm(x, "<=", 9).post();
        model.arithm(y, ">=", 6).post();

        // Gerade 1: y = 3x - 2
        // Gerade 2: y = -x + 4
        double p = 0.0000001d;
        //RealVar xx = model.realVar(-2, 10, p);
        //RealVar xx = model.realVar(-2, -1, p);
        // hier gibt es keine Lösung - zurecht
        //RealVar xx = model.realVar(-2222, -111, p);
        // hier gibt es eine grottenfalsche Lösung - zuunrecht
        RealVar xx = model.realVar(-2222, -1, p);
        // und hier gibt es eine Lösung - zurecht
        //RealVar xx = model.realVar(-2, 10, p);
        RealVar yy = model.realVar(-7, 9, p);
        // 3 * xx - 2 = - xx + 4
        xx.mul(3).sub(2).eq(yy.mul(-1).add(4)).equation().post();
        // x / (y-2) <= 1.5
        //x.div(y.sub(2)).le(1.5).equation().post();

        // es zeigt sich: Reihenfolge der arithms egal und laufen unabhängig

        // Löse das Modell
        Solver solver = model.getSolver();
        boolean isSolutionFound = solver.solve();

        // Gib die Lösung aus, falls eine gefunden wurde
        if (isSolutionFound) {
            System.out.println("Eine Lösung wurde gefunden:");
            System.out.println("x = " + x.getValue());
            System.out.println("x lb = " + x.getLB());
            System.out.println("x ub = " + x.getUB());
            System.out.println("y = " + y.getValue());
            //System.out.println("xx = " + xx.toString());
            System.out.println("xx = " + xx);
            //System.out.println("yy = " + yy.toString());
            System.out.println("yy = " + yy);
            System.out.println("xx lb = " + xx.getLB());
            System.out.println("xx ub = " + xx.getUB());
        } else {
            System.out.println("Keine Lösung gefunden.");
        }
    }
}