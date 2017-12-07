
public class ConnectMessage implements Message {
    private int L;

    public ConnectMessage(int L) {
        this.L = L;
    }
    
    public int getL() {
        return L;
    }

    public void execute(GallagerHumbletSpira instance) {
        //TODO: Logic
    }
}
