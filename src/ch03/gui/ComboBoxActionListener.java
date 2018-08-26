package ch03.gui;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

class ItemChangeListener implements ItemListener{
    @Override
    public void itemStateChanged(ItemEvent evt) {
       if (evt.getStateChange() == ItemEvent.SELECTED) {
          Object item = evt.getItem();
          // do something with object
          System.out.println(item.toString());
       }
    }       
}