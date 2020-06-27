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


}