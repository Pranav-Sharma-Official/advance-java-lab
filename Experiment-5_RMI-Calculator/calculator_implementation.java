import java .rmi .server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class calculator_implementation extends UnicastRemoteObject{

    calculator_implementation() throws RemoteException{

        super();  // Parent Ko Call Kar Rha H ;


    }

    public int add(int a, int b) {
        return a+b;
    }
    public int subtract(int a, int b) {
        return a-b;
    }

    

}