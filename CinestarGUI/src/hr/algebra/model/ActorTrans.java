/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.model;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 *
 * @author zvoni
 */
public class ActorTrans implements Transferable {
    
     private final Actor actor;
    
    public ActorTrans(Actor actor) {
        this.actor = actor;
    }
    
    public static final DataFlavor ACTOR_FLAVOR = new DataFlavor(Actor.class, "Actor");

    public static final DataFlavor[] SUPPORTED_FLAVORS = {ACTOR_FLAVOR};

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return SUPPORTED_FLAVORS;
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return flavor.equals(ACTOR_FLAVOR);
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        if (flavor.equals(ACTOR_FLAVOR)) {
            return actor;
        }
        throw new UnsupportedFlavorException(flavor);
    }   
}
