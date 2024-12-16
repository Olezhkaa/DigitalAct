package com.example.digitalact.Domains.Model


data class Task(
    val accountNumber: String,
    val address: String,
    val fullName: String,
    val typePU: String,
    val numberPU: String,
    val reasonReplacement: String,
    val numberApplication: String,
    val telephone: String,
    val comment: String
) {
    constructor() : this("", "", "", "", "", "", "", "","")
}