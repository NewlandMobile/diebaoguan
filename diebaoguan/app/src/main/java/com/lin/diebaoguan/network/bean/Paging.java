package com.lin.diebaoguan.network.bean;

/**
 * Created by Alan on 2017/1/1 0001.
 */

public class Paging {
//    paging	分页返回值
//    total	整型	多少篇文章
//    pages	整型	总共有多少页
//    currentPage	整型	当前第几页
//    "paging":{"currentPage":1,"pages":514,"total":1028}

    int total;
    int pages;
    int currentPage;

    public int getTotal() {
        return total;
    }
//
//    public void setTotal(int total) {
//        this.total = total;
//    }
//
    public int getPages() {
        return pages;
    }
//
//    public void setPages(int pages) {
//        this.pages = pages;
//    }
//
    public int getCurrentPage() {
        return currentPage;
    }
//
//    public void setCurrentPage(int currentPage) {
//        this.currentPage = currentPage;
//    }
}
