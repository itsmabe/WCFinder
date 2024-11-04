package com.mbcoding.wcfinder.utils

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.android.gms.maps.model.LatLng
import com.mbcoding.wcfinder.domain.model.WC
import com.mbcoding.wcfinder.domain.usecase.GetWCDataUseCase
import javax.inject.Inject

class WCPagingSource @Inject constructor(
    private val useCase: GetWCDataUseCase,
    private val location: LatLng?
) : PagingSource<Int, WC>() {

    override fun getRefreshKey(state: PagingState<Int, WC>) =
        state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, WC> {
        val start = params.key ?: 0
        return try {
            val response = useCase.execute(location, start, params.loadSize)
            LoadResult.Page(
                data = response,
                prevKey = if (start == 0) null else start - params.loadSize,
                nextKey = if (response.size < params.loadSize) null else start + params.loadSize
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}