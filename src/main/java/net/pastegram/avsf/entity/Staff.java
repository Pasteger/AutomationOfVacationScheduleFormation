package net.pastegram.avsf.entity;

public class Staff {
    private Long id;
    private String fullName;
    private Integer vacation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getVacation() {
        return vacation;
    }

    public void setVacation(Integer vacation) {
        this.vacation = vacation;
    }
}
