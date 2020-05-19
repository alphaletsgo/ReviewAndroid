package cn.isif.rxdemo.data;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubApi {
    @GET("users/{user}/repos")
    Observable<List<Repo>> repoList(@Path("user") String user);
}
