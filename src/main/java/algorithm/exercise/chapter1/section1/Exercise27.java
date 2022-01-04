package algorithm.exercise.chapter1.section1;

public class Exercise27 {
    public static double binomial(int N,int k,double p){

        double[][] answers = new double[N+1][k+1];
        for (int i = 0; i <= N; i++) {
            for (int j = 0; j <= k; j++) {
                answers[i][j] = -1;
            }
        }

        return binomial(N, k, p,answers);
        
        
    }

    private static double binomial(int N, int k, double p, double[][] answers) {
        if((N==0) && (k ==0)) return 1.0;
        if((N<0) || (k<0)) return 0.0;
        if(answers[N][k] != -1){
            return answers[N][k];
        }
        double val1 = binomial(N-1, k, p,answers);
        double val2 = binomial(N-1, k-1, p,answers);
        double result = (1-p)*val1 + p*val2;
        answers[N][k] = result;
        return result;
    }    

    public static void main(String[] args) {
        System.out.println(binomial(100, 50, 0.25));
    }
}
