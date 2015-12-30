package introb;

 

import java.util.EventListener;

/**
 * @author hitesh
 *
 * DataChangeListener Interface.
 */
public interface DataChangeListener extends EventListener {
	
	/**
	 * This method will be called whenever there is change in data
	 * from a DataObject or a DataSet.
	 */
	public void dataChanged();
	
}
