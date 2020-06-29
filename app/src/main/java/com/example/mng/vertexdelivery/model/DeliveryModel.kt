package com.example.mng.vertexdelivery.model

class DeliveryModel {

    var package_id:String?= null
    var invoiceNumber: String?= null
    var shipper: String? = null
    var phone: String? = null
    var address: String? = null
    var endClientName: String? = null
    var status: String? = null
    var image: String? = null
    var description: String? = null
    var date: String? = null
    var dateOperation: String? = null

    constructor()
    constructor(
        package_id: String?,
        invoiceNummber: String?,
        shipper: String?,
        phone: String?,
        address: String?,
        endClientName: String?,
        status: String?,
        image: String?,
        description: String?,
        date: String?,
        dateOperation: String?
    ) {
        this.package_id = package_id
        this.invoiceNumber = invoiceNummber
        this.shipper = shipper
        this.phone = phone
        this.address = address
        this.endClientName = endClientName
        this.status = status
        this.image = image
        this.description = description
        this.date = date
        this.dateOperation = dateOperation
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DeliveryModel

        if (package_id != other.package_id) return false
        if (invoiceNumber != other.invoiceNumber) return false
        if (shipper != other.shipper) return false
        if (phone != other.phone) return false
        if (address != other.address) return false
        if (endClientName != other.endClientName) return false
        if (status != other.status) return false
        if (image != other.image) return false
        if (description != other.description) return false
        if (date != other.date) return false
        if (dateOperation != other.dateOperation) return false

        return true
    }

    override fun hashCode(): Int {
        var result = package_id?.hashCode() ?: 0
        result = 31 * result + (invoiceNumber?.hashCode() ?: 0)
        result = 31 * result + (shipper?.hashCode() ?: 0)
        result = 31 * result + (phone?.hashCode() ?: 0)
        result = 31 * result + (address?.hashCode() ?: 0)
        result = 31 * result + (endClientName?.hashCode() ?: 0)
        result = 31 * result + (status?.hashCode() ?: 0)
        result = 31 * result + (image?.hashCode() ?: 0)
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + (date?.hashCode() ?: 0)
        result = 31 * result + (dateOperation?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "DeliveryModel(package_id=$package_id, invoiceNummber=$invoiceNumber, shipper=$shipper, phone=$phone, address=$address, endClientName=$endClientName, status=$status, image=$image, description=$description, date=$date, dateOperation=$dateOperation)"
    }


}