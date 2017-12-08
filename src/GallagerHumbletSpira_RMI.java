import java.rmi.Remote;
import java.rmi.RemoteException;


public interface GallagerHumbletSpira_RMI extends Remote{

    public void receiveMessage(Message m) throws RemoteException;
}
