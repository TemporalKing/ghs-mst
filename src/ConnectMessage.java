

public class ConnectMessage implements Message {
    
    private int L; //L of sender
    private int id; //id of sender
    private int counter;

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
        	// Absorb
        	instance.println("Absorb " + id);
            senderEdge.setStatus(Edge.IN_MST);
            instance.sendInitiate(id, instance.LN, instance.FN, instance.SN);
            if(instance.SN == GallagerHumbletSpira.STATUS_FIND) {
                instance.find_count++;
            }
        } else {
            if(senderEdge.getStatus() == Edge.UNKNOWN) {
            	instance.println(String.format("Added CONNECT %d.%d message to queue", this.getId(), this.getMessageCounter()));
                instance.message_queue.add(this);
            } else {
            	// Merge
            	instance.println("Merge " + id);
                instance.sendInitiate(id, instance.LN + 1, senderEdge.getWeight(), GallagerHumbletSpira.STATUS_FIND);
            }
        }
    }
    
    @Override
	public int getMessageCounter() {
		// TODO Auto-generated method stub
		return counter;
	}
	
	@Override
	public void setMessageCounter(int counter) {
		// TODO Auto-generated method stub
		this.counter = counter;
	}
}
