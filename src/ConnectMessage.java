
public class ConnectMessage implements Message {
    
    private int L; //L of sender
    private int id; //id of sender

    public ConnectMessage(int id, int L) {
        this.id = id;
        this.L = L;
    }

    public void execute(GallagerHumbletSpira instance) {
        if(instance.SN == -1) {
            instance.wakeUp();
        }
        
        if(L < instance.LN) {
            instance.neighbours_status.put(id, 1);
            instance.sendInitiate(id, instance.LN, instance.FN, instance.SN);
            if(instance.SN == 0) {
                instance.find_count++;
            }
        } else {
            if(instance.neighbours_status.get(id) == 0) {
                instance.message_queue.add(this);
            } else {
                instance.sendInitiate(id, instance.LN + 1, instance.neighbours_weight.get(id), 0);
            }
        }
    }
}
