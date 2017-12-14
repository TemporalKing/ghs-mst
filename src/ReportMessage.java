
public class ReportMessage implements Message{
    
    private int id;
    private int w;
    
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
            	instance.println("Added report message to queue");
                instance.message_queue.add(this);
            } else {
                if(w > instance.best_edge.getWeight()) {
                    instance.change_root();
                } else {
                    if(w == instance.best_edge.getWeight() && w == Integer.MAX_VALUE) {
                        // HALT
                    	instance.println("HALT");
                    }
                }
            }
        }
    }
}
