

public class TestMessage implements Message {

    private int id;
    private int L;
    private int F;
    private int counter;
    
    public TestMessage(int id, int L, int F) {
        this.id = id;
        this.L = L;
        this.F = F;
    }
    
    public int getId() {
        return id;
    }
    
    public void execute(GallagerHumbletSpira instance) {
        if(instance.SN == GallagerHumbletSpira.STATUS_SLEEPING) {
            instance.wakeUp();
        }
        instance.println(String.format("Test Message Executing"));
        if(L > instance.LN) {
        	instance.println(String.format("Adding TEST %d.%d message to queue", this.getId(), this.getMessageCounter()));
            instance.message_queue.add(this);
        } else {
            if(F != instance.FN) {
                instance.sendAccept(id);
            } else {
            	Edge senderEdge = Edge.getEdge(instance.edges, id);
                if(senderEdge.getStatus() == Edge.UNKNOWN) {
                    senderEdge.setStatus(Edge.NOT_IN_MST);
                }
                
                if(instance.test_edge != id) {
                    instance.sendReject(id);
                } else {
                    instance.test();
                }
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
