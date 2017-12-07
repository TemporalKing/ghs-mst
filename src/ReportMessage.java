
public class ReportMessage implements Message{
    
    private int id;
    private int w;
    
    public ReportMessage(int id, int w) {
        this.id = id;
        this.w = w;
    }

    public void execute(GallagerHumbletSpira instance) {
        if(id != instance.in_branch) {
            instance.find_count--;
            if(w < instance.best_weight) {
                instance.best_weight = w;
                instance.best_edge = id;
            }
            instance.report();
        } else {
            if(instance.SN == GallagerHumbletSpira.STATUS_FIND) {
                instance.message_queue.add(this);
            } else {
                if(w > instance.best_weight) {
                    instance.change_root();
                } else {
                    if(w == instance.best_weight && w == Integer.MAX_VALUE) {
                        instance.halt();
                    }
                }
            }
        }
    }

}
