package com.wonjung.cono.entity

import com.wonjung.cono.type.OsType
import com.wonjung.cono.type.PayType
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
class Cono(
    name: String,
    addressInfo: AddressInfo,
    operatingTime: String? = null,
    phoneNumber: String? = null,
    payTypes: List<PayType>? = null,
    roomCount: Int? = null,
    os: OsType? = null,
    hasScoreBonus: Boolean? = null,
    canControlSound: Boolean? = null,
    hasMoneyChanger: Boolean? = null
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    val id: Long = 0

    @Column(nullable = false)
    val createdDatetime: LocalDateTime = LocalDateTime.now()
    @Column(nullable = false)
    var updatedDatetime: LocalDateTime = LocalDateTime.now()
        protected set

    @Column(nullable = false, name = "cono_name")
    var name: String = name
        protected set

    @Embedded
    var addressInfo: AddressInfo = addressInfo
        protected set

    @Column
    var phoneNumber: String? = phoneNumber
        protected set
    @Column
    var operatingTime: String? = operatingTime
        protected set
    @Column
    var payTypes: String? = payTypes?.joinToString(",")
        protected set
    @Column
    var roomCount: Int? = roomCount
        protected set
    @Column
    @Enumerated(EnumType.STRING)
    var os: OsType? = os
        protected set
    @Column
    var hasScoreBonus: Boolean? = hasScoreBonus
        protected set
    @Column
    var canControlSound: Boolean? = canControlSound
        protected set
    @Column
    var hasMoneyChanger: Boolean? = hasMoneyChanger
        protected set

    fun getPayTypes(): Set<PayType> {
        val payTypeStrings: MutableSet<String> = mutableSetOf()
        this.payTypes?.split(",")
            ?.let { payTypeStrings.addAll(it) }
        return payTypeStrings.map {
            PayType.valueOf(it)
        }.toSet()
    }

    /**
     * Setters
     */
    fun editName(name: String) {
        this.name = name
    }

    fun editAddressInfo(addressInfo: AddressInfo) {
        this.addressInfo = addressInfo
    }

    fun editOperatingTime(operatingTime: String?) {
        this.operatingTime = operatingTime
    }

    fun editPhoneNumber(phoneNumber: String?) {
        this.phoneNumber = phoneNumber
    }

    fun editPayTypes(payTypes: List<PayType>?) {
        this.payTypes = payTypes?.joinToString(",")
    }

    fun editRoomCount(roomCount: Int?) {
        this.roomCount = roomCount
    }

    fun editOs(os: OsType?) {
        this.os = os
    }

    fun editHasScoreBonus(hasScoreBonus: Boolean?) {
        this.hasScoreBonus = hasScoreBonus
    }

    fun editCanControlSound(canControlSound: Boolean?) {
        this.canControlSound = canControlSound
    }

    fun editHasMoneyChanger(hasMoneyChanger: Boolean?) {
        this.hasMoneyChanger = hasMoneyChanger
    }

}

@Embeddable
data class AddressInfo(
    @Column(nullable = false)
    var address: String,
    @Column(nullable = false)
    var latitude: BigDecimal,
    @Column(nullable = false)
    var longitude: BigDecimal
)