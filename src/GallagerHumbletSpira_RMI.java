import java.rmi.Remote;


public interface GallagerHumbletSpira_RMI extends Remote{

    public void receiveMessage(Message m);
}
