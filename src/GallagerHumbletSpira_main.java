import java.rmi.RMISecurityManager;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;


public class GallagerHumbletSpira_main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// Localhost IP
		String localhost = System.getProperty("java.rmi.server.hostname");

		// TODO read in network topology and nodes per host
		
		// Add security manager
		if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }
		
		// Create Registry
		try{
            Registry registry = LocateRegistry.createRegistry(1099);
        } catch (Exception e) {
            System.err.println("Could not create registry exception: " + e.toString()); 
            e.printStackTrace(); 
        } 
		
		// TODO Generate GallagerHumbletSpira Objects for the local system
		
		
		
		// Pause execution until entire network is up and running
		System.out.println("Press enter to continue");
        Scanner scan = new Scanner(System.in);
        scan.nextLine();
        scan.close();
        System.out.println("Finding MST using Gallager Humblet Spira Algorithm");
        
		// TODO Wake some random nodes

	}

}
