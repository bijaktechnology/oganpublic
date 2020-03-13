package com.incendiary.ambulanceapp.utils.recyclerviews;

public class PageHelper {

    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_ITEM_PER_PAGE = 10;

    private int page;
    private int firstPage;
    private int itemPerPage;
    private boolean onHold;
    private boolean onLastPage;

    private PageHelper() {
    }

    public static PageHelper with(int firstPage) {
        PageHelper pageHelper = new PageHelper();
        pageHelper.setFirstPage(firstPage);
        pageHelper.setPage(firstPage);
        return pageHelper;
    }

    public static PageHelper getDefault() {
        PageHelper pageHelper = new PageHelper().itemPerPage(DEFAULT_ITEM_PER_PAGE);
        pageHelper.setFirstPage(DEFAULT_PAGE);
        pageHelper.setPage(DEFAULT_PAGE);
        pageHelper.hold();
        return pageHelper;
    }

    public PageHelper itemPerPage(int itemPerPage) {
        this.itemPerPage = itemPerPage;
        return this;
    }

    public synchronized void nextPage() {
        page++;
        onHold = false;
    }

    public synchronized void nextPage(int page) {
        this.page = page;
        onHold = false;
    }

    public synchronized void hold() {
        onHold = true;
    }

    public synchronized void reset() {
        page = firstPage;
        onHold = false;
        onLastPage = false;
    }

    public synchronized int getNextPage() {
        return page + 1;
    }

    public int getCurrentPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(int firstPage) {
        this.firstPage = firstPage;
    }

    public boolean isOnHold() {
        return onHold;
    }

    public void setLastPage(boolean isLastPage) {
        this.onLastPage = isLastPage;
    }

    public boolean isOnLastPage() {
        return this.onLastPage;
    }

    public boolean isFirstPage() {
        return firstPage == page;
    }

    public int getOffset() {
        return page * itemPerPage;
    }

    public int getItemPerPage() {
        return itemPerPage;
    }
}
