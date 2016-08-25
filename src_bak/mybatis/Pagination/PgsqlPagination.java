package mybatis.Pagination;

public class PgsqlPagination extends AbstractPagination {

	@Override
	public String getCountSql(String sql) {
		return "SELECT COUNT(*) AS count FROM (" + sql +") AS TEMPTABEL";
	}

	@Override
	public String getPaginationSql(String sql) {
		
		StringBuffer ol = new StringBuffer(sql);
		if (isLimit()) {
			ol.append(" LIMIT ");
			ol.append(getEndRow() - getStartRow() + 1 );
			ol.append(" ");
		}
		if (isOffset()) {
			ol.append(" OFFSET ");
			ol.append(getStartRow());
			ol.append(" ");
		}
		
		return ol.toString();
	}

}
