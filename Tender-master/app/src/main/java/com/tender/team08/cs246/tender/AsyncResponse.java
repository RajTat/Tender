package com.tender.team08.cs246.tender;

/**
 * Created by Michael on 3/7/2018.
 */

public interface AsyncResponse {
    void processFinish(boolean uniqueEntry);

    void processFinish(Patient patient);
}
