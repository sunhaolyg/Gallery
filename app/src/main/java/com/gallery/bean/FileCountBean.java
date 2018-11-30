package com.gallery.bean;

public class FileCountBean {

    private String path;
    private String name;
    private int count;

    public FileCountBean(String path, String name, int count) {
        this.path = path;
        this.name = name;
        this.count = count;
    }

    @Override
    public String toString() {
        return "FileCountBean{" +
                "path='" + path + '\'' +
                ", name='" + name + '\'' +
                ", count='" + count + '\'' +
                '}';
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
