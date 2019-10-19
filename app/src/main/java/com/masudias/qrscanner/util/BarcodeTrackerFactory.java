package com.masudias.qrscanner.util;

import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.barcode.Barcode;

/**
 * Factory for creating a tracker and associated graphic to be associated with a new barcode.  The
 * multi-processor uses this factory to create barcode trackers as needed -- one for each barcode.
 */
class BarcodeTrackerFactory implements MultiProcessor.Factory<Barcode> {

    private BarcodeGraphicTracker.BarcodeDetectorListener mDetectorListener;

    BarcodeTrackerFactory(BarcodeGraphicTracker.BarcodeDetectorListener detectorListener) {
        mDetectorListener = detectorListener;
    }

    @Override
    public Tracker<Barcode> create(Barcode barcode) {
        BarcodeGraphicTracker graphicTracker = new BarcodeGraphicTracker();
        if (mDetectorListener != null)
            graphicTracker.setDetectorListener(mDetectorListener);
        return graphicTracker;
    }
}

