package com.example.mytestapplication.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

@Entity(tableName = "MydataTable")
data class DataModel (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rowId")
    var rowId: Int = 0,

    @ColumnInfo(name = "results")
    @SerializedName("results")
    var results: List<Result>? = null,

    @ColumnInfo(name = "info")
    @SerializedName("info")
    var info: Info? = null
    )

data class Registered (

    @ColumnInfo(name = "date")
    @SerializedName("date")
    var date: String? = null,

    @ColumnInfo(name = "age")
    @SerializedName("age")
    var age: Int? = null
    )

data class Result (

    @ColumnInfo(name = "gender")
    @SerializedName("gender")
    var gender: String? = null,

    @ColumnInfo(name = "name")
    @SerializedName("name")
    var name: Name? = null,

    @ColumnInfo(name = "location")
    @SerializedName("location")
    var location: Location? = null,

    @ColumnInfo(name = "email")
    @SerializedName("email")
    var email: String? = null,

    @ColumnInfo(name = "login")
    @SerializedName("login")
    var login: Login? = null,

    @ColumnInfo(name = "dob")
    @SerializedName("dob")
    var dob: Dob? = null,

    @ColumnInfo(name = "registered")
    @SerializedName("registered")
    var registered: Registered? = null,

    @ColumnInfo(name = "phone")
    @SerializedName("phone")
    var phone: String? = null,

    @ColumnInfo(name = "cell")
    @SerializedName("cell")
    var cell: String? = null,

    @ColumnInfo(name = "id")
    @SerializedName("id")
    var id: Id? = null,

    @ColumnInfo(name = "picture")
    @SerializedName("picture")
    var picture: Pictures? = null,

    @ColumnInfo(name = "nat")
    @SerializedName("nat")
    var nat: String? = null
    )

data class Timezone (
    @ColumnInfo(name = "offset")
    @SerializedName("offset")
    var offset: String? = null,

    @ColumnInfo(name = "description")
    @SerializedName("description")
    var description: String? = null
    )

data class Street (
    @ColumnInfo(name = "number")
    @SerializedName("number")
    var number: Int? = null,

    @ColumnInfo(name = "name")
    @SerializedName("name")
    var name: String? = null
)

data class Pictures (
    @ColumnInfo(name = "large")
    @SerializedName("large")
    var large: String? = null,

    @ColumnInfo(name = "medium")
    @SerializedName("medium")
    var medium: String? = null,

    @ColumnInfo(name = "thumbnail")
    @SerializedName("thumbnail")
    var thumbnail: String? = null
    )

data class Name (
    @ColumnInfo(name = "title")
    @SerializedName("title")
    var title: String? = null,

    @ColumnInfo(name = "first")
    @SerializedName("first")
    var first: String? = null,

    @ColumnInfo(name = "last")
    @SerializedName("last")
    var last: String? = null
)

data class Login (
    @ColumnInfo(name = "uuid")
    @SerializedName("uuid")
    var uuid: String? = null,

    @ColumnInfo(name = "username")
    @SerializedName("username")
    var username: String? = null,

    @ColumnInfo(name = "password")
    @SerializedName("password")
    var password: String? = null,

    @ColumnInfo(name = "salt")
    @SerializedName("salt")
    var salt: String? = null,

    @ColumnInfo(name = "md5")
    @SerializedName("md5")
    var md5: String? = null,

    @ColumnInfo(name = "sha1")
    @SerializedName("sha1")
    var sha1: String? = null,

    @ColumnInfo(name = "sha256")
    @SerializedName("sha256")
    var sha256: String? = null
    )

data class Location (
    @ColumnInfo(name = "street")
    @SerializedName("street")
    var street: Street? = null,

    @ColumnInfo(name = "city")
    @SerializedName("city")
    var city: String? = null,

    @ColumnInfo(name = "state")
    @SerializedName("state")
    var state: String? = null,

    @ColumnInfo(name = "country")
    @SerializedName("country")
    var country: String? = null,

    @ColumnInfo(name = "postcode")
    @SerializedName("postcode")
    var postcode: String? = null,

    @ColumnInfo(name = "coordinates")
    @SerializedName("coordinates")
    var coordinates: Coordinates? = null,

    @ColumnInfo(name = "timezone")
    @SerializedName("timezone")
    var timezone: Timezone? = null
    )

data class Info (
    @ColumnInfo(name = "seed")
    @SerializedName("seed")
    var seed: String? = null,

    @ColumnInfo(name = "results")
    @SerializedName("results")
    var results: Int? = null,

    @ColumnInfo(name = "page")
    @SerializedName("page")
    var page: Int? = null,

    @ColumnInfo(name = "version")
    @SerializedName("version")
    var version: String? = null
)

data class Id (
    @ColumnInfo(name = "name")
    @SerializedName("name")
    var name: String? = null,

    @ColumnInfo(name = "value")
    @SerializedName("value")
    var value: String? = null
)

data class Dob (
    @ColumnInfo(name = "date")
    @SerializedName("date")
    var date: String? = null,

    @ColumnInfo(name = "age")
    @SerializedName("age")
    var age: Int? = null
)

data class Coordinates (
    @SerializedName("latitude")
    @ColumnInfo(name = "latitude")
    var latitude: String? = null,

    @ColumnInfo(name = "longitude")
    @SerializedName("longitude")
    var longitude: String? = null
    )


class PicturesConvertor {
    @TypeConverter
    fun listToJson(value: Pictures?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) =
        Gson().fromJson(value, Pictures::class.java)
}

class ResultConvertor {
    @TypeConverter
    fun listToJson(value: Result?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) =
        Gson().fromJson(value, Result::class.java)
}

class InfoConvertor {
    @TypeConverter
    fun listToJson(value: Info?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) =
        Gson().fromJson(value, Info::class.java)
}

class NameConvertor {
    @TypeConverter
    fun listToJson(value: Name?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) =
        Gson().fromJson(value, Name::class.java)
}

class RegisteredConvertor {
    @TypeConverter
    fun listToJson(value: Registered?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) =
        Gson().fromJson(value, Registered::class.java)
}

class CoordinatesConvertor {
    @TypeConverter
    fun listToJson(value: Coordinates?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) =
        Gson().fromJson(value, Coordinates::class.java)
}

class DobConvertor {
    @TypeConverter
    fun listToJson(value: Dob?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) =
        Gson().fromJson(value, Dob::class.java)
}

class IdConvertor {
    @TypeConverter
    fun listToJson(value: Id?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) =
        Gson().fromJson(value, Id::class.java)
}

class LocationConvertor {
    @TypeConverter
    fun listToJson(value: Location?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) =
        Gson().fromJson(value, Location::class.java)
}

class LoginConvertor {
    @TypeConverter
    fun listToJson(value: Login?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) =
        Gson().fromJson(value, Login::class.java)
}

class TimezoneConvertor {
    @TypeConverter
    fun listToJson(value: Timezone?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) =
        Gson().fromJson(value, Timezone::class.java)
}

class ListConverters {

    @TypeConverter
    fun listToJson(value: List<Result>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<Result>::class.java).toList()
}












