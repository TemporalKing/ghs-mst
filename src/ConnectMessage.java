

public class ConnectMessage implements Message {
    
    private int L; //L of sender
    private int id; //id of sender

    public ConnectMessage(int id, int L) {
        this.id = id;
        this.L = L;
    }
    
    public int getId() {
        return id;
    }    

    public void execute(GallagerHumbletSpira instance) {
        if(instance.SN == GallagerHumbletSpira.STATUS_SLEEPING) {
            instance.wakeUp();
        }
        Edge senderEdge = Edge.getEdge(instance.edges, id);
        if(L < instance.LN) {
            senderEdge.setStatus(Edge.IN_MST);
            instance.sendInitiate(id, instance.LN, instance.FN, instance.SN);
            if(instance.SN == GallagerHumbletSpira.STATUS_FIND) {
                instance.find_count++;
            }
        } else {
            if(senderEdge.getStatus() == Edge.UNKNOWN) {
                instance.message_queue.add(this);
            } else {
                instance.sendInitiate(id, instance.LN + 1, senderEdge.getWeight(), GallagerHumbletSpira.STATUS_FIND);
            }
        }
    }
}
