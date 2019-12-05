package com.example.qurbatask.network
/**
 * Base Class for handling errors/failures/exceptions.
 * Every feature specific failure should extend [FeatureFailure] class.
 */
sealed class Failure {
    data class NetworkConnection(val message: String = "") : Failure()
    data class ServerError(val message: String = "") : Failure()
    data class ValidationError(val message: String = "") : Failure()
    data class NoDataError(val message: String = "No Data returned.") : Failure()

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailur : Failure()
}