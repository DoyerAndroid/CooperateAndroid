package com.compain.libraryshare.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by jie.du on 17/1/9.
 */

public class BookBean extends BmobObject {
    private String book_Name;
    private String book_author;
    private String book_introduce;
    private String book_cover;
    private String book_type;
    private String book_for_age;

    public String getBook_Name() {
        return book_Name;
    }

    public void setBook_Name(String book_Name) {
        this.book_Name = book_Name;
    }

    public String getBook_author() {
        return book_author;
    }

    public void setBook_author(String book_author) {
        this.book_author = book_author;
    }

    public String getBook_introduce() {
        return book_introduce;
    }

    public void setBook_introduce(String book_introduce) {
        this.book_introduce = book_introduce;
    }

    public String getBook_cover() {
        return book_cover;
    }

    public void setBook_cover(String book_cover) {
        this.book_cover = book_cover;
    }

    public String getBook_type() {
        return book_type;
    }

    public void setBook_type(String book_type) {
        this.book_type = book_type;
    }

    public String getBook_for_age() {
        return book_for_age;
    }

    public void setBook_for_age(String book_for_age) {
        this.book_for_age = book_for_age;
    }
}
