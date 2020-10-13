package com.example.mng.vertexdelivery.model

class UserModel {
    var name:String?=null
    var email:String?=null
    var category:String?=null
    var lastName:String?=null
    var user_id:String?=null

    constructor()
    constructor(
        name: String?,
        email: String?,
        category: String?,
        lastName: String?,
        user_id: String?
    ) {
        this.name = name
        this.email = email
        this.category = category
        this.lastName = lastName
        this.user_id = user_id
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserModel

        if (name != other.name) return false
        if (email != other.email) return false
        if (category != other.category) return false
        if (lastName != other.lastName) return false
        if (user_id != other.user_id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name?.hashCode() ?: 0
        result = 31 * result + (email?.hashCode() ?: 0)
        result = 31 * result + (category?.hashCode() ?: 0)
        result = 31 * result + (lastName?.hashCode() ?: 0)
        result = 31 * result + (user_id?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "UserModel(name=$name, email=$email, category=$category, lastName=$lastName, user_id=$user_id)"
    }


}