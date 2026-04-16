package sql_lite_engine_java.storage;

public class Page {
    public static final int PAGE_SIZE = 4096;
    private int pageNumber;
    private byte[] data;

    public Page(int pageNumber, byte[] data) {
        this.pageNumber = pageNumber;
        this.data = data;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public byte[] getData() {
        return data;
    }
}
