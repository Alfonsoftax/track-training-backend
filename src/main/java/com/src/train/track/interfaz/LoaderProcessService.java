package com.src.train.track.interfaz;

import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Queue;

import org.aspectj.weaver.tools.Trace;

import com.src.train.track.general.GeneralData;
import com.src.train.track.general.domain.LoaderFile;



public interface LoaderProcessService {

    /**
     * Find files.
     *
     * @param generalData
     *            the general data
     * @return the queue
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    default Queue<LoaderFile> findFiles(final GeneralData generalData) throws IOException {
        // Empty implementation
        return new PriorityQueue<>();
    }

    /**
     * Execute.
     *
     * @param loaderFile
     *            the loader file
     */
    default void execute(final LoaderFile loaderFile, final GeneralData generalData) {
        // Empty implementation
    }



}
