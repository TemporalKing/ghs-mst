
public class ConnectMessage implements Message {
    
    private int L; //L of sender
    private int id; //id of sender

    public ConnectMessage(int id, int L) {
        this.id = id;
        this.L = L;
    }

    public void execute(GallagerHumbletSpira instance) {
        if(instance.SN == GallagerHumbletSpira.STATUS_SLEEPING) {
            instance.wakeUp();
        }
        
        if(L < instance.LN) {
            instance.neighbours_status.put(id, GallagerHumbletSpira.IN_MST);
            instance.sendInitiate(id, instance.LN, instance.FN, instance.SN);
            if(instance.SN == GallagerHumbletSpira.STATUS_FIND) {
                instance.find_count++;
            }
        } else {
            if(instance.neighbours_status.get(id) == GallagerHumbletSpira.UNKNOWN) {
                instance.message_queue.add(this);
            } else {
                instance.sendInitiate(id, instance.LN + 1, instance.neighbours_weight.get(id), 0);
            }
        }
    }
}
