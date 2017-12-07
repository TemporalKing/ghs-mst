import java.util.Iterator;

public class InitiateMessage implements Message {

    private int L;
    private int F;
    private int S;
    private int id; //ID of sender
    
    public InitiateMessage(int id, int L, int F, int S) {
        this.id = id;
        this.L = L;
        this.F = F;
        this.S = S;
    }
    
    public void execute(GallagerHumbletSpira instance) {
        instance.LN = L;
        instance.FN = F;
        instance.SN = S;
        instance.in_branch = instance.neighbours_weight.get(id);
        instance.best_edge = -1;
        instance.best_weight = Integer.MAX_VALUE;
        
        Iterator<Integer> keys = instance.neighbours_status.keySet().iterator();
        while(keys.hasNext()) {
            int adjacent_edge = keys.next();
            if(adjacent_edge != id && instance.neighbours_status.get(adjacent_edge) == 1) {
                instance.sendInitiate(adjacent_edge, L, F, S);
                if (S == 0) {
                    instance.find_count++;
                }
            }
        }
        
        if(S == 0) {
            instance.test();
        }
    }

}
