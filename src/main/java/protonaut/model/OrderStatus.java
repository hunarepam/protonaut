package protonaut.model;

public enum OrderStatus {
    PLACED("placed"),
    SUBMITTED("submitted"),
    PAID("paid"),
    DELIVERED("delivered"),
    ARCHIVED("archived");


    private String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
