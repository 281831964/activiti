package com.cms.test.springboot.domain;

public class Setting {
    private Integer id;
    private String actionname;
    private String actionclass;
    private String typename;
    private String actionshowname;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getActionname() {
        return actionname;
    }

    public void setActionname(String actionname) {
        this.actionname = actionname;
    }

    public String getActionclass() {
        return actionclass;
    }

    public void setActionclass(String actionclass) {
        this.actionclass = actionclass;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getActionshowname() {
        return actionshowname;
    }

    public void setActionshowname(String actionshowname) {
        this.actionshowname = actionshowname;
    }
}
