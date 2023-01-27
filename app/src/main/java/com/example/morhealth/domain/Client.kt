package com.example.morhealth.domain

class Client {
    var username: String? = null
    var name: String? = null
    var lastname_p: String? = null
    var lastname_m: String? = null
    var gender: Char? = null
    var age: Int? = null
    var email: String? = null
    var pswd: String? = null
    var clientImage: String? = null

    constructor() {}
    constructor(username: String?, pswd: String?) {
        this.username = username
        this.pswd = pswd
    }

    constructor(
        username: String?,
        name: String?,
        lastname_p: String?,
        lastname_m: String?,
        gender: Char?,
        age: Int?,
        email: String?,
        pswd: String?,
        clientImage: String?
    ) {
        this.username = username
        this.name = name
        this.lastname_p = lastname_p
        this.lastname_m = lastname_m
        this.gender = gender
        this.age = age
        this.email = email
        this.pswd = pswd
        this.clientImage = clientImage
    }

    override fun toString(): String {
        return "Client{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", lastname_p='" + lastname_p + '\'' +
                ", lastname_m='" + lastname_m + '\'' +
                ", gender=" + gender +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", pswd='" + pswd + '\'' +
                ", clientImage='" + clientImage + '\'' +
                '}'
    }
}