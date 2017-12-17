
public class ChangeRootMessage implements Message {
    
    private int id;
    private int counter;
    
    public ChangeRootMessage(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }

    public void execute(GallagerHumbletSpira instance) {
        instance.change_root();
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
