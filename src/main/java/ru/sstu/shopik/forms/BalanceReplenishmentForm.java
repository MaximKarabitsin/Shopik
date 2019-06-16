package ru.sstu.shopik.forms;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class BalanceReplenishmentForm {
    @NotBlank
    @Pattern(regexp = "[0-9]{1,9}")
    private String replenishment;

    public String getReplenishment() {
        return replenishment;
    }

    public void setReplenishment(String replenishment) {
        this.replenishment = replenishment;
    }
}
