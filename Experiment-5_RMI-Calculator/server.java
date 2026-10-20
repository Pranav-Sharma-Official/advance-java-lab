import java.rmi.Naming;
public class server {
public static void main(String[] args){

    try{

        calculator_implementation obj = new calculator_implementation();  // object create kiya h calculator implementation class ka 

        Naming.rebind("rmi://localhost/calculator" , obj);  // url ko server pe register karne ke liye 

        System.out.println("Rmi Server Started...");  // to show that rmi server is started ..
         
    }

    catch(Exception e){

        System.out.println(e);

    }


}
    
}
