

public class TestMessage implements Message {

    private int id;
    private int L;
    private int F;
    
    public TestMessage(int id, int L, int F) {
        this.id = id;
        this.L = L;
        this.F = F;
    }
    
    public void execute(GallagerHumbletSpira instance) {
        if(instance.SN == GallagerHumbletSpira.STATUS_SLEEPING) {
            instance.wakeUp();
        }
        
        if(L > instance.LN) {
            instance.message_queue.add(this);
        } else {
            if(F != instance.FN) {
                instance.sendAccept(id);
            } else {
                if(instance.neighbours_status.get(id) == GallagerHumbletSpira.UNKNOWN) {
                    instance.neighbours_status.put(id, GallagerHumbletSpira.NOT_IN_MST);
                }
                
                if(instance.test_edge != id) {
                    instance.sendReject(id);
                } else {
                    instance.test();
                }
            }
        }
    }

}
