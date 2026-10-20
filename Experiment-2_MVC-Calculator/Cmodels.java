public class Cmodels {

    public double add(double a ,double b){

        return a+b;

    }

    public double sub(double a ,double b){

        return a-b;

    }

    public double mul(double a ,double b){

        return a*b;

    }


    public double div(double a ,double b){

        if(b==0){
            return 0;
        }

        return a/b;

    }


    public double per(double a ,double b){

        return (a+b)/100;

    }


    
}
