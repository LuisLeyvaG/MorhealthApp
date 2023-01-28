package com.example.morhealth.domain

class Client(
    var username: String? = null,
    var name: String? = null,
    var lastname_p: String? = null,
    var lastname_m: String? = null,
    var gender: Boolean? = null,
    var age: Int? = null,
    var email: String? = null,
    var pswd: String? = null,
    var clientImage: String? = null,
    var description: String? = null
) {
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

