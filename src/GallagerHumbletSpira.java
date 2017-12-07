
public class GallagerHumbletSpira implements GallagerHumbletSpira_RMI{

    private int id;
    
    private int LN; //Level of the current fragment it is part of
    private int FN; //Name of the current fragment it is part of
    private int SN; //State of the node, 0 = find, 1 = found
    private int in_branch; //ID of edge towards core
    private int test_edge; //ID of edge checked whether other end in same fragment
    private int best_edge; //ID of candidate MOE
    private int best_weight; //Weight of candidate MOE
    private int find_count; //Number of report messages expected
	
    public void receiveMessage(Message m) {
        m.execute(this);
    }
    
    private void println(String message)
    {
        String pidStr = "(" + this.id + ") ";
        System.err.println(pidStr + message);
    }
    
}
