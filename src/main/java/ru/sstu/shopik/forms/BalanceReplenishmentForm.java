package ru.sstu.shopik.forms;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class BalanceReplenishmentForm {
    @NotNull
    @Min(1)
    private int replenishment;

    public int getReplenishment() {
        return replenishment;
    }

    public void setReplenishment(int replenishment) {
        this.replenishment = replenishment;
    }
}
