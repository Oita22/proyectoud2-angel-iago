package apirequests.proj_u1.model;

import java.io.File;
import java.io.Serializable;

/**
 * Config class model. Used to save and load the last state of the program just before exiting.
 *
 * @see java.io.Serializable
 */
public class Config implements Serializable {
    /**
     * Version number
     */
    private static final int serialVersionUID = 1;
    /**
     * Path to file in tmp (cache)
     */
    public static File tempPathCache = new File(System.getProperty("java.io.tmpdir") + "\\newsCache.bin");
    private boolean restoreLastSession = false;

    public Config() {
    }

    public boolean isRestoreLastSession() {
        return restoreLastSession;
    }

    public void setRestoreLastSession(boolean restoreLastSession) {
        this.restoreLastSession = restoreLastSession;
    }
}
