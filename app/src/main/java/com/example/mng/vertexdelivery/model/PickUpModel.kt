package com.example.mng.vertexdelivery.model

class PickUpModel {
    var task_id: String? = null
    var name: String? = null
    var phone: String? = null
    var address: String? = null
    var time: String? = null
    var status: String? = null
    var image: String? = null
    var description: String? = null
    var date: String? = null
    var dateOperation: String? = null

    constructor()
    constructor(
        task_id: String?,
        name: String?,
        phone: String?,
        address: String?,
        time: String?,
        status: String?,
        image: String?,
        description: String?,
        date: String?,
        dateOperation: String?
    ) {
        this.task_id = task_id
        this.name = name
        this.phone = phone
        this.address = address
        this.time = time
        this.status = status
        this.image = image
        this.description = description
        this.date = date
        this.dateOperation = dateOperation
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PickUpModel

        if (task_id != other.task_id) return false
        if (name != other.name) return false
        if (phone != other.phone) return false
        if (address != other.address) return false
        if (time != other.time) return false
        if (status != other.status) return false
        if (image != other.image) return false
        if (description != other.description) return false
        if (date != other.date) return false
        if (dateOperation != other.dateOperation) return false

        return true
    }

    override fun hashCode(): Int {
        var result = task_id?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (phone?.hashCode() ?: 0)
        result = 31 * result + (address?.hashCode() ?: 0)
        result = 31 * result + (time?.hashCode() ?: 0)
        result = 31 * result + (status?.hashCode() ?: 0)
        result = 31 * result + (image?.hashCode() ?: 0)
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + (date?.hashCode() ?: 0)
        result = 31 * result + (dateOperation?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "PickUpModel(task_id=$task_id, name=$name, phone=$phone, address=$address, time=$time, status=$status, image=$image, description=$description, date=$date, dateOperation=$dateOperation)"
    }




}