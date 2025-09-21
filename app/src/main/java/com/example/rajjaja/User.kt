package com.example.rajjaja

data class User(
    var name: String,
    var lastMessage: String,
    var imageId: Int,
    var sePrec2: String, // Campo que armazena a STRING do preço (ex: "20.00")
    var phoneNo: String, // Exemplo de outro campo / "sePreciso"
    var sePrec: String   // Exemplo de outro campo / "SePrecisar" da UserActivity
)