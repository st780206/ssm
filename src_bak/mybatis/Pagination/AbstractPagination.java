package mybatis.Pagination;

import org.apache.ibatis.session.RowBounds;

public abstract class AbstractPagination extends RowBounds implements Pagination {

	private boolean totol = true;
	private boolean offset;
	private boolean limit;
	private int count;
	private int startRow;
	private int endRow;

	public void setTotol(boolean totol) {
		this.totol = totol;
	}

	@Override
	public boolean isTotol() {
		return totol;
	}

	public void setOffset(boolean offset) {
		this.offset = offset;
	}
	
	@Override
	public boolean isOffset() {
		return offset;
	}

	@Override
	public boolean isLimit() {
		return limit;
	}
	
	public void setLimit(boolean limit) {
		this.limit = limit;
	}

	@Override
	public void setCount(int count) {
		if (count < 0) {
			this.count = 0;
		} else {
			this.count = count;
		}
	}

	@Override
	public int getCount() {
		return count;
	}

	public void setStartRow(int startRow) {
		if (startRow < 0) {
			setOffset(false);
			this.startRow = 0;
		} else {
			this.startRow = startRow;
			setOffset(true);
		}
	}

	@Override
	public int getStartRow() {
		return startRow;
	}

	public void setEndRow(int endRow) {
		if (endRow < 0) {
			setLimit(false);
			this.endRow = 0;
		} else {
			this.endRow = endRow;
			setLimit(true);
		}
	}

	@Override
	public int getEndRow() {
		return endRow;
	}

}
