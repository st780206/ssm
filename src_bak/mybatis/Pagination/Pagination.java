package mybatis.Pagination;

public interface Pagination {

	boolean isTotol();
	
	boolean isOffset();
	
	boolean isLimit();
	
	void setCount(int count);
	int getCount();
	
	//void setStartRow(int startRow);
	int getStartRow();
	
	//void setEndRow(int endRow);
	int getEndRow();
	
	String getCountSql(String sql);
	
	String getPaginationSql(String sql);
}
