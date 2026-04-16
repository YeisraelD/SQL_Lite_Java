package sql_lite_engine_java.storage;

import java.io.File;
import java.io.RandomAccessFile;
import java.io.IOException;

public class Pager {
    private RandomAccessFile file;
    private long fileLength;
    private int numPages;

    public Pager(String filename) throws IOException {
        File f = new File(filename);
        file = new RandomAccessFile(f, "rw");
        fileLength = file.length();
        numPages = (int) (fileLength / Page.PAGE_SIZE);
        if (fileLength % Page.PAGE_SIZE != 0) {
            throw new IOException("Db file is not an integer number of pages. Corrupt file.");
        }
    }

    public Page getPage(int pageNumber) throws IOException {
        if (pageNumber > numPages) {
            return null; // out of bounds
        }
        file.seek((long) pageNumber * Page.PAGE_SIZE);
        byte[] data = new byte[Page.PAGE_SIZE];
        file.read(data);
        return new Page(pageNumber, data);
    }

    public void writePage(Page page) throws IOException {
        file.seek((long) page.getPageNumber() * Page.PAGE_SIZE);
        file.write(page.getData());
    }

    public void close() throws IOException {
        file.close();
    }
}
