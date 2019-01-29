
package es.logongas.fpempresa.service.mail;

/**
 * Datos para añadir a un correo
 * @author logongas
 */
public class Attach {
    private final String fileName;
    private final byte[] data;
    private final String mimeType;

    public Attach(String fileName, byte[] data, String mimeType) {
        this.fileName = fileName;
        this.data = data;
        this.mimeType = mimeType;
    }

    
    
    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @return the data
     */
    public byte[] getData() {
        return data;
    }

    /**
     * @return the mimeType
     */
    public String getMimeType() {
        return mimeType;
    }
}
