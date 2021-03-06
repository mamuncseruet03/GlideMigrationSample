package com.ronnnnn.glidemigrationsample.services.sources

import com.ronnnnn.glidemigrationsample.models.response.RecentPhotosResponse
import com.ronnnnn.glidemigrationsample.services.FlickrService
import io.reactivex.Single

/**
 * Created by kokushiseiya on 2017/06/18.
 */
class FlickerSource(private val flickrService: FlickrService) {

    fun getRecentPhotos(): Single<RecentPhotosResponse> =
            flickrService.getRecentPhotos("flickr.photos.getRecent", "json", "b752e0023264f464d7be7a903d846a97", 1)
}