package introb;


import javax.swing.event.EventListenerList;

/**
 * @author hitesh
 *
 * DataChangeListnerRegistry class registers DataChangeListerns.
 */
public abstract class DataChangeListenerRegistry {
	
	//Change Listeners
	private EventListenerList m_listeners;


	/**
	 * Default Constructor
	 */
	public DataChangeListenerRegistry() {
		m_listeners = new EventListenerList();
	}

	
	/**
     * Adds a listener to the list that's notified each time a change
     * to the data model occurs.
     * @param l
	 */
    public void addChangeListener(DataChangeListener l) {
    	m_listeners.add(DataChangeListener.class, l);
    }


    /**
     * Removes a listener from the list that's notified each time a
     * change to the data model occurs.
     */
    public void removeChangeListener(DataChangeListener l) {
    	m_listeners.remove(DataChangeListener.class, l);
    }

    
    /**
     * @return array of all the registered data change listeners.
     */
    public DataChangeListener[] getTableModelListeners() {
        return (DataChangeListener[])m_listeners.getListeners(DataChangeListener.class);
    }
    
    /**
     * This method will notify all the registered listeners of the
     * data change. 
     */
	public void notifyListeners() {
		// Notify all the listeners of the change..
		Object[] listeners = m_listeners.getListenerList();
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length-2; i>=0; i-=2) {
		    if (listeners[i]==DataChangeListener.class) {
		    	((DataChangeListener)listeners[i+1]).dataChanged();
		    }
		}
	}
}
