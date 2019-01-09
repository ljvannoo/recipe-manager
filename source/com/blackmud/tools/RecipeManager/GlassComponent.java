/******************************************************
 *
 * Copyright © 2004 Northrop Grumman Corporation
 *
 * All Rights Reserved
 *
 * This material may be reproduced by or for the U.S.
 * Government pursuant to the copyright license under
 * the clause at Defense Federal Acquisition Regulation
 * Supplement (DFARS) 252.227-7014 (June 1995).
 *
 ******************************************************/
package com.blackmud.tools.RecipeManager;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

/**
 * All-consuming glass pane.
 */
public class GlassComponent extends JComponent implements AWTEventListener
{
    private static final long serialVersionUID = 1L;
    Window parentWindow;
    
    /**
     * Instantiates a new glass component.
     */
    public GlassComponent ()
    {
        super ();
        this.setCursor (Cursor.getPredefinedCursor
        (Cursor.WAIT_CURSOR));
        setOpaque (false);
        
        addMouseListener (new MouseAdapter ()
        {});
    }
    
    /**
     * Override setVisible to install/remove key events hook
     * that will allow us to
     * disable key events when the glass pane is visible.
     * 
     * @param visible the boolean visible flag
     */
    public void setVisible (boolean visible)
    {
        if (visible)
        {
            if (this.parentWindow == null)
                this.parentWindow =
                SwingUtilities.windowForComponent (this);
            
            Toolkit.getDefaultToolkit ().addAWTEventListener
            (this, AWTEvent.KEY_EVENT_MASK);
        }
        else
        {
            Toolkit.getDefaultToolkit ().removeAWTEventListener
            (this);
        }
        super.setVisible (visible);
    }
    
    /**
     * Called whenever ther is an event in AWT queue. Note that
     * the current implementation
     * skips all key events, not just events for this window.
     * Logic can be enhanced to examine
     * the source of the event and it's parent window and skip
     * only those events
     * that originated from disabled window
     * 
     * @param event the event
     */
    public void eventDispatched (AWTEvent event)
    {
        if (event instanceof KeyEvent && event.getSource () instanceof Component)
        {
            if (SwingUtilities.windowForComponent ((Component)
            event.getSource ()) == this.parentWindow)
                // Consume events only for our window
                ((KeyEvent)event).consume ();
        }
    }
}
