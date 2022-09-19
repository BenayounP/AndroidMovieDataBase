package eu.benayoun.androidmoviedatabase.data.source.network

import eu.benayoun.androidmoviedatabase.data.model.TmdbMovie
import eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIResponse
import kotlinx.coroutines.delay

class FakeTmdbDataSource: TmdbDataSource {

    var delayInMs : Long = 5000


    fun setdelay(newDelayInMs: Long){
        delayInMs = newDelayInMs
    }

    override suspend fun getPopularMovies(): TmdbAPIResponse {
                return getSuccessResponse()
    }

    private suspend fun getSuccessResponse() : TmdbAPIResponse {
        delay(delayInMs)
        return TmdbAPIResponse.Success(getDefaultList())
    }

    private fun getDefaultList() : List<TmdbMovie> = listOf(
        TmdbMovie(id=532639, title="Pinocchio", posterUrl="https://image.tmdb.org/t/p/original/h32gl4a3QxQWNiNaR4Fc1uvLBkV.jpg", releaseDate="2022-09-07"),
        TmdbMovie(id=921360, title="Wire Room", posterUrl="https://image.tmdb.org/t/p/original/b9ykj4v8ykjRoGB7SpI1OuxblNU.jpg", releaseDate="2022-09-02"),
        TmdbMovie(id=773867, title="Seoul Vibe", posterUrl="https://image.tmdb.org/t/p/original/ffX0TL3uKerLXACkuZGWhAPMbAq.jpg", releaseDate="2022-08-26"),
        TmdbMovie(id=629176, title="Samaritan", posterUrl="https://image.tmdb.org/t/p/original/vwq5iboxYoaSpOmEQrhq9tHicq7.jpg", releaseDate="2022-08-25"),
        TmdbMovie(id=760741, title="Beast", posterUrl="https://image.tmdb.org/t/p/original/iRV0IB5xQeOymuGGUBarTecQVAl.jpg", releaseDate="2022-08-11"),
        TmdbMovie(id=985939, title="Fall", posterUrl="https://image.tmdb.org/t/p/original/9f5sIJEgvUpFv0ozfA6TurG4j22.jpg", releaseDate="2022-08-11"),
        TmdbMovie(id=766507, title="Prey", posterUrl="https://image.tmdb.org/t/p/original/ujr5pztc1oitbe7ViMUOilFaJ7s.jpg", releaseDate="2022-08-02"),
        TmdbMovie(id=760161, title="Orphan: First Kill", posterUrl="https://image.tmdb.org/t/p/original/wSqAXL1EHVJ3MOnJzMhUngc8gFs.jpg", releaseDate="2022-07-27")
    )

}