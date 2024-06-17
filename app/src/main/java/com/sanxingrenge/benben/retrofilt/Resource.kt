package com.sanxingrenge.benben.retrofilt

sealed class Resource<T>(val data: T?, val message: String?) {

    class Error<T>(message: String?) : Resource<T>(null, message)
    class Success<T>(data: T?, message: String?) : Resource<T>(data, message)

}