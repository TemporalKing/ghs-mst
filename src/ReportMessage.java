
public class ReportMessage implements Message{
    
    private int id;
    private int w;
    private int counter;
    
    public ReportMessage(int id, int w) {
        this.id = id;
        this.w = w;
    }

    public int getId() {
        return id;
    }
    
    public void execute(GallagerHumbletSpira instance) {
        
    	if(id != instance.in_branch) {
            instance.find_count--;
            if(w < instance.best_edge.getWeight()) {
            	instance.best_edge = new Edge(id, w);
            }

            instance.report();
        } else {
            if(instance.SN == GallagerHumbletSpira.STATUS_FIND) {
            	instance.println(String.format("Added REPORT %d.%d message to queue", this.getId(), this.getMessageCounter()));
                instance.message_queue.add(this);
            } else {
                if(w > instance.best_edge.getWeight()) {
                    instance.change_root();
                } else {
                    if(w == instance.best_edge.getWeight() && w == Integer.MAX_VALUE) {
                        // HALT
                    	instance.println("HALT");
                    	instance.println(String.format("Level %d, Fragment Name %d, Status %d, In branch %d", instance.LN, instance.FN, instance.SN, instance.in_branch));
                    	new TerminateMessage(instance.id).execute(instance);
                    }
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
