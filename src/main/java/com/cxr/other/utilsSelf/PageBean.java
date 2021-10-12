package com.cxr.other.utilsSelf;

import java.util.ArrayList;
import java.util.List;

public class PageBean<T> {

    private Integer offset = 0;

    /**
     * 当前页
     */
    private Integer page = 1;

    /**
     * 每页数
     */
    private Integer pageSize = 10;

    /**
     * 总数据量
     */
    private long count = 0;
    private List<T> data = new ArrayList();

    public PageBean() {
    }

    public PageBean(Integer page, Integer offset) {

        if (offset != null && offset <= 100 && offset >= 0) {
            this.pageSize = offset;
        }

        if (page != null && page >= 1) {
            this.offset = (page - 1) * this.pageSize;
            this.page = page;
        }

    }

    public Integer getOffset() {
        return this.offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getData() {
        return this.data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public long getCount() {
        return this.count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public Integer getPage() {
        return this.page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}
