package com.example.rest.domain.imdb.scheduler;

import com.example.rest.domain.exception.APIException;
import com.example.rest.domain.imdb.service.impl.IMDbAsyncServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class RankingScheduler {

    private final IMDbAsyncServiceImpl imDbAsyncService;

    @Scheduled(cron = "0 7 23 * * ?")
    public void getTop250Movies() throws IOException, APIException {
        imDbAsyncService.getTop250Movies();
        log.info("Scheduled top 250 movies update");
    }

    @Scheduled(cron = "0 7 23 * * ?")
    public void getTop250TVs() throws IOException, APIException {
        imDbAsyncService.getTop250Tvs();
        log.info("Scheduled top 250 tvs update");
    }

    @Scheduled(cron = "0 11 23 * * ?")
    public void getMostPopularMovies() throws IOException, APIException {
        imDbAsyncService.getMostPopularMovies();
        log.info("Scheduled most popular movies update");
    }

    @Scheduled(cron = "0 7 23 * * ?")
    public void getMostPopularTVs() throws IOException, APIException {
        imDbAsyncService.getMostPopularTVs();
        log.info("Scheduled most popular Tvs update");
    }

}
