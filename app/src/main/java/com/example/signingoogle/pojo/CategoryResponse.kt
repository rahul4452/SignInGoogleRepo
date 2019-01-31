package com.example.signingoogle.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class CategoryResponse {

    @SerializedName("trivia_categories")
    @Expose
    var triviaCategories: List<TriviaCategory>? = null

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    /**
     *
     * @param triviaCategories
     */
    constructor(triviaCategories: List<TriviaCategory>) : super() {
        this.triviaCategories = triviaCategories
    }

    inner class TriviaCategory {

        @SerializedName("id")
        @Expose
        var id: Int? = null
        @SerializedName("name")
        @Expose
        var name: String? = null

        /**
         * No args constructor for use in serialization
         *
         */
        constructor() {}

        /**
         *
         * @param id
         * @param name
         */
        constructor(id: Int?, name: String) : super() {
            this.id = id
            this.name = name
        }

    }

}