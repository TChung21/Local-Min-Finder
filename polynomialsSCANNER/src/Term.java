public class Term {
        public double coefficient;
        public double power;

        public Term (double coef, double expo){
            coefficient=coef;
            power=expo;
        }


        public String toString(){
            return "TERMS: " + coefficient + "x^" + power;
        }
}
