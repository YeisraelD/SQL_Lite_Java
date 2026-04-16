package sql_lite_engine_java.storage;

import java.io.IOException;

public class TableManager {
    private Table table;
    private Pager pager;
    private int rowsPerPage;

    public TableManager(Table table, String dbFile) throws IOException {
        this.table = table;
        this.pager = new Pager(dbFile);
        // Simplified row size calculation
        this.rowsPerPage = Page.PAGE_SIZE / 256; 
    }

    public void insert(Row row) throws IOException {
        // In memory addition
        table.insertRow(row);
        
        // Persistence logic (simplified)
        int rowNum = table.getRows().size();
        int pageNum = rowNum / rowsPerPage;
        
        Page page = pager.getPage(pageNum);
        if(page == null) {
            page = new Page(pageNum, new byte[Page.PAGE_SIZE]);
        }
        
        // Simplified: serializing row into page's byte array would go here
        
        // Write the updated page back to disk
        pager.writePage(page);
    }
    
    public Table getTable() {
        return table;
    }
    
    public void close() throws IOException {
        pager.close();
    }
}
