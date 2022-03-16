package com.example.navesstarwars.listas


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Parcelize
data class ListaPersonajes(
    val count: Long,
    val next: String,
    //val previous: JsonObject? = null,
    val results: List<Personajes>
) : Parcelable

@Parcelize
data class Personajes(
    val name: String,
    val height: String,
    val mass: String,

    @SerialName("hair_color")
    val hairColor: String,

    @SerialName("skin_color")
    val skinColor: String,

    @SerialName("eye_color")
    val eyeColor: String,

    @SerialName("birth_year")
    val birthYear: String,

    val gender: Gender,
    val homeworld: String,
    val films: List<String>,
    val species: List<String>,
    val vehicles: List<String>,
    val starships: List<String>,
    val created: String,
    val edited: String,
    val url: String
) : Parcelable

@Parcelize
enum class Gender(val value: String) : Parcelable {
    Female("female"),
    Male("male"),
    NA("n/a");

    companion object : KSerializer<Gender> {
        override val descriptor: SerialDescriptor
            get() {
                return PrimitiveSerialDescriptor(
                    "com.examen.pmdm.model.Gender",
                    PrimitiveKind.STRING
                )
            }

        override fun deserialize(decoder: Decoder): Gender =
            when (val value = decoder.decodeString()) {
                "female" -> Female
                "male" -> Male
                "n/a" -> NA
                else -> throw IllegalArgumentException("Gender could not parse: $value")
            }

        override fun serialize(encoder: Encoder, value: Gender) {
            return encoder.encodeString(value.value)
        }
    }
}