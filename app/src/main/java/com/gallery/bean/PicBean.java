package com.gallery.bean;

public class PicBean {

    private String path;
    private boolean checked;

    public PicBean() {
    }


    public PicBean(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return "PicBean{" +
                "path='" + path + '\'' +
                ", checked=" + checked +
                '}';
    }
}
