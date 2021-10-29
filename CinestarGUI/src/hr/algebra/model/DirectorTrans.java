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
public class DirectorTrans implements Transferable {

    private final Director director;

    public DirectorTrans(Director director) {
        this.director = director;
    }
    
    public static final DataFlavor DIRECTOR_FLAVOR = new DataFlavor(Director.class, "Director");

    public static final DataFlavor[] SUPPORTED_FLAVORS = {DIRECTOR_FLAVOR};

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return SUPPORTED_FLAVORS;
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return flavor.equals(DIRECTOR_FLAVOR);
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        if (flavor.equals(DIRECTOR_FLAVOR)) {
            return director;
        }
        throw new UnsupportedFlavorException(flavor);
    }
}
