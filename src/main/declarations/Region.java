package main.declarations;

public enum Region {
    NA("ncsa"),
    EU("emea"),
    ASIA("apac");

    private String name;

    Region(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
