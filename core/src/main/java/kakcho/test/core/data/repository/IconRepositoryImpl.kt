package kakcho.test.core.data.repository

import com.google.gson.JsonParseException
import kakcho.test.core.data.api.IconFinderService
import kakcho.test.core.data.model.network.ErrorResponse
import kakcho.test.core.data.model.network.Failure
import kakcho.test.core.data.model.network.Result
import kakcho.test.core.data.model.response.CategoryResponse
import kakcho.test.core.data.model.response.IconSetsResponse
import kakcho.test.core.data.model.response.IconsResponse
import kakcho.test.core.domain.repository.IconRepository
import kakcho.test.core.utils.Constants

/**
 * Created by Kamal Nayan on 22-09-2021 at 11:04
 */
class IconRepositoryImpl(private val iconFinderService: IconFinderService) :
    IconRepository {

    /**
     * Used to fetch icons of a specific IconSet
     *
     * Returns list of icons if response is
     * successful otherwise error is returned
     * accordingly
     *
     * @param setID String -> IconSet Id
     * @return Result<IconsResponse, Failure>
     */
    override suspend fun getIconsOfSet(setID: String): Result<IconsResponse, Failure> {
        return try {
            val response = iconFinderService.getIcons(setId = setID)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                if (response.code() == 401) {
                    Result.Error(
                        Failure.UnauthorizedError,
                        ErrorResponse(
                            Constants.Error.unauthorizedErrorMessage,
                            Exception(response.raw().toString())
                        )
                    )
                } else {
                    Result.Error(
                        Failure.ServerError,
                        ErrorResponse(
                            Constants.Error.genericErrorMessage,
                            Exception(response.raw().toString())
                        )
                    )
                }
            }
        } catch (e: Exception) {
            return if (e is JsonParseException) {
                Result.Error(
                    Failure.ParsingError,
                    ErrorResponse(Constants.Error.genericErrorMessage, e)
                )
            } else {
                Result.Error(
                    Failure.NetworkConnection,
                    ErrorResponse(Constants.Error.genericErrorMessage, e)
                )
            }
        }
    }


    /**
     * Uses search api to search user's query
     * and returns result according to response
     *
     * Returns list of icons if response is
     * successful otherwise error is returned
     * accordingly
     *
     * @param query String -> query entered by user
     * @return Result<IconsResponse, Failure>
     */
    override suspend fun searchIcon(query: String): Result<IconsResponse, Failure> {
        return try {
            val response = iconFinderService.searchIcon(query = query, count = 20)

            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                if (response.code() == 401) {
                    Result.Error(
                        Failure.UnauthorizedError,
                        ErrorResponse(
                            Constants.Error.unauthorizedErrorMessage,
                            Exception(response.raw().toString())
                        )
                    )
                } else {
                    Result.Error(
                        Failure.ServerError,
                        ErrorResponse(
                            Constants.Error.genericErrorMessage,
                            Exception(response.raw().toString())
                        )
                    )
                }
            }

        } catch (e: Exception) {
            return if (e is JsonParseException) {
                Result.Error(
                    Failure.ParsingError,
                    ErrorResponse(Constants.Error.genericErrorMessage, e)
                )
            } else {
                Result.Error(
                    Failure.NetworkConnection,
                    ErrorResponse(Constants.Error.genericErrorMessage, e)
                )
            }
        }
    }


    /**
     * Used to fetch all categories available on IconFinder
     *
     * @param count Int -> Number of items to retrieve
     * @param after String -> used for pagination, the id of element after which next elements should be fetched
     * @return Response<CategoryResponse>
     */
    override suspend fun getALlCategories(
        count: Int,
        after: String
    ): Result<CategoryResponse, Failure> {
        return try {
            val response = iconFinderService.getAllCategories(count = count, after = after)

            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                if (response.code() == 401) {
                    Result.Error(
                        Failure.UnauthorizedError,
                        ErrorResponse(
                            Constants.Error.unauthorizedErrorMessage,
                            Exception(response.raw().toString())
                        )
                    )
                } else {
                    Result.Error(
                        Failure.ServerError,
                        ErrorResponse(
                            Constants.Error.genericErrorMessage,
                            Exception(response.raw().toString())
                        )
                    )
                }
            }

        } catch (e: Exception) {
            return if (e is JsonParseException) {
                Result.Error(
                    Failure.ParsingError,
                    ErrorResponse(Constants.Error.genericErrorMessage, e)
                )
            } else {
                Result.Error(
                    Failure.NetworkConnection,
                    ErrorResponse(Constants.Error.genericErrorMessage, e)
                )
            }
        }
    }


    /**
     * Used to fetch all icon set of a selected category
     *
     * @param identifier String -> selected category identifier
     * @param count Int -> number of items to fetch
     * @param after String -> used for pagination
     *
     * @return Result<IconSetsResponse, Failure>
     */
    override suspend fun getIconSetOfCategory(
        identifier: String,
        count: Int,
        after: String
    ): Result<IconSetsResponse, Failure> {
        return try {
            val response = iconFinderService.getIconSetsOfCategory(
                identifier = identifier,
                count = count,
                after = after
            )

            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                if (response.code() == 401) {
                    Result.Error(
                        Failure.UnauthorizedError,
                        ErrorResponse(
                            Constants.Error.unauthorizedErrorMessage,
                            Exception(response.raw().toString())
                        )
                    )
                } else {
                    Result.Error(
                        Failure.ServerError,
                        ErrorResponse(
                            Constants.Error.genericErrorMessage,
                            Exception(response.raw().toString())
                        )
                    )
                }
            }

        } catch (e: Exception) {
            return if (e is JsonParseException) {
                Result.Error(
                    Failure.ParsingError,
                    ErrorResponse(Constants.Error.genericErrorMessage, e)
                )
            } else {
                Result.Error(
                    Failure.NetworkConnection,
                    ErrorResponse(Constants.Error.genericErrorMessage, e)
                )
            }
        }
    }
}