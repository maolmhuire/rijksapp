package com.maolmhuire.rijksapp.model

import com.squareup.moshi.Json

/**
 * Responses:
 */
data class CollectionResponse(
    val artObjects: List<ArtObject>,
    val count: Int,
    val countFacets: CountFacets,
    val elapsedMilliseconds: Int,
    val facets: List<Facet>?
)

data class ArtObject(
    val hasImage: Boolean,
    val headerImage: HeaderImage,
    val id: String,
    val links: Links,
    val longTitle: String,
    val objectNumber: String,
    val permitDownload: Boolean,
    val principalOrFirstMaker: String,
    val productionPlaces: List<String>,
    val showImage: Boolean,
    val title: String,
    val webImage: WebImage
)

data class CountFacets(
    val hasImage: Int?,
    val onDisplay: Int?
)

data class Facet(
    val facets: List<FacetX>,
    val name: String,
    val otherTerms: Int,
    val prettyName: Int
)

data class HeaderImage(
    val guid: String,
    val height: Int,
    val offsetPercentageX: Int,
    val offsetPercentageY: Int,
    val url: String,
    val width: Int
)

data class Links(
    val self: String?,
    val web: String?,
    val search: String?
)

data class WebImage(
    val guid: String,
    val height: Int,
    val offsetPercentageX: Int,
    val offsetPercentageY: Int,
    val url: String,
    val width: Int
)

data class FacetX(
    val key: String,
    val value: Int
)


data class ArtworkDetailResponse(
    val artObject: ArtObjectDetailed,
    val artObjectPage: ArtObjectPage,
    val elapsedMilliseconds: Int
)

data class ArtObjectDetailed(
    val acquisition: Acquisition,
    val artistRole: Any,
    val associations: List<Any>,
    val catRefRPK: List<Any>,
    val copyrightHolder: Any,
    val dating: Dating,
    val description: String,
    val dimensions: List<Dimension>,
    val documentation: List<String>,
    val exhibitions: List<Any>,
    val hasImage: Boolean,
    val historicalPersons: List<String>,
    val id: String,
    val inscriptions: List<Any>,
    val label: Label,
    val labelText: Any,
    val language: String,
    val links: Links,
    val location: String,
    val longTitle: String,
    val makers: List<Any>,
    val materials: List<String>,
    val objectCollection: List<String>,
    val objectNumber: String,
    val objectTypes: List<String>,
    val physicalMedium: String,
    val physicalProperties: List<Any>,
    val plaqueDescriptionDutch: String,
    val plaqueDescriptionEnglish: String,
    val principalMaker: String,
    val principalMakers: List<PrincipalMaker>,
    val principalOrFirstMaker: String,
    val priref: String,
    val productionPlaces: List<String>,
    val scLabelLine: String,
    val showImage: Boolean,
    val subTitle: String,
    val techniques: List<Any>,
    val title: String,
    val titles: List<String>,
    val webImage: WebImage
)

data class ArtObjectPage(
    val audioFile1: Any?,
    val audioFileLabel1: Any?,
    val audioFileLabel2: Any?,
    val createdOn: String?,
    val id: String?,
    val lang: String?,
    val objectNumber: String?,
    val plaqueDescription: String?,
    val similarPages: List<Any>?,
    val tags: List<Any>?,
    val updatedOn: String?
)

data class Acquisition(
    val creditLine: String?,
    val date: String?,
    val method: String?
)

data class Dating(
    val period: Int?,
    val presentingDate: String?,
    val sortingDate: Int?,
    val yearEarly: Int?,
    val yearLate: Int?
)

data class Dimension(
    val part: Any?,
    val precision: Any?,
    val type: String?,
    val unit: String?,
    val value: String?
)

data class Label(
    val date: String?,
    val description: String?,
    val makerLine: String?,
    val notes: Any,
    val title: String?
)

data class PrincipalMaker(
    val biography: String?,
    val dateOfBirth: String?,
    val dateOfBirthPrecision: Any,
    val dateOfDeath: String?,
    val dateOfDeathPrecision: Any,
    val labelDesc: String?,
    val name: String?,
    val nationality: String?,
    val occupation: List<String?>?,
    val placeOfBirth: String?,
    val placeOfDeath: String?,
    val productionPlaces: List<String?>?,
    val qualification: Any,
    val roles: List<String?>?,
    val unFixedName: String?
)