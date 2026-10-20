public class main {

    public static void main(String[] args) {

        System.out.println("Hello");

        Cmodels cm = new Cmodels();
        Cviews cv = new Cviews();

        Ccontroller controller = new Ccontroller(cm, cv);
    }
}