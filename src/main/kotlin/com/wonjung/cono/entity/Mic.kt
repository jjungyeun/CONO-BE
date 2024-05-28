package com.wonjung.cono.entity

import com.wonjung.cono.type.MicType
import jakarta.persistence.*

@Entity
class Mic(
    cono: Cono,
    micType: MicType
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    val id: Long = 0

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "cono_id")
    val cono: Cono = cono
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val micType: MicType = micType
}