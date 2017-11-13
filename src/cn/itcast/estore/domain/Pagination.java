package cn.itcast.estore.domain;

import java.util.List;

public class Pagination<T> {
	// 封装页面显示数据
	private List<T> data;// 来自数据库查询 select * from product limit ?,?
	private int pageNum = 1;// 请求分页页码 (计算出 参数1)
	private int pageSize = 8; // 请求分页显示每页记录数 参数2
	private long totalCounts;// 数据库查询 select count(*) from product
	private int totalPages;// 计算出来 总记录数 结合 每页显示记录数计算出来
	private int startIndex;// 起始记录数 (pageNum-1)*pageSize
	private int[] pageBar;// 当前页码pageNum 7和 总页码totalPages 计算出来 [2--11] [3---12]
	private int beforePage;// 上一页 计算

	public int getBeforePage() {
		this.beforePage = this.pageNum - 1;
		if (this.beforePage <= 0) {
			this.beforePage = 1;
		}
		return this.beforePage;
	}

	public int getNextPage() {
		this.nextPage = this.pageNum + 1;
		if (this.nextPage >= this.totalPages) {
			this.nextPage = this.totalPages;
		}
		return this.nextPage;
	}

	private int nextPage;// 下一页

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotalCounts() {
		return totalCounts;
	}

	public void setTotalCounts(long totalCounts) {
		this.totalCounts = totalCounts;
	}

	public int getTotalPages() {
		// 总页码 = 总记录数和 每页显示记录数 计算
		// 100 10 -->10 99 10 ---> 101
		this.totalPages = (int) (this.totalCounts + this.pageSize - 1) / this.pageSize;
		return this.totalPages;
	}

	public int getStartIndex() {
		// 起始记录数
		this.startIndex = (this.pageNum - 1) * this.pageSize;
		return this.startIndex;
	}

	// 分页栏 7 [2-11]
	public int[] getPageBar() {
		// 根据当前页面 pageNum 7---> 首位数字2 和 尾巴数字 11
		int beginPage;
		int endPage;
		if (this.totalPages <= 10) {
			beginPage = 1;
			endPage = this.totalPages;
		} else {
			// 总页码大于10 存在百度前五后四原则 总页码 13 页 当前页面 10 页 当前页码 3 页
			beginPage = this.pageNum - 5;
			endPage = this.pageNum + 4;

			if (endPage >= this.totalPages) {
				endPage = this.totalPages;
				beginPage = endPage - 9;
			}

			if (beginPage <= 0) {
				beginPage = 1;
				endPage = beginPage + 9;
			}

		}
		// 计算出 首和尾 2 11
		this.pageBar = new int[endPage - beginPage + 1];
		int index = 0;
		for (int i = beginPage; i <= endPage; i++) {
			pageBar[index++] = i;
		}

		return this.pageBar;
	}

}
