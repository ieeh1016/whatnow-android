package com.depromeet.whatnow.data.model

import com.depromeet.whatnow.data.entity.LocationEntity
import com.google.gson.annotations.SerializedName

data class LocationRequest(
    @SerializedName("location")
    val location : String
)

fun LocationEntity.toRemote() : LocationRequest = LocationRequest(
    location = location
)