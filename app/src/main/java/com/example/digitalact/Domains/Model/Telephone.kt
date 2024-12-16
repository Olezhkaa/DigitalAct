package com.example.digitalact.Domains.Model

data class Telephone(
    val numberTelephone: String,
    val iccId: String
) {
    constructor() : this("", "")
}