

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
        instance.println("Entered Connect Message Execute: " + id);
        if(L < instance.LN) {
        	// Absorb
        	instance.println("Absorb " + id);
            senderEdge.setStatus(Edge.IN_MST);
            instance.sendInitiate(id, instance.LN, instance.FN, instance.SN);
            if(instance.SN == GallagerHumbletSpira.STATUS_FIND) {
                instance.find_count++;
            }
        } else {
            if(senderEdge.getStatus() == Edge.UNKNOWN) {
            	instance.println("Added connect message to queue");
                instance.message_queue.add(this);
            } else {
            	// Merge
            	instance.println("Merge " + id);
                instance.sendInitiate(id, instance.LN + 1, senderEdge.getWeight(), GallagerHumbletSpira.STATUS_FIND);
            }
        }
    }
}
