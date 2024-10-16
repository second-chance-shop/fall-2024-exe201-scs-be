package scs.exe201.secondchanceshopbe.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import scs.exe201.secondchanceshopbe.models.dtos.enums.StatusEnum;

public class VoucherEntity {
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusEnum status ;
}
