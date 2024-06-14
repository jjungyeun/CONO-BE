package com.wonjung.cono.entity

import com.wonjung.cono.type.FeeUnit
import jakarta.persistence.*

@Entity
class Fee(
    cono: Cono,
    price: Int,
    feeValue: Int,
    unit: FeeUnit
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    val id: Long = 0

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "cono_id")
    val cono: Cono = cono
    @Column(nullable = false)
    val price: Int = price
    @Column(nullable = false)
    val feeValue: Int = feeValue
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val unit: FeeUnit = unit


    /**
     * methods
     */

    fun feeToString(): String {
        return StringBuilder()
            .append(price)
            .append("원에 ")
            .append(feeValue)
            .append(unit.value)
            .toString()
    }
}