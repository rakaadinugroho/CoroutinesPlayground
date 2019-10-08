package com.rakaadinugroho.coroutinesplayground.data


import com.google.gson.annotations.SerializedName

data class DocumentResponse(
    @SerializedName("response")
    var response: Response
) {
    data class Response(
        @SerializedName("docs")
        var docs: List<Doc>,
        @SerializedName("maxScore")
        var maxScore: Double,
        @SerializedName("numFound")
        var numFound: Int,
        @SerializedName("start")
        var start: Int
    ) {
        data class Doc(
            @SerializedName("abstract")
            var `abstract`: List<String>,
            @SerializedName("article_type")
            var articleType: String,
            @SerializedName("author_display")
            var authorDisplay: List<String>,
            @SerializedName("eissn")
            var eissn: String,
            @SerializedName("id")
            var id: String,
            @SerializedName("journal")
            var journal: String,
            @SerializedName("publication_date")
            var publicationDate: String,
            @SerializedName("score")
            var score: Double,
            @SerializedName("title_display")
            var titleDisplay: String
        )
    }
}