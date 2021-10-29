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
public class GenreTrans implements Transferable {
    
    private final Genre genre;

    public GenreTrans(Genre genre) {
        this.genre = genre;
    }
    
    public static final DataFlavor GENRE_FLAVOR = new DataFlavor(Genre.class, "Genre");

    public static final DataFlavor[] SUPPORTED_FLAVORS = {GENRE_FLAVOR};

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return SUPPORTED_FLAVORS;
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return flavor.equals(GENRE_FLAVOR);
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        if (flavor.equals(GENRE_FLAVOR)) {
            return genre;
        }
        throw new UnsupportedFlavorException(flavor);
    }
}
